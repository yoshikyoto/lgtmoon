package controllers.response

case class ImageResponse(val url: String)

class ImageResponseFactory {
  def create(urls: Seq[String]): Seq[ImageResponse] = {
    urls.map(url => ImageResponse(url))
  }
}