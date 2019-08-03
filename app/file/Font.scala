package file

import play.api.Configuration
import javax.inject.Inject

class Font @Inject() (
  val config: Configuration
) {
  /** フォントファイルがあるディレクトリ */
  val fontDir = config.getString("imagemagick.fontDir").get

  def path(fontName: String): String = {
    fontDir + "/" + fontName
  }
}
