package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.mvc._
import javax.inject._

@Singleton
class MsgBoardAjax @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc){
    def load = Action { implicit request =>
        Ok(views.html.MsgBoardAjaxMain())
    }

    def login = Action{
        Ok(views.html.login2())
    }

    def validate(username:String, password:String) = Action{
        Ok("validating")
    }
}
