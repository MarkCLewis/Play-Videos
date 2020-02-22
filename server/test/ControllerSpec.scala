import org.scalatestplus.play.PlaySpec
import controllers.Application
import play.api.test.Helpers
import play.api.test.FakeRequest
import play.api.test.Helpers._


class ControllerSpec extends PlaySpec {
  "The Application controller" must {
    val controller = new Application(Helpers.stubControllerComponents())
    "give back expected index page" in {
      val result = controller.index.apply(FakeRequest())
      val bodyText = contentAsString(result)
      bodyText must include ("Play and Scala.js")
      bodyText must include ("Click for a new random number:")
      bodyText must include ("Click for a new random string:")
    }

    "give back a product" in {
      val result = controller.product("test", 42).apply(FakeRequest())
      val bodyText = contentAsString(result)
      bodyText mustBe ("Product type is: test, product number is: 42")
    }

    "give back a number" in {
      val result = controller.randomNumber.apply(FakeRequest())
      val bodyText = contentAsString(result)
      bodyText.toInt must be >= 0
      bodyText.toInt must be < 100
    }
  }
}