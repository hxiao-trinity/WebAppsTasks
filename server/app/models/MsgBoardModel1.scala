package models

import scala.collection.mutable

object MsgBoardModel1 {

    // A simple message structure.
    case class Message(from:String, content:String, to:Option[String] = None)

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

    def postMessage(from: String, content: String, to:Option[String] = None): Unit = {
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
        getMessagesSentTo(currentUsername) ++ getMessagesSentBy(currentUsername) ++ getPublicMessages(currentUsername)
    }

}
