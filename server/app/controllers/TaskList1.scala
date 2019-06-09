package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._

@Singleton
class TaskList1 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def taskList = Action {
    val tasks = List("task1", "task2", "task3", "sleep", "eat")
    Ok(views.html.taskList1(tasks))
  }

}