/*
package controllers

import models.MsgBoardModel1._
import models._
import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.libs.json._
import play.api.mvc._

import javax.inject._

@Singleton
class MsgBoardReact @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc){

    def load() = Action { implicit request =>
        Ok(views.html.MsgBoardReactMain())
    }

    implicit val userDataReads = Json.reads[UserData]

    def data() = Action {
        Ok(Json.toJson(Seq("a", "b", "c")))
    }

    def validate = Action { implicit request =>
        request.body.asJson.map {body => 
            Json.fromJson[UserData](body) match {
                case JsSuccess(ud, path) =>
                    if (MsgBoardModel1.validateUser(ud.username, ud.password)){
                        Ok(Json.toJson(true)).withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
                    }
                    else{
                        Ok(Json.toJson(false))
                    }
                case e @ JsError(_) => Redirect(routes.MsgBoardReact.load)
            }
        }.getOrElse(Redirect(routes.MsgBoardReact.load))
    }


    
    def createUser = TODO
    def messageApp = TODO
    def putMessage = TODO
    def logOut = TODO

}
*/