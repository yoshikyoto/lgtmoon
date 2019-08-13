package file

import play.api.Configuration
import com.google.inject.Inject

class Font @Inject() (
  val config: Configuration
) {
  /** フォントファイルがあるディレクトリ */
  val fontDir = config.get[String]("imagemagick.fontDir")

  def path(fontName: String): String = {
    fontDir + "/" + fontName
  }
}
