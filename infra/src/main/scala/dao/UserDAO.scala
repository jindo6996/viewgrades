package dao

import dto.UserDTO
import exceptions.{ EmailNotFoundException, IdNotFoundException }
import scalikejdbc._

import scala.util.Try

class UserDAO {
  def getByEmail(email: String)(implicit dbsession: DBSession = AutoSession): Try[UserDTO] = Try {
    sql"select * from users where email = ${email} ".map(rs => UserDTO(rs)).single().apply()
      .getOrElse(throw new EmailNotFoundException("User not found"))
  }

  def getAll(implicit dbsession: DBSession = AutoSession): Try[List[UserDTO]] = Try {
    sql"select * from users".map(rs => UserDTO(rs)).list().apply()
  }

  def insert(userDTO: UserDTO)(implicit session: DBSession = AutoSession): Try[Int] = Try {
    sql"INSERT INTO users (userId, email, password, entryCompanyDate, userRole, department, annualLeave, userStatus) VALUES (${userDTO.userId}, ${userDTO.email}, ${userDTO.password}, ${userDTO.entryCompanyDate}, ${userDTO.userRole}, ${userDTO.department}, ${userDTO.annualLeave}, ${userDTO.userStatus})"
      .updateAndReturnGeneratedKey().apply().toInt
  }

  def getByID(id: Int)(implicit dbsession: DBSession = AutoSession): Try[UserDTO] = Try {
    sql"select * from users where userId = ${id} ".map(rs => UserDTO(rs)).single().apply()
      .getOrElse(throw new IdNotFoundException("User not found"))
  }
}
