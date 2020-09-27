package test.lib

import database.Tables
import play.api.Configuration
import play.api.db.Databases
import slick.dbio.DBIO
import scala.concurrent.Await
import scala.concurrent.duration.Duration
// schema.create を使うためにこの import が必要
import slick.jdbc.H2Profile.api._


object TablesForTest extends Tables {
  val profile = slick.jdbc.H2Profile
}

trait InMemoryH2Database {
  /** playの機能を使ってインメモリデータベース（H2）の設定を取得 */
  val inMemoryDb: play.api.db.Database = Databases.inMemory()

  val connection: java.sql.Connection = inMemoryDb.getConnection()

  // inMemoryDb に接続するように slick.dbs.default を上書きする設定
  // コネクションプールは無効にする
  val dbConfiguration: Configuration = Configuration.from(
    Map(
      "slick.dbs.default.profiile" -> "slick.jdbc.H2Profile$",
      "slick.dbs.default.db.driver" -> "org.h2.Driver",
      "slick.dbs.default.db.url" -> inMemoryDb.url,
      "slick.dbs.default.db.connectionPool" -> "disabled",
      "slick.dbs.default.db.user" -> "",
      "slick.dbs.default.db.password" -> "",
    )
  )

  /** Slick で生成されたクエリを実行するためのdbオブジェクト */
  val dbForSlick = slick.jdbc.JdbcBackend.Database.forURL(inMemoryDb.url)

  // DB をマイグレーション
  Await.result(
    dbForSlick.run(DBIO.seq(TablesForTest.schema.create)),
    Duration.Inf,
  )
}
