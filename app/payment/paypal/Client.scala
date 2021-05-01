package payment.paypal

import java.time.OffsetDateTime
import java.util.Base64

import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.Inject
import play.api.Configuration
import play.api.libs.json.JsValue
import play.api.libs.ws.WSClient

import scala.concurrent.Future

class Client @Inject() (
  config: Configuration,
  ws: WSClient,
) {
  private val apiBaseUrl = config.get[String]("paypal.apiBaseUrl")
  private val clientId = config.get[String]("paypal.clientId")
  private val secret = config.get[String]("paypal.secret")
  private val merchantId = config.get[String]("paypal.merchantId")


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
  def getOrder(orderId: String): Future[Option[Order]] = {
    println(basicCredential())
    ws.url(apiBaseUrl + "/v2/checkout/orders/" + orderId)
      .addHttpHeaders("Authorization" -> ("Basic " + basicCredential()))
      .get()
      .map(response => jsonToOrder(response.json))
  }

  def jsonToOrder(json: JsValue): Option[Order] = {
    //try {
    val purchaseUnitJson = json("purchase_units")(0)
    val orderMerchantId = purchaseUnitJson("payee")("merchant_id").as[String]
    val captureJson = purchaseUnitJson("payments")("captures")(0)
    return Some(Order(
      json("id").as[String],
      json("intent").as[String],
      json("status").as[String],
      purchaseUnitJson("amount")("currency_code").as[String],
      purchaseUnitJson("amount")("breakdown")("item_total")("value").as[String],
      Payee(
        orderMerchantId,
        orderMerchantId == merchantId
      ),
      OffsetDateTime.parse(captureJson("create_time").as[String])
    ))
    //        } catch {
    //          case e: Throwable => {
    //            print(e)
    //            return Future(None)
    //          }
    //        }
  }
}

/**
 * 注文の詳細
 * @param id 注文ID
 * @param status 注文の状態（注文確定したかどうか）
 * @param currencyCode 通貨。 "JPY" とか
 * @param itemTotalAmount 税別の本体価格
 * @param payee 売り手（=LGTMoon）の情報
 * @param capturedAt 売上確定した日時
 */
case class Order(
  id: String,
  intent: String,
  status: String,
  currencyCode: String,
  itemTotalAmount: String,
  payee: Payee,
  capturedAt: OffsetDateTime
) {
  def isCaptureCompleted: Boolean =
    (intent == "CAPTURE" && status == "COMPLETED")
}

/**
 * 売り手の情報
 * @param merchantId マーチャントID
 * @param isLGTMoon マーチャントIDがLGTMoonかどうか
 */
case class Payee(
  merchantId: String,
  isLGTMoon: Boolean,
)
