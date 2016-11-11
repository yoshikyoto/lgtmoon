package infra.datasource

import java.io._
import play.api.Play

object FontStorage extends FontStorageTrait {
}

trait FontStorageTrait {
  /** フォントファイルがあるディレクトリ */
  val fontDir = Play.current.configuration.getString("imagemagick.fontDir").get

  def path(fontName: String): String = {
    fontDir + "/" + fontName
  }
}
