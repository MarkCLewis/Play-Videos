package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel
import play.api.libs.json._
import models._

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

@Singleton
class Faculty @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext) 
    extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  private val model = new TaskListDatabaseModel(db)
  private var sessionId:Int = 0
  private var facultyCourselist:ListBuffer[(Int, String, String, Int)] = mutable.ListBuffer()

  //display student login page
  def loginFaculty = Action { implicit request =>
      Ok(views.html.facultyLogin())
  }

  //display faculty profile 
  def facultyProfile() = Action {  implicit request =>
      Ok(views.html.facultyProfile())
  }

  //function to validate a faculty's login information
  def validateFaculty = Action.async { implicit request =>
    val validVals = request.body.asFormUrlEncoded
    var validUsername:String = ""
      var validPassword:String = ""
     validVals.map { args => 
      validUsername = args("username").head
      validPassword = args("password").head
      }.getOrElse(Redirect(routes.Faculty.loginFaculty()))
      model.validateFaculty(validUsername, validPassword).map {  ofacultyId =>
        ofacultyId match {
          case Some(facultyid) =>
            sessionId = facultyid
            Redirect(routes.Faculty.facultyProfile())
          case None =>
            Redirect(routes.Faculty.loginFaculty())
        }
      }
    }

    //function to create a new faculty user
def createFacultyUser = Action.async { implicit request =>
    val createVals = request.body.asFormUrlEncoded
    var createName:String = ""
    var createUsername:String = ""
    var createPassword:String = ""
     createVals.map { args => 
      createName = args("name").head
      createUsername = args("username").head
      createPassword = args("password").head
    }.getOrElse(Redirect(routes.Faculty.loginFaculty()))
    model.createFacultyUser(createName, createUsername, createPassword).map {  ofacultyId =>
        ofacultyId match {
          case Some(facultyid) =>
            sessionId = facultyid
            Redirect(routes.Faculty.facultyProfile())
          case None =>
            Redirect(routes.Faculty.facultyProfile())
        }
      }
  }

  //function to get Faculty's courselist
  def getFacultyCourses = Action.async { implicit request => 
    model.getFacultyCourses(sessionId).map{ courses =>
      courses match { 
        case Some(courses) =>
          courses.map(course => 
            facultyCourselist += ((course.courseId, course.courseName, course.courseNumber, course.facultyId)))
            Redirect(routes.Faculty.facultyProfile())
        case None =>
          Redirect(routes.Faculty.facultyProfile())
    }}
      
  }

}
