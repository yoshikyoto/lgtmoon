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

  // status定数
  val CONVERTING: Short = 0;
  val AVAILABLE: Short = 1;

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
  def updateStatus(id: Long, status: Short, bin: Array[Byte]): Future[Option[Int]] = {
    val action = Image.filter(_.id === id)
      .map(x => (x.status, x.bin))
      .update((status, Some(bin)))
    db.run(action).map {
      // 正しい挙動（1件のレコードが更新される）
      case num: Int if num == 1 => Some(num)
      case _ => None
    }.recover {
      case e => None
    }
  }

  /**
   * 画像を最新20件取得する
   * 
   * @param limit: Int 何件取得するか。デフォルト20
   * @return Future[Option[Seq[ImageRow]]]
   */
  def images(limit: Int = 20): Future[Option[Seq[ImageRow]]] = {
    val action = Image.filter(_.status === AVAILABLE)
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

  /**
   * 画像を取得する
   * 
   * @param id: Long
   * @return Future[Option[ImageRow]]
   */
  def image(id: Long): Future[Option[ImageRow]] = {
    val action = Image.filter(_.id === id).result
    db.run(action).map {
      case images: Seq[ImageRow] if images.length > 0
          => Some(images(0))
      case _ => None
    }.recover {
      case e => {
        println("error on database")
        None
      }
    }
  }

}
