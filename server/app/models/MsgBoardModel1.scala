package models

import scala.collection.mutable.Map

object MsgBoardModel1 {

    private var users = Map[String,String]("mark" -> "lewis", "web" -> "apps")


    def validateUser(username:String, password:String) : Boolean = {
        users.get(username).map(_ == password).getOrElse(false)
    }

    def createUser(username:String, password:String) : Boolean = {
        
        users += (username -> password)
    }

    def sendMsg(msg:String) = {
        ???
    }
}
