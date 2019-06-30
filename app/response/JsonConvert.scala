package response

import play.api.libs.json._
import play.api.libs.functional.syntax._

trait JsonConvert {
  implicit val imageWrites = Json.writes[Image]
  implicit val errorWirtes = Json.writes[Error]

  def urlsToImages(urls: Seq[String]): Seq[Image] = urls.map(url => Image(url))
}
