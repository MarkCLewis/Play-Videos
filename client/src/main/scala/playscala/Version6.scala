package playscala

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import org.scalajs.dom.experimental.Fetch
import org.scalajs.dom.experimental.Headers
import org.scalajs.dom.experimental.RequestInit
import org.scalajs.dom.experimental.HttpMethod
import org.scalajs.dom.experimental.RequestMode
import play.api.libs.json.Json
import models.ReadsAndWrites._
import scala.scalajs.js.Thenable.Implicits._
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsError
import scala.concurrent.ExecutionContext
import scala.scalajs.js.annotation.JSExportTopLevel
import scalajs.js
import models.TaskItem

object Version6 {
  implicit val ec = ExecutionContext.global

  val csrfToken = document.getElementById("csrfToken").asInstanceOf[html.Input].value
  val validateRoute = document.getElementById("validateRoute").asInstanceOf[html.Input].value
  val tasksRoute = document.getElementById("tasksRoute").asInstanceOf[html.Input].value
  val createRoute = document.getElementById("createRoute").asInstanceOf[html.Input].value
  val deleteRoute = document.getElementById("deleteRoute").asInstanceOf[html.Input].value
  val addRoute = document.getElementById("addRoute").asInstanceOf[html.Input].value
  val logoutRoute = document.getElementById("logoutRoute").asInstanceOf[html.Input].value

  def init(): Unit = {
    println("In version 6.")
  }

  @JSExportTopLevel("login")
  def login(): Unit = {
    val username = document.getElementById("loginName").asInstanceOf[html.Input].value
    val password = document.getElementById("loginPass").asInstanceOf[html.Input].value
    val data = models.UserData(username, password)
    FetchJson.fetchPost(validateRoute, csrfToken, data, (bool: Boolean) => {
      if(bool) {
        document.getElementById("login-section").asInstanceOf[js.Dynamic].hidden = true
        document.getElementById("task-section").asInstanceOf[js.Dynamic].hidden = false
        document.getElementById("login-message").innerHTML = ""
        document.getElementById("create-message").innerHTML = ""
        document.getElementById("loginName").asInstanceOf[html.Input].value = ""
        document.getElementById("loginPass").asInstanceOf[html.Input].value = ""
        loadTasks()
      } else {
        document.getElementById("login-message").innerHTML = "Login Failed"
      }
    }, e => {
      println("Fetch error: " + e)
    })
  }

  @JSExportTopLevel("createUser")
  def createUser(): Unit = {
    val username = document.getElementById("createName").asInstanceOf[html.Input].value
    val password = document.getElementById("createPass").asInstanceOf[html.Input].value
    val data = models.UserData(username, password)
    FetchJson.fetchPost(createRoute, csrfToken, data, (bool: Boolean) => {
      if(bool) {
        document.getElementById("login-section").asInstanceOf[js.Dynamic].hidden = true
        document.getElementById("task-section").asInstanceOf[js.Dynamic].hidden = false
        document.getElementById("login-message").innerHTML = ""
        document.getElementById("create-message").innerHTML = ""
        document.getElementById("createName").asInstanceOf[html.Input].value = ""
        document.getElementById("createPass").asInstanceOf[html.Input].value = ""
        loadTasks()
      } else {
        document.getElementById("create-message").innerHTML = "User Creation Failed"
      }
    }, e => {
      println("Fetch error: " + e)
    })
  }

  def loadTasks(): Unit = {
    val ul = document.getElementById("task-list")
    ul.innerHTML = ""
    FetchJson.fetchGet(tasksRoute, (tasks: Seq[TaskItem]) => {
      for (task <- tasks) {
        val li = document.createElement("li")
        val text = document.createTextNode(task.text)
        li.appendChild(text);
        li.asInstanceOf[html.LI].onclick = e => {
          delete(task.id)
        }
        ul.appendChild(li)
      }
    }, e => {
      println("Fetch error: " + e)
    })
  }

  def delete(id: Int): Unit = {
    FetchJson.fetchPost(deleteRoute, csrfToken, id, (bool: Boolean) => {
      if(bool) {
        document.getElementById("task-message").innerHTML = ""
        loadTasks()
      } else {
        document.getElementById("task-message").innerHTML = "Failed to delete."
      }
    }, e => {
      println("Fetch error: " + e)
    })
  }

  @JSExportTopLevel("addTask")
  def addTask(): Unit = {
    val task = document.getElementById("newTask").asInstanceOf[html.Input].value
    FetchJson.fetchPost(addRoute, csrfToken, task, (bool: Boolean) => {
      if(bool) {
        loadTasks()
        document.getElementById("newTask").asInstanceOf[html.Input].value = ""
        document.getElementById("task-message").innerHTML = ""
      } else {
        document.getElementById("task-message").innerHTML = "Failed to add."
      }
    }, e => {
      println("Fetch error: " + e)
    })
  }

  @JSExportTopLevel("logout")
  def logout(): Unit = {
    FetchJson.fetchGet(logoutRoute, (bool: Boolean) => {
      document.getElementById("login-section").asInstanceOf[js.Dynamic].hidden = false;
      document.getElementById("task-section").asInstanceOf[js.Dynamic].hidden = true;
    }, e => {
      println("Fetch error: " + e)
    })
  }
}