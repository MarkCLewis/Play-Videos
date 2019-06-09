package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n._

@Singleton
class Todo1Controller @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def taskList = Action {
    val tasks = List("Make videos", "Make Website", "Teach stuff")
    Ok(views.html.taskList1(tasks))
  }
}