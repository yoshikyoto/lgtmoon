package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import java.io.File
import play.api._
import play.api.mvc._
import akka.actor.ActorRef
import domain.image.ImageRepository
import actor.ImageGenerateMessage
import actor.ImageDownloadAndGenerateMessage
import infra.util.UrlBuilder
import infra.datasource.ImageStorage
import javax.inject.{Inject, Named}
import play.api.libs.json.Json
import controllers.module.JsonConvert
import controllers.response.ErrorResponse

/** 画像生成を行うコントロラー */
class ImageGenerateController @Inject() (
  imageRepository: ImageRepository,
  @Named("image-actor") imageActor: ActorRef
) extends BaseControllerTrait with JsonConvert {

  /** postされたurlから画像生成をする */
  def withUrl = Action.async { request =>
    val jsonOpt = request.body.asJson
    jsonOpt match {
      case None => Future(BadRequest(Json.toJson(ErrorResponse("Json形式でPOSTしてください"))))
      case Some(json) => {
          (json \ "url").asOpt[String] match {
            case None => Future(PARAMETER_KEYWORD_NOT_FOUND_RESPONSE)
            case Some(url)  => {
              val xForwardedFor = request.remoteAddress
              Logger.info(xForwardedFor)
              // とりあえずURLだけ先に払い出して返す
              imageRepository.create() map {
                case None => DATABASE_CONNECTION_ERROR_RESPONSE
                case Some(id) => {
                  val lgtmUrl = UrlBuilder.imageUrl(id.toString)
                  imageActor ! ImageDownloadAndGenerateMessage(id, url)
                  Ok(JsonBuilder.imageUrl(lgtmUrl))
                }
              }
            }
          }
      }
    }
  }

  def withBinary = Action.async { request =>
    request.body.asMultipartFormData match {
      case None => Future(INVALID_IMAGE_RESPONSE)
      case Some(data) => {
        data.file("file") match {
          case None => Future(INVALID_IMAGE_RESPONSE)
          case Some(file) => {
            // TODO file validation
            imageRepository.create().map {
              case None => DATABASE_CONNECTION_ERROR_RESPONSE
              case Some(id) => {
                val lgtmImageUrl = UrlBuilder.imageUrl(id.toString)
                val tmpPath = ImageStorage.getTmpPath(id.toString)
                file.ref.moveTo(new File(tmpPath))
                imageActor ! ImageGenerateMessage(id)
                Ok(JsonBuilder.imageUrl(lgtmImageUrl))
              }
            }
          }
        }
      }
    }
  }
}
