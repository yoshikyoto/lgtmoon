package utils

import java.io._
import play.api.Play
import entities.OriginalPicture

/**
 * ファイルIOを行うためのtarit
 */
object FileStorage extends FileStorageTrait {
}

trait FileStorageTrait {
  val originalPictureDir = Play.current.configuration.getString("imagemagick.originalPictureDir")
    .getOrElse("/tmp")
  val convertedPictureDir = Play.current.configuration.getString("imagemagick.convertedPictureDir").get



  /**
   * originalPictureを保存する
   */
  def saveOriginalPicture(originalPicture: OriginalPicture): String = {
    val originalFilePath = originalPicturePath(originalPicture.fileName)
    val originalFile = new File(originalFilePath)
    val outputStream = new FileOutputStream(originalFile)
    outputStream.write(originalPicture.binary)
    outputStream.close
    originalFilePath
  }

  /**
   * 元画像のファイルのパスを取得する
   */
  def originalPicturePath(fileName: String): String = {
    originalPictureDir + "/" + fileName
  }

  /**
   * 変換後の画像のパスを取得する
   */
  def convertedPicturePath(fileName: String): String = {
    convertedPictureDir + "/" + fileName
  }

}
