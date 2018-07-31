package model

import dao.UserDAO
import javax.inject.Inject
import javax.swing.text.html.parser.Entity
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

  def store(entity: User): Try[User] = Try {
    userDAO.getByID(userDAO.insert(UserDomainService.toDataTransferObject(entity)).get).map(UserDomainService.toEntity).get
  }
}