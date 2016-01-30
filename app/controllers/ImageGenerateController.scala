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

/** 画像生成を行うコントロラー */
class ImageGenerateController extends BaseControllerTrait {
  /** 非同期で画像生成をするためのActor */
  val imageActor = Akka.system.actorOf(Props(new ImageActor()))

  /** postされたurlから画像生成をする */
  def withUrl = Action.async { request =>
    val jsonOpt = request.body.asJson
    jsonOpt match {
      case None => Future(NOT_JSON_RESPONSE)
      case Some(json) => {
          (json \ "url").asOpt[String] match {
            case None => Future(PARAMETER_KEYWORD_NOT_FOUND_RESPONSE)
            case Some(url)  => {
              // とりあえずURLだけ先に払い出して返す
              ImageModel.create() map {
                case None => DATABASE_CONNECTION_ERROR_RESPONSE
                case Some(id) => {
                  val lgtmUrl = UrlBuilder.imageUrl(id.toString)
                  imageActor ! ImageGenerateMessage(id, url)
                  Ok(JsonBuilder.imageUrl(lgtmUrl))
                }
              }
            }
          }
      }
    }
  }
}
