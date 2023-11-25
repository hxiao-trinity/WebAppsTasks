package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.mvc._

import javax.inject._

case class User(username:String, password:String)

@Singleton
class MsgBoardWeb1_0 @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc){
/*
    def msgBoard() = Action {
        Ok(views.html.MsgBoardWeb1_0(Nil))
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
            if (models.MsgBoardModel1.validateUser(username,password)) // if credential matches
                Redirect(routes.MsgBoardWeb1_0.msgBoard).withSession("username" -> username)    //let it in
            else
                Redirect(routes.MsgBoardWeb1_0.login).flashing("error" -> "Invalid credential")   //redirect to login page
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
            if (models.MsgBoardModel1.createUser(username,password)) // if credential matches
                Redirect(routes.MsgBoardWeb1_0.msgBoard).withSession("username" -> username)     //let it in
            else
                Redirect(routes.MsgBoardWeb1_0.login).flashing("error" -> "User already exists")   //redirect to login page
        }.getOrElse(Redirect(routes.MsgBoardWeb1_0.login))
    }


    /////NEW CODES
    def postMessage = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = request.session.get("username").getOrElse("anonymous")
            val content = args("content").head
            val to = args("to").head
            if (!to.isEmpty)
                models.MsgBoardModel1.postMessage(username, content, Some(to))
            else
                models.MsgBoardModel1.postMessage(username, content)
            Redirect(routes.MsgBoardWeb1_0.msgBoard)
        }.getOrElse(Redirect(routes.MsgBoardWeb1_0.msgBoard))
    }

    def msgBoard() = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{username => 
            val msgs = models.MsgBoardModel1.getMessages(username)
            Ok(views.html.MsgBoardWeb1_0(username, msgs))
        }.getOrElse(Redirect(routes.MsgBoardWeb1_0.login))
        

//        val username = request.session.get("username").getOrElse("")
//        val messages = models.MsgBoardModel1.getMessages(username) ++ models.MsgBoardModel1.getPublicMessages()
        
    }

    def logOut() = Action {
        Redirect(routes.MsgBoardWeb1_0.login).withNewSession
    }

}
