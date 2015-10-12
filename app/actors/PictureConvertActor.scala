package actors

import scala.language.postfixOps
import akka.actor.Actor
import akka.event.Logging
import entities.OriginalPicture
import externals.imagemagick.ImageMagickAdapter

/**
 *  RabbitMQにたまっているタスクを取得して画像変換を行うメッセージ
 */
case class ConvertPictureMessage(originalPicture: OriginalPicture)

/**
 *  画像を変換するアクター
 */
class PictureConvertActor extends Actor {
  val log = Logging(context.system, this)

  /**
   * メッセージを受けたとき、それがConvertPictureなら画像の変換を行う
   */
  override def receive: Receive = {
    case ConvertPictureMessage(originalPicture) => {
      log.info(s"画像の変換を開始します. ファイル名:${originalPicture.fileName}")
      val converter = new ImageMagickAdapter(originalPicture)
      converter.convert()
      log.info(s"画像の変換が終わりました. ファイル名:${originalPicture.fileName}")
      // TODO 変換された画像の保存をする
      log.info(s"変換した画像の保存が終わりました. ファイル名:${originalPicture.fileName}")
    }
  }

}
