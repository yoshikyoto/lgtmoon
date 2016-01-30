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

/** LGTMoonが持っているimagesの情報を返すcontroller */
class ImageController extends BaseControllerTrait {
  def recent = Action.async { request =>
    ImageModel.images(20).map {
      case _ => InternalServerError("")
      case Some(images) => Ok(JsonBuilder.images(images))
    }
  }
}
