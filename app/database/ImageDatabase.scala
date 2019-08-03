package database

import repositories.Tables.Image
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ImageDatabase {
  val db = Database.forConfig("pg_database")

  val CONVERTIONG: Short = 0
  val AVAILABLE: Short = 1

  def recentIds(limit: Int): Future[Option[Seq[Long]]] = {
    val action = Image.filter(_.status === AVAILABLE)
      .sortBy(_.createdAt.desc)
      .map(p => p.id)
      .take(limit)
      .result
    db.run(action).map {
      case imageIds: Seq[Long] => Some(imageIds)
      case _ => None
    }.recover {
      case e => None
    }
  }
}
