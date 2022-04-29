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
  lazy val schema: profile.SchemaDescription = Course.schema ++ Faculty.schema ++ Student.schema ++ Studentcourses.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Course
   *  @param courseId Database column course_id SqlType(serial), AutoInc, PrimaryKey
   *  @param courseName Database column course_name SqlType(varchar), Length(100,true)
   *  @param courseNumber Database column course_number SqlType(varchar), Length(9,true)
   *  @param facultyId Database column faculty_id SqlType(int4) */
  case class CourseRow(courseId: Int, courseName: String, courseNumber: String, facultyId: Int)
  /** GetResult implicit for fetching CourseRow objects using plain SQL queries */
  implicit def GetResultCourseRow(implicit e0: GR[Int], e1: GR[String]): GR[CourseRow] = GR{
    prs => import prs._
    CourseRow.tupled((<<[Int], <<[String], <<[String], <<[Int]))
  }
  /** Table description of table course. Objects of this class serve as prototypes for rows in queries. */
  class Course(_tableTag: Tag) extends profile.api.Table[CourseRow](_tableTag, "course") {
    def * = (courseId, courseName, courseNumber, facultyId) <> (CourseRow.tupled, CourseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(courseId), Rep.Some(courseName), Rep.Some(courseNumber), Rep.Some(facultyId))).shaped.<>({r=>import r._; _1.map(_=> CourseRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column course_id SqlType(serial), AutoInc, PrimaryKey */
    val courseId: Rep[Int] = column[Int]("course_id", O.AutoInc, O.PrimaryKey)
    /** Database column course_name SqlType(varchar), Length(100,true) */
    val courseName: Rep[String] = column[String]("course_name", O.Length(100,varying=true))
    /** Database column course_number SqlType(varchar), Length(9,true) */
    val courseNumber: Rep[String] = column[String]("course_number", O.Length(9,varying=true))
    /** Database column faculty_id SqlType(int4) */
    val facultyId: Rep[Int] = column[Int]("faculty_id")

    /** Foreign key referencing Faculty (database name course_faculty_id_fkey) */
    lazy val facultyFk = foreignKey("course_faculty_id_fkey", facultyId, Faculty)(r => r.facultyId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Course */
  lazy val Course = new TableQuery(tag => new Course(tag))

  /** Entity class storing rows of table Faculty
   *  @param facultyId Database column faculty_id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(40,true)
   *  @param username Database column username SqlType(varchar), Length(20,true)
   *  @param password Database column password SqlType(varchar), Length(200,true) */
  case class FacultyRow(facultyId: Int, name: String, username: String, password: String)
  /** GetResult implicit for fetching FacultyRow objects using plain SQL queries */
  implicit def GetResultFacultyRow(implicit e0: GR[Int], e1: GR[String]): GR[FacultyRow] = GR{
    prs => import prs._
    FacultyRow.tupled((<<[Int], <<[String], <<[String], <<[String]))
  }
  /** Table description of table faculty. Objects of this class serve as prototypes for rows in queries. */
  class Faculty(_tableTag: Tag) extends profile.api.Table[FacultyRow](_tableTag, "faculty") {
    def * = (facultyId, name, username, password) <> (FacultyRow.tupled, FacultyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(facultyId), Rep.Some(name), Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> FacultyRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column faculty_id SqlType(serial), AutoInc, PrimaryKey */
    val facultyId: Rep[Int] = column[Int]("faculty_id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(40,true) */
    val name: Rep[String] = column[String]("name", O.Length(40,varying=true))
    /** Database column username SqlType(varchar), Length(20,true) */
    val username: Rep[String] = column[String]("username", O.Length(20,varying=true))
    /** Database column password SqlType(varchar), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
  }
  /** Collection-like TableQuery object for table Faculty */
  lazy val Faculty = new TableQuery(tag => new Faculty(tag))

  /** Entity class storing rows of table Student
   *  @param studentId Database column student_id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(40,true)
   *  @param username Database column username SqlType(varchar), Length(20,true)
   *  @param password Database column password SqlType(varchar), Length(200,true) */
  case class StudentRow(studentId: Int, name: String, username: String, password: String)
  /** GetResult implicit for fetching StudentRow objects using plain SQL queries */
  implicit def GetResultStudentRow(implicit e0: GR[Int], e1: GR[String]): GR[StudentRow] = GR{
    prs => import prs._
    StudentRow.tupled((<<[Int], <<[String], <<[String], <<[String]))
  }
  /** Table description of table student. Objects of this class serve as prototypes for rows in queries. */
  class Student(_tableTag: Tag) extends profile.api.Table[StudentRow](_tableTag, "student") {
    def * = (studentId, name, username, password) <> (StudentRow.tupled, StudentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(studentId), Rep.Some(name), Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> StudentRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column student_id SqlType(serial), AutoInc, PrimaryKey */
    val studentId: Rep[Int] = column[Int]("student_id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(40,true) */
    val name: Rep[String] = column[String]("name", O.Length(40,varying=true))
    /** Database column username SqlType(varchar), Length(20,true) */
    val username: Rep[String] = column[String]("username", O.Length(20,varying=true))
    /** Database column password SqlType(varchar), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
  }
  /** Collection-like TableQuery object for table Student */
  lazy val Student = new TableQuery(tag => new Student(tag))

  /** Entity class storing rows of table Studentcourses
   *  @param studentId Database column student_id SqlType(int4)
   *  @param courseId Database column course_id SqlType(int4)
   *  @param rating Database column rating SqlType(int4), Default(None) */
  case class StudentcoursesRow(studentId: Int, courseId: Int, rating: Option[Int] = None)
  /** GetResult implicit for fetching StudentcoursesRow objects using plain SQL queries */
  implicit def GetResultStudentcoursesRow(implicit e0: GR[Int], e1: GR[Option[Int]]): GR[StudentcoursesRow] = GR{
    prs => import prs._
    StudentcoursesRow.tupled((<<[Int], <<[Int], <<?[Int]))
  }
  /** Table description of table studentcourses. Objects of this class serve as prototypes for rows in queries. */
  class Studentcourses(_tableTag: Tag) extends profile.api.Table[StudentcoursesRow](_tableTag, "studentcourses") {
    def * = (studentId, courseId, rating) <> (StudentcoursesRow.tupled, StudentcoursesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(studentId), Rep.Some(courseId), rating)).shaped.<>({r=>import r._; _1.map(_=> StudentcoursesRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column student_id SqlType(int4) */
    val studentId: Rep[Int] = column[Int]("student_id")
    /** Database column course_id SqlType(int4) */
    val courseId: Rep[Int] = column[Int]("course_id")
    /** Database column rating SqlType(int4), Default(None) */
    val rating: Rep[Option[Int]] = column[Option[Int]]("rating", O.Default(None))

    /** Primary key of Studentcourses (database name studentcourses_pkey) */
    val pk = primaryKey("studentcourses_pkey", (studentId, courseId))

    /** Foreign key referencing Course (database name studentcourses_course_id_fkey) */
    lazy val courseFk = foreignKey("studentcourses_course_id_fkey", courseId, Course)(r => r.courseId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Student (database name studentcourses_student_id_fkey) */
    lazy val studentFk = foreignKey("studentcourses_student_id_fkey", studentId, Student)(r => r.studentId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Studentcourses */
  lazy val Studentcourses = new TableQuery(tag => new Studentcourses(tag))
}
