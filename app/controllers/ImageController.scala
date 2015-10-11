package controllers

import play.api._
import play.api.mvc._
import org.joda.time.DateTime
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import entities.OriginalPicture
import entities.OriginalPictureBuilder
import externals.rabbitmq.RabbitMqPublisher

/**
 * imagesのpost,getを行うためのcontroller
 */
class ImageController extends Controller {

  /**
   * 画像投稿
   */
  def post = Action { request =>
    val multiFormOpt = request.body.asMultipartFormData
    multiFormOpt match {
      case Some(multiForm) => {
        val file: FilePart[TemporaryFile] = multiForm.file("file").get
        val originalPicture: OriginalPicture = OriginalPictureBuilder.createSerializableObject(file)
        RabbitMqPublisher.publish(originalPicture)
        Ok("Ok")
      }
      case _ => Forbidden("アップロードでエラーが発生しました")
    }
  }

}
