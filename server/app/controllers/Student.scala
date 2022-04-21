package controllers

import javax.inject._



import shared.SharedMessages
import play.api.mvc._
import play.api.i18n._

@Singleton
class Student @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def loginStudent = Action { implicit request =>
    
      Ok(views.html.studentLogin())

  }

  def validateStudent(username: String, password: String) = Action {
    Ok(s"$username logged in with $password.")
  }

  def profile() = Action {  implicit request =>
      Ok(views.html.studentProfile())

  }

// Need to have a page of both valid and invalid outcomes. STILL IN PROGRESS with the outcomes.
// It will only lead to the profile funtion.
  def validateStudentPost() = Action { request =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args => 
           val username = args("username").head
           val password = args("password").head
           //Ok(s"$username logged in with $password.")
           Redirect(routes.Student.profile())
           }.getOrElse(Redirect(routes.Student.profile())) // This witl return the user back 
           // to the login page. Ok("Oops"))
      }
//def validateFacultyPost() = Action { request =>
//      val postvals = request.body.asFormUrlEncoded
//      postvals.map { args => 
//           val username = args("username").head
//           val password = args("password").head
//           Redirect(routes.Application.faculty())
           //Ok(s"$username logged in with $password.")
//           }.getOrElse(Redirect(routes.Faculty.validateFacultyPost()))
//      }

  def index = Action { implicit request =>
      val facultyMember = "John"
      val password = 12345
      Ok(views.html.studentLogin())
  }

}