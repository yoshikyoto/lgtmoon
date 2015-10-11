package externals

import java.io._
import play.api.Play;
import com.rabbitmq.client.ConnectionFactory
import entities.OriginalPicture

/**
 * RabbitMqに画像を積む
 * 
 * サービスじゃなくてAdapterであるという説もある...
 */
object RabbitMqAdapter extends RabbitMqAdapter {
}

/**
 * RabbitMqAdapterの実装があるtrait
 */
trait RabbitMqAdapter {
  val queueName = Play.current.configuration.getString("rabbitmq.originalPictureQueueName")
    .getOrElse("original_pictures");
  val hostName = Play.current.configuration.getString("rabbitmq.hostName")
    .getOrElse("localhost");

  /**
   * RabbitMqにOriginalPictureを積む
   * 
   * @param originalPicture: OriginalPicture
   */
  def pushOriginalPicture(originalPicture: OriginalPicture) {
    // binary を返すまではメソッド化してもいいかも
    val byteArrayOutputStream = new ByteArrayOutputStream(1024)
    val objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(originalPicture)
    val binary = byteArrayOutputStream.toByteArray
    objectOutputStream.close()

    val factory = new ConnectionFactory()
    factory.setHost(hostName)
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare(queueName, false, false, false, null)
    channel.basicPublish("", queueName, null, binary)
  }

}
