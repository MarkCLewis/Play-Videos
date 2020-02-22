import org.scalatestplus.play._
import models._

class TaskListInMemoryModelSpec extends PlaySpec {
  "TaskListInMemoryModel" must {
    "do valid login for default user" in {
      TaskListInMemoryModel.validateUser("Mark", "pass") mustBe (true)
    }

    "reject login with wrong password" in {
      TaskListInMemoryModel.validateUser("Mark", "password") mustBe (false)
    }

    "reject login with wrong username" in {
      TaskListInMemoryModel.validateUser("Mike", "pass") mustBe (false)
    }

    "reject login with wrong username and password" in {
      TaskListInMemoryModel.validateUser("Mike", "password") mustBe (false)
    }

    "get correct default tasks" in {
      TaskListInMemoryModel.getTasks("Mark") mustBe (List("Make videos", "eat","sleep","code"))
    }

    "create new user with no tasks" in {
      TaskListInMemoryModel.createUser("Mike", "password") mustBe (true)
      TaskListInMemoryModel.getTasks("Mike") mustBe (Nil)
    }

    "create new user with existing name" in {
      TaskListInMemoryModel.createUser("Mark", "password") mustBe (false)
    }

    "add new task for default user" in {
      TaskListInMemoryModel.addTask("Mark", "testing")
      TaskListInMemoryModel.getTasks("Mark") must contain ("testing")
    }

    "add new task for new user" in {
      TaskListInMemoryModel.addTask("Mike", "testing1")
      TaskListInMemoryModel.getTasks("Mike") must contain ("testing1")
    }

    "remove task from default user" in {
      TaskListInMemoryModel.removeTask("Mark", TaskListInMemoryModel.getTasks("Mark").indexOf("eat"))
      TaskListInMemoryModel.getTasks("Mark") must not contain ("eat")
    }
  }
}