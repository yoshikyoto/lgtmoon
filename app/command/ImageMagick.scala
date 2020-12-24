package command

import file.FilePath
import image.{ConvertedImage, ImageConverter, SourceImage}
import play.api.Configuration
import org.im4java.core._
import com.google.inject.Inject
import storage.ImageTemporaryStorage


/** imagemagickを使ってlgtmoon画像を作ってくれるやつ */
class ImageMagick @Inject() (
  val font: FilePath,
  val config: Configuration,
  val temporaryStorage: ImageTemporaryStorage
) extends ImageConverter {
  /**
   * 画像の変換を行う
   */
  def convert(sourceImage: SourceImage): ConvertedImage = {
    val operation = new IMOperation()
    operation.addImage(sourceImage.path)
    operation.coalesce()
    operation.geometry(400, 400)
    // LGTMの文字
    operation.gravity("center")
    operation.font(font.fontPath("Aileron-Black.otf"))
    operation.pointsize(72)
    operation.stroke("none")
    operation.fill("white")
    operation.kerning(12)
    operation.strokewidth(5)
    operation.annotate(0, 0, 0, 0, "LGTM")
    // Looks Good To Me の文字
    operation.font(font.fontPath("Aileron-Regular.otf"))
    operation.pointsize(11)
    operation.kerning(6)
    operation.annotate(0, 0, 0, 52, "Looks  Good  To  Me")

    // ここで直接取得した PATH に保存している
    val destPath = temporaryStorage.destPath(sourceImage.id)
    operation.addImage(destPath)
    // コマンド実行
    execute(operation)
    new ConvertedImage(sourceImage.id, destPath)
  }

  def execute(operation: IMOperation): Unit = {
    val command = new ConvertCmd()
    command.run(operation)
  }
}
