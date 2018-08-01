package model

import dao.UserDAO
import javax.inject.Inject
import service.UserDomainService

import scala.util.Try

class UserRepository @Inject() (userDAO: UserDAO) {
  def resolveByEmail(email: String): Try[User] = Try {
    userDAO.getByEmail(email: String).map(UserDomainService.toEntity).get
  }

  def resolveAll: Try[List[User]] = Try {
    userDAO.getAll.map(_.map(UserDomainService.toEntity)).get
  }

  def store(entity: User): Try[Unit] = Try {
    userDAO.insert(UserDomainService.toDataTransferObject(entity))
  }
}