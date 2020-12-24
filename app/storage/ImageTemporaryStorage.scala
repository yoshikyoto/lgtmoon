package storage

import com.google.inject.Inject
import java.net.URL

import play.api.Configuration
import java.io.{BufferedOutputStream, FileInputStream, FileOutputStream, InputStream}

import image.{ConvertedImage, SourceImage}

/** 画像のバイナリを一時的に保存するストレージ */
class ImageTemporaryStorage @Inject() (
  config: Configuration
) extends image.ImageTemporaryStorage {
  /** 変換前の画像が保存されるパス */
  val srcDir: String = config.get[String]("storage.image.src.dir")

  /**
   * 変換後の画像が保存されるパス
   * @see command.ImageMagick で使われている
   */
  val destDir: String = config.get[String]("storage.image.dest.dir")

  def srcPath(id: Int): String = srcDir + "/" + id

  def destPath(id: Int): String = destDir + "/" + id

  def sourceImage(id: Int): SourceImage = new SourceImage(id, srcPath(id))

  /** URLから画像をダウンロードしてきて
   * 変換前の画像ストレージに保存する
   * @return 保存されたバイナリの絶対パス */
  def save(id: Int, url: String): SourceImage = {
    val downloadSrcStream = new URL(url).openStream
    val downloadSrcBinary = streamToBinary(downloadSrcStream)
    // ダウンロードされたファイルはLGTM画像変換のためのソース画像のPATHに入る
    val downloadDestStream = new BufferedOutputStream(new FileOutputStream(srcPath(id)))
    downloadDestStream.write(downloadSrcBinary)
    downloadDestStream.close()
    downloadSrcStream.close()
    new SourceImage(id, srcPath(id))
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
