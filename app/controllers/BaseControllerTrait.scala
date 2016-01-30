package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api._
import play.api.mvc._
import org.joda.time.DateTime
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import play.api.libs.json._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import akka.actor.Props
import externals.google.CustomSearchAdapter
import utils._

trait BaseControllerTrait extends Controller {
  /** Json以外のデータ形式が来た時のレスポンス */
  val NOT_JSON_RESPONSE =
    BadRequest(JsonBuilder.badRequest("Json形式でPOSTしてください"))

  /** jsonにkeywordが見つからなかった時のレスポンス */
  val PARAMETER_KEYWORD_NOT_FOUND_RESPONSE =
    BadRequest(JsonBuilder.badRequest("keywordパラメータが見つかりませんでした"))
  
  /** データベースの接続時にエラーが出た時のレスポンス */
  val DATABASE_CONNECTION_ERROR_RESPONSE =
    InternalServerError(JsonBuilder.internalServerError("データベースの接続に失敗しました"))
}
