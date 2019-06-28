package controllers

import play.api.libs.json._
import repositories.Tables.ImageRow
import infra.util.UrlBuilder

object JsonBuilder {
  def imageUrl(url: String): String = {
    Json.stringify(JsObject(Seq("url" -> JsString(url))))
  }

  /**
   * @param urls: Sea[String]
   * @return String urlsをJson形式の文字列にしたもの
   */
  def imageUrls(urls: Seq[String]): String = {
    val urlsJson = urls.map(url => JsObject(Seq("url" -> JsString(url))))
    Json.stringify(JsObject(Seq("images" -> JsArray(urlsJson))))
  }

  def images(images: Seq[ImageRow]): String = {
    val urls = images.map { image =>
      JsObject(Seq("url" -> JsString(UrlBuilder.imageUrl(image.id.toString))))
    }
    Json.stringify(JsObject(Seq("images" -> JsArray(urls))))
  }

  def imagesByIds(ids: Seq[Int]): String = {
    val urls = ids.map { id =>
      JsObject(Seq("url" -> JsString(UrlBuilder.imageUrl(id.toString))))
    }
    Json.stringify(JsObject(Seq("images" -> JsArray(urls))))
  }

  def error(message: String): String = {
    Json.stringify(JsObject(Seq("message" -> JsString(message))))
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
