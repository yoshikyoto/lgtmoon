package controllers

import play.api.mvc.Action
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import javax.inject.Inject
import image.ImageSearcher
import response.Image
import response.JsonConvert

/** 画像検索コントローラー */
class ImageSearchController @Inject() (
  imageSearcher: ImageSearcher
) extends BaseControllerTrait with JsonConvert {

  /** キーワードを受け取り検索結果を返す */
  def search(keyword: String) = Action.async { request =>
    imageSearcher.urls(keyword) map {
      case None => CUSTOM_SEARCH_ERROR_RESPONSE
      case Some(urls) => Ok(Json.obj("images" -> Json.toJson(urlsToImages(urls))))
    }
  }

}
