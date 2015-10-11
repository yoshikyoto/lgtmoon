package entities

import java.io._
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
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

  /**
   * OriginalPictureをbytearrayに変換する
   * 
   * TODO これはBuilderではないが...OriginalPictureに持たせる？
   * 
   * @param originalPicture: OriginalPicture
   * 
   * @return Array[Byte]
   */
  def toByteArray(originalPicture: OriginalPicture): Array[Byte] = {
    val byteArrayOutputStream = new ByteArrayOutputStream(1024)
    val objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(originalPicture)
    val binary = byteArrayOutputStream.toByteArray
    objectOutputStream.close()
    binary
  }

}
