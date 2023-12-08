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
  lazy val schema: profile.SchemaDescription = Messages.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Messages
   *  @param messageId Database column message_id SqlType(serial), AutoInc, PrimaryKey
   *  @param fromUser Database column from_user SqlType(varchar), Length(20,true)
   *  @param content Database column content SqlType(varchar), Length(2000,true), Default(None)
   *  @param toUser Database column to_user SqlType(varchar), Length(20,true), Default(None)
   *  @param sentAt Database column sent_at SqlType(timestamp) */
  case class MessagesRow(messageId: Int, fromUser: String, content: Option[String] = None, toUser: Option[String] = None, sentAt: Option[java.sql.Timestamp])
  /** GetResult implicit for fetching MessagesRow objects using plain SQL queries */
  implicit def GetResultMessagesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[java.sql.Timestamp]]): GR[MessagesRow] = GR{
    prs => import prs._
    MessagesRow.tupled((<<[Int], <<[String], <<?[String], <<?[String], <<?[java.sql.Timestamp]))
  }
  /** Table description of table messages. Objects of this class serve as prototypes for rows in queries. */
  class Messages(_tableTag: Tag) extends profile.api.Table[MessagesRow](_tableTag, "messages") {
    def * = (messageId, fromUser, content, toUser, sentAt).<>(MessagesRow.tupled, MessagesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(messageId), Rep.Some(fromUser), content, toUser, sentAt)).shaped.<>({r=>import r._; _1.map(_=> MessagesRow.tupled((_1.get, _2.get, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column message_id SqlType(serial), AutoInc, PrimaryKey */
    val messageId: Rep[Int] = column[Int]("message_id", O.AutoInc, O.PrimaryKey)
    /** Database column from_user SqlType(varchar), Length(20,true) */
    val fromUser: Rep[String] = column[String]("from_user", O.Length(20,varying=true))
    /** Database column content SqlType(varchar), Length(2000,true), Default(None) */
    val content: Rep[Option[String]] = column[Option[String]]("content", O.Length(2000,varying=true), O.Default(None))
    /** Database column to_user SqlType(varchar), Length(20,true), Default(None) */
    val toUser: Rep[Option[String]] = column[Option[String]]("to_user", O.Length(20,varying=true), O.Default(None))
    /** Database column sent_at SqlType(timestamp) */
    val sentAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("sent_at")

    /** Foreign key referencing Users (database name messages_from_user_fkey) */
    lazy val usersFk1 = foreignKey("messages_from_user_fkey", fromUser, Users)(r => r.username, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Users (database name messages_to_user_fkey) */
    lazy val usersFk2 = foreignKey("messages_to_user_fkey", toUser, Users)(r => Rep.Some(r.username), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Messages */
  lazy val Messages = new TableQuery(tag => new Messages(tag))

  /** Entity class storing rows of table Users
   *  @param username Database column username SqlType(varchar), PrimaryKey, Length(20,true)
   *  @param password Database column password SqlType(varchar), Length(200,true) */
  case class UsersRow(username: String, password: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[String]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (username, password).<>(UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column username SqlType(varchar), PrimaryKey, Length(20,true) */
    val username: Rep[String] = column[String]("username", O.PrimaryKey, O.Length(20,varying=true))
    /** Database column password SqlType(varchar), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
