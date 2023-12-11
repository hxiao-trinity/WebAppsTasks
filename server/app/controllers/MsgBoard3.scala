package controllers

import models.MsgBoardModel1._
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
                case e @ JsError(_) => BadRequest("V In validate(), Json.fromJson[UserData](body) match JsError") //Redirect(routes.MsgBoard3.load)
            }
        }.getOrElse(Redirect(routes.MsgBoard3.load))
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

    def putMessage = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username =>
            request.body.asJson.map{ body =>
                println("What's going to be matched? " + Json.fromJson[Message](body))
                Json.fromJson[Message](body) match {
                    case JsSuccess(message, path) => 
                        message.to match {
                            case Some(toValue) => 
                                models.MsgBoardModel1.putMessage(username, message.content, Some(toValue))
                            case None => 
                                models.MsgBoardModel1.putMessage(username, message.content)
                        }
                        Ok(Json.toJson(true))
                        //Ok(views.html.MsgBoardAjax(username, MsgBoardModel1.getMessages(username)))

                    case e @ JsError(_) => BadRequest("P In putMessage(), Json.fromJson[Message](body) match JsError") //Redirect(routes.MsgBoard3.load)
                }
            }.getOrElse(Ok(Json.toJson(false)))      
        }.getOrElse(Ok(Json.toJson(false)))
    }


}