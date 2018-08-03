package controllers

import controllers.user.UserController
import model.UserRepository
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.test.Helpers.stubControllerComponents

class UserControllerSpec extends Specification with Mockito {

  val mockUserRepo: UserRepository = mock[UserRepository]
  val controller = new UserController(mockUserRepo, stubControllerComponents())

}
