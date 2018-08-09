package dao

import dto.{ GradeDTO, SemesterDTO, SubjectDTO, YearDTO }
import scalikejdbc._

import scala.util.Try

class GradeDAO {
  def getAllByID(id: String)(implicit dbsession: DBSession = AutoSession): Try[List[GradeDTO]] = Try {
    sql"""SELECT * FROM students_grades SG
          INNER JOIN grades G ON SG.id_grade = G.id
          INNER JOIN subjects S ON S.code = G.code_sub
          INNER JOIN semesters ST ON ST.id = G.id_semester
          INNER JOIN years Y ON Y.id = G.id_year
          WHERE SG.id_user=${id}""".map(rs => GradeDTO(rs)).list().apply()
  }
  def getAll()(implicit dbsession: DBSession = AutoSession): Try[List[GradeDTO]] = Try {
    sql"""SELECT * FROM grades G
          INNER JOIN subjects S ON S.code = G.code_sub
          INNER JOIN semesters ST ON ST.id = G.id_semester
          INNER JOIN years Y ON Y.id = G.id_year""".map(rs => GradeDTO(rs)).list().apply()
  }
  def insertYear(year: String)(implicit session: DBSession = AutoSession): Try[Long] = Try {
    sql"INSERT INTO years(year) VALUES (${year})".updateAndReturnGeneratedKey().apply()
  }
  def getSubject()(implicit dbsession: DBSession = AutoSession): Try[List[SubjectDTO]] = Try {
    sql"""SELECT * FROM subjects """.map(rs => SubjectDTO(rs)).list().apply()
  }
  def getYear()(implicit dbsession: DBSession = AutoSession): Try[List[YearDTO]] = Try {
    sql"""SELECT * FROM years ORDER BY id desc """.map(rs => YearDTO(rs)).list().apply()
  }

  def getSemester()(implicit dbsession: DBSession = AutoSession): Try[List[SemesterDTO]] = Try {
    sql"""SELECT * FROM semesters """.map(rs => SemesterDTO(rs)).list().apply()
  }
  def insertGrade(file: String, code: String, year: Long, sems: Long)(implicit session: DBSession = AutoSession): Try[Long] = Try {
    sql"INSERT INTO grades (file, code_sub, id_year, id_semester) VALUES (${file}, ${code}, ${year}, ${sems});".updateAndReturnGeneratedKey().apply()
  }
}
