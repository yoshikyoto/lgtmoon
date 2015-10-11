package externals.rabbitmq

import java.io._
import play.api.Play;
import com.rabbitmq.client.ConnectionFactory
import entities.OriginalPicture
import entities.OriginalPictureBuilder

/**
 * RabbitMqに画像を積む
 */
object RabbitMqPublisher extends RabbitMqPublisherTrait {
}

/**
 * RabbitMqAdapterの実装があるtrait
 */
trait RabbitMqPublisherTrait {
  val queueName = Play.current.configuration.getString("rabbitmq.originalPictureQueueName")
    .getOrElse("original_pictures")
  val hostName = Play.current.configuration.getString("rabbitmq.hostName")
    .getOrElse("localhost")

  /**
   * RabbitMqにOriginalPictureを積む
   * 
   * @param originalPicture: OriginalPicture
   */
  def publish(originalPicture: OriginalPicture) {
    val binary = OriginalPictureBuilder.toByteArray(originalPicture)

    val factory = new ConnectionFactory()
    factory.setHost(hostName)
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare(queueName, false, false, false, null)
    channel.basicPublish("", queueName, null, binary)
  }
  
}
