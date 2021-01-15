package task

import play.api.inject.{SimpleModule, bind}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.Inject
import image.{ImageRepository, ImageStorage, ImageTemporaryStorage}

/**
 * Heroku PostgreSQL の容量の問題のため、
 * 画像を PostgreSQL から S3 に移行するための処理
 *
 * application.conf に
 * play.modules.enabled += "task.TaskModule"
 * の設定を追加すると動く。
 *
 * @see https://www.playframework.com/documentation/2.8.x/ScheduledTasks
 */
class ImageS3Migrator @Inject() (
  val imageRepository: ImageRepository,
  val imageTemporaryStorage: ImageTemporaryStorage,
  val imageStorage: ImageStorage
) {
  val startId = 106732
  val endId = 112000
  // val endId = 112000
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
