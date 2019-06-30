package response

import play.api.libs.json._
import play.api.libs.functional.syntax._

trait JsonConvert {
  implicit val jsonWrites = Json.writes[Image]
}
