object Codegen extends {
  def main(args: Array[String]): Unit = {
    /**
      * Slick ドキュメント
      * https://scala-slick.org/doc/3.3.3/code-generation.html
      */
    slick.codegen.SourceCodeGenerator.main(Array(
      "slick.jdbc.PostgresProfile",
      "org.postgresql.Driver",

      // URL
      "jdbc:postgresql://localhost/lgtmoon",

      // アプリケーションのルートディレクトリ
      "../app",

      // 出力されるコードのパッケージ名
      // これでapp/database ディレクトリ以下に出力される
      "database",

      // DBのユーザー名
      "postgres",

      // パスワード
      "postgres",
    ))
  }
}
