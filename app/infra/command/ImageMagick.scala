package infra.command

import play.api.Play
import org.im4java.core._

object ImageMagick extends ImageMagickTrait {
}

trait ImageMagickTrait {
  /** imagemagickのバイナリのディレクトリ */
  val imagemagickDir = Play.current.configuration.getString("imagemagick.dir").get

  def command(operation: IMOperation) {
    val command = new ConvertCmd()
    command.setSearchPath(imagemagickDir)
    command.run(operation)
  }
}
