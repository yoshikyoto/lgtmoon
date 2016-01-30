package externals.google

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.data.validation.ValidationError

/**
 * Google Custom Search
 */
object CustomSearchAdapter extends CustomSearchAdapterTrait {
  override val baseUrl = Play.current.configuration.getString("google.baseUrl").get
  override val key = Play.current.configuration.getString("google.key").get
  override val cx = Play.current.configuration.getString("google.cx").get
}

trait CustomSearchAdapterTrait {
  implicit val imageReads: Reads[Image] = (
    (__ \ "byteSize").read[Int]
      and (__ \ "contextLink").read[String]
      and (__ \ "height").read[Int]
      and (__ \ "width").read[Int]
      and (__ \ "thumbnailHeight").read[Int]
      and (__ \ "thumbnailWidth").read[Int]
      and (__ \ "thumbnailLink").read[String]
  )(Image)

  implicit val itemReads: Reads[Item] = (
    (__ \ "displayLink").read[String]
      and (__ \ "htmlSnippet").read[String]
      and (__ \ "htmlTitle").read[String]
      and (__ \ "image").read[Image]
      and (__ \ "kind").read[String]
      and (__ \ "link").read[String]
      and (__ \ "mime").read[String]
      and (__ \ "snippet").read[String]
      and (__ \ "title").read[String]
  )(Item)

  val baseUrl = "https://www.googleapis.com/customsearch/v1"
  val key = ""
  val cx = ""
  val searchType = "image"

  def search(keyword: String): Future[Option[Seq[Item]]] = {
    val future = WS.url(baseUrl)
      .withQueryString("searchType" -> searchType)
      .withQueryString("key" -> key)
      .withQueryString("cx" -> cx)
      .withQueryString("q" -> keyword)
      .get()
    future.map {
      response => {
        (response.json \ "items").asOpt[Seq[Item]]
      }
    }
  }

  def imageUrls(keyword: String): Future[Option[Seq[String]]] = {
    search(keyword).map {
      case None => None
      case Some(items) => Some(items.map(item => item.link))
    }
  }

  def imageUrl(keyword: String): Future[Option[String]] = {
    search(keyword).map {
      itemsOpt => {
        itemsOpt match {
          case None => None
          case Some(items) => {
            Some(items(1).link)
          }
        }
      }
    }
  }

  def generateUrl(keyword: String): String = {
    return s"${baseUrl}?key=${key}&cx=${cx}&q=${keyword}";
  }

}
