package payment.paypal

import java.util.Base64
import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.Inject
import play.api.Configuration
import play.api.libs.ws.WSClient
import play.api.libs.json._
import play.api.libs.functional.syntax._

import scala.concurrent.Future

class Client @Inject() (
  config: Configuration,
  ws: WSClient,
) {
  private val apiBaseUrl = config.get[String]("paypal.apiBaseUrl")
  private val clientId = config.get[String]("paypal.clientId")
  private val secret = config.get[String]("paypal.secret")


  /**
   * PayPal の API を叩くのに必要な Basic 認証の credential を返す
   * @return
   */
  def basicCredential(): String = {
    Base64.getEncoder.encodeToString(
      (clientId + ":" + secret).getBytes("utf-8")
    )
  }

  /**
   * 注文の詳細を取得する
   * @see https://developer.paypal.com/docs/api/orders/v2/#orders_get
   * @param orderId
   */
  def getOrder(orderId: String) = {
    println(basicCredential())
    ws.url(apiBaseUrl + "/v2/checkout/orders/" + orderId)
      .addHttpHeaders("Authorization" -> ("Basic " + basicCredential()))
      .get()
      .map(response => {
        println("response.body")
        println(response.body)
        println("parsed")
        println(response.json.validate[Order])
      })
  }

  implicit val jsonReads: Reads[Order] = Json.reads[Order]
}

/**
 * 注文の詳細
 * @param id 注文ID
 * @param status 注文の状態（注文確定したかどうか）
 */
case class Order(
  id: String,
)
