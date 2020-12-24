package actor

import akka.actor.Actor
import image.{ImageConverter, ImageRepository, SourceImage}
import com.google.inject.Inject
import storage.ImageTemporaryStorage

// TODO idをIntにする
case class ImageGenerateMessage(id: Long)

case class ImageDownloadAndGenerateMessage(id: Long, url: String)

/**
 * 画像idとURLを受け
 * 1. 画像をダウンロードし、
 * 2. その画像を変換キューに乗せる
 */
class ImageActor @Inject() (
  imageRepository: ImageRepository,
  imageConverter: ImageConverter,
  temporaryStorage: ImageTemporaryStorage
) extends Actor {

  override def receive: Receive = {
    case ImageDownloadAndGenerateMessage(id, url) => {
      val sourceImage = temporaryStorage.save(id.toInt, url)
      val convertedImage = imageConverter.convert(sourceImage)
      // convertされた画像をバイナリで取得してDBに入れる
      val bin = temporaryStorage.convertedBinary(convertedImage)
      imageRepository.makeAvailable(id.toInt, bin)
    }

    case ImageGenerateMessage(id) => {
      val sourceImage: SourceImage = temporaryStorage.sourceImage(id.toInt)
      val convertedImage = imageConverter.convert(sourceImage)
      // convertされた画像をバイナリで取得してDBに入れる
      val bin = temporaryStorage.convertedBinary(convertedImage)
      imageRepository.makeAvailable(id.toInt, bin)
    }
  }

}
