package edu.trinity.webapps.controllers

import javax.inject._

import edu.trinity.webapps.shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

}
