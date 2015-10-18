package externals.rabbitmq

import java.io._
import play.api._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import akka.actor.Props

/**
 * RabbitMqのキューの監視を行う
 */
class RabbitMqConsumer(app: Application)  extends RabbitMqConsumerTrait {
}

/**
 * RabbitMqConsumer の実装をするtrait
 */
trait RabbitMqConsumerTrait extends Runnable {
  implicit val executionContext = Akka.system.dispatcher
 //  val pictureConvertActor = Akka.system.actorOf(Props(new PictureConvertActor()))

  /**
   * RabbitMqの監視を開始する
   */
  override def run(): Unit =  {
    Logger.logger.info(s"RabbitMQの監視を開始しました スレッド名:${Thread.currentThread().getName}")
    while (true) {
      val binary = RabbitMqAdapter.consume
      // ここでバイナリを変換してほげほげする
    }
  }

}
