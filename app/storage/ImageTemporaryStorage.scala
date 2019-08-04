package storage

import com.google.inject.Inject
import java.net.URL
import play.api.Configuration
import java.io.{
  BufferedOutputStream,
  FileOutputStream
}

/** 画像のバイナリを一時的に保存するストレージ */
class ImageTemporaryStorage @Inject() (
  config: Configuration
){
  /** 変換前の画像が保存されるパス */
  val srcDir: String = config.get[String]("storage.image.src.dir")

  /** 変換後の画像が保存されるパス */
  val destDir: String = config.get[String]("storage.image.dest.dir")

  def srcPath(id: Int): String = srcDir + "/" + id

  def destPath(id: Int): String = destDir + "/" + id

  /** URLから画像をダウンロードしてきて
   * 変換前の画像ストレージに保存する
   * @return 保存されたバイナリの絶対パス */
  def save(id: Int, url: String): String = {
    val downloadSrcStream = new URL(url).openStream
    val downloadSrcBuffer = Stream.continually(downloadSrcStream.read).takeWhile(-1 !=).map(_.byteValue).toArray
    // ダウンロードされたファイルはLGTM画像変換のためのソース画像のPATHに入る
    val downloadDestPath = srcPath(id)
    val downloadDestStream = new BufferedOutputStream(new FileOutputStream(downloadDestPath))
    downloadDestStream.write(downloadSrcBuffer)
    downloadDestStream.close()
    downloadSrcStream.close()
    return downloadDestPath
  }

}
