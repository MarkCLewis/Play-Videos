package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models._
import play.api.libs.json._
import models.Tables._
import play.api.data.Forms._


import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future

import shared.SharedMessages
import play.api.mvc._
import play.api.i18n._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable

//this class is the controller for all Student functions
@Singleton
class Student @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext) 
    extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {
  
  private val model = new TaskListDatabaseModel(db)
  private var sessionId:Int = 0
  private var courselist:ListBuffer[(Int, String, String, Int)] = mutable.ListBuffer()
  private var studentCourselist:ListBuffer[(Int, String, String, Int)] = mutable.ListBuffer()


    //load student login page
  def load = Action { implicit request =>
     Ok(views.html.studentLogin())
   }

    //display student login page
  def loginStudent = Action { implicit request =>
      Ok(views.html.studentLogin())
  }

  //function to validate a student's login information
  def validateStudent = Action.async { implicit request =>
    val validVals = request.body.asFormUrlEncoded
    var validUsername:String = ""
      var validPassword:String = ""
     validVals.map { args => 
      validUsername = args("username").head
      validPassword = args("password").head
      }.getOrElse(Redirect(routes.Student.loginStudent()))
      model.validateStudent(validUsername, validPassword).map {  ostudentId =>
        ostudentId match {
          case Some(studentid) =>
            sessionId = studentid
            Redirect(routes.Student.studentProfile())
          case None =>
            Redirect(routes.Student.loginStudent())
        }
      }
    }

    //display student profile page
  def studentProfile = Action { implicit request =>
      // println("before call")
      // val test = model.getAllCourses()
      // println(test)
      // println("after call")
      //model.addCourse(4, 4) //model.addCourse works!!
      //model.addRating(4, 4, Some(1)) //model.addRating works!!!
      //model.removeCourse(4, 4) //model.removeCourse works!!!
      //var test:Future[Vector[CourseRow]] = model.getStudentCourses(4)
      //println(test)
      Ok(views.html.studentProfile())
  }

  def getAllCoursesList(): ListBuffer[(Int, String, String, Int)] =  {
    courselist
  }
  
  //function to get list of all courses in database
   def getAllCourses = Action.async { implicit request =>
      model.getAllCourses().map{ courses =>
      courses match { 
        case Some(courses) =>
          courses.map(course => 
            courselist += ((course.courseId, course.courseName, course.courseNumber, course.facultyId)))
            Redirect(routes.Student.studentProfile())
        case None =>
          Redirect(routes.Student.studentProfile())
    }}
  }

    //function to get a student's list of courses
  def getStudentCourses() = Action.async { implicit request =>
      model.getStudentCourses(sessionId).map { courses =>
      courses.map(course => 
            studentCourselist += ((course.courseId, course.courseName, course.courseNumber, course.facultyId)))
            Redirect(routes.Student.studentProfile())
      }
  }

  //function to add a course to a student's course list
  def addCourse = Action.async { implicit request =>
    val addVals = request.body.asFormUrlEncoded
    var addCourseId:Int = 0
    addVals.map { args => 
      addCourseId = args("courseId").head.toInt
    }.getOrElse(Redirect(routes.Student.studentProfile()))
    model.addCourse(sessionId, addCourseId).map { ocount => 
      ocount match { 
      case Some(count) => 
        if (count > 0) Redirect(routes.Student.studentProfile()) else Redirect(routes.Student.studentProfile()) 
      case None => 
        Redirect(routes.Student.studentProfile())} 
    }
  }

  //function to remove a course from a student's course list
  def removeCourse = Action.async { implicit request =>
    val removeVals = request.body.asFormUrlEncoded
    var removeCourseId:Int = 0
    removeVals.map { args =>
      removeCourseId = args("removeCourseId").head.toInt
    }.getOrElse(Redirect(routes.Student.studentProfile()))
    model.removeCourse(sessionId, removeCourseId).map( bool =>
          Redirect(routes.Student.studentProfile())
    )
    
  }

//function to add a rating to a course
     def addRating = Action.async { implicit request => 
      val addVals = request.body.asFormUrlEncoded
      var addRating:Int = 0
      var toCourse:Int = 0
      addVals.map { args => 
        addRating = args("rating").head.toInt
        toCourse = args("course").head.toInt
      }.getOrElse(Redirect(routes.Student.studentProfile()))
      model.addRating(sessionId, toCourse, Some(addRating)).map(count =>
          if (count > 0) Redirect(routes.Student.studentProfile()) else Redirect(routes.Student.studentProfile())
      )
     }

  //function to create a new student user
def createStudentUser = Action.async { implicit request =>
    val createVals = request.body.asFormUrlEncoded
    var createName:String = ""
    var createUsername:String = ""
    var createPassword:String = ""
     createVals.map { args => 
      createName = args("name").head
      createUsername = args("username").head
      createPassword = args("password").head
    }.getOrElse(Redirect(routes.Student.loginStudent()))
    model.createStudentUser(createName, createUsername, createPassword).map {  ostudentId =>
        ostudentId match {
          case Some(studentid) =>
            sessionId = studentid
            Redirect(routes.Student.studentProfile())
          case None =>
            Redirect(routes.Student.loginStudent())
        }
      }
  }


}