package test.feature

import database.Tables
import org.specs2.mock.Mockito
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.{FakeRequest, PlaySpecification, WithApplication}
import test.lib.InMemoryH2Database


class ImageControllerTest extends PlaySpecification
  with Mockito
  with InMemoryH2Database {

  "recent" should {
    val appBuilder = new GuiceApplicationBuilder().configure(dbConfiguration)
    val app = appBuilder.build()
    "ImageRepositoryから返ってきた画像をそのまま返す" in new WithApplication(app) {
      val Some(result) = route(app, FakeRequest(GET, "/api/images/recent"))
      "h" must equalTo("h")
    }
  }
}
