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

  def isEmailNotExist(email: String)(implicit dbsession: DBSession = AutoSession): Boolean = {
    sql"select * from users where email = ${email} ".map(rs => UserDTO(rs)).list().apply().isEmpty
  }

  def isEmailNotExistWithoutID(email: String, code: String)(implicit dbsession: DBSession = AutoSession): Boolean = {
    sql"select * from users where email = ${email} and userId !=${code}".map(rs => UserDTO(rs)).list().apply().isEmpty
  }

  def getById(id: String)(implicit dbsession: DBSession = AutoSession): Try[UserDTO] = Try {
    sql"select * from users where UserId = ${id} ".map(rs => UserDTO(rs)).single().apply()
      .getOrElse(throw new IdNotFoundException("User not found"))
  }

  def editUser(userDTO: UserDTO)(implicit session: DBSession = AutoSession): Try[Int] = Try {
    sql"UPDATE users SET userId=${userDTO.userId},email=${userDTO.email},entryCompanyDate=${userDTO.entryCompanyDate},userRole=${userDTO.userRole},department=${userDTO.department},annualLeave=${userDTO.annualLeave},userStatus= ${userDTO.userStatus} WHERE userId=${userDTO.userId}".update().apply()
  }

}
