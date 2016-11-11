package actors

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play
import akka.actor.Actor
import externals.google.CustomSearchAdapter
import infra.datasource.ImageStorage
import domain.imagemagick.ImageMagickService
import domain.image.ImageRepository

case class ImageGenerateMessage(id: Long, url: String)

/**
 * 画像idとURLを受け
 * 1. 画像をダウンロードし、
 * 2. その画像を変換キューに乗せる
 */
class ImageActor extends Actor {
  val downloadDir = "/tmp"
  val convertedDir = "/tmp"
  val imageStorage = ImageStorage
  val imageRepository = ImageRepository
  val imageMagickService = ImageMagickService

  override def receive: Receive = {
    case ImageGenerateMessage(id, url) => {
      val downloadPath = downloadDir + "/" + id
      val convertedPath = convertedDir + "/" + id
      imageStorage.download(url, downloadPath)
      imageMagickService.convert(downloadPath, convertedPath)
      // convertされた画像をバイナリで取得してDBに入れる
      val bin = imageStorage.binary(convertedPath)
      imageRepository.updateStatus(id, imageRepository.AVAILABLE, bin)
    }
  }

}
