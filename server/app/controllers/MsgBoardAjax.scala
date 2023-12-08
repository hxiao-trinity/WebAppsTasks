package controllers

import models.MsgBoardModel1
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

    def login() = Action{ implicit request =>
        Ok(views.html.login2())
    }

    def validate(username:String, password:String) = Action { implicit request =>
        if (MsgBoardModel1.validateUser(username, password)) 
            Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username))).withSession("username" -> username)
        else
            Ok(views.html.login2())
    }

    def createUser(username:String, password:String) = Action { implicit request =>
        if (MsgBoardModel1.createUser(username, password)) 
            Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username))).withSession("username" -> username)
        else
            Ok(views.html.login2())
    }
}
