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
  // lazy val schema: profile.SchemaDescription = Student.schema ++ Faculty.schema ++ Course.schema ++ StudentCourses.schema
  // @deprecated("Use .schema instead of .ddl", "3.0")
  // def ddl = schema

  /** Entity class storing rows of table Student
   *  @param student_id Database column item_id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column user_id SqlType(int4)
   *  @param username
   *  @param password */
  case class StudentRow(student_id: Int, name: String, username: String, password: String)
  /** GetResult implicit for fetching ItemsRow objects using plain SQL queries */
  implicit def GetResultStudentRow(implicit e0: GR[Int], e1: GR[String]): GR[StudentRow] = GR{
    prs => import prs._
    StudentRow.tupled((<<[Int], <<[String], <<[String], <<[String]))
  }

   /** Table description of table items. Objects of this class serve as prototypes for rows in queries. */
   class Student(_tableTag: Tag) extends profile.api.Table[StudentRow](_tableTag, "student") {
     def * = (student_id, name, username, password) <> (StudentRow.tupled, StudentRow.unapply)
     /** Maps whole row to an option. Useful for outer joins. */
     def ? = ((Rep.Some(student_id), Rep.Some(name), Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> StudentRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column item_id SqlType(serial), AutoInc, PrimaryKey */
    val student_id: Rep[Int] = column[Int]("student_id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(int4) */
    val name: Rep[String] = column[String]("name", O.Length(40))
    /** Database column text SqlType(varchar), Length(2000,true) */
    val username: Rep[String] = column[String]("username", O.Length(20))
    val password: Rep[String] = column[String]("password", O.Length(200))

     /** Foreign key referencing Users (database name items_user_id_fkey) */
//     lazy val usersFk = foreignKey("items_user_id_fkey", userId, Users)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
   }
//   /** Collection-like TableQuery object for table Student */
   lazy val Student = new TableQuery(tag => new Student(tag))

  /** Entity class storing rows of table Faculty
   *  @param faculty_id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param name 
   *  @param username Database column username SqlType(varchar), Length(20,true)
   *  @param password Database column password SqlType(varchar), Length(200,true) */
  case class FacultyRow(faculty_id: Int, name: String, username: String, password: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultFacultyRow(implicit e0: GR[Int], e1: GR[String]): GR[FacultyRow] = GR{
    prs => import prs._
    FacultyRow.tupled((<<[Int], <<[String], <<[String], <<[String]))
  }
   /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
   class Faculty(_tableTag: Tag) extends profile.api.Table[FacultyRow](_tableTag, "faculty") {
     def * = (faculty_id, name, username, password) <> (FacultyRow.tupled, FacultyRow.unapply)
     /** Maps whole row to an option. Useful for outer joins. */
     def ? = ((Rep.Some(faculty_id), Rep.Some(name), Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> FacultyRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

     /** Database column id SqlType(serial), AutoInc, PrimaryKey */
     val faculty_id: Rep[Int] = column[Int]("faculty_id", O.AutoInc, O.PrimaryKey)
     val name: Rep[String] = column[String]("name", O.Length(40))
     /** Database column username SqlType(varchar), Length(20,true) */
     val username: Rep[String] = column[String]("username", O.Length(20,varying=true))
     /** Database column password SqlType(varchar), Length(200,true) */
     val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
   }
   /** Collection-like TableQuery object for table Users */
   lazy val Faculty = new TableQuery(tag => new Faculty(tag))

   /** Entity class storing rows of table Course
   *  @param course_id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param course_name 
   *  @param course_number
   *  @param faculty_id */
  case class CourseRow(course_id: Int, course_name: String, course_number: String, faculty_id: Int)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultCourseRow(implicit e0: GR[Int], e1: GR[String]): GR[CourseRow] = GR{
    prs => import prs._
    CourseRow.tupled((<<[Int], <<[String], <<[String], <<[Int]))
  }
   /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
   class Course(_tableTag: Tag) extends profile.api.Table[CourseRow](_tableTag, "course") {
     def * = (course_id, course_name, course_number, faculty_id) <> (CourseRow.tupled, CourseRow.unapply)
     /** Maps whole row to an option. Useful for outer joins. */
     def ? = ((Rep.Some(course_id), Rep.Some(course_name), Rep.Some(course_number), Rep.Some(faculty_id))).shaped.<>({r=>import r._; _1.map(_=> CourseRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

     /** Database column id SqlType(serial), AutoInc, PrimaryKey */
     val course_id: Rep[Int] = column[Int]("course_id", O.AutoInc, O.PrimaryKey)
     val course_name: Rep[String] = column[String]("course_name", O.Length(100))
     /** Database column username SqlType(varchar), Length(20,true) */
     val course_number: Rep[String] = column[String]("course_number", O.Length(9))
     /** Database column password SqlType(varchar), Length(200,true) */
     val faculty_id: Rep[Int] = column[Int]("faculty_id")

     lazy val courseFk = foreignKey("course_faculty_id_fkey", faculty_id, Faculty)(r => r.faculty_id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
   }
   /** Collection-like TableQuery object for table Users */
   lazy val Course = new TableQuery(tag => new Course(tag))

   /** Entity class storing rows of table StudentCourses
   *  @param student_id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param course_id 
   *  @param rating */
  case class StudentCoursesRow(student_id: Int, course_id: Int, rating: Int)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultStudentCoursesRow(implicit e0: GR[Int]): GR[StudentCoursesRow] = GR{
    prs => import prs._
    StudentCoursesRow.tupled((<<[Int], <<[Int], <<[Int]))
  }
   /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
   class StudentCourses(_tableTag: Tag) extends profile.api.Table[StudentCoursesRow](_tableTag, "studentcourses") {
     def * = (student_id, course_id, rating) <> (StudentCoursesRow.tupled, StudentCoursesRow.unapply)
     /** Maps whole row to an option. Useful for outer joins. */
     def ? = ((Rep.Some(student_id), Rep.Some(course_id), Rep.Some(rating))).shaped.<>({r=>import r._; _1.map(_=> StudentCoursesRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

     /** Database column id SqlType(serial), AutoInc, PrimaryKey */
     val student_id: Rep[Int] = column[Int]("student_id", O.PrimaryKey)
     val course_id: Rep[Int] = column[Int]("course_id", O.PrimaryKey)
     /** Database column username SqlType(varchar), Length(20,true) */
     val rating: Rep[Int] = column[Int]("rating")
     /** Database column password SqlType(varchar), Length(200,true) */

     lazy val studentCoursesFk1 = foreignKey("studentCourses_student_id_fkey", student_id, Student)(r => r.student_id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
     lazy val studentCoursesFk2 = foreignKey("studentCourses_course_id_fkey", course_id, Course)(r => r.course_id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
   }
   /** Collection-like TableQuery object for table Users */
   lazy val studentCourses = new TableQuery(tag => new StudentCourses(tag))

 }
