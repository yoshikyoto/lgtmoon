package models

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import repositories.Tables.{Image, ImageRow}
import slick.driver.PostgresDriver.api._
import java.sql.Timestamp
import constants.ImageStatus

/**
 * imageテーブルのアクセスする
 */
object ImageModel extends ImageModelTrait {
}

trait ImageModelTrait {
  // TODO このへんdaoに移す
  val db = Database.forConfig("pg_database")

  /**
   * status=0でレコードを作成する
   * 
   * @return Future[Option[Long]] 画像のID
   */
  def create(): Future[Option[Long]] = {
    val timestamp = new Timestamp(System.currentTimeMillis())
    val imageRow = ImageRow(0, "", timestamp, 0)
    val action = for {
      id <- Image returning Image.map(_.id) += imageRow
    } yield Some(id)
    db.run(action)
  }

  /**
   * レコードのstatusを更新する
   * 
   * @param id: Long 画像ID
   * @param status: Short 更新後のstatusの値
   * @return Future[Option[Int]]
   */
  def updateStatus(id: Long, status: Short): Future[Option[Int]] = {
    val action = Image.filter(_.id === id).map(_.status).update(status)
    db.run(action).map {
      // 正しい挙動（1件のレコードが更新される）
      case num: Int if num == 1 => Some(num)
      case _ => None
    }.recover {
      case e => None
    }
  }

  def images(limit: Int = 20): Future[Option[Seq[ImageRow]]] = {
    val action = Image.filter(_.status === ImageStatus.AVAILABLE)
      .sortBy(_.createdAt.desc)
      .take(limit)
      .result
    db.run(action).map {
      case images: Seq[ImageRow] => Some(images)
      case _ => None
    }.recover {
      case e => None
    }
  }

}
