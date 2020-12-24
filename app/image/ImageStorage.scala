package image

import com.google.inject.ImplementedBy

/**
 * 画像のバイナリデータを保存するリポジトリ
 */
@ImplementedBy(classOf[storage.ImageStorage])
trait ImageStorage {
  def save(id: Int, localAbsolutePath: String): Unit
}
