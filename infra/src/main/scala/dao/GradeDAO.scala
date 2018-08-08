package dao

import dto.GradeDTO
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
}
