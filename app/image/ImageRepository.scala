package image

import scala.concurrent.Future
import com.google.inject.ImplementedBy

/**
 * 画像のメタデータを保存するリポジトリ
 */
@ImplementedBy(classOf[database.ImageDatabase])
trait ImageRepository {
  def recentIds(limit: Int): Future[Option[Seq[Int]]]
  def randomIds(limit: Int): Future[Option[Seq[Int]]]
  def binary(id: Int): Future[Option[Array[Byte]]]
  def create(): Future[Option[Int]]
  def makeAvailable(id: Int): Future[Option[Int]]
}
