package dao

import dto.GradeDTO
import scalikejdbc._

import scala.util.Try

class GradeDAO {
  def getAllByEmail(email: String)(implicit dbsession: DBSession = AutoSession): Try[List[GradeDTO]] = Try {
    sql"SELECT * FROM users U  INNER JOIN classes C ON C.id_user=U.userId WHERE U.email=${email}".map(rs => GradeDTO(rs)).list().apply()
  }
}
