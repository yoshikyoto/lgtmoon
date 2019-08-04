package actor

import akka.actor.Actor
import image.ImageConverter
import image.ImageRepository
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
  imageTemporaryStorage: ImageTemporaryStorage
) extends Actor {

  override def receive: Receive = {
    case ImageDownloadAndGenerateMessage(id, url) => {
      val srcImagePath = imageTemporaryStorage.save(id.toInt, url)
      val destImagePath = imageTemporaryStorage.destPath(id.toInt)
      imageConverter.convert(srcImagePath, destImagePath)
      // convertされた画像をバイナリで取得してDBに入れる
      val bin = imageTemporaryStorage.convertedBinary(id.toInt)
      imageRepository.makeAvailable(id.toInt, bin)
    }

    case ImageGenerateMessage(id) => {
      val sourcePath = imageTemporaryStorage.srcPath(id.toInt)
      val destPath = imageTemporaryStorage.destPath(id.toInt)
      imageConverter.convert(sourcePath, destPath)
      // convertされた画像をバイナリで取得してDBに入れる
      val bin = imageTemporaryStorage.convertedBinary(id.toInt)
      imageRepository.makeAvailable(id.toInt, bin)
    }
  }

}
