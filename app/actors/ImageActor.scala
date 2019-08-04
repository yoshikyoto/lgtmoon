package actor

import akka.actor.Actor
import infra.datasource.ImageStorage
import image.ImageConverter
import domain.image.ImageRepository
import javax.inject.Inject

case class ImageGenerateMessage(id: Long)

case class ImageDownloadAndGenerateMessage(id: Long, url: String)

/**
 * 画像idとURLを受け
 * 1. 画像をダウンロードし、
 * 2. その画像を変換キューに乗せる
 */
class ImageActor @Inject() (
  val imageRepository: ImageRepository,
  val imageConverter: ImageConverter
) extends Actor {
  val downloadDir = "/tmp"
  val destDir = "/tmp"
  val imageStorage = ImageStorage

  override def receive: Receive = {
    case ImageDownloadAndGenerateMessage(id, url) => {
      val downloadPath = downloadDir + "/" + id
      val convertedPath = destDir + "/" + id
      imageStorage.download(url, downloadPath)
      imageConverter.convert(downloadPath, convertedPath)
      // convertされた画像をバイナリで取得してDBに入れる
      val bin = imageStorage.binary(convertedPath)
      imageRepository.updateStatus(id, imageRepository.AVAILABLE, bin)
    }

    case ImageGenerateMessage(id) => {
      val sourcePath = downloadDir + "/" + id
      val destPath = destDir + "/" + id
      imageConverter.convert(sourcePath, destPath)
      // convertされた画像をバイナリで取得してDBに入れる
      val bin = imageStorage.binary(destPath)
      imageRepository.updateStatus(id, imageRepository.AVAILABLE, bin)
    }
  }

}
