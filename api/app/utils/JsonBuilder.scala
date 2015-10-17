package utils

import play.api.libs.json._
import constants.ResponseStatus
import repositories.Tables.ImageRow
import utils.UrlBuilder

object JsonBuilder {
  def imageUrlJson(url: String): JsObject = {
    JsObject(Seq("path" -> JsString(url)))
  }

  def images(images: Seq[ImageRow]): JsObject = {
    val urls = images.map { image =>
      JsObject(Seq("url" -> JsString(UrlBuilder.imageUrl(image.id.toString))))
    }
    JsObject(Seq("images" -> JsArray(urls)))
  }

  def badRequest(message: String): JsObject = {
    JsObject(Seq(
      "status" -> JsString(ResponseStatus.BAD_REQUEST)))
  }
}

object JsonStringBuilder {
  def imageUrlJson(url: String): String = {
    Json.stringify(JsonBuilder.imageUrlJson(url))
  }

  def images(images: Seq[ImageRow]): String = {
    Json.stringify(JsonBuilder.images(images))
  }
}
