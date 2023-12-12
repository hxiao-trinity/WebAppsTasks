package controllers

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.stream.Materializer
import play.api.i18n._
import play.api.libs.json._
import play.api.libs.streams.ActorFlow
import play.api.mvc._

import javax.inject._


case class Player(id: String, x: Int, y: Int)
case class PlayerMoved(id: String, x: Int, y: Int)
case class GameState(players: Map[String, Player])

class GameActor extends Actor {
  var gameState = GameState(Map())

  def receive = {
    case PlayerMoved(id, x, y) =>
      gameState = GameState(gameState.players + (id -> Player(id, x, y)))
      context.system.eventStream.publish(gameState)
  }
}

class WSGame @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  val gameActor = system.actorOf(Props[GameActor], "gameActor")

  implicit val playerReads = Json.reads[Player]
  implicit val playerWrites = Json.writes[Player]
  implicit val playerMovedReads = Json.reads[PlayerMoved]
  implicit val gameStateReads = Json.reads[GameState]
  implicit val gameStateWrites = Json.writes[GameState]

  def index = Action { implicit request =>
    Ok(views.html.WSGamePage())
  }

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      Props(new Actor {
        system.eventStream.subscribe(self, classOf[GameState])

        def receive = {
          case msg: String =>
            val playerMoved = Json.parse(msg).as[PlayerMoved]
            gameActor ! playerMoved

          case gameState: GameState =>
            out ! Json.toJson(gameState).toString
        }
      })
    }
  }
}
