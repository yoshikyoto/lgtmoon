package utils

/**
 * ランダムな文字列を生成する
 */
object RandomString {

  /**
   * ランダムな文字列を生成する
   * 
   * @return String ランダム文字列
   */
  def generate(length: Int = 16): String = {
    new scala.util.Random(new java.security.SecureRandom()).alphanumeric.take(length).mkString
  }

}
