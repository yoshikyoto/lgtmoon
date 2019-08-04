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
  val srcImageDir: String = config.get[String]("storage.image.src.dir")

  /** URLから画像をダウンロードしてきて
   * 変換前の画像ストレージに保存する */
  def save(id: Int, url: String): Unit = {
    val srcStream = new URL(url).openStream
    val srcBuffer = Stream.continually(srcStream.read).takeWhile(-1 !=).map(_.byteValue).toArray
    val destPath = srcImageDir + "/" + id
    val destStream = new BufferedOutputStream(new FileOutputStream(destPath))
    destStream.write(srcBuffer)
    destStream.close()
    srcStream.close()
  }
}
