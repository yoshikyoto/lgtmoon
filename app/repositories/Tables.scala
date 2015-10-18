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
  lazy val schema = Image.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Image
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param contentType Database column content_type SqlType(varchar), Length(32,true)
   *  @param createdAt Database column created_at SqlType(timestamp)
   *  @param status Database column status SqlType(int2)
   *  @param bin Database column bin SqlType(bytea), Default(None) */
  case class ImageRow(id: Long, contentType: String, createdAt: java.sql.Timestamp, status: Short, bin: Option[Array[Byte]] = None)
  /** GetResult implicit for fetching ImageRow objects using plain SQL queries */
  implicit def GetResultImageRow(implicit e0: GR[Long], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Short], e4: GR[Option[Array[Byte]]]): GR[ImageRow] = GR{
    prs => import prs._
    ImageRow.tupled((<<[Long], <<[String], <<[java.sql.Timestamp], <<[Short], <<?[Array[Byte]]))
  }
  /** Table description of table image. Objects of this class serve as prototypes for rows in queries. */
  class Image(_tableTag: Tag) extends Table[ImageRow](_tableTag, "image") {
    def * = (id, contentType, createdAt, status, bin) <> (ImageRow.tupled, ImageRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(contentType), Rep.Some(createdAt), Rep.Some(status), bin).shaped.<>({r=>import r._; _1.map(_=> ImageRow.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column content_type SqlType(varchar), Length(32,true) */
    val contentType: Rep[String] = column[String]("content_type", O.Length(32,varying=true))
    /** Database column created_at SqlType(timestamp) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column status SqlType(int2) */
    val status: Rep[Short] = column[Short]("status")
    /** Database column bin SqlType(bytea), Default(None) */
    val bin: Rep[Option[Array[Byte]]] = column[Option[Array[Byte]]]("bin", O.Default(None))

    /** Index over (createdAt) (database name image_created_at) */
    val index1 = index("image_created_at", createdAt)
  }
  /** Collection-like TableQuery object for table Image */
  lazy val Image = new TableQuery(tag => new Image(tag))
}
