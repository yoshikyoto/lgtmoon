object Codegen extends {
  def main(args: Array[String]): Unit = {
    val slickDriver = "slick.driver.PostgresDriver"
    val jdbcDriver = "org.postgresql.Driver"
    val url = "jdbc:postgresql://localhost/lgtmoon"
    val outputDir = "../app"
    val pkg = "repositories"
    val user = "yoshiyuki_sakamoto"
    val password = ""
    slick.codegen.SourceCodeGenerator.main(
      Array(slickDriver, jdbcDriver, url, outputDir, pkg, user, password)
    )
  }
}
