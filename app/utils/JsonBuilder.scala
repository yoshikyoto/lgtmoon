package utils

import play.api.libs.json._
import repositories.Tables.ImageRow

object JsonBuilder {
  def imageUrl(url: String): String = {
    Json.stringify(JsObject(Seq("url" -> JsString(url))))
  }

  def images(images: Seq[ImageRow]): String = {
    val urls = images.map { image =>
      JsObject(Seq("url" -> JsString(UrlBuilder.imageUrl(image.id.toString))))
    }
    Json.stringify(JsObject(Seq("images" -> JsArray(urls))))
  }

  def badRequest(message: String = "リクエストが不正です"): String = {
    Json.stringify(JsObject(Seq(
      "code" -> JsNumber(400),
      "status" -> JsString("Bad Request"),
      "message" -> JsString(message))))
  }

  def internalServerError(message: String): String = {
    Json.stringify(JsObject(Seq(
      "status" -> JsString("Internal Server Error"))))
  }
}
