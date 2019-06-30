package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc.Action
import play.api.libs.json._
import javax.inject.Inject
import domain.image.ImageRepository

/** LGTMoonが持っているimagesの情報を返すcontroller */
class ImageController @Inject() (val imageRepository: ImageRepository)
    extends BaseControllerTrait {

  def recent = Action.async { request =>
    imageRepository.imageIds(20).map {
      case None => InternalServerError(JsonBuilder.error("サーバーエラー"))
      case Some(imageIds) => Ok(JsonBuilder.imagesByIds(imageIds.map(_.toInt)))
    }
  }

  def random = Action.async { request =>
    imageRepository.randomIds().map {
      case None => InternalServerError("データベース接続エラー")
      case Some(ids) => Ok(JsonBuilder.imagesByIds(ids))
    }
  }
}
