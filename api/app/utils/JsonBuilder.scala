package utils

import play.api.libs.json._

object JsonBuilder {
  def imageUrlJson(url: String): JsObject = {
    JsObject(Seq("path" -> JsString(url)))
  }
}

object JsonStringBuilder {
  def imageUrlJson(url: String): String = {
    Json.stringify(JsonBuilder.imageUrlJson(url))
  }
}
