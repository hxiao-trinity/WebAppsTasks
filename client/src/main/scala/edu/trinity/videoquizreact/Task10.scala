package edu.trinity.videoquizreact

import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw.HTMLImageElement

import scala.scalajs.js

case class Position(x: Int, y: Int)

case class Monster(pos: Position)

case class Hero(
  pos: Position,
  speed: Int = 256 // movement in pixels per second
)

object Hero {
  val size = 32
}

class Image(src: String) {
  private var ready: Boolean = false

  val element = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
  element.onload = (e: dom.Event) => ready = true
  element.src = src

  def isReady: Boolean = ready
}

object Task10 {

  def main(args: Array[String]): Unit = {
    print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
    initGame
  }

  def isValidPosition(pos: Position, canvas: Canvas): Boolean = {
    0 <= pos.x && (pos.x + Hero.size) <= canvas.width && 0 <= pos.y && (pos.y + Hero.size) <= canvas.height
  }

  def areTouching(posA: Position, posB: Position): Boolean = {
    posA.x <= (posB.x + Hero.size) && posB.x <= (posA.x + Hero.size) && posA.y <= (posB.y + Hero.size) && posB.y <= (posA.y + Hero.size)
  }

  def initGame(): Unit = {
    // Create the canvas
    val canvas = dom.document.createElement("canvas").asInstanceOf[Canvas]
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.width = (0.95 * dom.window.innerWidth).toInt
    canvas.height = (0.95 * dom.window.innerHeight).toInt
    dom.document.body.appendChild(canvas)

    val bgImage = new Image("images/background.png")
    val heroImage = new Image("images/hero.png")
    val monsterImage = new Image("images/monster.png")

    var hero = Hero(Position(0, 0))
    var monster = Monster(Position(0, 0))

    var monstersCaught = 0

    // Handle keyboard controls
    import scala.collection.mutable.HashMap
    val keysDown = HashMap[Int, Boolean]()

    dom.window.addEventListener("keydown", (e: dom.KeyboardEvent) => {
      keysDown += e.keyCode -> true
    }, false)

    dom.window.addEventListener("keyup", (e: dom.KeyboardEvent) => {
      keysDown -= e.keyCode
    }, false)

    // Reset the game when the player catches a monster
    def reset() = {
      hero = hero.copy(pos = Position(canvas.width / 2, canvas.height / 2))

      // Throw the monster somewhere on the screen randomly
      monster = Monster(Position(
        Hero.size + (Math.random() * (canvas.width - 64)).toInt,
        Hero.size + (Math.random() * (canvas.height - 64)).toInt))
    }

    // Update game objects
    def update(modifier: Double) {
      val modif = (hero.speed * modifier).toInt
      var Position(x, y) = hero.pos
      if (keysDown.contains(KeyCode.Left))  x -= modif
      if (keysDown.contains(KeyCode.Right)) x += modif
      if (keysDown.contains(KeyCode.Up))    y -= modif
      if (keysDown.contains(KeyCode.Down))  y += modif

      val newPos = Position(x, y)
      if (isValidPosition(newPos, canvas)) {
        hero = hero.copy(pos = newPos)
      }

      // Are they touching?
      if (areTouching(hero.pos, monster.pos)) {
        monstersCaught += 1
        reset()
      }
    }

    // Draw everything
    def render() {
      if (bgImage.isReady) {
        ctx.drawImage(bgImage.element, 0, 0, canvas.width, canvas.height)
      }
      else{
          ctx.fillStyle = "#00FFFF" // Light blue color
          ctx.fillRect(0, 0, canvas.width, canvas.height)
      }
      if (heroImage.isReady) {
        ctx.drawImage(heroImage.element, hero.pos.x, hero.pos.y)
      }
      else{
          ctx.fillStyle = "red" // Color for hero
          ctx.fillRect(hero.pos.x, hero.pos.y, Hero.size, Hero.size)
      }
      if (monsterImage.isReady) {
        ctx.drawImage(monsterImage.element, monster.pos.x, monster.pos.y)
      }
      else{
          ctx.fillStyle = "green" // Color for monster
          ctx.fillRect(monster.pos.x, monster.pos.y, Hero.size, Hero.size)
      }

      // Score
      ctx.fillStyle = "FFFFFF"
      //ctx.font = "24px Helvetica"
      ctx.textAlign = "left"
      ctx.textBaseline = "top"
      ctx.fillText("Score: " + monstersCaught, 32, 32)
    }

    var prev = js.Date.now()
    // The main game loop
    val gameLoop = () => {
      val now = js.Date.now()
      val delta = now - prev

      update(delta / 1000)
      render()

      prev = now
    }

    // Let's play this game!
    reset()

    dom.window.setInterval(gameLoop, 1) // Execute as fast as possible
  }




}





















































