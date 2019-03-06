package edu.trinity.webapps.controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

case class MonthYear(month: Int, year: Int)

@Singleton
class TempController @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  val monthForm = Form(mapping(
    "month" -> number(1, 12),
    "year" -> number(1946, 2014))(MonthYear.apply)(MonthYear.unapply))

  def welcome = Action { implicit request =>
    Ok(views.html.tempWelcome(monthForm))
  }

  def month(m: Int, y: Int) = Action {
    val monthData = models.TempDataModel.monthData(m, y)
    Ok(views.html.monthTable(m, y, monthData))
  }

  def postMonth = Action { implicit request =>
    val postBody = request.body.asFormUrlEncoded
    postBody.map { args =>
      try {
        val m = args("month").head.toInt
        val y = args("year").head.toInt
        val monthData = models.TempDataModel.monthData(m, y)
        Ok(views.html.monthTable(m, y, monthData))
      } catch {
        case ex: NumberFormatException => Redirect("tempWelcome", 200)
      }
    }.getOrElse(Redirect("tempWelcome", 200))
  }

  def postMonthForm = Action { implicit request =>
    monthForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.tempWelcome(formWithErrors)),
      monthYear => {
        val monthData = models.TempDataModel.monthData(monthYear.month, monthYear.year)
        Ok(views.html.monthTable(monthYear.month, monthYear.year, monthData))
      })
  }
}