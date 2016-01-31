package externals.google

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.data.validation.ValidationError

/** GoogleCustomSearchClientを利用する */
object CustomSearchAdapter extends CustomSearchAdapterTrait {
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

  val client = infra.apiclient.GoogleCustomSearchClient

  /**
   * @param keyword: String 画像検索するキーワード
   * @return Future[Option[Seq[String]]]
   */
  def imageUrls(keyword: String): Future[Option[Seq[String]]] = {
    client.search(keyword).map { wsResponse => 
      (wsResponse.json \ "items").asOpt[Seq[Item]] match {
        case None => None
        case Some(items) => Some(items.map(item => item.link))
      }
    }
  }
}
