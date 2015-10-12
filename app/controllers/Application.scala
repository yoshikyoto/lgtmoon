package controllers

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import models.PictureModel
import utils.UrlBuilder

/**
 * indexページを表示するコントローラー
 */
class Application extends Controller {

  /**
   * indexページを表示する
   */
  def index = Action.async {
    PictureModel.pictures().map {
      case Some(pictures) => {
        val pictureUrls = pictures.map {
          picture => UrlBuilder.imageUrl(picture.fileName)
        }
        Ok(views.html.index(pictureUrls))
      }
      case None => InternalServerError("")
    }
  }

}
