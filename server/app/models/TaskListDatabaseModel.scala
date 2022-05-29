package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext
import models.Tables._
import scala.concurrent.Future
import org.mindrot.jbcrypt.BCrypt


//This class accesses the database and returns data to the controllers
class TaskListDatabaseModel(db: Database)(implicit ec: ExecutionContext) {

  //function to validate a student logging in
  def validateStudent(username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Student.filter(studentRow => studentRow.username === username).result)
    matches.map(studentRows => studentRows.headOption.flatMap {
      studentRow => if (BCrypt.checkpw(password, studentRow.password)) Some(studentRow.studentId) else None
    })
  }
  //function to validate a faculty logging in 
  def validateFaculty(username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Faculty.filter(userRow => userRow.username === username).result)
    matches.map(userRows => userRows.headOption.flatMap {
      userRow => if (BCrypt.checkpw(password, userRow.password)) Some(userRow.facultyId) else None
    })
  }
  
  //function to create a new student user
  def createStudentUser(name: String, username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Student.filter(studentRow => studentRow.username === username).result)
    matches.flatMap { studentRows =>
      if (studentRows.isEmpty) {
        db.run(Student += StudentRow(-1, name, username, BCrypt.hashpw(password, BCrypt.gensalt())))
          .flatMap { addCount => 
            if (addCount > 0) db.run(Student.filter(studentRow => studentRow.username === username).result)
              .map(_.headOption.map(_.studentId))
            else Future.successful(None)
          }
      } else Future.successful(None)
    }
  }
  //function to create a new faculty user
  def createFacultyUser(name: String, username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Faculty.filter(facultyRow => facultyRow.username === username).result)
    matches.flatMap { facultyRows =>
      if (facultyRows.isEmpty) {
        db.run(Faculty += FacultyRow(-1, name, username, BCrypt.hashpw(password, BCrypt.gensalt())))
          .flatMap { addCount => 
            if (addCount > 0) db.run(Faculty.filter(facultyRow => facultyRow.username === username).result)
              .map(_.headOption.map(_.facultyId))
            else Future.successful(None)
          }
      } else Future.successful(None)
    }
  }
  
  def getAllCourses(): Future[Option[Seq[CourseRow]]] = {
    val matches = db.run(Course.result)
    matches.flatMap { coursesRows => 
      Future.successful(Some(coursesRows))
    }
  }

  //function to remove a course from a student's course list
  def removeCourse(studentID:Int, courseID:Int): Future[Boolean] = {
    db.run( Studentcourses.filter(studentcoursesRow => studentcoursesRow.studentId === studentID && studentcoursesRow.courseId === courseID).delete ).map(count => count > 0)
  }

  //fucntion to return a list of the student's courses
  def getStudentCourses(studentID: Int): Future[Vector[CourseRow]]  = {
    val matches = db.run(sql"""select Course.course_id, Course.course_name, Course.course_number, Course.faculty_id from Course inner join Studentcourses on Course.course_id = Studentcourses.course_id where Studentcourses.student_id = $studentID""".as[CourseRow])
    matches
  }

  //fucntion to return a list of the faculty's courses
  def getFacultyCourses(facultyId: Int): Future[Option[Seq[CourseRow]]]  = {
    val matches = db.run(Course.filter(coursesRow => coursesRow.facultyId === facultyId).result)
    matches.flatMap { coursesRows => 
      Future.successful(Some(coursesRows))
    }
  }
  
  //fucntion to add a course to a student's course list
  def addCourse(studentID: Int, courseID:Int): Future[Option[Int]] = {
    val matches = db.run(Studentcourses.filter(studentcoursesRow => studentcoursesRow.courseId === courseID && studentcoursesRow.studentId === studentID).result)
    matches.flatMap { studentcoursesRows =>
      if (studentcoursesRows.isEmpty) {
        db.run(Studentcourses += StudentcoursesRow(studentID, courseID))
          .flatMap { addCount => 
            if (addCount > 0) db.run(Studentcourses.filter(studentcoursesRow => (studentcoursesRow.courseId === courseID && studentcoursesRow.studentId === studentID)).result)
              .map(_.headOption.map(_.studentId))
            else Future.successful(None)
          }
      } else Future.successful(None)
    }
  }


  //function to add a rating to a course
  def addRating(studentID: Int, courseID:Int, newRating:Option[Int]): Future[Int] = {
    newRating match {
      case Some(newRating) => 
        val updatedRow = StudentcoursesRow(studentID, courseID, Some(newRating))
        db.run(Studentcourses.filter(studentcoursesRow => studentcoursesRow.studentId === studentID && studentcoursesRow.courseId === courseID).update(updatedRow))
      case None => 
        Future.successful(0)
    }   
  }
  
}
