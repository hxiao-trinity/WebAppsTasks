package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.mvc._

import javax.inject._

case class User(username:String, password:String)

@Singleton
class MsgBoard1 @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc){
/*
    def msgBoard() = Action {
        Ok(views.html.MsgBoard1(Nil))
    }

    def validateLoginGet(username:String, password:String) = Action {
        Ok(s"$username logged in with password $password")
    }
*/

    def validateLoginPost = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = args("username").head
            val password = args("password").head
            if (models.MemModel.validateUser(username,password)) // if credential matches
                Redirect(routes.MsgBoard1.msgBoard).withSession("username" -> username)    //let it in
            else
                Redirect(routes.MsgBoard1.login).flashing("error" -> "Invalid credential")   //redirect to login page
        }.getOrElse(Ok(""))
    }

    def login = Action { implicit request =>
        Ok(views.html.login())
    }

    def createUser = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = args("username").head
            val password = args("password").head
            if (models.MemModel.createUser(username,password)) // if credential matches
                Redirect(routes.MsgBoard1.msgBoard).withSession("username" -> username)     //let it in
            else
                Redirect(routes.MsgBoard1.login).flashing("error" -> "User already exists")   //redirect to login page
        }.getOrElse(Redirect(routes.MsgBoard1.login))
    }


    def putMessage = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = request.session.get("username").getOrElse("anonymous")
            val content = args("content").head
            val to = args("to").head
            if (!to.isEmpty)
                models.MemModel.putMessage(username, content, Some(to))
            else
                models.MemModel.putMessage(username, content)
            Redirect(routes.MsgBoard1.msgBoard)
        }.getOrElse(Redirect(routes.MsgBoard1.msgBoard))
    }

    def msgBoard() = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{username => 
            val msgs = models.MemModel.getMessages(username)
            Ok(views.html.MsgBoard1(username, msgs))
        }.getOrElse(Redirect(routes.MsgBoard1.login))
        

//        val username = request.session.get("username").getOrElse("")
//        val messages = models.MemModel.getMessages(username) ++ models.MsgBoardModel1.getPublicMessages()
        
    }

    def logOut() = Action {
        Redirect(routes.MsgBoard1.login).withNewSession
    }

}
