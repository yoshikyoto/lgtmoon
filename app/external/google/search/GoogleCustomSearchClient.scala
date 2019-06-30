package external.google.search

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import javax.inject.Inject
import image.ImageSearcher

/** GoogleCustomSearchのAPIを叩くクライアント */
class GoogleCustomSearchClient @Inject() extends ImageSearcher {
  val baseUrl = Play.current.configuration.getString("google.baseUrl").get
  val key = Play.current.configuration.getString("google.key").get
  val cx = Play.current.configuration.getString("google.cx").get
  val searchType = "image"

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

  /**
   * 画像検索を行う
   */
  def urls(keyword: String): Future[Option[Seq[String]]] = {
    WS.url(baseUrl)
      .withQueryString("searchType" -> searchType)
      .withQueryString("key" -> key)
      .withQueryString("cx" -> cx)
      .withQueryString("q" -> keyword)
      .get()
      .map{ wsResponse =>
        (wsResponse.json \ "items").asOpt[Seq[Item]] match {
          case None => None
          case Some(items) => Some(items.map(item => item.link))
        }
      }
  }
}

case class Item(
  val displayLink: String,
  val htmlSnippet: String,
  val htmlTitle: String,
  val image: Image,
  val kind: String,
  val link: String,
  val mime: String,
  val snippet: String,
  val title: String)

case class Image(
  val byteSize:Int,
  val contextLink: String,
  val height: Int,
  val width: Int,
  val thumbnailHeight: Int,
  val thumbnailWidth: Int,
  val thumbnailLink: String)
