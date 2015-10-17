package utils

import play.api.libs.json._
import constants.ResponseStatus
import repositories.Tables.ImageRow

object JsonBuilder {
  def imageUrlJson(url: String): JsObject = {
    JsObject(Seq("path" -> JsString(url)))
  }

  def urls(images: Seq[ImageRow]): JsObject = {
    
    JsObject(
      Seq("urls" -> JsString("hoge")))
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

  def urls(images: Seq[ImageRow]): String = {
    Json.stringify(JsonBuilder.urls(images))
  }
}
