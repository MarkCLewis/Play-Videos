package models

import play.api.libs.json.Json

case class UserData(username: String, password: String)
case class TaskItem(id: Int, text: String)

object ReadsAndWrites {
  implicit val userDataReads = Json.reads[UserData]
  implicit val userDataWrites = Json.writes[UserData]

  implicit val taskItemReads = Json.reads[TaskItem]
  implicit val taskItemWrites = Json.writes[TaskItem]
}