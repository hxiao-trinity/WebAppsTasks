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
            Ok(views.html.MsgBoardAjaxMain(routes.MsgBoardAjax.msgBoard.toString))
        }.getOrElse(Ok(views.html.MsgBoardAjaxMain(routes.MsgBoardAjax.login.toString)))
    }

    def login = Action{ implicit request =>
        Ok(views.html.login2())
    }

    def msgBoard = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username => 
            Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username)))
        }.getOrElse(Ok(views.html.login2()))
    }

    def validate = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = args("username").head
            val password = args("password").head
            if (MsgBoardModel1.validateUser(username, password)) 
                Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username)))
                    .withSession("username" -> username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
            else
                Ok(views.html.login2())
        }.getOrElse(Ok(views.html.login2()))
        
    }

    def createUser = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
        val username = args("username").head
        val password = args("password").head
        if (MsgBoardModel1.createUser(username, password)) 
            Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username)))
                .withSession("username" -> username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
        else
            Ok(views.html.login2())
        }.getOrElse(Redirect(routes.MsgBoardWeb1_0.login))
    }

    def putMessage = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username =>
            val postVals = request.body.asFormUrlEncoded
            postVals.map { args =>
                val username = request.session.get("username").getOrElse("anonymous")
                val content = args("content").head
                val to = args("to_user").head
                println("Post Vals: " + postVals) // Add this line for debugging
                if (!to.isEmpty){
                    models.MsgBoardModel1.putMessage(username, content, Some(to))
                }
                else{
                    models.MsgBoardModel1.putMessage(username, content)
                }
                Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username))).withSession("username" -> username)
            }.getOrElse(Ok(views.html.login2()))
        }.getOrElse(Ok(views.html.login2()))
        
    }



    def logOut = Action {
        Redirect(routes.MsgBoardAjax.load).withNewSession
    }

    def generatedJS = Action {
        Ok(views.js.generatedJS())
    }
}
