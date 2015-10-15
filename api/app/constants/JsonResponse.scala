package constants

import play.api.libs.json._

object JsonResponse {
  val BAD_REQUEST = JsObject(Seq("status" -> JsString("Bad Request")))
  val INTERNAL_SERVER_ERROR = JsObject(Seq("status" -> JsString("Internal Server Error")))
}

object JsonResponseString {
  val BAD_REQUEST = Json.stringify(JsonResponse.BAD_REQUEST)
  val INTERNAL_SERVER_ERROR = Json.stringify(JsonResponse.INTERNAL_SERVER_ERROR)
}
