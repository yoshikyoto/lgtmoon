package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._

/** トップページを表示するためのController */
class ApplicationController extends Controller {
  def index = Action { request =>
    Logger.info("remoteAddress: " + request.remoteAddress +
      "\tx-forwarded-for: " + request.headers.get("x-forwarded-for").getOrElse("") +
      "\tx-real-ip: " + request.headers.get("x-real-ip").getOrElse(""))
    Ok(views.html.index())
  }
}
