package controllers

import javax.inject._



import shared.SharedMessages
import play.api.mvc._
import play.api.i18n._

@Singleton
class Faculty @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def loginFaculty = Action { implicit request =>
    
      Ok(views.html.facultyLogin1())

  }

  def validateFaculty(username: String, password: String) = Action {
    Ok(s"$username logged in with $password.")
  }

  def profile() = Action {  implicit request =>
      Ok(views.html.facultyProfile())

  }

// Need to have a page of both valid and invalid outcomes. STILL IN PROGRESS with the outcomes.
// It will only lead to the profile funtion.
  def validateFacultyPost() = Action { request =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args => 
           val username = args("username").head
           val password = args("password").head
           //Ok(s"$username logged in with $password.")
           Redirect(routes.Faculty.profile())
           }.getOrElse(Redirect(routes.Faculty.profile())) // This witl return the user back 
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
      Ok(views.html.facultyLogin1())
  }

}
