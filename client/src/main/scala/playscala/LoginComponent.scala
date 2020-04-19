package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html
import models.UserData
import models.ReadsAndWrites._

@react class LoginComponent extends Component {
  case class Props(doLogin: () => Unit)
  case class State(loginName: String, loginPass: String, 
    createName: String, createPass: String,
    loginMessage: String, createMessage:String)

  def initialState: State = State("", "", "", "", "", "")

  implicit val ec = scala.concurrent.ExecutionContext.global

  val csrfToken = document.getElementById("csrfToken").asInstanceOf[html.Input].value
  val validateRoute = document.getElementById("validateRoute").asInstanceOf[html.Input].value
  val createRoute = document.getElementById("createRoute").asInstanceOf[html.Input].value

  def render(): ReactElement = div (
    h2 ("Login:"),
    br (),
    "Username:",
    input (`type` := "text", value := state.loginName, 
      onChange := (e => setState(state.copy(loginName = e.target.value)))),
    br (),
    "Password:",
    input (`type` := "password", value := state.loginPass, 
      onChange := (e => setState(state.copy(loginPass = e.target.value)))),
    br (),
    button ("Login", onClick := (e => login())),
    state.loginMessage,
    br (),
    h2 ("Create User:"),
    br (),
    "Username:",
    input (`type` := "text", value := state.createName, 
      onChange := (e => setState(state.copy(createName = e.target.value)))),
    br (),
    "Password:",
    input (`type` := "password", value := state.createPass, 
      onChange := (e => setState(state.copy(createPass = e.target.value)))),
    br (),
    button ("Login", onClick := (e => createUser())),
    state.loginMessage,
  )

  def login(): Unit = {
    FetchJson.fetchPost(validateRoute, csrfToken, UserData(state.loginName, state.loginPass), (bool: Boolean) => {
      if(bool) {
        props.doLogin()
      } else {
        setState(state.copy(loginMessage = "Login Failed"))
      }
    }, e => {
      println("Fetch error: " + e)
    })
  }

  def createUser(): Unit = {
    FetchJson.fetchPost(createRoute, csrfToken, UserData(state.createName, state.createPass), (bool: Boolean) => {
      if(bool) {
        props.doLogin()
      } else {
        setState(state.copy(createMessage = "User Creation Failed"))
      }
    }, e => {
      println("Fetch error: " + e)
    })  }
}