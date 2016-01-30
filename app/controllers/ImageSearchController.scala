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

/** 画像検索コントローラー */
class ImageSearchController extends BaseControllerTrait {
  /** キーワードを受け取り検索結果を返す */
  def search(keyword: String) = Action.async { request =>
    CustomSearchAdapter.imageUrls(keyword).map {
      case None => CUSTOM_SEARCH_ERROR_RESPONSE
      case Some(urls) => {
        Ok(JsonBuilder.imageUrls(urls))
      }
    }
  }
}
