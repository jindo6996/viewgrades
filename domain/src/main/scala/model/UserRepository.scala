package model

import dao.UserDAO
import exceptions.EntityDuplicateException
import javax.inject.Inject
import service.UserDomainService

import scala.util.Try

class UserRepository @Inject() (userDAO: UserDAO) {
  def resolveByEmail(email: String): Try[User] = Try {
    userDAO.getByEmail(email).map(UserDomainService.toEntity).get
  }

  def resolveAll: Try[List[User]] = Try {
    userDAO.getAll.map(_.map(UserDomainService.toEntity)).get
  }

  def store(entity: User): Try[User] = Try {

    if (userDAO.isEmailNExist(entity.email)) {
      if (userDAO.isIdNotExist(entity.userId.value)) {
        userDAO.insert(UserDomainService.toDataTransferObject(entity))
        userDAO.getByEmail(entity.email).map(UserDomainService.toEntity).get
      } else {
        throw EntityDuplicateException("ID Duplicate", entity)
      }
    } else {
      throw EntityDuplicateException("Email Duplicate", entity)
    }

  }
}