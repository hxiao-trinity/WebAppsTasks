package models

import models.Tables._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads
import play.api.libs.json._
import slick.jdbc.PostgresProfile.api._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.concurrent.Future



class DbModel(db:Database)(implicit ec:ExecutionContext){

    case class Message(from:String, content:String, to:Option[String] = None, sentAt:LocalDateTime = LocalDateTime.now())

    def validateUser(username:String, password:String) : Future[Boolean] = {
        val matches = db.run(Users.filter(userRow => userRow.username === username && userRow.password === password).result)
        matches.map(userRows => userRows.nonEmpty)
    }

    def createUser(username:String, password:String) : Future[Boolean] = {
        ???
    }

    def putMessage(from: String, content: String, to:Option[String] = None): Future[Int] = {
        ???
    }

    def getMessages(currentUsername:String) : Future[Seq[Message]] = {
        ???
    }


    object Message {
        // Define a formatter for LocalDateTime
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        // Implicit Reads for LocalDateTime
        implicit val localDateTimeReads: Reads[LocalDateTime] = new Reads[LocalDateTime] {
            def reads(json: JsValue): JsResult[LocalDateTime] = json match {
            case JsString(value) =>
                try {
                JsSuccess(LocalDateTime.parse(value, dateTimeFormatter))
                } catch {
                case e: Exception => JsError("error.expected.date.isoformat")
                }
            case _ => JsError("error.expected.jsstring")
            }
        }

        // Implicit Reads for the Message case class
        implicit val messageReads: Reads[Message] = (
            (__ \ "from").read[String] and
            (__ \ "content").read[String] and
            (__ \ "to").readNullable[String] and
            Reads.pure(LocalDateTime.now)
        )(Message.apply _)

        // Implicit Writes for the Message case class
        implicit val messageWrites: Writes[Message] = Json.writes[Message]

        // Implicit Writes for Seq[Message]
        // Note: This is typically automatically available if messageWrites is in scope.
        // implicit val seqMessageWrites: Writes[Seq[Message]] = Writes.seq[Message]
    }



}