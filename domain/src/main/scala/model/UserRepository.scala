package model

import dao.UserDAO
import javax.inject.Inject
import org.mindrot.jbcrypt.BCrypt
import service.UserDomainService

import scala.util.Try

class UserRepository @Inject() (userDAO: UserDAO) {
  def resolveByEmail(email: String): Try[User] = Try {
    userDAO.getByEmail(email: String).map(UserDomainService.toEntity).get
  }

  def resolveAll: Try[List[User]] = Try {
    userDAO.getAll.map(_.map(UserDomainService.toEntity)).get
  }

  def checkPassword(password: String, passwordHash: String): Boolean =
    BCrypt.checkpw(password, passwordHash)
}