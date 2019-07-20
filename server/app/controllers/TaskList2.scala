package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel

@Singleton
class TaskList2 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      Ok(views.html.version2Main(routes.TaskList2.taskList().toString))
    }.getOrElse(Ok(views.html.version2Main(routes.TaskList2.login().toString)))
  }

  def login = Action { implicit request =>
    Ok(views.html.login2())
  }
  
  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.login2()))
  }

  def validate = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)) {
        Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
          .withSession("username" -> username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
      } else {
        Ok(views.html.login2())
      }
    }.getOrElse(Ok(views.html.login2()))
  }
  
  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
          .withSession("username" -> username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
      } else {
        Ok(views.html.login2())
      }
    }.getOrElse(Ok(views.html.login2()))    
  }
  
  def delete = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val index = args("index").head.toInt
        TaskListInMemoryModel.removeTask(username, index);
        Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
      }.getOrElse(Ok(views.html.login2()))
    }.getOrElse(Ok(views.html.login2()))
  }
  
  def addTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val task = args("task").head
        TaskListInMemoryModel.addTask(username, task);
        Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
      }.getOrElse(Ok(views.html.login2()))
    }.getOrElse(Ok(views.html.login2()))
  }
  
  def logout = Action {
    Redirect(routes.TaskList2.load()).withNewSession
  }
  
  def generatedJS = Action {
    Ok(views.js.generatedJS())
  }
}