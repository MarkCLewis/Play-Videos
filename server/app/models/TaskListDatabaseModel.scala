package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext
import models.Tables._
import scala.concurrent.Future
import org.mindrot.jbcrypt.BCrypt

class TaskListDatabaseModel(db: Database)(implicit ec: ExecutionContext) {
  // def validateUser(username: String, password: String): Future[Option[Int]] = {
  //   val matches = db.run(Users.filter(userRow => userRow.username === username).result)
  //   matches.map(userRows => userRows.headOption.flatMap {
  //     userRow => if (BCrypt.checkpw(password, userRow.password)) Some(userRow.id) else None
  //   })
  // }

   //val testStudent = StudentRow(-1, "John Doe", "jdoe", "password")
   //val insertResullt:Future[Int] = db.run(Student += StudentRow(-1, "name", "username", "password"))

  def validateStudent(username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Student.filter(studentRow => studentRow.username === username).result)
    matches.map(studentRows => studentRows.headOption.flatMap {
      studentRow => if (BCrypt.checkpw(password, studentRow.password)) Some(studentRow.studentId) else None
    })
  }

  def validateFaculty(username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Faculty.filter(userRow => userRow.username === username).result)
    matches.map(userRows => userRows.headOption.flatMap {
      userRow => if (BCrypt.checkpw(password, userRow.password)) Some(userRow.facultyId) else None
    })
  }
  
  
  // def createUser(username: String, password: String): Future[Option[Int]] = {
  //   val matches = db.run(Users.filter(userRow => userRow.username === username).result)
  //   matches.flatMap { userRows =>
  //     if (userRows.isEmpty) {
  //       db.run(Users += UsersRow(-1, username, BCrypt.hashpw(password, BCrypt.gensalt())))
  //         .flatMap { addCount => 
  //           if (addCount > 0) db.run(Users.filter(userRow => userRow.username === username).result)
  //             .map(_.headOption.map(_.id))
  //           else Future.successful(None)
  //         }
  //     } else Future.successful(None)
  //   }
  // }

  def createStudentUser(name: String, username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Student.filter(studentRow => studentRow.username === username).result)
    matches.flatMap { studentRows =>
      if (studentRows.isEmpty) {
        db.run(Student += StudentRow(-1, name, username, BCrypt.hashpw(password, BCrypt.gensalt())))
          .flatMap { addCount => 
            if (addCount > 0) db.run(Student.filter(studentRow => studentRow.username === username).result)
              .map(_.headOption.map(_.studentId))
            else Future.successful(None)
          }
      } else Future.successful(None)
    }
  }

  def createFacultyUser(name: String, username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Faculty.filter(facultyRow => facultyRow.username === username).result)
    matches.flatMap { facultyRows =>
      if (facultyRows.isEmpty) {
        db.run(Faculty += FacultyRow(-1, name, username, BCrypt.hashpw(password, BCrypt.gensalt())))
          .flatMap { addCount => 
            if (addCount > 0) db.run(Faculty.filter(facultyRow => facultyRow.username === username).result)
              .map(_.headOption.map(_.facultyId))
            else Future.successful(None)
          }
      } else Future.successful(None)
    }
  }
  
  // def getTasks(username: String): Future[Seq[TaskItem]] = {
  //   db.run(
  //     (for {
  //       user <- Users if user.username === username
  //       item <- Items if item.userId === user.id
  //     } yield {
  //       item
  //     }).result
  //   ).map(items => items.map(item => TaskItem(item.itemId, item.text)))
  // }
  
  // def addTask(userid: Int, task: String): Future[Int] = {
  //   //db.run(Items += ItemsRow(-1, userid, task))
  // }
  
  // def removeTask(itemId: Int): Future[Boolean] = {
  //   db.run( Items.filter(_.itemId === itemId).delete ).map(count => count > 0)
  // }
  
}
