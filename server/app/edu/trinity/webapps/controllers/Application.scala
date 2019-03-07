package edu.trinity.webapps.controllers

import javax.inject._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._
import play.api.i18n._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index("What happens?<script>alert('hahahaha')</script>"))
        //SharedMessages.itWorks))
  }
  
  def multTable = Action {
    Ok(views.html.multTable(12))
  }

  def deepFile = Action {
    Ok("This isn't a file.")
  }
  
  def enterName = Action { implicit request =>
    Ok(views.html.enterName(request.session.get("username")))
  }
  
  def rememberName(name: String) = Action { implicit request =>
    Redirect(routes.Application.enterName).
      withSession("username" -> name, "userid" -> "000")
  }
  
  def forgetName = Action { implicit request =>
    Redirect(routes.Application.enterName).withNewSession
  }
  
}
