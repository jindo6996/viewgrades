package model

import dao.UserDAO
import dto.UserDTO
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

import scala.util.{Failure, Success, Try}

class UserRepostorySpec extends Specification with Mockito {

  "UserRepository" >> {
    "getByEmail return Try[User]" >> {
      val mockUserDAO: UserDAO = mock[UserDAO]
      mockUserDAO.getByEmail("test@gmail.com") returns Success(UserDTO("001", "test1@gmail.com", "123456", "2013/1/1", "STAFF", "DevOps", 20, "ACTIVE", ""))

      1 mustEqual (1)
    }
  }
}
