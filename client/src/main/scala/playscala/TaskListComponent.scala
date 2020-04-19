package playscala

import slinky.core.annotations.react
import slinky.core.Component
import models.TaskItem
import models.ReadsAndWrites._
import slinky.core.facade.ReactElement
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html

@react class TaskListComponent extends Component {
  case class Props(doLogout: () => Unit)
  case class State(tasks: Seq[TaskItem], newTask: String, taskMessage: String)

  val csrfToken = document.getElementById("csrfToken").asInstanceOf[html.Input].value
  val tasksRoute = document.getElementById("tasksRoute").asInstanceOf[html.Input].value
  val deleteRoute = document.getElementById("deleteRoute").asInstanceOf[html.Input].value
  val addRoute = document.getElementById("addRoute").asInstanceOf[html.Input].value
  val logoutRoute = document.getElementById("logoutRoute").asInstanceOf[html.Input].value
  implicit val ec = scala.concurrent.ExecutionContext.global

  def initialState: State = State(Nil, "", "")

  override def componentDidMount(): Unit = {
    loadTasks()
  }

  def render(): ReactElement = div (
    ul (
      state.tasks.zipWithIndex.map { case (taskItem, i) => 
        li (key := i.toString, taskItem.text, onClick := (e => deleteTask(taskItem.id)))
      }
    ),
    br (),
    input (`type` := "text", value := state.newTask, 
      onChange := (e => setState(state.copy(newTask = e.target.value)))),
    button ("Add Task", onClick := (e => addTask())),
    br(),
    button ("Logout", onClick := (e => logout())),
    state.taskMessage
  )

  def loadTasks(): Unit = {
    FetchJson.fetchGet(tasksRoute, (tasks: Seq[TaskItem]) => {
      setState(state.copy(tasks = tasks))
    }, e => {
      println("Fetch error: " + e)
    })
  }

  def addTask(): Unit = {
    FetchJson.fetchPost(addRoute, csrfToken, state.newTask, (bool: Boolean) => {
      if(bool) {
        setState(state.copy(newTask = "", taskMessage = ""))
        loadTasks()
      } else {
        setState(state.copy(taskMessage = "Failed to add."))
      }
    }, e => {
      println("Fetch error: " + e)
    })
  }

  def deleteTask(id: Int): Unit = {
    FetchJson.fetchPost(deleteRoute, csrfToken, id, (bool: Boolean) => {
      if(bool) {
        setState(state.copy(taskMessage = ""))
        loadTasks()
      } else {
        setState(state.copy(taskMessage = "Failed to delete."))
      }
    }, e => {
      println("Fetch error: " + e)
    })  
  }

  def logout(): Unit = {
    FetchJson.fetchGet(logoutRoute, (bool: Boolean) => {
      props.doLogout()
    }, e => {
      println("Fetch error: " + e)
    })
  }
}