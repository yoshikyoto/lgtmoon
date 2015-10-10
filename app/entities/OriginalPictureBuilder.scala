package entities

import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import java.io._
import org.joda.time.DateTime

/**
 * OriginalPictureを生成するためのBuilder
 */
object OriginalPictureBuilder {

  /**
   * OriginalPictureオブジェクトを生成する
   * 
   * @param file: FilePart[TemporaryFile]
   */
  def createSerializableObject(file: FilePart[TemporaryFile]): OriginalPicture = {
    val fileInputStream = new FileInputStream(file.ref.file)
    OriginalPicture(
      "LGTM",
      60,
      file.filename,
      file.contentType.getOrElse(""),
      Stream.continually(fileInputStream.read).takeWhile(-1 !=).map(_.toByte).toArray,
      DateTime.now())
  }

}
