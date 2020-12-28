package task

import play.api.inject.{SimpleModule, bind}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.Inject
import image.{ImageRepository, ImageStorage, ImageTemporaryStorage}

class ImageS3Migrator @Inject() (
  val imageRepository: ImageRepository,
  val imageTemporaryStorage: ImageTemporaryStorage,
  val imageStorage: ImageStorage
) {
  val startId = 1
  val endId = 100
  Future {
    println("S3へのマイグレーション処理開始")
    println("start: " + startId + "\tend:" + endId)
    for (i <- startId to endId) {
      println("ID " + i + ": 画像をS3に移行します。")
      imageRepository.binary(i).map {
        case None =>
          println("ID " + i + ": 画像が見つからなかったのでスキップします")
        case Some(bin) =>
          println("ID " + i + ": 画像が見つかりました")
          val image = imageTemporaryStorage.saveConvertedBinary(i, bin)
          imageStorage.save(image)
          println("ID " + i + ": S3への画像のアップロードが完了しました")
      }
      Thread.sleep(10000)
    }
  }
}

class TaskModule extends SimpleModule(bind[ImageS3Migrator].toSelf.eagerly())
