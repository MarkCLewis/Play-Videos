import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory

class TaskList1Spec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
  "Task list 1" must {
    "login and see task list" in {
      go to s"http://localhost:$port/login1"
      pageTitle mustBe "Login"
      find(cssSelector("h2")).isEmpty mustBe false
      find(cssSelector("h2")).foreach(e => e.text mustBe "Login")
      click on "username-login"
      textField("username-login").value = "Mark"
      click on "password-login"
      pwdField(id("password-login")).value = "pass"
      submit()
      eventually {
        pageTitle mustBe "Task List"
        find(cssSelector("h2")).isEmpty mustBe false
        find(cssSelector("h2")).foreach(e => e.text mustBe "Task List")
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("Make videos", "eat","sleep", "code")
      }
    }

    "add task in" in {
      pageTitle mustBe "Task List"
      click on "newTask"
      textField("newTask").value = "test"
      submit()
      eventually {
        pageTitle mustBe "Task List"
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("test", "Make videos", "eat", "sleep","code")
      }
    }

    "delete task" in {
      pageTitle mustBe "Task List"
      click on "delete-3"
      eventually {
        pageTitle mustBe "Task List"
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("test", "Make videos", "eat", "code")
      }
    }

    "logout" in {
      pageTitle mustBe "Task List"
      click on "logout"
      eventually {
        pageTitle mustBe "Login"
      }
    }

    "create user and add two tasks" in {
      pageTitle mustBe "Login"
      click on "username-create"
      textField("username-create").value = "Lewis"
      click on "password-create"
      pwdField(id("password-create")).value = "foo"
      submit()
      eventually {
        pageTitle mustBe "Task List"
        findAll(cssSelector("li")).toList.map(_.text) mustBe Nil
        click on "newTask"
        textField("newTask").value = "test1"
        submit()
        eventually {
          pageTitle mustBe "Task List"
          findAll(cssSelector("li")).toList.map(_.text) mustBe List("test1")
          click on "newTask"
          textField("newTask").value = "test2"
          submit()
          eventually {
            pageTitle mustBe "Task List"
            findAll(cssSelector("li")).toList.map(_.text) mustBe List("test2", "test1")
          }
        }
      }
    }
  }  
}