package infra.datasource

import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import slick.dbio.DBIOAction

object LgtmoonDatabase extends LgtmoonDatabaseTrait {
}

trait LgtmoonDatabaseTrait {

  val db = Database.forConfig("pg_database")

  def run[R](action: DBIOAction[R, NoStream, Nothing]): Future[R] =  db.run(action)

}
