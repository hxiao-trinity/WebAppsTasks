package controllers

import models.MsgBoardModel1
import models._
import play.api.data.Forms._
import play.api.data._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.mvc._
import play.twirl.api.Html

import javax.inject._


@Singleton
class MsgBoard3 @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc){

    implicit val userDataReads = Json.reads[UserData]

    def validate = Action { implicit request =>
        request.body.asJson.map{ body =>
            Json.fromJson[UserData](body) match {
                case JsSuccess(ud, path) => 
                    if (MsgBoardModel1.validateUser(ud.username, ud.password)) {
                        Ok(Json.toJson(true))
                            .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
                    }
                    else{
                        Ok(Json.toJson(false))
                    }
                case e @ JsError(_) => Redirect(routes.MsgBoard3.load)
            }
        }.getOrElse(Redirect(routes.MsgBoard3.load))
    } 

    

    def data = Action {
        Ok(Json.toJson(Seq("a", "b", "c")))
    }

    
    def load = Action {implicit request =>
        Ok(views.html.MsgBoard3Main())
    }

    def msgBoard = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username => 
            Ok(Json.toJson(MsgBoardModel1.getMessages(username)))
        }.getOrElse(Ok(Json.toJson(Seq.empty[String])))
    } 


}