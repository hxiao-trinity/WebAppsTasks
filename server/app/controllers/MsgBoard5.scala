package controllers
/*
import models.MsgBoardModel1._
import models._
import play.api.data.Forms._
import play.api.data._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.i18n._
import play.api.libs.json._
import play.api.mvc._
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class MsgBoard5 @Inject() (protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)
                          (implicit ec:ExecutionContext) 
                extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile]{

    private val model = new MsgBoardDBModel(db)

    def withJsonBody[A](f: A => Future[Result])(implicit request: Request[AnyContent], reads: Reads[A]): Future[Result] = {
        request.body.asJson.map { body =>
        Json.fromJson[A](body) match {
            case JsSuccess(a, path) => f(a)
            case e @ JsError(_) => Future.successful(Redirect(routes.MsgBoard3.load))
        }
        }.getOrElse(Future.successful(Redirect(routes.MsgBoard3.load)))
    }

    def withSessionUsername(f: String => Future[Result])(implicit request: Request[AnyContent]): Future[Result] = {
        request.session.get("username").map(f).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
    }

    def load() = Action { implicit request =>
        Ok(views.html.MsgBoard4Main())
    }

    implicit val userDataReads = Json.reads[UserData]

    def validate = Action.async { implicit request =>
        withJsonBody[UserData] { ud =>
            model.validateUser(ud.username, ud.password).map { userExists =>
                if (userExists){
                     Ok(Json.toJson(true))
                            .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
                }
                else{
                    Ok(Json.toJson(false))
                }   
            }
        }
    }

    def msgBoard = Action.async { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username => 
            Ok(Json.toJson(model.getMessages(username)))
        }.getOrElse(Ok(Json.toJson(Seq.empty[String])))
    } 

    def putMessage = Action.async { implicit request =>
        val usernameOption = request.session.get("username")
        usernameOption.map{ username =>
            request.body.asJson.map{ body =>
                println("What's going to be matched? " + Json.fromJson[Message](body))
                Json.fromJson[Message](body) match {
                    case JsSuccess(message, path) => 
                        message.to match {
                            case Some(toValue) => 
                                model.putMessage(username, message.content, Some(toValue))
                            case None => 
                                model.putMessage(username, message.content)
                        }
                        Ok(Json.toJson(true))
                        //Ok(views.html.MsgBoardAjax(username, model.getMessages(username)))

                    case e @ JsError(_) => BadRequest("P In putMessage(), Json.fromJson[Message](body) match JsError") //Redirect(routes.MsgBoard3.load)
                }
            }.getOrElse(Ok(Json.toJson(false)))      
        }.getOrElse(Ok(Json.toJson(false)))
    }

    def createUser = Action.async { implicit request =>
        withJsonBody[UserData] { ud => model.createUser(ud.username, ud.password).map { ouserId =>   
        ouserId match {
            case Some(userid) =>
            Ok(Json.toJson(true))
                .withSession("username" -> ud.username, "userid" -> userid.toString, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
            case None =>
            Ok(Json.toJson(false))
        }
        } }
    }


    def logOut = Action.async {implicit request =>
        Ok(Json.toJson(true)).withSession()    
    }







}


*/