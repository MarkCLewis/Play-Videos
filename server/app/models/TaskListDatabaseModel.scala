package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext
import models.Tables._
import scala.concurrent.Future
import org.mindrot.jbcrypt.BCrypt

class TaskListDatabaseModel(db: Database)(implicit ec: ExecutionContext) {
  def validateUser(username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Users.filter(userRow => userRow.username === username).result)
    matches.map(userRows => userRows.headOption.flatMap {
      userRow => if (BCrypt.checkpw(password, userRow.password)) Some(userRow.id) else None
    })
  }
  
  def createUser(username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Users.filter(userRow => userRow.username === username).result)
    matches.flatMap { userRows =>
      if (userRows.isEmpty) {
        db.run(Users += UsersRow(-1, username, BCrypt.hashpw(password, BCrypt.gensalt())))
          .flatMap { addCount => 
            if (addCount > 0) db.run(Users.filter(userRow => userRow.username === username).result)
              .map(_.headOption.map(_.id))
            else Future.successful(None)
          }
      } else Future.successful(None)
    }
  }
  
  def getTasks(username: String): Future[Seq[TaskItem]] = {
    db.run(
      (for {
        user <- Users if user.username === username
        item <- Items if item.userId === user.id
      } yield {
        item
      }).result
    ).map(items => items.map(item => TaskItem(item.itemId, item.text)))
  }
  
  def addTask(userid: Int, task: String): Future[Int] = {
    db.run(Items += ItemsRow(-1, userid, task))
  }
  
  def removeTask(itemId: Int): Future[Boolean] = {
    db.run( Items.filter(_.itemId === itemId).delete ).map(count => count > 0)
  }
}