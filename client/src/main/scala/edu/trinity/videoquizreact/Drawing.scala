
package edu.trinity.videoquizreact

package edu.trinity.videoquizreact

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D
import org.scalajs.dom.raw.Event
import org.scalajs.dom.raw.HTMLCanvasElement
import org.scalajs.dom.raw.HTMLInputElement
import shared.SharedMessages
import slinky.core._
import slinky.web.ReactDOM
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("DrawingApp")
object Drawing {

  def main(args: Array[String]): Unit = {
    val canvas = document.getElementById("canvas2").asInstanceOf[HTMLCanvasElement]
    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    var drawing = false
    var color = "#000000"
    var thick = 5

    document.getElementById("colorPicker").addEventListener("input", (event: Event) => {
      color = event.target.asInstanceOf[HTMLInputElement].value
    })

    document.getElementById("lineWidthSlider").addEventListener("input", (event: Event) => {
      thick = event.target.asInstanceOf[HTMLInputElement].value.toInt
      document.getElementById("lineWidthValue").textContent = thick.toString
    })

    var states: List[String] = List()
    val UNDOLIMIT = 30

    canvas.addEventListener("mousedown", (_: Event) => {
      saveCanvasState()
      drawing = true
      ctx.beginPath()
    })

    canvas.addEventListener("mousemove", (e: dom.MouseEvent) => {
      if (!drawing) return
      ctx.lineWidth = thick
      ctx.lineCap = "round"
      ctx.strokeStyle = color

      ctx.lineTo(e.clientX - canvas.offsetLeft, e.clientY - canvas.offsetTop)
      ctx.stroke()
      ctx.beginPath()
      ctx.moveTo(e.clientX - canvas.offsetLeft, e.clientY - canvas.offsetTop)
    })

    canvas.addEventListener("mouseup", (_: Event) => {
      drawing = false
      ctx.beginPath()
    })

    def saveCanvasState(): Unit = {
      if (states.length >= UNDOLIMIT) {
        states = states.tail
      }
      states = states :+ canvas.toDataURL("/draw")
    }

    canvas.addEventListener("contextmenu", (e: Event) => {
      e.preventDefault()
    })

    document.getElementById("clearButton").addEventListener("click", (_: Event) => clearCanvas())

    def clearCanvas(): Unit = {
      if (dom.window.confirm("Are you sure to clear the board?")) {
        ctx.clearRect(0, 0, canvas.width, canvas.height)
      }
    }

/*
    document.getElementById("undoButton").addEventListener("click", (_: Event) => undo())

    def undo(): Unit = {
      if (states.nonEmpty) {
        states = states.init
        val prevState = states.lastOption
        prevState.foreach { state =>
          val img = dom.document.createElement("img").asInstanceOf[dom.html.Image]
          img.src = state
          img.onload = (_: Event) => {
            ctx.clearRect(0, 0, canvas.width, canvas.height)
            ctx.drawImage(img, 0, 0)
          }
        }
      }
    }

    document.addEventListener("keydown", (event: dom.KeyboardEvent) => {
      if (event.ctrlKey && event.key == "z") undo()
    })
*/


  }





}



