package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models._
import play.api.libs.json._
import models.Tables._
import play.api.data.Forms._


import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future

import shared.SharedMessages
import play.api.mvc._
import play.api.i18n._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable

@Singleton
class Student @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext) 
    extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {
  
  private val model = new TaskListDatabaseModel(db)
  private var sessionId:Int = 0
  private var courselist:ListBuffer[(Int, String, String, Int)] = mutable.ListBuffer()

  implicit val studentDataReads = Json.reads[StudentData]
  //implicit val taskItemWrites = Json.writes[TaskItem]

  def load = Action { implicit request =>
     Ok(views.html.studentLogin())
   }

  def withJsonBody[A](f: A => Future[Result])(implicit request: Request[AnyContent], reads: Reads[A]): Future[Result] = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Future.successful(Redirect(routes.Student.loginStudent()))
      }
    }.getOrElse(Future.successful(Redirect(routes.Student.studentProfile())))
  }
  
  def withSessionName(f: String => Future[Result])(implicit request: Request[AnyContent]): Future[Result] = {
    request.session.get("name").map(f).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  def withSessionUsername(f: String => Future[Result])(implicit request: Request[AnyContent]): Future[Result] = {
    request.session.get("username").map(f).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  def withSessionStudentid(f: Int => Future[Result])(implicit request: Request[AnyContent]): Future[Result] = {
    request.session.get("studentId").map(studentId => f(studentId.toInt)).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }


  def loginStudent = Action { implicit request =>
      Ok(views.html.studentLogin())
  }


  def validateStudent = Action.async { implicit request =>
    val validVals = request.body.asFormUrlEncoded
    var validUsername:String = ""
      var validPassword:String = ""
     validVals.map { args => 
      validUsername = args("username").head
      validPassword = args("password").head
      }.getOrElse(Redirect(routes.Student.loginStudent()))
      model.validateStudent(validUsername, validPassword).map {  ostudentId =>
        ostudentId match {
          case Some(studentid) =>
            sessionId = studentid
            Redirect(routes.Student.studentProfile())
          case None =>
            Redirect(routes.Student.loginStudent())
        }
      }
    }

  def studentProfile = Action { implicit request =>
      //println("before call")
      //val test = getAllCourses()
      //println(test)
      //println(courselist)
      //model.addCourse(4, 4) //model.addCourse works!!
      //model.addRating(4, 4, 1)
      Ok(views.html.studentProfile())
  }

  // def getAllCoursesList(): Future[ListBuffer[(Int, String, String, Int)]] =  {
  //   courselist
  // }
  
  //  def getAllCourses() = Action.async { implicit request =>
  //    println("beginning")
  //    model.getAllCourses().map { courseRows => 
  //      println("before match")
  //      courseRows match { 
  //      case courseRow =>
  //        println("before map")
  //        courseRow.map( course => 
  //         courselist += ((course.courseId, course.courseName, course.courseNumber, course.facultyId)) )
  //         println("case 1")
  //           Ok(views.html.studentProfile())
  //      case _ =>
  //        println("case 2")
  //           Ok(views.html.studentProfile())
  //      }
  //   }
  // }


  // def getStudentCourses() = Action.async { implicit request =>
      //model.getMyCourses(sessionId)
  // }

  def addCourse = Action.async { implicit request =>
    val addVals = request.body.asFormUrlEncoded
    var addCourseId:Int = 0
    addVals.map { args => 
      addCourseId = args("courseId").head.toInt
    }.getOrElse(Redirect(routes.Student.studentProfile()))
    model.addCourse(sessionId, addCourseId).map { ocount => 
      ocount match { 
      case Some(count) => 
        if (count > 0) Redirect(routes.Student.studentProfile()) else Redirect(routes.Student.studentProfile()) 
      case None => 
        Redirect(routes.Student.studentProfile())} 
    }
  }

  // def getStudentName = Action { implicit request => 
  //}

    // def addRating = Action.async = { implicit request => 

    // }


def createStudentUser = Action.async { implicit request =>
    val createVals = request.body.asFormUrlEncoded
    var createName:String = ""
    var createUsername:String = ""
    var createPassword:String = ""
     createVals.map { args => 
      createName = args("name").head
      createUsername = args("username").head
      createPassword = args("password").head
    }.getOrElse(Redirect(routes.Student.loginStudent()))
    model.createStudentUser(createName, createUsername, createPassword).map {  ostudentId =>
        ostudentId match {
          case Some(studentid) =>
            sessionId = studentid
            Redirect(routes.Student.studentProfile())
          case None =>
            Redirect(routes.Student.loginStudent())
        }
      }
  }


  def index = Action { implicit request =>
      val facultyMember = "John"
      val password = 12345
      Ok(views.html.studentLogin())
  }

}