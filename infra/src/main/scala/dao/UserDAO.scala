package dao

import dto.UserDTO
import exceptions.{ EmailNotFoundException, IdNotFoundException }
import org.mindrot.jbcrypt.BCrypt
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
    val password = BCrypt.hashpw(userDTO.password, BCrypt.gensalt())
    sql"INSERT INTO users (userId, email, password, entryCompanyDate, userRole, department, annualLeave, userStatus) VALUES (${userDTO.userId}, ${userDTO.email}, ${password}, ${userDTO.entryCompanyDate}, ${userDTO.userRole}, ${userDTO.department}, ${userDTO.annualLeave}, ${userDTO.userStatus})"
      .update().apply()
  }

  def isIdNotExist(id: String)(implicit dbsession: DBSession = AutoSession): Boolean = {
    sql"select * from users where userId = ${id} ".map(rs => UserDTO(rs)).list().apply().isEmpty
  }

  def isEmailNExist(email: String)(implicit dbsession: DBSession = AutoSession): Boolean = {
    sql"select * from users where email = ${email} ".map(rs => UserDTO(rs)).list().apply().isEmpty
  }
}
