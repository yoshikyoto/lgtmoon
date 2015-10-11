package models

import repositories._
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import entities.OriginalPicture
import scala.concurrent.ExecutionContext.Implicits.global

object PictureModel extends PictureModelTrait {
}

trait PictureModelTrait {
  val db = Database.forConfig("pg_database")

  def insert(originalPicture: OriginalPicture): Future[Option[Long]] = {
    val pictureRow = Tables.PictureRow(
      0,
      originalPicture.fileName,
      originalPicture.contentType,
      originalPicture.uploadedAt)
    val action = for {
      id <- Tables.Picture returning Tables.Picture.map(_.id) += pictureRow
    } yield Some(id)
    db.run(action)
  }
}
