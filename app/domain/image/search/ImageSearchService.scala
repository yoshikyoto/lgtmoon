package domain.image.search

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ImageSearchService extends ImageSearchServiceTrait {
}


trait ImageSearchServiceTrait {
  val client = externals.google.CustomSearchAdapter

  def images(keyword: String): Future[Option[Seq[Image]]] = {
    client.items(keyword).map {
      case None => None
      case Some(items) => Some{
        items.map { item =>
          Image(item.link, item.mime)
        }
      }
    }
  }
}
