package test.lib

import play.api.db.Databases

trait InMemoryH2Database {
  /** playの機能を使ってインメモリデータベース（H2）の設定を取得 */
  val inMemoryDb: play.api.db.Database = Databases.inMemory()

  val connection: java.sql.Connection = inMemoryDb.getConnection()

}
