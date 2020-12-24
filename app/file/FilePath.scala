package file

import play.api.Configuration
import com.google.inject.Inject

class FilePath @Inject() (
  val config: Configuration
) {
  /** フォントファイルがあるディレクトリ */
  val fontDir: String = config.get[String]("imagemagick.fontDir")
  val srcImageDir: String = config.get[String]("storage.image.src.dir")
  val destImageDir: String = config.get[String]("storage.image.dest.dir")

  def fontPath(name: String): String = fontDir + "/" + name
  def sourceImagePath(id: Int): String = srcImageDir + "/" + id
  def convertedImagePath(id: Int): String = destImageDir + "/" + id
}
