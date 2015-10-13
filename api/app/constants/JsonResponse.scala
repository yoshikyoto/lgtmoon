package constants

import play.api.libs.json._

object JsonResponse {
  val BAD_REQUEST = JsObject(Seq("status" -> JsString("Bad Request")))
}

object JsonResponseString {
  val BAD_REQUEST = Json.stringify(JsonResponse.BAD_REQUEST)
}
