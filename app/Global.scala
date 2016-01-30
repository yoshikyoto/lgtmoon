
import java.util.concurrent.Executors
import externals.rabbitmq.RabbitMqConsumer
import play.api._
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Play起動時に呼び出されるObject
 * 
 * 無名パッケージに定義してあることが必須です
 */
object Global extends GlobalSettings {
  private[this] val rabbitMQConsumeExecutor = Executors.newSingleThreadExecutor()
  private[this] val isRabbitMQConsumerStarted = new AtomicBoolean(false)

  /**
   * Play起動時に呼び出されるメソッド
   */
  override def onStart(app: Application) {
    Logger.info("Application has started")
    // RabbitMQの監視を開始
    // テスト時は何度もonStartが走るのでその状況にも対応
    if(!isRabbitMQConsumerStarted.getAndSet(true)) {
      Logger.info("Rabbit MQ の監視を開始します...")
      rabbitMQConsumeExecutor.submit(new RabbitMqConsumer(app))
    }
  }

  /**
   * Play終了時に呼び出されるメソッド
   */
  override def onStop(app: Application) {
    if(Play.current.mode != Mode.Test){
      Logger.info("Rabbit MQ の監視を終了します")
      rabbitMQConsumeExecutor.shutdownNow()
    }
  }

}
