package dao

import dto.UserDTO
import exceptions.EmailNotFoundException
import scalikejdbc._

import scala.util.Try

class UserDAO {
  def getByEmail(email: String)(implicit dbsession: DBSession = AutoSession): Try[UserDTO] = Try {
    sql"select * from users where email = ${email} ".map(rs => UserDTO(rs)).single().apply().getOrElse(throw new EmailNotFoundException("User not found"))
  }

}
