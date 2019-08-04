package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import play.mvc.Controller
import play.api.mvc.Results.NotFound
import play.api.mvc.{Action, Result, ResponseHeader}
import play.api.http.HttpEntity
import image.ImageRepository
import akka.util.ByteString
import javax.inject.Inject

/** 画像のバイナリデータを返すコントローラー */
class ImageBinaryController @Inject() (
  val imageRepository: ImageRepository
) extends Controller {
  /** idを受け取り画像のバイナリデータを返す */
  def image(id: Int)  = Action.async { request =>
    imageRepository.binary(id).map {
      case Some(bin) => {
        /** bin: Array[Byte] */
        Result(
          header = ResponseHeader(200),
          body =  HttpEntity.Strict(ByteString.fromArray(bin), Some("image/png"))
        ).withHeaders(
          // 1週間
          "Cache-Control" -> "max-age=604800"
        )
      }
      case None => NotFound("Not Found")
    }
  }
}
