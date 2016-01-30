package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api._
import play.api.mvc._
import org.joda.time.DateTime
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import play.api.libs.json._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import akka.actor.Props
import externals.rabbitmq.RabbitMqPublisher
import externals.google.CustomSearchAdapter
import models.ImageModel
import actors.ImageActor
import actors.ImageGenerateMessage
import utils._

/**
 * imagesのpost,getを行うためのcontroller
 */
class ImageController extends BaseControllerTrait {
  val imageActor = Akka.system.actorOf(Props(new ImageActor()))

  def postKeyword = Action.async { request =>
    val jsonOpt = request.body.asJson
    jsonOpt match {
      case Some(json) => {
        val keywordOpt = (json \ "keyword").asOpt[String]
        keywordOpt match {
          case Some(keyword) => {
            // とりあえずURLだけ先に払い出して返す
            ImageModel.create() map {
              case Some(id) => {
                val url = UrlBuilder.imageUrl(id.toString)
                // 動画のダウンロードと変換を要請
                imageActor ! ImageGenerateMessage(id, keyword)
                Ok(JsonBuilder.imageUrl(url))
              }
              case None => DATABASE_CONNECTION_ERROR_RESPONSE
            }
          }
          case None => Future(PARAMETER_KEYWORD_NOT_FOUND_RESPONSE)
        }
      }
      case None => Future(NOT_JSON_RESPONSE)
    }
  }

  def recent = Action.async { request =>
    ImageModel.images(20).map {
      case Some(images) => Ok(JsonBuilder.images(images))
      case _ => InternalServerError("")
    }
  }

}
