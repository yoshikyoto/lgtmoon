package utils

import java.io.{FileOutputStream, BufferedOutputStream}
import java.net.URL
import scala.language.postfixOps

object ImageDownloader {

  def download(url: String, filePath: String) = {
    val stream = new URL(url).openStream
    val buf = Stream.continually(stream.read).takeWhile( -1 != ).map(_.byteValue).toArray
    val bw = new BufferedOutputStream(new FileOutputStream(filePath))
    bw.write(buf)
    bw.close
  }

}
