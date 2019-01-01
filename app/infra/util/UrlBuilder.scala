package infra.util

import play.api.Play

object UrlBuilder extends UrlBuilderTrait {
}

trait UrlBuilderTrait {
  val host = Play.current.configuration.getString("storage.hostName").getOrElse("localhost")
  val path = Play.current.configuration.getString("storage.path").getOrElse("")

  def imageUrl(fileName: String): String = {
    "http://" + host + path + "/" +  fileName
  }
}
