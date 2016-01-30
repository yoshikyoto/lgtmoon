import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.duration._
import scala.concurrent._
import externals.google._


class CustomSearchAdapterTest extends Specification {
  "CustomSearchAdapter" should {
    "searchで検索結果が取得できる" in new WithApplication {
      val future = CustomSearchAdapter.search("ご注文はうさぎですか？");
      val itemsOpt = Await.result(future, 10 seconds)
      itemsOpt must not be None
    }

    "imagesで検索結果画像のURL一覧が取得できる" in new WithApplication {
      val future = CustomSearchAdapter.imageUrls("ご注文はうさぎですか？");
      val urlsOpt = Await.result(future, 10 seconds)
      urlsOpt must not be None
    }

    /*
    "serachで検索結果が無い場合は空配列が返ってくる" should {
      val future = CustomSearchAdapter.search("ご注文はうさぎですか？");
      val itemsOpt = Await.result(future, 10 seconds)
    }
     */
  }
}
