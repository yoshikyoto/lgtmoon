object Codegen extends {
  def main(args: Array[String]): Unit = {
    val slickDriver = "slick.driver.PostgresDriver"
    val jdbcDriver = "org.postgresql.Driver"
    val url = "jdbc:postgresql://localhost/lgtmoon"
    val outputDir = "../api/app"
    val pkg = "repositories"
    val user = "postgres"
    val password = "postgres"
    slick.codegen.SourceCodeGenerator.main(
      Array(slickDriver, jdbcDriver, url, outputDir, pkg, user, password)
    )
  }
}
