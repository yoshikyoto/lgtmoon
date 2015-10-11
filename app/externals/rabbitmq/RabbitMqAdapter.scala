package externals.rabbitmq

import play.api._
import com.rabbitmq.client._

/**
 * RabbitMqでoriginal_picturesのchannelを生成するFactory
 */
object RabbitMqAdapter extends RabbitMqAdapterTrait {
}

/**
 * RabbitMqAdapterの実装トレイト
 */
trait RabbitMqAdapterTrait {
  val queueName = Play.current.configuration.getString("rabbitmq.originalPictureQueueName")
    .getOrElse("original_pictures")
  val hostName = Play.current.configuration.getString("rabbitmq.hostName")
    .getOrElse("localhost")

  val factory = new ConnectionFactory()
  factory.setHost(hostName)
  val connection = factory.newConnection()
  val channel = connection.createChannel()
  channel.queueDeclare(queueName, false, false, false, null)

  /**
   * RabbitMqにキューを積む
   */
  def publish(binary: Array[Byte]) {
    channel.basicPublish("", queueName, null, binary)
  }

  /**
   * RabbitMqのキューの中身を引っ張れるconsumerを取得する
   * 
   * @return QueueingConsumer
   */
  def consumer(): QueueingConsumer = {
    val consumer = new QueueingConsumer(channel)
    channel.basicConsume(queueName, true, consumer)
    consumer
  }
}
