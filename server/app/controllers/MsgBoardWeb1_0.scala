package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.mvc._

import javax.inject._

case class User(username:String, password:String)

class MsgBoardWeb1_0 @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc){
/*
    def msgBoard() = Action {
        Ok(views.html.MsgBoardWeb1_0(Nil))
    }
*/
    def validateLoginGet(username:String, password:String) = Action {
        Ok(s"$username logged in with password $password")
    }

    def validateLoginPost = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = args("username").head
            val password = args("password").head
            if (models.MsgBoardModel1.validateUser(username,password)) // if credential matches
                Redirect(routes.MsgBoardWeb1_0.msgBoard).withSession("username" -> username)    //let it in
            else
                Redirect(routes.MsgBoardWeb1_0.login)   //redirect to login page
        }.getOrElse(Ok(""))
    }

    def login() = Action {
        Ok(views.html.login(Nil))
    }

    def createUser = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = args("username").head
            val password = args("password").head
            if (models.MsgBoardModel1.createUser(username,password)) // if credential matches
                Redirect(routes.MsgBoardWeb1_0.msgBoard).withSession("username" -> username)     //let it in
            else
                Redirect(routes.MsgBoardWeb1_0.login)   //redirect to login page
        }.getOrElse(Ok(""))
    }


    /////NEW CODES
    def postMessage = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = request.session.get("username").getOrElse("anonymous")
            val content = args("content").head
            models.MsgBoardModel1.sendMsg(username, content)
            Redirect(routes.MsgBoardWeb1_0.msgBoard)
        }.getOrElse(Redirect(routes.MsgBoardWeb1_0.msgBoard))
    }

    def msgBoard() = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{username => 
            val msgs = models.MsgBoardModel1.getMessages(username)
            Ok(views.html.MsgBoardWeb1_0(msgs))
        }.getOrElse(Redirect(routes.MsgBoardWeb1_0.login))
        

//        val username = request.session.get("username").getOrElse("")
//        val messages = models.MsgBoardModel1.getMessages(username) ++ models.MsgBoardModel1.getPublicMessages()
        
    }

}
