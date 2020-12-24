package test.feature

import play.core.server.Server
import org.specs2.mock.Mockito
import play.api.Configuration
import play.api.http.HttpVerbs
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json._
import play.api.libs.ws.WSClient
import play.api.test.{FakeRequest, PlaySpecification, WithApplication, WsTestClient}
import play.api.routing.sird
import play.api.routing.sird._
import play.api.mvc.Results.Ok
import play.api.inject.bind


/**
  * /api/search のテスト
  *
  * 参考: wsのテスト
  * https://www.playframework.com/documentation/2.8.x/ScalaTestingWebServiceClients
  */
class ImageSearchTest extends PlaySpecification
  with Mockito {

  "/api/search" should {
    val app = new GuiceApplicationBuilder()
      .configure(Configuration.from(Map(
        "google.baseUrl" -> "/customsearch/v1",
        "google.key" -> "googleapikey",
        "google.cx" -> "googleapicx",
      )))
      .build()
    "GoogleAPIの結果を返せる" in new WithApplication(app) {
      Server.withRouterFromComponents() { components =>
        // モックサーバーのリクエストハンドラ
        {
          case sird.GET(p"/customsearch/v1") => components.defaultActionBuilder {
            Ok(Json.obj("hoge" -> "fufa"))
          }
        }
      } { implicit port =>
        WsTestClient.withClient { client =>
          println("これだああああああああああああああああああ")
          val Some(result) = route(app, FakeRequest(HttpVerbs.GET, "/api/search?keyword=ねこ"))
          println(result)
          "h" must equalTo("h")
        }
      }
    }
  }
}
