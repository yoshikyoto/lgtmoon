package util

/**
 * ランダムな文字列を生成する
 */
object RandomString {
  def generate(): String =
    new scala.util.Random(new java.security.SecureRandom()).alphanumeric.take(16).mkString
}
