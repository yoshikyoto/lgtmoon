package controllers.module

import play.api.libs.json.Json
import controllers.response.{
  ImageResponse,
  ErrorResponse
}

trait JsonConvert {
  implicit val imageResponseWrites = Json.writes[ImageResponse]
  implicit val errorResponseWirtes = Json.writes[ErrorResponse]

  def urlsToImages(urls: Seq[String]): Seq[ImageResponse] = urls.map(url => ImageResponse(url))
}
