package image

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[external.google.search.GoogleCustomSearchClient])
trait ImageSearcher {
  def urls(keyword: String): Future[Option[Seq[String]]]
}
