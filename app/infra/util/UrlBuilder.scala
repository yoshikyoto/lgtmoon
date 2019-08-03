package infra.util

import play.api.Play

object UrlBuilder extends UrlBuilderTrait {
}

trait UrlBuilderTrait {
  val base = Play.current.configuration.getString("image.baseUrl").getOrElse("http://localhost:9000/images")

  def imageUrl(fileName: String): String = {
    base + "/" +  fileName
  }
}
