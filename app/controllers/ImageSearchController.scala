package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api._
import play.api.mvc._
import org.joda.time.DateTime
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import play.api.libs.json._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import akka.actor.Props
import externals.google.CustomSearchAdapter
import utils._

class ImageSearchController extends BaseControllerTrait {
  def search = Action.async { request =>
    val jsonOpt = request.body.asJson
    jsonOpt match {
      case None => Future(NOT_JSON_RESPONSE)
      case Some(json) => {
        val keywordOpt = (json \ "keyword").asOpt[String]
        keywordOpt match {
          case None => Future(PARAMETER_KEYWORD_NOT_FOUND_RESPONSE)
          case Some(keyword) => {
            CustomSearchAdapter.imageUrls(keyword).map {
              case None => CUSTOM_SEARCH_ERROR_RESPONSE
              case Some(urls) => {
                Ok(JsonBuilder.imageUrls(urls))
              }
            }
          }
        }
      }
    }
  }

}
