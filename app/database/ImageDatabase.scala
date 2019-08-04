package database

import repositories.Tables.Image
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import image.ImageRepository
import java.sql.Timestamp
import repositories.Tables.ImageRow

class ImageDatabase extends ImageRepository {
  val db = Database.forConfig("pg_database")

  val CONVERTIONG: Short = 0
  val AVAILABLE: Short = 1

  def recentIds(limit: Int): Future[Option[Seq[Int]]] = {
    val action = Image.filter(_.status === AVAILABLE)
      .sortBy(_.createdAt.desc)
      .map(p => p.id)
      .take(limit)
      .result
    db.run(action).map {
      // Longで帰ってくるのでIntに変換する
      case imageIds: Seq[Long] => Some(imageIds.map(_.toInt))
      case _ => None
    }.recover {
      case e => {
        println(e)
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
      case _ => None
    }.recover {
      case e => {
        None
      }
    }
  }

  /** 画像のバイナリを取得する */
  def binary(id: Int): Future[Option[Array[Byte]]] = {
    // DBのidはLong型なのでIntからLongに変換する
    val action = Image.filter(_.id === id.toLong).result
    db.run(action).map {
      case images: Seq[ImageRow] if images.length > 0
      => Some(images(0))
      case _ => None
    }.map {
      case Some(image) => image.bin
      case _ => None
    }.recover {
      case e => {
        None
      }
    }
  }
}
