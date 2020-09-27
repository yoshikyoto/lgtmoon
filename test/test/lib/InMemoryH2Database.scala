package test.lib

import play.api.Configuration
import play.api.db.Databases

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
}
