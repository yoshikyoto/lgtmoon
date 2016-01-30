package actors

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play
import akka.actor.Actor
import externals.google.CustomSearchAdapter
import utils.ImageDownloader
import externals.imagemagick.ImageMagickAdapter
import models.ImageModel

case class ImageGenerateMessage(id: Long, url: String)

/**
 * 画像idとkeywordを受け
 * 1. 画像検索し、
 * 2. 画像をダウンロードし、
 * 3. 変換キューに乗せる
 */
class ImageActor extends Actor {
  val downloadDir = "/tmp"
  val convertedDir = "/tmp" //Play.current.configuration.getString("image.converted.dir").get

  override def receive: Receive = {
    case ImageGenerateMessage(id, url) => {
      val downloadPath = downloadDir + "/" + id
      val convertedPath = convertedDir + "/" + id
      println(downloadPath)
      ImageDownloader.download(url, downloadPath)
      val imagemagick = new ImageMagickAdapter()
      imagemagick.convert(downloadPath, convertedPath)
      val bin = ImageDownloader.binary(convertedPath)
      ImageModel.updateStatus(id, ImageModel.AVAILABLE, bin)
    }
  }

}
