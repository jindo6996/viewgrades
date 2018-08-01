package test.controller

import controllers.auth.AuthController
import exceptions.EmailNotFoundException
import model._
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.test.CSRFTokenHelper._
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.util.{ Failure, Success }

class AuthControllerSpec extends Specification with Mockito {
  val mockUserRepo: UserRepository = mock[UserRepository]
  //  val check= mock(AccountService)
  val controller = new AuthController(mockUserRepo, stubControllerComponents())
  "AuthController" >> {
    "loginView" >> {
      "go to page login if not logged in" >> {
        val result = controller.loginView.apply(FakeRequest(GET, "/").withCSRFToken)
        status(result) must equalTo(OK)
        contentAsString(result) must contain("Sign in")
        contentAsString(result) must contain("email address")
        contentAsString(result) must contain("password")
      }
    }

    "processLogin" >> {
      "email is empty" >> {
        val result = controller.processLogin().apply(FakeRequest(POST, "/").
          withFormUrlEncodedBody("email" -> "", "password" -> "123456").withCSRFToken)
        status(result) must equalTo(303)
      }

      "password is empty" >> {
        val result = controller.processLogin().apply(FakeRequest(POST, "/").
          withFormUrlEncodedBody("email" -> "test@gmail.com", "password" -> "").withCSRFToken)
        status(result) must equalTo(303)
      }

      "email incorrect or user not exists" >> {
        mockUserRepo.resolveByEmail("test@gmail.com") returns Failure(EmailNotFoundException("Email not found"))
        val result = controller.processLogin().apply(FakeRequest(POST, "/").
          withFormUrlEncodedBody("email" -> "test@gmail.com", "password" -> "123456").withCSRFToken)
        status(result) must equalTo(303)
      }

      "user exists but password incorrect" >> {
        val user = User(UserId("1"), "test@gmail.com", "$2a$10$y1yl0I7dOxfQj26OYBRIn.Lv0UXWRokm7mGKGtuXOvMHtBnZYywdO", "2013/1/1", UserRole.Staff, Department("BO"), 12, UserStatus.Active, "2018-07-30 14:49:35.0")
        mockUserRepo.resolveByEmail("test@gmail.com") returns Success(user)
        //        mockUserRepo.checkPassword("123455", user.password) returns false
        val result = controller.processLogin.apply(FakeRequest(POST, "/").
          withFormUrlEncodedBody("email" -> "test@gmail.com", "password" -> "123456").withCSRFToken)
        status(result) must equalTo(303)
      }

      "user login successfully" >> {
        val user = User(UserId("1"), "test@gmail.com", "$2a$10$y1yl0I7dOxfQj26OYBRIn.Lv0UXWRokm7mGKGtuXOvMHtBnZYywdO", "2013/1/1", UserRole.Staff, Department("BO"), 12, UserStatus.Active, "2018-07-30 14:49:35.0")
        mockUserRepo.resolveByEmail("test@gmail.com") returns Success(user)
        //        mockUserRepo.checkPassword("123456", user.password) returns true
        val result = controller.processLogin().apply(FakeRequest(POST, "/").
          withFormUrlEncodedBody("email" -> "test@gmail.com", "password" -> "123456").withCSRFToken)
        status(result) must equalTo(303)
      }
      "System error" >> {
        mockUserRepo.resolveByEmail("test@gmail.com") returns Failure(new Exception("System error"))
        controller.processLogin.apply(FakeRequest(POST, "/").
          withFormUrlEncodedBody("email" -> "test@gmail.com", "password" -> "123455").withCSRFToken) must throwA[Exception]
      }
    }
    "logout" >> {
      "go to login page" >> {
        val result = controller.loginView.apply(FakeRequest(GET, "/logout").withCSRFToken)
        status(result) must equalTo(OK)
        contentAsString(result) must contain("Sign in")
        contentAsString(result) must contain("email address")
        contentAsString(result) must contain("password")
      }
    }
  }
}
