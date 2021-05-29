package actor

import akka.actor.Actor
import image.{ImageConverter, ImageRepository, ImageStorage, SourceImage}
import com.google.inject.Inject
import storage.{ImageTemporaryStorage, ImageWasabiS3}

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
  temporaryStorage: ImageTemporaryStorage,
  storage: ImageStorage,
  wasabi: ImageWasabiS3,
) extends Actor {

  override def receive: Receive = {
    case ImageDownloadAndGenerateMessage(id, url) => {
      val sourceImage = temporaryStorage.save(id.toInt, url)

      // 変換に失敗したら例外になるので、変換失敗した場合は成功フラグは立たない
      // ほんとは Option とかで結果をうけとるように変更したほうが良い
      val convertedImage = imageConverter.convert(sourceImage)

      // aws s3 に保存
      // S3 はバックアップ的な感じなので保存失敗しても無視する
      storage.save(convertedImage)

      // Wasabi S3 に保存
      if (wasabi.save(convertedImage)) {
        // 保存に成功してはじめて変換成功フラグを立てる
        imageRepository.makeAvailable(id.toInt)
      }
    }

    case ImageGenerateMessage(id) => {
      val sourceImage: SourceImage = temporaryStorage.sourceImage(id.toInt)

      // 変換に失敗したら例外になるので、変換失敗した場合は成功フラグは立たない
      // ほんとは Option とかで結果をうけとるように変更したほうが良い
      val convertedImage = imageConverter.convert(sourceImage)

      // aws s3 に保存
      // S3 はバックアップ的な感じなので保存失敗しても無視する
      storage.save(convertedImage)

      // Wasabi S3 に保存
      if (wasabi.save(convertedImage)) {
        // 保存に成功してはじめて変換成功フラグを立てる
        imageRepository.makeAvailable(id.toInt)
      }
    }
  }

}
