package image

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[command.ImageMagick])
trait ImageConverter {
  def convert(sourceImage: SourceImage): ConvertedImage
}
