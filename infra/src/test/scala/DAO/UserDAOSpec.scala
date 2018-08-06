package DAO

import dao.UserDAO
import dto.UserDTO
import org.specs2.mutable.Specification
import scalikejdbc._
import scalikejdbc.config.DBs

import scala.util.{ Failure, Success, Try }

class UserDAOSpec extends Specification {
  DBs.setup()
  implicit val session: DBSession = AutoSession
  val userDao = new UserDAO
  "getByEmail" >> {
    sql"DELETE FROM users".update().apply()
    "User not exit" >> {
      val result = userDao.getByEmail("testnotfound@gmail.com") match {
        case Failure(exception) => exception
      }
      result.getMessage mustEqual ("User not found")
    }
    "get success" >> {
      sql"INSERT INTO users(userId, email, password, entryCompanyDate, userRole, department, annualLeave, userStatus) VALUES (${"1"}, ${"test@gmail.com"}, ${"123456"}, ${"2013/1/1"}, ${"Staff"}, ${"BO"}, 12, ${"Active"})".update().apply()
      val result = userDao.getByEmail("test@gmail.com") match {
        case Success(user) => user
      }
      result.email mustEqual ("test@gmail.com")
    }

  }

  "getByID" >> {
    "User not exit" >> {
      val result = userDao.getById("IdNotFound") match {
        case Failure(exception) => exception
      }
      result.getMessage mustEqual ("User not found")
    }
    "get success" >> {
      sql"INSERT INTO users(userId, email, password, entryCompanyDate, userRole, department, annualLeave, userStatus) VALUES (${"001"}, ${"test@test.com"}, ${"123456"}, ${"2013/1/1"}, ${"Staff"}, ${"BO"}, 12, ${"Active"})".update().apply()
      val result = userDao.getById("001") match {
        case Success(user) => user
      }
      result.userId mustEqual ("001")
    }

  }

  "insert" >> {
    "Insert success" >> {
      userDao.insert(UserDTO("002", "test2@gmail.com", "123456", "2013/1/1", "STAFF", "DevOps", 20, "ACTIVE", ""))
      userDao.getById("002").get.email must be equalTo ("test2@gmail.com")
    }
  }

  "edit" >> {
    "Insert success" >> {
      userDao.insert(UserDTO("003", "test3@gmail.com", "123456", "2013/1/1", "STAFF", "DevOps", 20, "ACTIVE", ""))
      userDao.editUser(UserDTO("003", "test333@gmail.com", "123456", "2013/1/1", "STAFF", "DevOps", 20, "ACTIVE", ""))
      userDao.getById("003").get.email must be equalTo ("test333@gmail.com")
    }
  }

  "is exit" >> {
    userDao.insert(UserDTO("004", "test4@gmail.com", "123456", "2013/1/1", "STAFF", "DevOps", 20, "ACTIVE", ""))
    "email" >> {
      userDao.isEmailNotExist("test4@gmail.com") must be equalTo (false)
    }
    "id" >> {
      userDao.isIdNotExist("004") must be equalTo (false)
    }
    "email without id" >> {
      userDao.isEmailNotExistWithoutID("test2@gmail.com", "004") must be equalTo (false)
    }
  }

  "is not exit" >> {
    userDao.insert(UserDTO("005", "test5@gmail.com", "123456", "2013/1/1", "STAFF", "DevOps", 20, "ACTIVE", ""))
    "email" >> {
      userDao.isEmailNotExist("test50@gmail.com") must be equalTo (true)
    }
    "id" >> {
      userDao.isIdNotExist("0050") must be equalTo (true)
    }
    "email without id" >> {
      userDao.isEmailNotExistWithoutID("test50@gmail.com", "005") must be equalTo (true)
    }
  }
}
