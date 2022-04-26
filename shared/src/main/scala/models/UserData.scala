package models

import play.api.libs.json.Json

// case class UserData(username: String, password: String)
// case class TaskItem(id: Int, text: String)

// object ReadsAndWrites {
//   implicit val userDataReads = Json.reads[UserData]
//   implicit val userDataWrites = Json.writes[UserData]

//   implicit val taskItemReads = Json.reads[TaskItem]
//   implicit val taskItemWrites = Json.writes[TaskItem]
// }

case class StudentData(name: String, username: String, password: String)
//case class TaskItem(id: Int, text: String)

object ReadsAndWrites {
  implicit val studentDataReads = Json.reads[StudentData]
  implicit val studentDataWrites = Json.writes[StudentData]

  // implicit val taskItemReads = Json.reads[TaskItem]
  // implicit val taskItemWrites = Json.writes[TaskItem]
}