package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc.Action
import play.api.libs.json._
import javax.inject.Inject
import image.ImageRepository

/** LGTMoonが持っているimagesの情報を返すcontroller */
class ImageController @Inject() (
  val imageRepository: ImageRepository
) extends BaseControllerTrait {

  def recent = Action.async { request =>
    imageRepository.recentIds(20).map {
      case None => InternalServerError(JsonBuilder.error("サーバーエラー"))
      case Some(imageIds) => Ok(JsonBuilder.imagesByIds(imageIds))
    }
  }

  def random = Action.async { request =>
    imageRepository.randomIds(20).map {
      case None => InternalServerError("データベース接続エラー")
      case Some(ids) => Ok(JsonBuilder.imagesByIds(ids))
    }
  }
}
