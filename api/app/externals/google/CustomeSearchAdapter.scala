package externals.google

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.json._

/**
 * Google Custom Search
 */
object CustomSearchAdapter extends CustomSearchAdapterTrait {
  override val baseUrl = Play.current.configuration.getString("google.baseUrl").get
  override val key = Play.current.configuration.getString("google.key").get
  override val cx = Play.current.configuration.getString("google.cx").get
}

trait CustomSearchAdapterTrait {
  val baseUrl = "https://www.googleapis.com/customsearch/v1"
  val key = ""
  val cx = ""
  val searchType = "image"

  def imageUrl(keyword: String): Future[Option[String]] = {
    val url = s"${baseUrl}?key=${key}&cx=${cx}&q=${keyword}";
    val future = WS.url(baseUrl)
      .withQueryString("searchType" -> searchType)
      .withQueryString("key" -> key)
      .withQueryString("cx" -> cx)
      .withQueryString("q" -> keyword)
      .get()
    future.map {
      response => {
        val urlOpt = ((response.json \ "items")(1) \ "link").asOpt[String]
        urlOpt match {
          case Some(url) => Some(url)
          case  _ => None
        }
      }
    }
  }

}
