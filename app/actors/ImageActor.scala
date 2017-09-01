package actors

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play
import akka.actor.Actor
import play.api.libs.concurrent.Akka
import infra.datasource.ImageStorage
import domain.imagemagick.ImageMagickService
import domain.image.ImageRepository

case class ImageGenerateMessage(id: Long)

case class ImageDownloadAndGenerateMessage(id: Long, url: String)

/**
 * 画像idとURLを受け
 * 1. 画像をダウンロードし、
 * 2. その画像を変換キューに乗せる
 */
class ImageActor extends Actor {
  val downloadDir = "/tmp"
  val destDir = "/tmp"
  val imageStorage = ImageStorage
  val imageRepository = ImageRepository
  val imageMagickService = ImageMagickService

  override def receive: Receive = {
    case ImageDownloadAndGenerateMessage(id, url) => {
      val downloadPath = downloadDir + "/" + id
      val convertedPath = destDir + "/" + id
      imageStorage.download(url, downloadPath)
      imageMagickService.convert(downloadPath, convertedPath)
      // convertされた画像をバイナリで取得してDBに入れる
      val bin = imageStorage.binary(convertedPath)
      imageRepository.updateStatus(id, imageRepository.AVAILABLE, bin)
    }

    case ImageGenerateMessage(id) => {
      val sourcePath = downloadDir + "/" + id
      val destPath = destDir + "/" + id
      imageMagickService.convert(sourcePath, destPath)
      // convertされた画像をバイナリで取得してDBに入れる
      val bin = imageStorage.binary(destPath)
      imageRepository.updateStatus(id, imageRepository.AVAILABLE, bin)
    }
  }

}
