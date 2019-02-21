package edu.trinity.webapps.controllers

import javax.inject._
import play.api.mvc._

@Singleton
class TempController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def welcome = Action { implicit request =>
    Ok(views.html.tempWelcome())
  }
  
  def month(m: Int, y: Int) = Action {
    Ok(m+"/"+y)
  }
}