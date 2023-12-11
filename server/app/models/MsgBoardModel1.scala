package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.collection.mutable

object MsgBoardModel1 {

    // A simple message structure.
    case class Message(from:String, content:String, to:Option[String] = None, sentAt:LocalDateTime = LocalDateTime.now())

    private var users = mutable.Map[String,String]("mlewis" -> "prof", "web" -> "apps")
    private var messages = mutable.ListBuffer[Message](
        Message("mlewis", "Hello, Web!", Some("web")), 
        Message("web", "Hello, people!")
    )

    def validateUser(username:String, password:String) : Boolean = {
        users.get(username).map(_ == password).getOrElse(false)
    }

    def createUser(username:String, password:String) : Boolean = {
        if (users.contains(username)) 
            false 
        else {
            users(username) = password
            true
        }
    }

    def putMessage(from: String, content: String, to:Option[String] = None): Unit = {
        messages += Message(from, content, to)
    }

    def getMessagesSentBy(fromUsername: String): Seq[Message] = {
        messages.filter(_.from.contains(fromUsername)).toSeq
    }

    def getMessagesSentTo(toUsername: String): Seq[Message] = {
        messages.filter(_.to.contains(toUsername)).toSeq
    }

    //Get all PUBLIC messages. If that one is from fromUsername, count ONCE.
    def getPublicMessages(exceptFromUsername:String): Seq[Message] = { 
        (messages.filter(_.to.isEmpty)).filterNot((_.from.equals(exceptFromUsername))).toSeq
    }

    def getMessages(currentUsername:String) :Seq[Message] = {
        //(getMessagesSentTo(currentUsername) ++ getMessagesSentBy(currentUsername) ++ getPublicMessages(currentUsername))
        (
        messages.filter(_.from.contains(currentUsername)).toSeq ++
        messages.filter(_.to.contains(currentUsername)).toSeq ++
        (messages.filter(_.to.isEmpty)).filterNot((_.from.equals(currentUsername))).toSeq
        )
        .sortBy(_.sentAt)
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
