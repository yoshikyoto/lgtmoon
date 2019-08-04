package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc.Action
import javax.inject.Inject
import image.ImageRepository
import play.api.libs.json.Json

/** LGTMoonが持っているimagesの情報を返すcontroller */
class ImageController @Inject() (
  imageRepository: ImageRepository,
  urlBuilder: UrlBuilder
) extends BaseControllerTrait {

  def recent = Action.async { request =>
    imageRepository.recentIds(20).map {
      case None => InternalServerError(JsonBuilder.error("サーバーエラー"))
      case Some(imageIds) => Ok(Json.obj(
        "images" -> Json.toJson(urlBuilder.images(imageIds))
      ))
    }
  }

  def random = Action.async { request =>
    imageRepository.randomIds(20).map {
      case None => InternalServerError("データベース接続エラー")
      case Some(imageIds) =>Ok(Json.obj(
        "images" -> Json.toJson(urlBuilder.images(imageIds))
      ))
    }
  }
}
