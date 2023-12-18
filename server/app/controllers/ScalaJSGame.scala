package controllers


import models.MemModel._
import models._
import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.libs.json._
import play.api.mvc._

import javax.inject._

@Singleton
class ScalaJSGame @Inject() (cc: ControllerComponents) extends AbstractController(cc){

    def load10() = Action { implicit request =>
        Ok(views.html.ScalaJSGame())
    }

    def load11() = Action { implicit request =>
        Ok(views.html.Drawing())
    }    

}