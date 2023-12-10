package models

import play.api.libs.json.Json
import play.api.libs.json.Writes

case class UserData (username:String, password:String)

object ReadsAndWrites {
  implicit val userDataReads = Json.reads[UserData]
  implicit val userDataWrites = Json.writes[UserData]
}