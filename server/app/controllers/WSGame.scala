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

class WSGame @Inject() (cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
        def index = Action{ implicit request =>
            Ok(views.html.gamePage())
        }

}