package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc.Action
import javax.inject.Inject
import image.ImageRepository
import play.api.libs.json.Json
import controllers.response.ImageResponseFactory
import controllers.module.JsonConvert
import controllers.response.ImageResponse
import controllers.response.ErrorResponse

/** LGTMoonが持っているimagesの情報を返すcontroller */
class ImageController @Inject() (
  imageRepository: ImageRepository,
  urlBuilder: UrlBuilder,
  imageResponseFactory: ImageResponseFactory
) extends BaseControllerTrait with JsonConvert {

  def recent = Action.async { request =>
    imageRepository.recentIds(20).map {
      case None => InternalServerError(Json.toJson(ErrorResponse("データベース接続エラー")))
      case Some(imageIds) => Ok(Json.obj(
        "images" -> Json.toJson(toImageResponses(imageIds))
      ))
    }
  }

  def random = Action.async { request =>
    imageRepository.randomIds(20).map {
      case None => InternalServerError(Json.toJson(ErrorResponse("データベース接続エラー")))
      case Some(imageIds) =>Ok(Json.obj(
        "images" -> Json.toJson(toImageResponses(imageIds))
      ))
    }
  }

  /** imageのIDの配列を受けとって ImageResponse の配列に変換する */
  def toImageResponses(imageIds: Seq[Int]): Seq[ImageResponse] = {
    imageResponseFactory.create(urlBuilder.images(imageIds))
  }
}
