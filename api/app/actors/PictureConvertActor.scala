package actors

import scala.language.postfixOps
import akka.actor.Actor
import akka.event.Logging
import entities.OriginalPicture
import externals.imagemagick.ImageMagickAdapter
import models.PictureModel
import utils.FileStorage

/**
 *  RabbitMQにたまっているタスクを取得して画像変換を行うメッセージ
 */
case class ConvertPictureMessage(originalPicture: OriginalPicture)

/**
 *  画像を変換するアクター
 */
class PictureConvertActor extends Actor {

  /**
   * メッセージを受けたとき、それがConvertPictureなら画像の変換を行い、
   * データベースにストアする
   */
  override def receive: Receive = {
    case ConvertPictureMessage(originalPicture) => {
      val originalPicturePath = FileStorage.saveOriginalPicture(originalPicture)
      val converter = new ImageMagickAdapter(originalPicture)
      converter.convert(originalPicturePath)
      PictureModel.insert(originalPicture)
    }
  }

}
