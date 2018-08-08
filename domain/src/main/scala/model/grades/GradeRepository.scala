package model.grades

import dao.{ GradeDAO, UserDAO }
import javax.inject.Inject
import service.GradeDomainService

import scala.util.Try

class GradeRepository @Inject() (gradeDao: GradeDAO, userDAO: UserDAO) {
  def resolveAllByEmail(email: String): Try[List[Grade]] = Try {
    val id = userDAO.getByEmail(email).get.userId
    gradeDao.getAllByID(id).map(_.map(GradeDomainService.toEntity)).get
  }

}
