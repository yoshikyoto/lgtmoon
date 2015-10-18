package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._

class ApplicationController extends Controller {
  def index = Action { request =>
    Ok(views.html.index())
  }
}
