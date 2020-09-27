package test.feature

import database.Tables
import org.specs2.mock.Mockito
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.{FakeRequest, PlaySpecification, WithApplication}
import test.lib.InMemoryH2Database
// Future を使うのでこれを import する必要がある
import play.api.Configuration
import slick.dbio.DBIO
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.Await
import scala.concurrent.duration.Duration


object TablesForTest extends Tables {
  val profile = slick.jdbc.H2Profile
}

class ImageControllerTest extends PlaySpecification
  with Mockito
  with InMemoryH2Database {

  "recent" should {
    val appBuilder = new GuiceApplicationBuilder().configure(Configuration.from(
      // オンメモリのデータベースを利用するように設定を上書き
      // コネクションプールは無効にする
      Map(
        "slick.dbs.default.profiile" -> "slick.jdbc.H2Profile$",
        "slick.dbs.default.db.driver" -> "org.h2.Driver",
        "slick.dbs.default.db.url" -> inMemoryDb.url,
        "slick.dbs.default.db.connectionPool" -> "disabled",
        "slick.dbs.default.db.user" -> "",
        "slick.dbs.default.db.password" -> "",
      )
    ))
    val app = appBuilder.build()
    "ImageRepositoryから返ってきた画像をそのまま返す" in new WithApplication(app) {
      val dbForSlick = Database.forURL(inMemoryDb.url)
      println(Await.result(
        dbForSlick.run(DBIO.seq(TablesForTest.schema.create)),
        Duration.Inf,
      ))
      val Some(result) = route(app, FakeRequest(GET, "/api/images/recent"))
      print(result)

      "h" must equalTo("h")
    }
  }
}
