package controllers

import play.api.mvc.{Action, AnyContent, InjectedController, Result}
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.Inject
import image.ImageSearcher
import controllers.response.ImageResponseFactory
import controllers.response.ErrorResponse
import controllers.module.JsonConvert


/** 画像検索コントローラー */
class ImageSearchController @Inject() (
  imageSearcher: ImageSearcher,
  imageResponseFactory: ImageResponseFactory
) extends InjectedController with JsonConvert {

  /** キーワードを受け取り検索結果を返す */
  def search(keyword: String): Action[AnyContent] = Action.async { request =>
    imageSearcher.urls(keyword) map {
      case None => internalServerErrorWith("検索でサーバーエラーが発生しました")
      case Some(urls) => Ok(Json.obj(
        "images" -> Json.toJson(imageResponseFactory.create(urls))
      ))
    }
  }

  def internalServerErrorWith(message: String): Result = {
    InternalServerError(Json.toJson(ErrorResponse(message)))
  }
}
