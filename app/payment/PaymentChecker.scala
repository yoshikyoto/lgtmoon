package payment

import java.time.OffsetDateTime

import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.Inject
import play.api.Logging
import scala.concurrent.Future

class PaymentChecker @Inject() (
  paypal: payment.paypal.Client
) extends Logging {
  /**
   * プレミアム特典を受けられる時間（秒）
   * @return
   */
  def premiumTimeSecond = 365 * 24 * 60 * 60

  def remainSecond(order: payment.paypal.Order): Int = {
    // 売上確定まで完了していない場合は Order として不正
    logger.info(s"売上確定状況をチェックします\torderId:${order.id}\tintent:${order.intent}\tstatus:${order.status}\tisCaptureCompleted:${order.isCaptureCompleted}")
    if (!order.isCaptureCompleted) {
      return 0
    }

    // Payee が LGTMoon ではない場合は Order として不正
    logger.info(s"マーチャントIDをチェックします\torderId:${order.id}\tmerchantId:${order.payee.merchantId}\tisLGTMoon:${order.payee.isLGTMoon}")
    if (!order.payee.isLGTMoon) {
      return 0
    }

    // 価格は 300 円である
    // ※ 300 は String である
    // TODO この判定はここにあるべきかどうか
    logger.info(s"価格をチェックします\torderId:${order.id}\titemTotalAmount:${order.itemTotalAmount}")
    if (order.itemTotalAmount != "300") {
      return 0
    }

    val expireAt = order.capturedAt.toEpochSecond + premiumTimeSecond
    val now = OffsetDateTime.now().toEpochSecond
    logger.info(s"決済日時をチェックします\torderId:${order.id}\tcapturedAt:${order.capturedAt}\texpireAt:${expireAt}\tnow:${now}")
    return Math.max(0, (expireAt - now).toInt)
  }

  /**
   * プレミアム特典をいつまで受けられるかの残り秒数を取得する
   *
   * @param orderId
   * @return
   */
  def remainSecondWithPayPal(orderId: String): Future[Int] = {
      paypal.getOrder(orderId).map {
        case None => 0
        case Some(order) => remainSecond(order)
      }
  }
}