/*
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import shared.SharedMessages
import slinky.core._
import slinky.web.ReactDOM
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object Task10 {
    
    val canvas = document.getElementById("canvas").asInstanceOf[html.Canvas]
    val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.style.background = "#80C080"

    val windowWidth = dom.window.innerWidth
    val windowHeight = 600

    canvas.width = windowWidth.toInt
    canvas.height = windowHeight

    val player = new Player(0, 0, 0, 0, context)
    val dangerZones = (1 to 7).map(_ => new DangerZone(context)).toList

    def updateZone(): Unit = {
      dom.window.requestAnimationFrame((_: Double) => updateZone())

      context.clearRect(0, 0, windowWidth, windowHeight)

      player.update()

      dangerZones.foreach(_.update(context))
    }

    updateZone()

    val destinationX = Math.random() * (windowWidth - 50) + 50
    val destinationY = Math.random() * (windowHeight - 50) + 50
    val destination = new Circle(destinationX, destinationY, 50, "#FF0000", 1)
    destination.draw(context)

    dangerZones.foreach(_.draw(context))

    document.addEventListener("keydown", (key: dom.KeyboardEvent) => {
      key.code match {
        case "KeyW" => player.setVelocity(0, -5)
        case "KeyS" => player.setVelocity(0, 5)
        case "KeyA" => player.setVelocity(-5, 0)
        case "KeyD" => player.setVelocity(5, 0)
        case _ =>
      }
    })

    document.addEventListener("keyup", (key: dom.KeyboardEvent) => {
      key.code match {
        case "KeyW" | "KeyS" => player.setVelocity(0, 0)
        case "KeyA" | "KeyD" => player.setVelocity(0, 0)
        case _ =>
      }
    })
  }

  class Circle(var xpos: Double, var ypos: Double, val radius: Double, val color: String, val speed: Double) {
    var dx = 1 * speed
    var dy = 1 * speed

    def draw(context: dom.CanvasRenderingContext2D): Unit = {
      context.beginPath()
      context.lineWidth = 5
      context.arc(xpos, ypos, radius, 0, Math.PI * 2, false)
      context.stroke()
      context.closePath()
    }

    def update(context: dom.CanvasRenderingContext2D): Unit = {
      //draw(ScalaJSExample.this.context)
      draw(context)

      if ((xpos + radius) > dom.window.innerWidth || (xpos - radius) < 0) {
        dx = -dx
      }
      if ((ypos + radius) > dom.window.innerHeight || (ypos - radius) < 0) {
        dy = -dy
      }

      xpos += dx
      ypos += dy
    }
  }

  class Player(var xpos: Double, var ypos: Double, var vx: Double, var vy: Double, val context: dom.CanvasRenderingContext2D) {
    def update(): Unit = {
      xpos += vx
      ypos += vy
      xpos = Math.max(0, Math.min(xpos, dom.window.innerWidth - 50))
      ypos = Math.max(0, Math.min(ypos, dom.window.innerHeight - 50))
      context.fillRect(xpos, ypos, 50, 50)
    }

    def setVelocity(newVx: Double, newVy: Double): Unit = {
      vx = newVx
      vy = newVy
    }
  }

  class DangerZone(val context: dom.CanvasRenderingContext2D) extends Circle(
    Math.random() * (dom.window.innerWidth - 75) + 75,
    Math.random() * (dom.window.innerHeight - 75) + 75,
    50,
    "black",
    1
  )
  

  // def drawToCanvas(canvas: html.Canvas): Unit = {
  //   val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  //   context.fillRect(100,100,200,150)
  // }
*/