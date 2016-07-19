package controllers

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.concurrent.ExecutionContext.Implicits.global
import domain.image.search.ImageSearchService
import domain.image.search._

/** 画像検索コントローラー */
class ImageSearchController extends BaseControllerTrait {
  implicit val imageWrites = (
    (__ \ "url").write[String] and
      (__ \ "mime").write[String]
  )(unlift(Image.unapply))

  /** キーワードを受け取り検索結果を返す */
  def search(keyword: String) = Action.async { request =>
    ImageSearchService.images(keyword).map {
      case None => CUSTOM_SEARCH_ERROR_RESPONSE
      case Some(images) => {
        Ok(Json.obj("images" -> Json.toJson(images)))
      }
    }
  }
}
