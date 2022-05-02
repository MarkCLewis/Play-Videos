package controllers

import javax.inject._


import shared.SharedMessages
import play.api.mvc._
import play.api.i18n._
import play.api.data._
import play.api.data.Forms._
//test data for Ratings
import models.RatingsInMemoryModel

@Singleton
class Courses @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  //current course page
  //no database linkage, just hardcoded list of courses rn
  def courseList() = Action { implicit request =>
    val courses = List[String]("course", "other course", "another course")
    Ok(views.html.courseList(courses))
  }  
  

  }
