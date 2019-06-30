package controllers

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.concurrent.ExecutionContext.Implicits.global
import domain.image.search.ImageSearchService
import domain.image.search._
import scala.concurrent.Future
import javax.inject.Inject
import image.ImageSearcher

/** 画像検索コントローラー */
class ImageSearchController @Inject() (
  imageSearcher: ImageSearcher
) extends BaseControllerTrait {

  /** キーワードを受け取り検索結果を返す */
  def search(keyword: String) = Action.async { request =>
    imageSearcher.urls(keyword) map {
      case None => CUSTOM_SEARCH_ERROR_RESPONSE
      case Some(urls) => Ok(Json.obj("images" -> Json.toJson(urls)))
    }
  }

}
