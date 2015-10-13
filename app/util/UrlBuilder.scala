package utils

import play.api.Play

object UrlBuilder {
  val host = Play.current.configuration.getString("storage.hostName")
    .getOrElse("localhost")
  val path = Play.current.configuration.getString("storage.path")
    .getOrElse("")

  def imageUrl(fileName: String): String = {
    "http://" + host + path + "/" +  fileName
  }
}
