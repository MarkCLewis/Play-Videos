package models

import collection.mutable

object RatingsInMemoryModel {
  //Ratings are associated with usernames
  private val ratings = mutable.Map[String, List[Double]]("Dr. Professor" -> List(5.0))
  
  //Gets rating from ratings
  def getRating(username: String): String = {
    (ratings.get(username).getOrElse("")).toString
  }
  
  //Adds rating to ratings
  def addRating(username: String, rating: Double): Unit = {
    ratings(username) = rating :: ratings.get(username).getOrElse(Nil)
  }
  
  //def removeRating(username: String, index: Int): Boolean = ??
}