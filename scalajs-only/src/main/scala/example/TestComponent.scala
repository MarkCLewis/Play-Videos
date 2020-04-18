package example

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class TestComponent extends Component {
  case class Props(msg: String, back: () => Unit)
  case class State(text: String)

  def initialState: State = State("")

  def render(): ReactElement = div (
    h1 (props.msg),
    input (`type` := "text", value := state.text, 
      onChange := (e => setState(state.copy(text = e.target.value)))),
    button ("Go Back", onClick := (e => props.back()))
  )
}