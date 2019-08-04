package controllers

import play.api.mvc.{Action, AnyContent, Controller}
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import image.ImageSearcher
import controllers.response.ImageResponseFactory
import controllers.response.ErrorResponse
import controllers.module.JsonConvert


/** 画像検索コントローラー */
class ImageSearchController @Inject() (
  imageSearcher: ImageSearcher,
  imageResponseFactory: ImageResponseFactory
) extends Controller with JsonConvert {

  /** キーワードを受け取り検索結果を返す */
  def search(keyword: String): Action[AnyContent] = Action.async { request =>
    imageSearcher.urls(keyword) map {
      case None => InternalServerError(Json.toJson(ErrorResponse("Server Error")))
      case Some(urls) => Ok(Json.obj(
        "images" -> Json.toJson(imageResponseFactory.create(urls))
      ))
    }
  }
}
