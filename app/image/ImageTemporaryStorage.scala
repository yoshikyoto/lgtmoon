package image

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[storage.ImageTemporaryStorage])
trait ImageTemporaryStorage {
  def srcPath(id: Int): String
}
