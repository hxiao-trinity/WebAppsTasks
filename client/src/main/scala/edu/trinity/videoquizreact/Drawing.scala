package edu.trinity.videoquizreact

package edu.trinity.videoquizreact

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import shared.SharedMessages
import slinky.core._
import slinky.web.ReactDOM
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object Drawing {
  def want = {}
  val canvas1 = document.getElementById("canvas2").asInstanceOf[html.Canvas]
  val context1 = canvas1.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  context1.fillStyle = "#ff8"
  context1.fillRect(0, 0, canvas1.width, canvas1.height)

  var drawingPaths1: List[DrawingPath] = List.empty

  case class DrawingPath(points: List[Point], color: String)
  case class Point(x: Double, y: Double)

  canvas1.focus()

  def updateCanvas1(): Unit = {
    context1.fillStyle = "#ff8"
    context1.fillRect(0, 0, canvas1.width, canvas1.height)

    drawingPaths1.foreach { path1 =>
      path1.points.sliding(2).foreach {
        case List(Point(x1, y1), Point(x2, y2)) =>
          context1.beginPath()
          context1.strokeStyle = path1.color
          context1.lineWidth = 3
          context1.moveTo(x1, y1)
          context1.lineTo(x2, y2)
          context1.stroke()
          context1.closePath()
        case _ => // Handle other cases if necessary
      }
    }
  }

  document.addEventListener("keydown", (event: dom.KeyboardEvent) => {
    val step = 5
    val color = "#00FF00"

    // val x = drawingPaths.lastOption.map(_.points.last.x).getOrElse(0)
    // val y = drawingPaths.lastOption.map(_.points.last.y).getOrElse(0)
    val lastPointOption = drawingPaths1.lastOption.flatMap(_.points.lastOption)

    val (x, y) = lastPointOption match {
      case Some(lastPoint) => (lastPoint.x, lastPoint.y)
      case None            => (0.0, 0.0)  // Default values if no previous points
    }

    event.code match {
      case "ArrowUp"    => drawingPaths1 = drawingPaths1 :+ DrawingPath(List(Point(x, y - step)), color)
      case "ArrowDown"  => drawingPaths1 = drawingPaths1 :+ DrawingPath(List(Point(x, y + step)), color)
      case "ArrowLeft"  => drawingPaths1 = drawingPaths1 :+ DrawingPath(List(Point(x - step, y)), color)
      case "ArrowRight" => drawingPaths1 = drawingPaths1 :+ DrawingPath(List(Point(x + step, y)), color)
      case _            =>
    }
    updateCanvas1()
  })


}
