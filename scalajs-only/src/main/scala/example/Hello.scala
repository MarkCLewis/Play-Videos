package example

import org.scalajs.dom
import org.scalajs.dom.document
import scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html
import slinky.web.ReactDOM
import slinky.web.html._

object Hello {
  def main(args: Array[String]): Unit = {
    println("Hello World.")
    for (i <- 1 to 10) println(i)

    document.getElementById("title").innerHTML = "Scala.js"
    document.getElementById("content").innerHTML = "This is a tutorial on using Scala.js."

    appendParagraph(document.getElementById("content"), "This is a new paragraph.")
    drawToCanvas(document.getElementById("canvas").asInstanceOf[html.Canvas])

    ReactDOM.render(
      TopComponent (),
      document.getElementById("react-root")
    )
  }

  def appendParagraph(target: dom.Node, text: String): Unit = {
    val p = document.createElement("p")
    val textNode = document.createTextNode(text)
    p.appendChild(textNode)
    target.appendChild(p)
  }

  @JSExportTopLevel("doClickStuff")
  def doClickStuff(): Unit = {
    println("Button clicked.")
  }

  def drawToCanvas(canvas: html.Canvas): Unit = {
    val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    context.fillRect(100, 100, 200, 150)
  }
}
