package utils

import java.io._
import java.net.URL
import scala.language.postfixOps

object ImageDownloader {

  def download(url: String, filePath: String) = {
    val stream = new URL(url).openStream
    val buf = Stream.continually(stream.read).takeWhile(-1 !=).map(_.byteValue).toArray
    val bw = new BufferedOutputStream(new FileOutputStream(filePath))
    bw.write(buf)
    bw.close
  }

  def binary(path: String): Array[Byte] = {
    val fis = new FileInputStream(path)
    Stream.continually(fis.read).takeWhile(-1 !=).map(_.byteValue).toArray
  }

}
