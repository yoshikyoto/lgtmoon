package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import java.io.File

import play.api.mvc.{Action, AnyContent, InjectedController, Result}
import akka.actor.ActorRef
import image.{ImageRepository, ImageTemporaryStorage}
import actor.ImageGenerateMessage
import actor.ImageDownloadAndGenerateMessage
import javax.inject.{Inject, Named}
import play.api.libs.json.Json
import controllers.module.JsonConvert
import controllers.response.{ErrorResponse, ImageResponse}

/** 画像生成を行うコントロラー */
class ImageGenerateController @Inject() (
  imageRepository: ImageRepository,
  @Named("image-actor") imageActor: ActorRef,
  urlBuilder: UrlBuilder,
  imageTemporaryStorage: ImageTemporaryStorage
) extends InjectedController with JsonConvert {

  /** postされたurlから画像生成をする */
  def withUrl: Action[AnyContent] = Action.async { request =>
    val jsonOptional = request.body.asJson
    jsonOptional match {
      case None => Future(badRequestWith("Json形式でPOSTしてください"))
      case Some(json) => {
        (json \ "url").asOpt[String] match {
          case None => Future(badRequestWith("keywordパラメータは必須です"))
          case Some(url) => {
            // Logger.info(request.remoteAddress)
            // とりあえずURLだけ先に払い出して返す
            imageRepository.create() map {
              case None => internalServerErrorWith("データベース接続エラー")
              case Some(id) => {
                imageActor ! ImageDownloadAndGenerateMessage(id, url)
                Ok(Json.toJson(ImageResponse(
                  urlBuilder.image(id.toInt),
                  false
                )))
              }
            }
          }
        }
      }
    }
  }

  def withBinary: Action[AnyContent] = Action.async { request =>
    request.body.asMultipartFormData match {
      case None => Future(badRequestWith("multipart/form-data形式ではありません"))
      case Some(data) => {
        data.file("file") match {
          case None => Future(badRequestWith("fileパラメータは必須です"))
          case Some(file) => {
            // ファイルバリデーションはしていない（どうせimagemagickで弾かれるし）
            imageRepository.create().map {
              case None => internalServerErrorWith("データベース接続エラー")
              case Some(id) => {
                val lgtmImageUrl = urlBuilder.image(id.toInt)
                imageTemporaryStorage.save(id.toInt, file.ref)
                imageActor ! ImageGenerateMessage(id)
                Ok(Json.toJson(ImageResponse(lgtmImageUrl, false)))
              }
            }
          }
        }
      }
    }
  }

  def badRequestWith(message: String): Result = {
    BadRequest(Json.toJson(ErrorResponse(message)))
  }

  def internalServerErrorWith(message: String): Result = {
    InternalServerError(Json.toJson(ErrorResponse(message)))
  }
}
