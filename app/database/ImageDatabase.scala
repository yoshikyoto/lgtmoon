package database

import java.sql.Timestamp

import database.Tables.Image
import database.Tables.ImageRow
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import image.ImageRepository
import play.api.{Configuration, Logging}
import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

/**
 * データベースにアクセスするクラス
 * play-slickを利用している
 * DatabaseConfigProvider で slicl.dbs.default の設定が勝手に読み込まれる
 * dbConfigProvider という名前で接続情報をもたせて
 * HasDatabaseConfigProvider[JdbcProfile] を extends しておくと
 * db.run で action が実行できる。
 *
 */
class ImageDatabase @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile]
  with Logging
  with ImageRepository {

  val STATUS_PROCESSING: Short = 0
  val STATUS_AVAILABLE: Short = 1

  def recentIds(limit: Int): Future[Option[Seq[Int]]] = {
    val action = Image.filter(_.status === STATUS_AVAILABLE)
      .sortBy(_.createdAt.desc)
      .map(p => p.id)
      .take(limit)
      .result
    db.run(action).map {
      // Longで帰ってくるのでIntに変換する
      case imageIds: Seq[Long] => Some(imageIds.map(_.toInt))
      case _ => {
        logger.error("ImageDatabase.recentIdsでSeq[Long]以外が返された")
        None
      }
    }.recover {
      case e => {
        logger.error("ImageDatabase.recentIdsでエラー", e)
        None
      }
    }
  }

  def randomIds(limit: Int): Future[Option[Seq[Int]]] = {
    val action =  sql"""
      SELECT id FROM image
      WHERE status = 1
      ORDER BY random()
      LIMIT 20
    """.as[Int]
    db.run(action).map {
      case ids: Seq[Int] => Some(ids)
      case _ => {
        logger.error("ImageDatabase.randomIdsでSeq[Int]以外が返された")
        None
      }
    }.recover {
      case e => {
        logger.error("ImageDatabase.randomIdsでエラー", e)
        None
      }
    }
  }

  /** 画像のバイナリを取得する */
  def binary(id: Int): Future[Option[Array[Byte]]] = {
    // DBのidはLong型なのでIntからLongに変換する
    val action = Image.filter(_.id === id.toLong).result
    db.run(action).map {
      case images: Seq[ImageRow] if images.nonEmpty
      => Some(images.head)
      case _ => None
    }.map {
      case Some(image) => image.bin
      case _ => None
    }.recover {
      case e => {
        logger.error("ImageDatabase.binaryでエラー", e)
        None
      }
    }
  }

  /** status = STATUS_PROCESSING でレコードを作成する */
  def create(): Future[Option[Int]] = {
    val timestamp = new Timestamp(System.currentTimeMillis())
    val imageRow = ImageRow(0, "", timestamp, STATUS_PROCESSING)
    val action = for {
      id <- Image returning Image.map(_.id) += imageRow
    } yield Some(id.toInt)
    db.run(action)
  }


  /**
   * 変換完了した画像のバイナリを保存してStatusをAvailableにする
   * @return 正常に終了した場合 Future(Some(1)) （1はUpdateされたカラムの数）
   * 更新に失敗した場合は None
   */
  def makeAvailable(id: Int, bin: Array[Byte]): Future[Option[Int]] = {
    val action = Image.filter(_.id === id.toLong)
      .map(x => (x.status, x.bin))
      .update((STATUS_AVAILABLE, Some(bin)))
    db.run(action).map {
      // 正しい挙動（1件のレコードが更新される）
      case num: Int if num == 1 => Some(num)
      case _ => None
    }.recover {
      case e => {
        logger.error("ImageDatabase.makeAvailableでエラーが発生しましたID:" + id)
        None
      }
    }
  }
}
