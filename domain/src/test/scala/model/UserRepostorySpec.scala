package model

import dao.UserDAO
import dto.UserDTO
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

import scala.util.Success

class UserRepostorySpec extends Specification with Mockito {
  val mockUserDAO: UserDAO = mock[UserDAO]
  val mockUserRepo = new UserRepository(mockUserDAO)
  "UserRepository" >> {
    "getByEmail return Try[User]" >> {
      val userDTO = UserDTO("1", "test@gmail.com", "123456", "2013/1/1", "Staff", "BO", 12, "Active")
      val user = User(UserId("1"), "test@gmail.com", "123456", "2013/1/1", UserRole("Staff"), Department("BO"), 12, UserStatus("Active"))
      mockUserDAO.getByEmail("test@gmail.com") returns Success(userDTO)
      mockUserRepo.resolveByEmail("test@gmail.com").get must equalTo(user)
    }
  }
}
