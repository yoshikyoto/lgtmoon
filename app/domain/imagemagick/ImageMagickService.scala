package domain.imagemagick

import play.api.Configuration
import java.io._
import play.api.Play
import org.im4java.core._
import file.Font
import infra.command.ImageMagick
import javax.inject.Inject

/** imagemagickを使ってlgtmoon画像を作ってくれるやつ */
class ImageMagickService @Inject() (
  val font: Font,
  val config: Configuration
) {
  val imagemagickDir = config.getString("imagemagick.dir").get


  /**
   * 画像の変換を行う
   *
   * @param beforePath: String 生成前の画像のPATH
   * @param afterPath: String 生成後の画像のPATH
   */
  def convert(
    beforePath: String,
    afterPath: String) {
    val operation = new IMOperation()
    operation.addImage(beforePath)
    operation.coalesce()
    operation.geometry(400, 400)
    // LGTMの文字
    operation.gravity("center")
    operation.font(font.path("Aileron-Black.otf"))
    operation.pointsize(72)
    operation.stroke("none")
    operation.fill("white")
    operation.kerning(12);
    operation.strokewidth(5);
    operation.annotate(0, 0, 0, 0, "LGTM")
    // Looks Good To Me の文字
    operation.font(font.path("Aileron-Regular.otf"))
    operation.pointsize(11)
    operation.kerning(6);
    operation.annotate(0, 0, 0, 52, "Looks  Good  To  Me")
    operation.addImage(afterPath)
    // コマンド実行
    execute(operation)
  }

  def execute(operation: IMOperation) {
    val command = new ConvertCmd()
    command.setSearchPath(imagemagickDir)
    command.run(operation)
  }
}
