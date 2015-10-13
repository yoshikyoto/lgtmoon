package externals.rabbitmq

import java.io._
import play.api.Play
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

  /**
   * RabbitMqにOriginalPictureを積む
   * 
   * @param originalPicture: OriginalPicture
   */
  def publish(originalPicture: OriginalPicture) {
    val binary = OriginalPictureBuilder.toByteArray(originalPicture)
    RabbitMqAdapter.publish(binary)
  }

}