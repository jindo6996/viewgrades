package dto

import scalikejdbc._

case class UserDTO(userId: String, email: String, password: String,
                   userRole: String, userStatus: String, lastModified: String)
object UserDTO extends SQLSyntaxSupport[UserDTO] {
  def apply(rs: WrappedResultSet) = new UserDTO(
    rs.string("userId"), rs.string("email"), rs.string("password"), rs.string("userRole"), rs.string("userStatus"),
    rs.string("lastModified")
  )

}