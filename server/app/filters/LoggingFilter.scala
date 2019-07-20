package filters

import javax.inject.Inject
import akka.stream.Materializer
import play.api.Logging
import play.api.mvc._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class LoggingFilter @Inject()(implicit val mat: Materializer, ec: ExecutionContext) extends Filter with Logging {

  def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {

    val startTime = System.currentTimeMillis

    nextFilter(requestHeader).map { result =>
      val endTime     = System.currentTimeMillis
      val requestTime = endTime - startTime

      println(
        s"!!! ${requestHeader.method} ${requestHeader.uri} took ${requestTime}ms and returned ${result.header.status}"
      )
      requestHeader.headers.headers.foreach(println)

      result.withHeaders("Request-Time" -> requestTime.toString)
    }
  }
}