package dto

import scalikejdbc._

case class UserDTO(userId: String, email: String, password: String, entryCompanyDate: String,
                   userRole: String, department: String, annualLeave: Float, userStatus: String, lastModified: String)
object UserDTO extends SQLSyntaxSupport[UserDTO] {
  def apply(rs: WrappedResultSet) = new UserDTO(
    rs.string("userId"), rs.string("email"), rs.string("password"),
    rs.string("entryCompanyDate"), rs.string("userRole"), rs.string("department"),
    rs.float("annualLeave"), rs.string("userStatus"), rs.string("lastModified")
  )

}