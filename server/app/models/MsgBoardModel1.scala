package models

import scala.collection.mutable

object MsgBoardModel1 {

    // A simple message structure.
    case class Message(content:String, from:String, to:Option[String] = None)

    private var users = mutable.Map[String,String]("mlewis" -> "prof", "web" -> "apps")
    private var msgs = mutable.ListBuffer[Message](Message("Hello, Web!", "mlewis", Some("web")), Message("Hello, people!", "web"))

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

    def sendMsg(username: String, content: String): Unit = {
        msgs += Message(content, username, Some(username))
    }

    def getMessagesFrom(username: String): Seq[Message] = {
        msgs.filter(_.from.contains(username)).toSeq
    }

    //Get all PUBLIC messages. If that one is from username, count ONCE.
    def getPublicMessages(username:String): Seq[Message] = { 
        var temp = (msgs.filter(_.to.isEmpty)).filterNot((_.from.equals(username))).toSeq
        temp
    }

    def getMessages(username:String) :Seq[Message] = {
        getMessagesFrom(username) ++ getPublicMessages(username)
    }

}
