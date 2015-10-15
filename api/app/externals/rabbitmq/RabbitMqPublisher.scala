package externals.rabbitmq

import java.io._
import play.api.Play
import entities.{ImageEntity, ImageEntityBuilder}

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
  def publish(image: ImageEntity) {
    val binary = ImageEntityBuilder.toByteArray(image)
    RabbitMqAdapter.publish(binary)
  }

}
