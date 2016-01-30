package externals.google

/** 未使用 */
case class SearchInformation(
  val formattedSearchTime: String,
  val formattedTotalResults: String,
  val searchTime: Double,
  val totalResults: String)

/** 未使用 */
case class Query(
  val count: Int,
  val cx: String,
  val inputEncoding: String,
  val outputEncoding: String,
  val saf: String,
  val searchTerms: String,
  val searchType: String,
  val startIndex: Int,
  val title: String,
  val totalResults: String)

/** 未使用 */
case class Context(val title: String)

case class Item(
  val displayLink: String,
  val htmlSnippet: String,
  val htmlTitle: String,
  val image: Image,
  val kind: String,
  val link: String,
  val mime: String,
  val snippet: String,
  val title: String)

case class Image(
  val byteSize:Int,
  val contextLink: String,
  val height: Int,
  val width: Int,
  val thumbnailHeight: Int,
  val thumbnailWidth: Int,
  val thumbnailLink: String)
