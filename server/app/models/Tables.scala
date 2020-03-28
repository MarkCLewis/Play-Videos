package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Items.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Items
   *  @param itemId Database column item_id SqlType(serial), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(int4), Default(None)
   *  @param text Database column text SqlType(varchar), Length(2000,true), Default(None) */
  case class ItemsRow(itemId: Int, userId: Option[Int] = None, text: Option[String] = None)
  /** GetResult implicit for fetching ItemsRow objects using plain SQL queries */
  implicit def GetResultItemsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]]): GR[ItemsRow] = GR{
    prs => import prs._
    ItemsRow.tupled((<<[Int], <<?[Int], <<?[String]))
  }
  /** Table description of table items. Objects of this class serve as prototypes for rows in queries. */
  class Items(_tableTag: Tag) extends profile.api.Table[ItemsRow](_tableTag, "items") {
    def * = (itemId, userId, text) <> (ItemsRow.tupled, ItemsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(itemId), userId, text)).shaped.<>({r=>import r._; _1.map(_=> ItemsRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column item_id SqlType(serial), AutoInc, PrimaryKey */
    val itemId: Rep[Int] = column[Int]("item_id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(int4), Default(None) */
    val userId: Rep[Option[Int]] = column[Option[Int]]("user_id", O.Default(None))
    /** Database column text SqlType(varchar), Length(2000,true), Default(None) */
    val text: Rep[Option[String]] = column[Option[String]]("text", O.Length(2000,varying=true), O.Default(None))

    /** Foreign key referencing Users (database name items_user_id_fkey) */
    lazy val usersFk = foreignKey("items_user_id_fkey", userId, Users)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Items */
  lazy val Items = new TableQuery(tag => new Items(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param username Database column username SqlType(varchar), Length(20,true)
   *  @param password Database column password SqlType(varchar), Length(200,true) */
  case class UsersRow(id: Int, username: String, password: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (id, username, password) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column username SqlType(varchar), Length(20,true) */
    val username: Rep[String] = column[String]("username", O.Length(20,varying=true))
    /** Database column password SqlType(varchar), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
