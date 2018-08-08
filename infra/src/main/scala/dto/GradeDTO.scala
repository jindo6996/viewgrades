package dto

import scalikejdbc._

case class GradeDTO(id: Long, codeSubject: String, nameSubject: String, file: String, year: String, semester: String, upload_at: String)
object GradeDTO extends SQLSyntaxSupport[UserDTO] {
  def apply(rs: WrappedResultSet) = new GradeDTO(
    rs.long("id"), rs.string("code_sub"), rs.string("name"), rs.string("file"),
    rs.string("year"), rs.string("semester"), rs.string("upload_at")
  )

}
