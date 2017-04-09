package test.domain.image

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.duration._
import scala.concurrent._
import domain.image._

class ImageRepositoryTest extends Specification {
  "ImageRepository" should {
    "全てのAVAILABLEな画像IDを取得できる" in new WithApplication {
      val future = ImageRepository.randomIds()
      val result = Await.result(future, 10 seconds).get
      println(result)
    }
  }
}
