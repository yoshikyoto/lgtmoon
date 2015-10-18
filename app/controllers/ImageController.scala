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
class ImageController extends Controller {
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
              case None =>
                InternalServerError(
                  JsonBuilder.internalServerError(
                    "データベースの接続に失敗しました"))
            }
          }
          case None => {
            println("no keyword")
            Future(
              BadRequest(
                JsonBuilder.badRequest(
                  "keywordが見つかりませんでした")))
          }
        }
      }
      case None => {
        Future(
          BadRequest(
            JsonBuilder.badRequest(
              "Json形式でPOSTしてください")))
      }
    }
  }

  def recent = Action.async { request =>
    ImageModel.images(20).map {
      case Some(images) => Ok(JsonBuilder.images(images))
      case _ => InternalServerError("")
    }
  }

}