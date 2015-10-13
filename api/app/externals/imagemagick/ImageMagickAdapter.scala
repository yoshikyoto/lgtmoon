package externals.imagemagick

import java.io._
import play.api.Play
import org.im4java.core._
import entities.OriginalPicture
// FIXME FileStorageに依存しないようにしたい？
import utils.FileStorage

/**
 * imagemagickを利用するためのAdapter
 */
class ImageMagickAdapter(
  val originalPicture: OriginalPicture) {
  val imagemagickDir = Play.current.configuration.getString("imagemagick.dir").get
  val fontDir = Play.current.configuration.getString("imagemagick.fontDir").get

  /**
   * 画像の変換を行う
   */
  def convert(originalPicturePath: String) {
    val operation = new IMOperation()
    operation.addImage(originalPicturePath)
    operation.geometry(320, 320)
    operation.gravity("center")
    operation.font(font("AvenirNext.ttc"))
    operation.pointsize(60)
    println((font("AvenirNext.ttc")))
    operation.stroke("none")
    operation.fill("white")
    operation.annotate(0, 0, 0, 0, "LGTM")
    val convertedPicturePath = FileStorage.convertedPicturePath(originalPicture.fileName)
    operation.addImage(convertedPicturePath)
    val command = new ConvertCmd()
    command.setSearchPath(imagemagickDir)
    command.run(operation)
  }

  /**
   * フォントの絶対パスを取得する
   */
  def font(fontName: String): String = {
    fontDir + "/" + fontName
  }

}
