package repositories
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Picture.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Picture
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param fileName Database column file_name SqlType(varchar), Length(255,true)
   *  @param contentType Database column content_type SqlType(varchar), Length(32,true)
   *  @param createdAt Database column created_at SqlType(timestamp) */
  case class PictureRow(id: Long, fileName: String, contentType: String, createdAt: java.sql.Timestamp)
  /** GetResult implicit for fetching PictureRow objects using plain SQL queries */
  implicit def GetResultPictureRow(implicit e0: GR[Long], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[PictureRow] = GR{
    prs => import prs._
    PictureRow.tupled((<<[Long], <<[String], <<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table picture. Objects of this class serve as prototypes for rows in queries. */
  class Picture(_tableTag: Tag) extends Table[PictureRow](_tableTag, "picture") {
    def * = (id, fileName, contentType, createdAt) <> (PictureRow.tupled, PictureRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(fileName), Rep.Some(contentType), Rep.Some(createdAt)).shaped.<>({r=>import r._; _1.map(_=> PictureRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column file_name SqlType(varchar), Length(255,true) */
    val fileName: Rep[String] = column[String]("file_name", O.Length(255,varying=true))
    /** Database column content_type SqlType(varchar), Length(32,true) */
    val contentType: Rep[String] = column[String]("content_type", O.Length(32,varying=true))
    /** Database column created_at SqlType(timestamp) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
  }
  /** Collection-like TableQuery object for table Picture */
  lazy val Picture = new TableQuery(tag => new Picture(tag))
}
