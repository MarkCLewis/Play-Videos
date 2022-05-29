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
class Ratings @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  //Presents a test-page for the rating system
  //currently takes a username/course name as input for displaying the page
  def ratings() = Action { implicit request =>
  val user = "Dr. Professor"
  Ok(views.html.ratings(user)(RatingsInMemoryModel.getRating(user)))
  }  
  
  //Adds rating to current professor/course on scale of 0.0 to 5.0
  //Doesn't work with empty string
  def rate = Action { implicit request =>
    val user = "Dr. Professor"
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val rating = (args("newRating").head).toDouble
      if (rating > 0.0 && rating <= 5.0) {
        RatingsInMemoryModel.addRating(user, rating);
      }
      Redirect(routes.Ratings.ratings())
    }.getOrElse(Redirect(routes.Ratings.ratings()))
  }
  }
