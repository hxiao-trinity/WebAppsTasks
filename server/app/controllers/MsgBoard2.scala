package controllers

import models.MemModel
import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.mvc._

import javax.inject._

@Singleton
class MsgBoard2 @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc){
    def load = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username => 
            Ok(views.html.MsgBoard2Main(routes.MsgBoard2.msgBoard.toString))
        }.getOrElse(Ok(views.html.MsgBoard2Main(routes.MsgBoard2.login.toString)))
    }

    def login = Action{ implicit request =>
        Ok(views.html.login2())
    }

    def msgBoard = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username => 
            Ok(views.html.MsgBoard2(username, MemModel.getMessages(username)))
        }.getOrElse(Ok(views.html.login2()))
    }

    def validate = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = args("username").head
            val password = args("password").head
            if (MemModel.validateUser(username, password)) 
                Ok(views.html.MsgBoard2(username, MemModel.getMessages(username)))
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
        if (MemModel.createUser(username, password)) 
            Ok(views.html.MsgBoard2(username, MemModel.getMessages(username)))
                .withSession("username" -> username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
        else
            Ok(views.html.login2())
        }.getOrElse(Redirect(routes.MsgBoard1.login))
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
                    models.MemModel.putMessage(username, content, Some(to))
                }
                else{
                    models.MemModel.putMessage(username, content)
                }
                Ok(views.html.MsgBoard2(username, MemModel.getMessages(username)))
                .withSession("username" -> username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
            }.getOrElse(Ok(views.html.login2()))
        }.getOrElse(Ok(views.html.login2()))
        
    }



    def logOut = Action {
        Redirect(routes.MsgBoard2.load).withNewSession
    }

    def generatedJS = Action {
        Ok(views.js.generatedJS())
    }
}
