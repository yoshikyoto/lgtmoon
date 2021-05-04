package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc.{Action, AnyContent, InjectedController}
import com.google.inject.Inject
import payment.PaymentChecker
import play.api.Configuration

import scala.concurrent.Future


class PayPalController @Inject() (
  config: Configuration,
  paymentChecker: PaymentChecker,
)extends InjectedController {
  private val paypalPayButtonUrl = config.get[String]("paypal.payButtonUrl")

  /**
   * 決済ページを表示する
   * @return
   */
  def view(): Action[AnyContent] = Action.async { request =>
    Future(Ok(views.html.paypal(paypalPayButtonUrl)))
  }

  /**
   * PayPal での支払いが完了した時に呼ばれるAPI
   * @return
   */
  def approve(orderId: String): Action[AnyContent] = Action.async { request =>
    paymentChecker.remainSecondWithPayPal(orderId).map { seconds =>
      Ok("Premium Seconds:" + seconds)
    }
  }
}
