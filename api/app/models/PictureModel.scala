package models

import repositories.Tables.{Picture, PictureRow}
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import entities.OriginalPicture
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * pictureテーブルにアクセスするためのモデル
 */
object PictureModel extends PictureModelTrait {
}

/**
 * PictureModelの実装があるトレイト
 */
trait PictureModelTrait {
  val db = Database.forConfig("pg_database")

  /**
   * データベースにストアする
   * 
   * @param originalPicture: OriginalPicture
   * 
   * @return Future[Option[Long]] id
   */
  def insert(originalPicture: OriginalPicture): Future[Option[Long]] = {
    val pictureRow = PictureRow(
      0,
      originalPicture.fileName,
      originalPicture.contentType,
      originalPicture.uploadedAt)
    val action = for {
      id <- Picture returning Picture.map(_.id) += pictureRow
    } yield Some(id)
    db.run(action)
  }

  /**
   * データベースから最新画像を指定した数だけ取得してくる
   * 
   * @param count: Int 取得してくる数。デフォルトでは20
   * 
   * @return Future[Option[Seq[PictureRow]]] エラーの場合None
   */
  def pictures(
    count: Int = 20): Future[Option[Seq[PictureRow]]] = {
    val query = Picture.sortBy(_.createdAt.desc).take(count)
    val action = query.result
    db.run(action).map {
      case pictures: Seq[PictureRow]  => Some(pictures)
      case _ => None
    }.recover {
      case e => None // TODO: ログ吐いたりするようにする
    }
  }

}
