package image

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[GoogleCustomSearchClient])
trait ImageSearcher {
  def urls(keyword: String): Future[Option[Seq[String]]]
}

class GoogleCustomSearchClient extends ImageSearcher {
  def urls(keyword: String): Future[Option[Seq[String]]] = {
    return Future(Some(Seq("http://test.example")))
  }
}
