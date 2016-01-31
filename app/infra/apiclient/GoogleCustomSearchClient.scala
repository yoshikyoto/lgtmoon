package infra.apiclient

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/** GoogleCustomSearchのAPIを叩くクライアント */
object GoogleCustomSearchClient extends GoogleCustomSearchClientTrait {
  override val baseUrl = Play.current.configuration.getString("google.baseUrl").get
  override val key = Play.current.configuration.getString("google.key").get
  override val cx = Play.current.configuration.getString("google.cx").get
}

trait GoogleCustomSearchClientTrait {
  val baseUrl = "https://www.googleapis.com/customsearch/v1"
  val key = ""
  val cx = ""
  val searchType = "image"

  /**
   * 画像検索を行う
   * @param keyword: String 検索キーワード
   * @return Future[WSResponse]
   * TODO 画像検索以外が行いたい場合は、searchTypeも引数として受け取るようにする
   */
  def search(keyword: String): Future[WSResponse] = {
    WS.url(baseUrl)
      .withQueryString("searchType" -> searchType)
      .withQueryString("key" -> key)
      .withQueryString("cx" -> cx)
      .withQueryString("q" -> keyword)
      .get()
  }
}
