package model

import dao.UserDAO
import javax.inject.Inject
import service.UserDomainService

import scala.util.Try

class UserRepository @Inject() (userDAO: UserDAO) {
  def resolveByEmail(email: String): Try[Option[User]] = Try {
    userDAO.getByEmail(email: String).map(_.map(UserDomainService.toEntity)).get
  }
}