
import java.util.concurrent.Executors
import externals.rabbitmq.RabbitMqConsumer
import play.api._


/**
 * Play起動時に呼び出されるObject
 * 
 * 無名パッケージに定義してあることが必須です
 */
object Global extends GlobalSettings {
  private[this] val rabbitMQConsumeExecutor = Executors.newSingleThreadExecutor()

  /**
   * Play起動時に呼び出されるメソッド
   */
  override def onStart(app: Application) {
    Logger.info("Application has started")
    // RabbitMQの監視を開始
    rabbitMQConsumeExecutor.submit(new RabbitMqConsumer(app))
  }

  /**
   * Play終了時に呼び出されるメソッド
   */
  override def onStop(app: Application) {
    rabbitMQConsumeExecutor.shutdownNow()
    Logger.info("Application shutdown...")
  }

}
