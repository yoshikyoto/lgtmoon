package controllers

import javax.inject.Inject
import play.api.Configuration

class UrlBuilder @Inject() (
  config: Configuration
){
  val base = config.getString("image.baseUrl").get

  def image(id: Int): String = base + "/" +  id

  def images(ids: Seq[Int]): Seq[String] = ids.map(id => image(id))
}
