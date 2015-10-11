package externals.rabbitmq

import java.io._
import play.api._
import entities.OriginalPicture
import entities.OriginalPictureBuilder

/**
 * RabbitMqのキューの監視を行う
 */
class RabbitMqConsumer(app: Application)  extends RabbitMqConsumerTrait {
}

/**
 * RabbitMqConsumer の実装をするtrait
 */
trait RabbitMqConsumerTrait extends Runnable {

  /**
   * RabbitMqの監視を開始する
   */
  override def run() {
    Logger.logger.info(s"RabbitMQの監視を開始しました スレッド名:${Thread.currentThread().getName}")
    while (true) {
      val binary = RabbitMqAdapter.consume
      val originalPicture = OriginalPictureBuilder.fromByteArray(binary);
      println(originalPicture) // TODO 変換処理のActorに投げる
    }
  }

}
