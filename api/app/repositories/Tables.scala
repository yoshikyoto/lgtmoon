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
  lazy val schema = Image.schema ++ Picture.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Image
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param contentType Database column content_type SqlType(varchar), Length(32,true)
   *  @param createdAt Database column created_at SqlType(timestamp)
   *  @param status Database column status SqlType(int2) */
  case class ImageRow(id: Long, contentType: String, createdAt: java.sql.Timestamp, status: Short)
  /** GetResult implicit for fetching ImageRow objects using plain SQL queries */
  implicit def GetResultImageRow(implicit e0: GR[Long], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Short]): GR[ImageRow] = GR{
    prs => import prs._
    ImageRow.tupled((<<[Long], <<[String], <<[java.sql.Timestamp], <<[Short]))
  }
  /** Table description of table image. Objects of this class serve as prototypes for rows in queries. */
  class Image(_tableTag: Tag) extends Table[ImageRow](_tableTag, "image") {
    def * = (id, contentType, createdAt, status) <> (ImageRow.tupled, ImageRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(contentType), Rep.Some(createdAt), Rep.Some(status)).shaped.<>({r=>import r._; _1.map(_=> ImageRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column content_type SqlType(varchar), Length(32,true) */
    val contentType: Rep[String] = column[String]("content_type", O.Length(32,varying=true))
    /** Database column created_at SqlType(timestamp) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column status SqlType(int2) */
    val status: Rep[Short] = column[Short]("status")

    /** Index over (createdAt) (database name image_created_at) */
    val index1 = index("image_created_at", createdAt)
  }
  /** Collection-like TableQuery object for table Image */
  lazy val Image = new TableQuery(tag => new Image(tag))

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

    /** Index over (createdAt) (database name picture_created_at) */
    val index1 = index("picture_created_at", createdAt)
  }
  /** Collection-like TableQuery object for table Picture */
  lazy val Picture = new TableQuery(tag => new Picture(tag))
}
