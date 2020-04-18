package example

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class TopComponent extends Component {
  type Props = Unit
  case class State(showingInput: Boolean)

  def initialState: State = State(false)

  def render(): ReactElement = {
    if (state.showingInput) {
      TestComponent("Enter Input:", () => setState(state.copy(showingInput = false)))
    } else {
      div (
        h1 ("Top Level"),
        button ("View Input", onClick := (e => setState(state.copy(showingInput = true))))
      )
    }
  }
}