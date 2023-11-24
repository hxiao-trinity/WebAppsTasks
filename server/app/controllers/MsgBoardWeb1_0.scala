package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.mvc._

import javax.inject._

case class User(username:String, password:String)

class MsgBoardWeb1_0 @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc){

    def taskList() = Action {
        Ok(views.html.MsgBoardWeb1_0(Nil))
    }

    def validateLoginGet(username:String, password:String) = Action {
        Ok(s"$username logged in with password $password")
    }

    def validateLoginPost = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args =>
            val username = args("username").head
            val password = args("password").head
            if (models.MsgBoardModel1.validateUser(username,password)) // if credential matches
                Redirect(routes.MsgBoardWeb1_0.taskList)    //let it in
            else
                Redirect(routes.MsgBoardWeb1_0.login)   //redirect to login page
        }.getOrElse(Ok(""))
  }

    def login() = Action {
        Ok(views.html.login(Nil))
    }

}
