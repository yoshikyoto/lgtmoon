package image

import com.google.inject.ImplementedBy

/**
 * LGTMの文字を入れる前の画像を一時的に保存する
 */
@ImplementedBy(classOf[storage.ImageTemporaryStorage])
trait ImageTemporaryStorage {
  def srcPath(id: Int): String

  /**
   * 画像のIDを渡すと、一時的に保存されている元画像を取得する
   * @param id 画像ID
   * @return
   */
  def sourceImage(id: Int): SourceImage
}
