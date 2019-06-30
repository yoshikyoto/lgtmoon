package image

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class ImageSearcher {
  def urls(keyword: String): Future[Option[Seq[String]]] = {
    return Future(Some(Seq("http://test.example")))
  }
}
