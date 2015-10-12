package controllers

import play.api._
import play.api.mvc._
import models.PictureModel

class Application extends Controller {

  def index = Action {
    val future = PictureModel.pictures()
    Ok(views.html.index("Your new application is ready."))
  }

}
