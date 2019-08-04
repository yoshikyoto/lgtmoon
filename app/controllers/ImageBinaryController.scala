package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc.{
  Action,
  AnyContent,
  InjectedController,
  ResponseHeader,
  Result
}
import play.api.http.HttpEntity
import image.ImageRepository
import akka.util.ByteString
import javax.inject.Inject

/** 画像のバイナリデータを返すコントローラー */
class ImageBinaryController @Inject() (
  val imageRepository: ImageRepository
) extends InjectedController {
  /** idを受け取り画像のバイナリデータを返す */
  def image(id: Int): Action[AnyContent]  = Action.async { request =>
    imageRepository.binary(id).map {
      case Some(bin) => {
        imageOk(HttpEntity.Strict(
            ByteString.fromArray(bin),
            Some("image/png")
          )
        ).withHeaders(
          // 1週間
          "Cache-Control" -> "max-age=604800"
        )
      }
      case None => NotFound("Not Found")
    }
  }

  def imageOk(body: HttpEntity.Strict): Result = {
    Result(
      header = ResponseHeader(200),
      body = body
    )
  }
}
