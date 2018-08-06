package controllers

import controllers.user.UserController
import model.UserRepository
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.libs.mailer.Email
import play.api.test.Helpers.stubControllerComponents

class UserControllerSpec extends Specification with Mockito {

  val email = Email(
    "Password of Timesheet",
    "<septenitimesheetmanager@gmail.com>",
    Seq(s"<test@gmail.com>"),
    bodyText = Some(s"Dear Test \nThis your passwork for website timesheet manager: 123123\nThank you and best regards,\nManager".stripMargin)
  )
  val mockUserRepo: UserRepository = mock[UserRepository]
  val controller = new UserController(mockUserRepo, stubControllerComponents())

}
