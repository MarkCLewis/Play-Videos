package playscala

import play.api.libs.json.JsError
import org.scalajs.dom.experimental.Headers
import org.scalajs.dom.experimental.Fetch
import org.scalajs.dom.experimental.HttpMethod
import org.scalajs.dom.experimental.RequestInit
import play.api.libs.json.Json
import play.api.libs.json.JsSuccess
import play.api.libs.json.Writes
import play.api.libs.json.Reads
import scala.scalajs.js.Thenable.Implicits._
import scala.concurrent.ExecutionContext


object FetchJson {
  def fetchPost[A, B](url: String, csrfToken: String, 
      data: A, success: B => Unit, error: JsError => Unit)(implicit
      writes: Writes[A], reads: Reads[B], ec: ExecutionContext): Unit = {
    val headers = new Headers()
    headers.set("Content-Type", "application/json")
    headers.set("Csrf-Token", csrfToken)
    Fetch.fetch(url, RequestInit(method = HttpMethod.POST,
        headers = headers, body = Json.toJson(data).toString))
      .flatMap(res => res.text())
      .map { data => 
        Json.fromJson[B](Json.parse(data)) match {
          case JsSuccess(b, path) =>
            success(b)
          case e @ JsError(_) =>
            error(e)
        }
    }
  }

  def fetchGet[B](url: String, success: B => Unit, error: JsError => Unit)(implicit
      reads: Reads[B], ec: ExecutionContext): Unit = {
    Fetch.fetch(url)
      .flatMap(res => res.text())
      .map { data => 
        Json.fromJson[B](Json.parse(data)) match {
          case JsSuccess(b, path) =>
            success(b)
          case e @ JsError(_) =>
            error(e)
        }
    }
  }
}