package controllers.response

case class ImageResponse(
  url: String,
  isConverted: Boolean
)

class ImageResponseFactory {
  def create(urls: Seq[String], isConverted: Boolean): Seq[ImageResponse] = {
    urls.map(url => ImageResponse(url, isConverted))
  }
}
