package controllers

import play.api.i18n._
import play.api.mvc._

import javax.inject._

import shared.SharedMessages

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index(SharedMessages.itWorks))

  }

}
