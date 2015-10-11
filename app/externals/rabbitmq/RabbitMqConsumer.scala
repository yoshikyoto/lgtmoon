package externals.rabbitmq

import java.io._
import play.api._
import com.rabbitmq.client._
import entities.OriginalPicture

/**
 * RabbitMqのキューの監視を行う
 */
class RabbitMqConsumer(app: Application)  extends RabbitMqConsumerTrait {
}

/**
 * RabbitMqConsumer の実装をするtrait
 */
trait RabbitMqConsumerTrait extends Runnable {
  val queueName = Play.current.configuration.getString("rabbitmq.originalPictureQueueName")
    .getOrElse("original_pictures")
  val hostName = Play.current.configuration.getString("rabbitmq.hostName")
    .getOrElse("localhost")

  /**
   * RabbitMqの監視を開始する
   */
  override def run() {
    Logger.logger.info(s"RabbitMQの監視を開始しました スレッド名:${Thread.currentThread().getName}")
    val consumer = RabbitMqAdapter.consumer
    while (true) {
      // TODO Builderに移したい
      val delivery = consumer.nextDelivery() // 巨大なバイナリを受け取るリスク有り
      val byteArrayInputStream = new ByteArrayInputStream(delivery.getBody)
      val objectInputStream = new ObjectInputStream(byteArrayInputStream)
      val originalPicture = objectInputStream.readObject().asInstanceOf[OriginalPicture]
      println(originalPicture) // TODO 変換処理のActorに投げる
    }
  }

}
