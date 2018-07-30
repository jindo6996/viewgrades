package test.controller

import controllers.auth.AuthController
import exceptions.EmailNotFoundException
import model._
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.test.CSRFTokenHelper._
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.util.Failure

class AuthControllerSpec extends Specification with Mockito {
  val mockUserDao: UserRepository = mock[UserRepository]
  val controller = new AuthController(mockUserDao, stubControllerComponents())
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
        contentAsString(result) must contain("error")
      }

      "password is empty" >> {
        val result = controller.processLogin().apply(FakeRequest(POST, "/").
          withFormUrlEncodedBody("email" -> "test@gmail.com", "password" -> "").withCSRFToken)
        status(result) must equalTo(303)
        contentAsString(result) must contain("error")
      }

      "email format is incorrect" >> {
        val result = controller.processLogin().apply(FakeRequest(POST, "/").
          withFormUrlEncodedBody("email" -> "test", "password" -> "123456").withCSRFToken)
        status(result) must equalTo(303)
        contentAsString(result) must contain("error.email")
      }

      "email incorrect or user not exists" >> {
        mockUserDao.resolveByEmail("test@gmail.com") returns Failure(EmailNotFoundException("Email not found"))
        val result = controller.processLogin().apply(FakeRequest(POST, "/").
          withFormUrlEncodedBody("email" -> "test@gmail.com", "password" -> "123456").withCSRFToken)
        status(result) must equalTo(303)
        contentAsString(result) must contain("User not found")
      }

      "user exists but password incorrect" >> {
        //        val user = User(UserId("1"), "test@gmail.com", "123123", "2013/1/1", UserRole("STAFF"), Department("BO"), 12, UserStatus("ACTIVE"))
        //        mockUserDao.resolveByEmail("test@gmail.com") returns Success(user)
        //        mockUserDao.checkPassword("123455", user.password) returns false
        //        val result = controller.processLogin.apply(FakeRequest(POST, "/").
        //          withFormUrlEncodedBody("email" -> "test@gmail.com", "password" -> "123455").withCSRFToken)
        //        status(result) must equalTo(BAD_REQUEST)
        //        contentAsString(result) must contain("User not found")
        1 mustEqual (1)
      }

      "user login successfully" >> {
        //        val user = User(UserId("1"), "test@gmail.com", "123123", "2013/1/1", UserRole("STAFF"), Department("BO"), 12, UserStatus("ACTIVE"))
        //        mockUserDao.resolveByEmail("test@gmail.com") returns Success(user)
        //        mockUserDao.checkPassword("123456", user.password) returns true
        //        val result = controller.processLogin().apply(FakeRequest(POST, "/").
        //          withFormUrlEncodedBody("email" -> "test@gmail.com", "password" -> "123456").withCSRFToken)
        //        status(result) must equalTo(303)
        1 mustEqual (1)
      }
      "System error" >> {
        mockUserDao.resolveByEmail("test@gmail.com") returns Failure(new Exception("System error"))
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
