package controllers

import models.MemModel._
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


    def withJsonBody[A](f: A => Result)(implicit request: Request[AnyContent], reads: Reads[A]): Result = {
        request.body.asJson.map { body =>
            println(body)
        Json.fromJson[A](body) match {
            case JsSuccess(a, path) => f(a)
            case e @ JsError(_) => Redirect(routes.MsgBoard3.load)
        }
        }.getOrElse(Redirect(routes.MsgBoard3.load))
    }

    def withSessionUsername(f: String => Result)(implicit request: Request[AnyContent]) = {
        request.session.get("username").map(f).getOrElse(Ok(Json.toJson(Seq.empty[String])))
    }


    implicit val userDataReads = Json.reads[UserData]


    def validate = Action { implicit request =>
        withJsonBody[UserData] { ud =>
            if (MemModel.validateUser(ud.username, ud.password)) {
                Ok(Json.toJson(true))
                .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
            } else {
                Ok(Json.toJson(false))
            }
        }
    }

/*
    def validate = Action { implicit request =>
        request.body.asJson.map{ body =>
            Json.fromJson[UserData](body) match {
                case JsSuccess(ud, path) => 
                    if (MemModel.validateUser(ud.username, ud.password)) {
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
*/

    def load = Action {implicit request =>
        Ok(views.html.MsgBoard3Main(request.session.get("username").getOrElse("WHO ARE YOU?!")))
    }

    def msgBoard = Action { implicit request =>
        withSessionUsername { username =>
            println(username + " " + MemModel.getMessages(username))
            Ok(Json.toJson(MemModel.getMessages(username)))
        }
    } 

/*
    def msgBoard = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username => 
            Ok(Json.toJson(MemModel.getMessages(username)))
        }.getOrElse(Ok(Json.toJson(Seq.empty[String])))
    } 
*/

    def putMessage = Action { implicit request =>
        withSessionUsername { username =>
            withJsonBody[Message] { message =>
                if (message.to.isDefined && message.to.nonEmpty)
                    MemModel.putMessage(username, message.content, message.to)
                else
                    MemModel.putMessage(username, message.content)
                Ok(Json.toJson(true))
            }
        }
    }

/*
    def putMessage = Action { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username =>
            request.body.asJson.map{ body =>
                println("What's going to be matched? " + Json.fromJson[MemModel.Message](body))
                Json.fromJson[MemModel.Message](body) match {
                    case JsSuccess(message, path) => 
                        message.to match {
                            case Some(toValue) => 
                                models.MemModel.putMessage(username, message.content, Some(toValue))
                            case None => 
                                models.MemModel.putMessage(username, message.content)
                        }
                        Ok(Json.toJson(true))
                        //Ok(views.html.MsgBoardAjax(username, MemModel.getMessages(username)))

                    case e @ JsError(_) => BadRequest("P In putMessage(), Json.fromJson[Message](body) match JsError") //Redirect(routes.MsgBoard3.load)
                }
            }.getOrElse(Ok(Json.toJson(false)))      
        }.getOrElse(Ok(Json.toJson(false)))
    }
*/

    def createUser = Action { implicit request =>
        withJsonBody[UserData] { ud =>
            if (MemModel.createUser(ud.username, ud.password)) {
                Ok(Json.toJson(true))
                .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
            } else {
                Ok(Json.toJson(false))
            }
        }
    }

/*
    def createUser = Action { implicit request =>
        request.body.asJson.map{ body =>
            Json.fromJson[UserData](body) match {
                case JsSuccess(ud, path) => 
                    if (MemModel.createUser(ud.username, ud.password)) {
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
*/

    def logOut = Action {implicit request =>
        Ok(Json.toJson(true)).withSession()    
    }


}