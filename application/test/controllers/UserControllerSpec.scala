package test.controllers

import akka.actor.ActorSystem
import akka.stream._
import akka.util.Timeout
import controllers.user.UserController
import exceptions.EntityDuplicateException
import model._
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.test.CSRFTokenHelper._
import play.api.test.FakeRequest
import play.api.test.Helpers.{ POST, contentAsString, status, stubControllerComponents }

import scala.concurrent.duration._
import scala.util.{ Failure, Success }

class UserControllerSpec extends Specification with Mockito {
  val mockUserRepo: UserRepository = mock[UserRepository]
  val controller = new UserController(mockUserRepo, stubControllerComponents())
  implicit val duration: Timeout = 20.seconds
  implicit val sys = ActorSystem("MyTest")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  "User Controller" >> {
    "processAddUser" >> {
      "Add success" >> {
        val user = User(UserId("036"), "test@gmail.com", "$2a$10$y1yl0I7dOxfQj26OYBRIn.Lv0UXWRokm7mGKGtuXOvMHtBnZYywdO", "2018-08-07", UserRole.Staff, Department("Back Office"), 12, UserStatus.Active, "2018-07-30 14:49:35.0")
        mockUserRepo.store(user) returns Success(user)
        val result = controller.processAddUser.apply(FakeRequest(POST, "/").
          withFormUrlEncodedBody("userId" -> "036", "email" -> "test@gmail.com", "entryCompanyDate" -> "2018-08-07", "userRole" -> "STAFF", "department" -> "Back Office", "annualLeave" -> "12").withCSRFToken)
        status(result) must equalTo(303)
      }
      "Email is empty" >> {
        val user = User(UserId("036"), "test@gmail.com", "$2a$10$y1yl0I7dOxfQj26OYBRIn.Lv0UXWRokm7mGKGtuXOvMHtBnZYywdO", "2018-08-07", UserRole.Staff, Department("Back Office"), 12, UserStatus.Active, "2018-07-30 14:49:35.0")
        val result = controller.processAddUser.apply(FakeRequest(POST, "/users").
          withFormUrlEncodedBody("userId" -> "", "email" -> "", "entryCompanyDate" -> "2018-08-07", "userRole" -> "STAFF", "department" -> "Back Office", "annualLeave" -> "12").withCSRFToken)
        status(result) must equalTo(303)
      }
      "Email duplicate" >> {
        EntityDuplicateException
        val user = User(UserId("036"), "test@gmail.com", "$2a$10$y1yl0I7dOxfQj26OYBRIn.Lv0UXWRokm7mGKGtuXOvMHtBnZYywdO", "2018-08-07", UserRole.Staff, Department("Back Office"), 12, UserStatus.Active, "2018-07-30 14:49:35.0")
        mockUserRepo.store(user) returns Failure(new EntityDuplicateException("Email Duplicate", user))
        val result = controller.processAddUser.apply(FakeRequest(POST, "/users").
          withFormUrlEncodedBody("userId" -> "", "email" -> "", "entryCompanyDate" -> "2018-08-07", "userRole" -> "STAFF", "department" -> "Back Office", "annualLeave" -> "12").withCSRFToken)
        status(result) must equalTo(400)
        contentAsString(result) must contain("Email Duplicate")
      }
      "ID duplicate" >> {
        EntityDuplicateException
        val user = User(UserId("036"), "test@gmail.com", "$2a$10$y1yl0I7dOxfQj26OYBRIn.Lv0UXWRokm7mGKGtuXOvMHtBnZYywdO", "2018-08-07", UserRole.Staff, Department("Back Office"), 12, UserStatus.Active, "2018-07-30 14:49:35.0")
        mockUserRepo.store(user) returns Failure(new EntityDuplicateException("Code Duplicate", user))
        val result = controller.processAddUser.apply(FakeRequest(POST, "/users").
          withFormUrlEncodedBody("userId" -> "", "email" -> "", "entryCompanyDate" -> "2018-08-07", "userRole" -> "STAFF", "department" -> "Back Office", "annualLeave" -> "12").withCSRFToken)
        status(result) must equalTo(400)
        contentAsString(result) must contain("Code Duplicate")
      }
      "System Error" >> {
        EntityDuplicateException
        val user = User(UserId("036"), "test@gmail.com", "$2a$10$y1yl0I7dOxfQj26OYBRIn.Lv0UXWRokm7mGKGtuXOvMHtBnZYywdO", "2018-08-07", UserRole.Staff, Department("Back Office"), 12, UserStatus.Active, "2018-07-30 14:49:35.0")
        mockUserRepo.store(user) returns Failure(new Exception("system error"))
        controller.processAddUser.apply(FakeRequest(POST, "/users").
          withFormUrlEncodedBody("userId" -> "", "email" -> "", "entryCompanyDate" -> "2018-08-07", "userRole" -> "STAFF", "department" -> "Back Office", "annualLeave" -> "12")
          .withCSRFToken) must throwA[Exception]
      }
    }
  }
}
