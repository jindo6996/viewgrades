package model.grades

import dao.GradeDAO
import javax.inject.Inject
import service.GradeDomainService

import scala.util.Try

class GradeRepository @Inject() (gradeDao: GradeDAO) {
  def resolveAllByEmail(email: String): Try[List[Grade]] = Try {
    gradeDao.getAllByEmail(email).map(_.map(GradeDomainService.toEntity)).get
  }

}
