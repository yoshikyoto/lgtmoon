package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api._
import play.api.mvc._
import play.api.libs.iteratee.Enumerator
import java.io.ByteArrayInputStream
import domain.image.ImageRepository

/** 画像のバイナリデータを返すコントローラー */
class ImageBinaryController extends Controller {
  /** idを受け取り画像のバイナリデータを返す */
  def image(id: Long)  = Action.async { request =>
    ImageRepository.image(id).map {
      case Some(image) => {
        image.bin match {
          case Some(bin) => {
            println(bin)
            Result(
              header = ResponseHeader(200),
              body = Enumerator.fromStream(new ByteArrayInputStream(bin))
            ).withHeaders(CONTENT_TYPE -> "image/png")
          }
          case None => NotFound("Not Found")
        }
      }
      case None => NotFound("Not Found")
    }
  }
}
