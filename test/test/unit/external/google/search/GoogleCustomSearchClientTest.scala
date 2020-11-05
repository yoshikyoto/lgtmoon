package test.unit.external.google.search

import scala.concurrent.Await
import org.specs2.mutable.Specification
import play.api.Configuration
import play.api.libs.json._
import play.api.mvc.Results.Ok
import play.api.routing.sird
import play.api.routing.sird._
import play.api.test.WsTestClient
import play.core.server.Server
import external.google.search.GoogleCustomSearchClient
import scala.concurrent.duration._


/**
 * Google の API を叩くクライアントのテスト
 *
 * 参考: wsのテスト
 * https://www.playframework.com/documentation/2.8.x/ScalaTestingWebServiceClients
 * https://scrapbox.io/utakata/ws%E3%81%A7%E4%BB%96%E3%82%B5%E3%83%BC%E3%83%93%E3%82%B9%E3%81%ABAPI%E3%83%AA%E3%82%AF%E3%82%A8%E3%82%B9%E3%83%88%E3%81%97%E3%81%A6%E3%81%84%E3%82%8B%E9%83%A8%E5%88%86%E3%81%AE%E3%83%86%E3%82%B9%E3%83%88
 * https://www.utakata.work/entry/scala/play-test-ws-client
 */
class GoogleCustomSearchClientTest extends Specification {

  "GoogleCustomSearch" should {
    val configuration = Configuration.from(Map(
      "google.baseUrl" -> "/customsearch/v1",
      "google.key" -> "googleapikey",
      "google.cx" -> "googleapicx",
    ))
    "GoogleAPIの結果を返せる" in {
      // Google の API サーバーをモックする
      Server.withRouterFromComponents() { components =>
        {
          case sird.GET(p"/customsearch/v1") => components.defaultActionBuilder {
            Ok(Json.parse("""
{
  "items": [
    {
      "displayLink":"https://displaylink",
      "htmlSnippet":"html snippet",
      "htmlTitle":"html title",
      "image":{
        "byteSize":10,
        "contextLink":"context link",
        "height":20,
        "width":30,
        "thumbnailHeight":40,
        "thumbnailWidth":50,
        "thumbnailLink":"thumbnail link"
      },
      "kind":"kaindo",
      "link":"https://link",
      "mime":"mime",
      "snippet":"snippeto",
      "title":"taitoru"
    }
  ]
}
                """))
          }
        }
      } { implicit port =>
        WsTestClient.withClient { wsClient =>
          val googleCustomSearchClient = new GoogleCustomSearchClient(
            wsClient,
            configuration
          )
          val result = Await.result(
            googleCustomSearchClient.urls("abc"),
            10.seconds
          )
          val expected = Some(List("https://link"))
          result must equalTo(expected)
        }
      }
    }
  }
}
