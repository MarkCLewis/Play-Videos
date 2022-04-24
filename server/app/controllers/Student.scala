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

@Singleton
class Student @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext) 
    extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {
  
  private val model = new TaskListDatabaseModel(db)

  def loginStudent = Action { implicit request =>
    
      Ok(views.html.studentLogin())

  }

  def validateStudent(username: String, password: String) = Action {
    Ok(s"$username logged in with $password.")
  }

  def profile() = Action {  implicit request =>
      Ok(views.html.studentProfile())

  }

  def getAllCourses() = Action.async { ??? }

  def getMyCourses() = Action.async { ??? }

  def addCourse() = Action.async { ??? }

  def getStudentName () = Action.async { ??? }

// Need to have a page of both valid and invalid outcomes. STILL IN PROGRESS with the outcomes.
// It will only lead to the profile funtion.
  // def validateStudentPost() = Action { request =>
  //     val postVals = request.body.asFormUrlEncoded
  //     postVals.map { args => 
  //          val username = args("username").head
  //          val password = args("password").head
  //          //Ok(s"$username logged in with $password.")
  //          Redirect(routes.Student.profile())
  //          }.getOrElse(Redirect(routes.Student.profile())) // This witl return the user back 
  //          // to the login page. Ok("Oops"))
  //     }

  //functions below are copied from Lewis from TaskList5.scala
// def validateStudent = Action.async { implicit request =>
//     withJsonBody[UserData] { ud =>
//       model.validateStudent(ud.username, ud.password).map { ouserId =>
//         ouserId match {
//           case Some(userid) =>
//             Ok(Json.toJson(true))
//               .withSession("username" -> ud.username, "userid" -> userid.toString, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
//           case None =>
//             Ok(Json.toJson(false))
//         }
//       }
//     }
//   }

// def createStudentUser = Action.async { implicit request =>
//     withJsonBody[UserData] { ud => model.createStudentUser(ud.username, ud.password).map { ouserId =>   
//       ouserId match {
//         case Some(userid) =>
//           Ok(Json.toJson(true))
//             .withSession("username" -> ud.username, "userid" -> userid.toString, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
//         case None =>
//           Ok(Json.toJson(false))
//       }
//     } }
//   }


  def index = Action { implicit request =>
      val facultyMember = "John"
      val password = 12345
      Ok(views.html.studentLogin())
  }

}