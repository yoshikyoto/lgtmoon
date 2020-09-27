package image

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[command.ImageMagick])
trait ImageConverter {
  def convert(srcPath: String, destPath: String): Unit
}
