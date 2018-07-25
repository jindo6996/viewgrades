package DAO

import dao.UserDAO
import org.specs2.mutable.Specification
import scalikejdbc._
import scalikejdbc.config.DBs

import scala.util.{ Failure, Success }

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
}
