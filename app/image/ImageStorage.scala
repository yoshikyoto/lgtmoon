package image

import com.google.inject.ImplementedBy

/**
 * 画像のバイナリデータを保存するリポジトリ
 */
@ImplementedBy(classOf[storage.ImageS3])
trait ImageStorage {
  def save(convertedImage: ConvertedImage): Unit
}
