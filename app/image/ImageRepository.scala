package image

import scala.concurrent.Future
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[database.ImageDatabase])
trait ImageRepository {
  def recentIds(limit: Int): Future[Option[Seq[Int]]]
  def randomIds(limit: Int): Future[Option[Seq[Int]]]
  def binary(id: Int): Future[Option[Array[Byte]]]
  def makeAvailable(id: Int, bin: Array[Byte]): Future[Option[Int]]
}
