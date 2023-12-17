/*
package edu.trinity.videoquizreact

import org.scalajs.dom
import shared.SharedMessages
import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade._
import slinky.web.ReactDOM
import slinky.web.SyntheticMouseEvent
import slinky.web.html._
import slinky.web.html._
import slinky.web.html._

import scala.scalajs.js.timers._
import scala.util.Random

@react class GameComponent extends Component {
  type Props = Unit
  case class State(targetX: Int, targetY: Int, score: Int)

  def initialState: State = State(
    targetX = Random.nextInt(800),
    targetY = Random.nextInt(600),
    score = 0
  )

  private var canvasRef: ReactRef[dom.html.Canvas] = React.createRef[dom.html.Canvas]

  override def componentDidMount(): Unit = {
    val canvas = canvasRef.current
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    // Set up the game loop
    setInterval(1000) {
      val newX = Random.nextInt(800)
      val newY = Random.nextInt(600)

      // Clear the canvas
      ctx.clearRect(0, 0, canvas.width, canvas.height)

      // Draw the target
      ctx.beginPath()
      ctx.arc(newX, newY, 10, 0, 2 * Math.PI)
      ctx.fill()

      // Update state
      setState(_.copy(targetX = newX, targetY = newY))
    }
  }

  def handleClick(e: SyntheticMouseEvent[_]): Unit = {
      val rect = canvasRef.current.getBoundingClientRect()
      val x = e.clientX - rect.left
      val y = e.clientY - rect.top

      // Check if the click is within the target area
      if ((x - state.targetX).abs < 10 && (y - state.targetY).abs < 10) {
          setState(state => state.copy(score = state.score + 1))
      }
  }

  def render(): slinky.core.facade.ReactElement = {
      div(
          h1(s"Score: ${state.score}"),
          canvas(
              width := "800",
              height := "600",
              onClick := (e => handleClick(e)), // Updated to use SyntheticMouseEvent
              ref := canvasRef
          )
      )
  }
}


////////////////////////////////////////////////////////////////////////////////




object ScalaJSExample {

  def main(args: Array[String]): Unit = {
    // dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
    println("Call the react stuff.")
    ReactDOM.render(
      div(
        h1("Click the Moving Target Game"),
        GameComponent()
      ),
      dom.document.getElementById("task10")
    )
  }
}
*/

/*
package edu.trinity.videoquizreact

import _root_.edu.trinity.videoquizreact.edu.trinity.videoquizreact._
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import shared.SharedMessages
import slinky.core._
import slinky.web.ReactDOM
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object ScalaJSExample {

}

*/