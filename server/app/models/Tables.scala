package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile = slick.jdbc.PostgresProfile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Course.schema ++ Messages.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Course
   *  @param courseId Database column course_id SqlType(int4), PrimaryKey
   *  @param courseCode Database column course_code SqlType(varchar), Length(10,true), Default(None)
   *  @param courseName Database column course_name SqlType(varchar), Default(None)
   *  @param courseDescrp Database column course_descrp SqlType(varchar), Default(None) */
  case class CourseRow(courseId: Int, courseCode: Option[String] = None, courseName: Option[String] = None, courseDescrp: Option[String] = None)
  /** GetResult implicit for fetching CourseRow objects using plain SQL queries */
  implicit def GetResultCourseRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[CourseRow] = GR{
    prs => import prs._
    CourseRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table course. Objects of this class serve as prototypes for rows in queries. */
  class Course(_tableTag: Tag) extends profile.api.Table[CourseRow](_tableTag, "course") {
    def * = (courseId, courseCode, courseName, courseDescrp).<>(CourseRow.tupled, CourseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(courseId), courseCode, courseName, courseDescrp)).shaped.<>({r=>import r._; _1.map(_=> CourseRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column course_id SqlType(int4), PrimaryKey */
    val courseId: Rep[Int] = column[Int]("course_id", O.PrimaryKey)
    /** Database column course_code SqlType(varchar), Length(10,true), Default(None) */
    val courseCode: Rep[Option[String]] = column[Option[String]]("course_code", O.Length(10,varying=true), O.Default(None))
    /** Database column course_name SqlType(varchar), Default(None) */
    val courseName: Rep[Option[String]] = column[Option[String]]("course_name", O.Default(None))
    /** Database column course_descrp SqlType(varchar), Default(None) */
    val courseDescrp: Rep[Option[String]] = column[Option[String]]("course_descrp", O.Default(None))
  }
  /** Collection-like TableQuery object for table Course */
  lazy val Course = new TableQuery(tag => new Course(tag))

  /** Entity class storing rows of table Messages
   *  @param messageId Database column message_id SqlType(serial), AutoInc, PrimaryKey
   *  @param fromUserId Database column from_user_id SqlType(int4)
   *  @param content Database column content SqlType(varchar), Default(None)
   *  @param toUserId Database column to_user_id SqlType(int4), Default(None)
   *  @param sentAt Database column sent_at SqlType(timestamp) */
  case class MessagesRow(messageId: Int, fromUserId: Int, content: Option[String] = None, toUserId: Option[Int] = None, sentAt: Option[java.sql.Timestamp])
  /** GetResult implicit for fetching MessagesRow objects using plain SQL queries */
  implicit def GetResultMessagesRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[java.sql.Timestamp]]): GR[MessagesRow] = GR{
    prs => import prs._
    MessagesRow.tupled((<<[Int], <<[Int], <<?[String], <<?[Int], <<?[java.sql.Timestamp]))
  }
  /** Table description of table messages. Objects of this class serve as prototypes for rows in queries. */
  class Messages(_tableTag: Tag) extends profile.api.Table[MessagesRow](_tableTag, "messages") {
    def * = (messageId, fromUserId, content, toUserId, sentAt).<>(MessagesRow.tupled, MessagesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(messageId), Rep.Some(fromUserId), content, toUserId, sentAt)).shaped.<>({r=>import r._; _1.map(_=> MessagesRow.tupled((_1.get, _2.get, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column message_id SqlType(serial), AutoInc, PrimaryKey */
    val messageId: Rep[Int] = column[Int]("message_id", O.AutoInc, O.PrimaryKey)
    /** Database column from_user_id SqlType(int4) */
    val fromUserId: Rep[Int] = column[Int]("from_user_id")
    /** Database column content SqlType(varchar), Default(None) */
    val content: Rep[Option[String]] = column[Option[String]]("content", O.Default(None))
    /** Database column to_user_id SqlType(int4), Default(None) */
    val toUserId: Rep[Option[Int]] = column[Option[Int]]("to_user_id", O.Default(None))
    /** Database column sent_at SqlType(timestamp) */
    val sentAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("sent_at")

    /** Foreign key referencing Users (database name messages_from_user_id_fkey) */
    lazy val usersFk1 = foreignKey("messages_from_user_id_fkey", fromUserId, Users)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Users (database name messages_to_user_id_fkey) */
    lazy val usersFk2 = foreignKey("messages_to_user_id_fkey", toUserId, Users)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Messages */
  lazy val Messages = new TableQuery(tag => new Messages(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param username Database column username SqlType(varchar)
   *  @param password Database column password SqlType(varchar) */
  case class UsersRow(id: Int, username: String, password: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (id, username, password).<>(UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column username SqlType(varchar) */
    val username: Rep[String] = column[String]("username")
    /** Database column password SqlType(varchar) */
    val password: Rep[String] = column[String]("password")
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
