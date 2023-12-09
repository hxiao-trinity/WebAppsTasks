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
        val usernameOption = request.session.get("username")
        usernameOption.map{ username => 
            Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username)))
        }.getOrElse(Ok(views.html.MsgBoardAjaxMain()))
    }

    def login() = Action{ implicit request =>
        Ok(views.html.login2())
    }

    def validate = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = args("username").head
            val password = args("password").head
            if (MsgBoardModel1.validateUser(username, password)) 
                Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username)))
                    .withSession("username" -> username)
            else
                Ok(views.html.login2())
        }.getOrElse(Ok(views.html.login2()))
        
    }

    def createUser(username:String, password:String) = Action { implicit request =>
        if (MsgBoardModel1.createUser(username, password)) 
            Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username))).withSession("username" -> username)
        else
            Ok(views.html.login2())
    }

    def putMessage(from_username:String, content:String, to_username:String) = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username => 
            MsgBoardModel1.putMessage(username, content, Some(to_username))
            Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username))).withSession("username" -> username)
        }.getOrElse(Ok(views.html.login2()))
    }

    def logOut() = Action {
        Redirect(routes.MsgBoardAjax.load).withNewSession
    }

    def generatedJS = Action {
        Ok(views.js.generatedJS())
    }
}
