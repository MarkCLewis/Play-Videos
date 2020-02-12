package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel
import play.api.libs.json.Json

@Singleton
class TaskList3 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def load = TODO

  def data = Action {
    Ok(Json.toJson(Seq("a", "b", "c")))
  }
}