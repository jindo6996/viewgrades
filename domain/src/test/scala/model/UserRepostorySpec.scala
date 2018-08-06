package model

import dao.UserDAO
import dto.UserDTO
import exceptions.{ EmailNotFoundException, EntityDuplicateException, IdNotFoundException }
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import service.UserDomainService

import scala.util.{ Failure, Success, Try }

class UserRepostorySpec extends Specification with Mockito {

  "resolveByEmail" >> {
    " return Try[User]" >> {
      val mockUserDAO = mock[UserDAO]
      mockUserDAO.getByEmail("test1@gmail.com") returns Success(UserDTO("001", "test1@gmail.com", "123456", "2013/1/1", "STAFF", "Infra", 20, "ACTIVE", ""))
      val repositorySpec = new UserRepository(mockUserDAO)
      repositorySpec.resolveByEmail("test1@gmail.com") must be equalTo Try(User(UserId("001"), "test1@gmail.com", "123456", "2013/1/1", UserRole.Staff, Department("Infra"), 20, UserStatus.Active, ""))
    }
    "return Exception" >> {
      val mockUserDAO = mock[UserDAO]
      mockUserDAO.getByEmail("test1@gmail.com") returns Failure(EmailNotFoundException("User not found"))
      val repositorySpec = new UserRepository(mockUserDAO)
      repositorySpec.resolveByEmail("test1@gmail.com") must be equalTo Failure(EmailNotFoundException("User not found"))
    }
  }

  "resolveAll" >> {
    "return Try[List[User]]" >> {
      val mockUserDAO = mock[UserDAO]
      mockUserDAO.getAll returns Success(List(UserDTO("001", "test1@gmail.com", "123456", "2013/1/1", "STAFF", "Infra", 20, "ACTIVE", "")))
      val repositorySpec = new UserRepository(mockUserDAO)
      repositorySpec.resolveAll must be equalTo Try(List(User(UserId("001"), "test1@gmail.com", "123456", "2013/1/1", UserRole.Staff, Department("Infra"), 20, UserStatus.Active, "")))
    }
  }

  "resolveById" >> {
    " return Try[User]" >> {
      val mockUserDAO = mock[UserDAO]
      mockUserDAO.getById("001") returns Success(UserDTO("001", "test1@gmail.com", "123456", "2013/1/1", "STAFF", "Infra", 20, "ACTIVE", ""))
      val repositorySpec = new UserRepository(mockUserDAO)
      repositorySpec.resolveById("001") must be equalTo Try(User(UserId("001"), "test1@gmail.com", "123456", "2013/1/1", UserRole.Staff, Department("Infra"), 20, UserStatus.Active, ""))
    }

    "return Exception" >> {
      val mockUserDAO = mock[UserDAO]
      mockUserDAO.getById("001") returns Failure(IdNotFoundException("User not found"))
      val repositorySpec = new UserRepository(mockUserDAO)
      repositorySpec.resolveById("001") must be equalTo Failure(IdNotFoundException("User not found"))
    }
  }

  val userDTO = UserDTO("001", "test1@gmail.com", "123456", "2013/1/1", "STAFF", "Infra", 20, "ACTIVE", "")
  "store" >> {
    "return user added" >> {
      val mockUserDAO = mock[UserDAO]
      mockUserDAO.isEmailNotExist("test1@gmail.com") returns true
      mockUserDAO.isIdNotExist("001") returns true
      mockUserDAO.insert(userDTO) returns Success(1)
      mockUserDAO.getByEmail("test1@gmail.com") returns Success(userDTO)
      val repositorySpec = new UserRepository(mockUserDAO)
      repositorySpec.store(UserDomainService.toEntity(userDTO)) must be equalTo Try(User(UserId("001"), "test1@gmail.com", "123456", "2013/1/1", UserRole.Staff, Department("Infra"), 20, UserStatus.Active, ""))
    }

    "ID Duplicate" >> {
      val mockUserDAO = mock[UserDAO]
      mockUserDAO.isEmailNotExist("test1@gmail.com") returns true
      mockUserDAO.isIdNotExist("001") returns false
      val repositorySpec = new UserRepository(mockUserDAO)
      repositorySpec.store(UserDomainService.toEntity(userDTO)) must be equalTo Failure(EntityDuplicateException("ID Duplicate", UserDomainService.toEntity(userDTO)))
    }

    "Email Duplicate" >> {
      val mockUserDAO = mock[UserDAO]
      mockUserDAO.isEmailNotExist("test1@gmail.com") returns false
      val repositorySpec = new UserRepository(mockUserDAO)
      repositorySpec.store(UserDomainService.toEntity(userDTO)) must be equalTo Failure(EntityDuplicateException("Email Duplicate", UserDomainService.toEntity(userDTO)))
    }
  }

  "edit" >> {
    "success" >> {
      val mockUserDAO = mock[UserDAO]
      mockUserDAO.isEmailNotExistWithoutID("test1@gmail.com", "001") returns true
      mockUserDAO.editUser(userDTO) returns Success(1)
      val repositorySpec = new UserRepository(mockUserDAO)
      repositorySpec.edit(UserDomainService.toEntity(userDTO)) must be equalTo Success(1)

    }

    "Email Duplicate" >> {
      val mockUserDAO = mock[UserDAO]
      mockUserDAO.isEmailNotExistWithoutID("test2@gmail.com", "001") returns false
      val repositorySpec = new UserRepository(mockUserDAO)
      repositorySpec.edit(UserDomainService.toEntity(userDTO)) must be equalTo Failure(EntityDuplicateException("Email Duplicate", UserDomainService.toEntity(userDTO)))
    }
  }
}

