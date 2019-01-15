package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api._
import play.mvc.Controller
import play.api.mvc.Results.NotFound
import play.api.mvc.{Action, Result, ResponseHeader}
import play.api.libs.iteratee.Enumerator
import play.api.http.HttpEntity
import java.io.ByteArrayInputStream
import domain.image.ImageRepository
import akka.util.ByteString

/** 画像のバイナリデータを返すコントローラー */
class ImageBinaryController extends Controller {
  /** idを受け取り画像のバイナリデータを返す */
  def image(id: Long)  = Action.async { request =>
    ImageRepository.image(id).map {
      case Some(image) => {
        image.bin match {
          case Some(bin) => {
            /** bin: Array[Byte] */
            Result(
              header = ResponseHeader(200),
              body =  HttpEntity.Strict(ByteString.fromArray(bin), Some("image/png"))
            ).withHeaders(
              "Cache-Control" -> "max-age=3600"
            )
          }
          case None => NotFound("Not Found")
        }
      }
      case None => NotFound("Not Found")
    }
  }
}
