package entities

import java.sql.Timestamp

/**
 *  変換前の元画像
 * 
 * rabbitmqにシリアライズして積めるようにして、
 * imagemagickでの変換に必要な情報を入れる
 * 
 *  @param twitterId
 *  @param overlayText
 *  @param overlayTextSize
 *  @param fileName
 *  @param contentType
 *  @param pictureBinary
 *  @param sentDate
 */
@SerialVersionUID(1l)
case class OriginalPicture(
  overlayText: String,
  overlayTextSize: Int,
  fileName: String,
  contentType: String,
  binary: Array[Byte],
  uploadedAt: Timestamp)
