package command

import file.Font
import image.ImageConverter
import play.api.Configuration
import org.im4java.core._
import com.google.inject.Inject


/** imagemagickを使ってlgtmoon画像を作ってくれるやつ */
class ImageMagick @Inject() (
  val font: Font,
  val config: Configuration
) extends ImageConverter {

  /**
    * 画像の変換を行う
    *
    * @param srcPath 生成前の画像のPATH
    * @param destPath 生成後の画像のPATH
    */
  def convert(srcPath: String, destPath: String): Unit = {
    val operation = new IMOperation()
    operation.addImage(srcPath)
    operation.coalesce()
    operation.geometry(400, 400)
    // LGTMの文字
    operation.gravity("center")
    operation.font(font.path("Aileron-Black.otf"))
    operation.pointsize(72)
    operation.stroke("none")
    operation.fill("white")
    operation.kerning(12)
    operation.strokewidth(5)
    operation.annotate(0, 0, 0, 0, "LGTM")
    // Looks Good To Me の文字
    operation.font(font.path("Aileron-Regular.otf"))
    operation.pointsize(11)
    operation.kerning(6)
    operation.annotate(0, 0, 0, 52, "Looks  Good  To  Me")
    operation.addImage(destPath)
    // コマンド実行
    execute(operation)
  }

  def execute(operation: IMOperation): Unit = {
    val command = new ConvertCmd()
    command.run(operation)
  }
}
