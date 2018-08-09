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
  def resolveAll(): Try[List[Grade]] = Try {
    gradeDao.getAll().map(_.map(GradeDomainService.toEntity)).get
  }

  def storeYear(entity: String): Try[Long] = Try {
    gradeDao.insertYear(entity).get
  }

  def getSubject: Try[List[Subject]] = Try {
    gradeDao.getSubject().map(_.map(GradeDomainService.toSubject)).get
  }

  def getYear: Try[List[Year]] = Try {
    gradeDao.getYear().map(_.map(GradeDomainService.toYear)).get
  }

  def getSemester: Try[List[Semester]] = Try {
    gradeDao.getSemester().map(_.map(GradeDomainService.toSemester)).get
  }
  def storeGrade(file: String, sub: String, year: Long, semester: Long): Try[Long] = Try {
    gradeDao.insertGrade(file, sub, year, semester).get
  }
}
