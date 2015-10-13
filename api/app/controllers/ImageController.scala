package controllers

import play.api._
import play.api.mvc._
import org.joda.time.DateTime
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import play.api.libs.json._
import entities.OriginalPicture
import entities.OriginalPictureBuilder
import externals.rabbitmq.RabbitMqPublisher
import constants.JsonResponseString
import utils.RandomString

/**
 * imagesのpost,getを行うためのcontroller
 */
class ImageController extends Controller {

  /**
   * 画像投稿
   * 
   * FIXME 画像サイズなどのバリデーション
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

  def postKeyword = Action { request =>
    val jsonOpt = request.body.asJson
    jsonOpt match {
      case Some(json) => {
        val keywordOpt = (json \ "keyword").asOpt[String]
        keywordOpt match {
          case Some(keyword) => {
            val fileName = RandomString.generate()
            Ok(fileName)
          }
          case None => BadRequest(JsonResponseString.BAD_REQUEST)
        }
      }
      case None => BadRequest(JsonResponseString.BAD_REQUEST)
    }
  }

}
