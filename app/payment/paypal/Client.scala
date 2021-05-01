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
  ws: WSClient
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
  def getOrder(orderId: String): Future[Option[Order]] = {
    println(basicCredential())
    ws.url(apiBaseUrl + "/v2/checkout/orders/" + orderId)
      .addHttpHeaders("Authorization" -> ("Basic " + basicCredential()))
      .get()
      .map(response => {
        println("JSON")
        println(response.json)
        print("parse")
        val result = response.json.asOpt[Order]
        print("parse")
        print(result)
        result
      })
  }

  implicit val orderReads: Reads[Order] = (
    (__ \ "id").read[String]
    and (__ \ "status").read[String]
    // and (__ \ "purchase_units").read[Seq[PurchaseUnit]]
  )(Order)

  implicit val purchaseUnitReads: Reads[PurchaseUnit] =
    (__ \ "description").read[String].map(PurchaseUnit)

  implicit val amountRead: Reads[Amount] =
    (__ \ "currency_code").read[String].map(Amount)

}

/**
 * 注文の詳細
 * @param id 注文ID
 * @param status 注文の状態（注文確定したかどうか）
 * @param purchaseUnits 支払内容の情報
 */
case class Order(
  id: String,
  status: String,
  // purchaseUnits: Seq[PurchaseUnit]
)

/**
 * 支払いの情報
 * @param amount 金額の情報
 * @param payee 売り手の情報
 * @param payments 支払いに関する情報
 */
case class PurchaseUnit(
  // amount: Amount,
  description: String,
  // payee: Payee,
  // payments: Payments
)

/**
 * 支払い金額についての情報
 * @param currencyCode JPY とか
 * @param breakdown 金額の内訳
 */
case class Amount(
  currencyCode: String
)
