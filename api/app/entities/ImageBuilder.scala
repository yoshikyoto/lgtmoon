package entities

import java.io._

object ImageEntityBuilder {
  def toByteArray(image: ImageEntity): Array[Byte] = {
    val byteArrayOutputStream = new ByteArrayOutputStream(1024)
    val objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(image)
    val binary = byteArrayOutputStream.toByteArray
    objectOutputStream.close()
    binary
  }
}
