package controllers

import models.MemModel._
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

    private val model = new DbModel(db)

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

    def load = Action { implicit request =>
        Ok(views.html.MsgBoard5Main())
    }

    implicit val userDataReads = Json.reads[UserData]

    def validate = TODO 
    // Action.async { implicit request =>
    //     withJsonBody[UserData] { ud =>
    //         (model.validateUser(ud.username, ud.password)).map { userExists =>
    //             if (userExists)
    //                 Ok(Json.toJson(true)).withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
    //             else
    //                 Ok(Json.toJson(false))
    //         }
    //     }
    // }

    def msgBoard = TODO
    // Action.async { implicit request =>
    //     withSessionUsername { username =>
    //         println(username + " " + model.getMessages(username))
    //         Ok(Json.toJson(model.getMessages(username)))
    //     }
    // } 

    def putMessage = TODO
    // Action.async { implicit request =>
    //     withSessionUsername { username =>
    //         withJsonBody[Message] { message =>
    //             if (message.to.isDefined && message.to.nonEmpty)
    //                 model.putMessage(username, message.content, message.to)
    //             else
    //                 model.putMessage(username, message.content)
    //             Ok(Json.toJson(true))
    //         }
    //     }
    // }

    def createUser = TODO
    // Action.async { implicit request =>
    //     withJsonBody[UserData] { ud =>
    //         if (model.createUser(ud.username, ud.password)) {
    //             Ok(Json.toJson(true))
    //             .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
    //         } else {
    //             Ok(Json.toJson(false))
    //         }
    //     }
    // }


    def logOut = Action {implicit request =>
        Ok(Json.toJson(true)).withSession()    
    }


}

