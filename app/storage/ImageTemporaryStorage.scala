package storage

import com.google.inject.Inject
import java.net.URL

import play.api.Configuration
import java.io.{BufferedOutputStream, File, FileInputStream, FileOutputStream, InputStream}

import file.FilePath
import image.{ConvertedImage, SourceImage}
import play.api.libs.Files.TemporaryFile

/** 画像のバイナリを一時的に保存するストレージ */
class ImageTemporaryStorage @Inject() (
  filePath: FilePath
) extends image.ImageTemporaryStorage {

  def sourceImage(id: Int): SourceImage = new SourceImage(
    id,
    filePath.sourceImagePath(id)
  )

  /**
   * URLから画像をダウンロードしてきて
   * 変換前の画像ストレージに保存する
   * @return 保存されたバイナリの絶対パス
   */
  def save(id: Int, url: String): SourceImage = {
    val downloadSrcStream = new URL(url).openStream
    val downloadSrcBinary = streamToBinary(downloadSrcStream)
    // ダウンロードされたファイルはLGTM画像変換のためのソース画像のPATHに入る
    val sourceImagePath = filePath.sourceImagePath(id)
    val downloadDestStream = new BufferedOutputStream(new FileOutputStream(sourceImagePath))
    downloadDestStream.write(downloadSrcBinary)
    downloadDestStream.close()
    downloadSrcStream.close()
    new SourceImage(id, sourceImagePath)
  }

  def save(id: Int, file: TemporaryFile): SourceImage = {
    val path = filePath.sourceImagePath(id)
    file.moveTo(new File(path))
    new SourceImage(id, path)
  }

  /** 変換後の画像のバイナリを取得する */
  def convertedBinary(convertedImage: ConvertedImage): Array[Byte] = {
    streamToBinary(new FileInputStream(convertedImage.path))
  }

  /** streamからデータを読んでバイナリを返す */
  def streamToBinary(stream: InputStream): Array[Byte] = {
    Stream.continually(stream.read).takeWhile(_ != -1).map(_.byteValue).toArray
  }
}
