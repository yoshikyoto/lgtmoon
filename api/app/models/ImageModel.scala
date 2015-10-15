package models

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import repositories.Tables.{Image, ImageRow}
import slick.driver.PostgresDriver.api._
import java.sql.Timestamp

/**
 * imageテーブルのアクセスする
 */
object ImageModel extends ImageModelTrait {
}

trait ImageModelTrait {
  // TODO このへんdaoに移す
  val db = Database.forConfig("pg_database")

  def create() :Future[Option[Long]] = {
    val timestamp = new Timestamp(System.currentTimeMillis())
    val imageRow = ImageRow(0, "", timestamp, 0)
    val action = for {
      id <- Image returning Image.map(_.id) += imageRow
    } yield Some(id)
    db.run(action)
  }

}
