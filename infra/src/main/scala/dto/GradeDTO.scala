package dto

import scalikejdbc._

case class GradeDTO(id: Long, nameSubject: String, file: String, year: Long, semester: Long, upload_at: String)
object GradeDTO extends SQLSyntaxSupport[UserDTO] {
  def apply(rs: WrappedResultSet) = new GradeDTO(
    rs.long("id"), rs.string("name"), rs.string("file"),
    rs.long("id_year"), rs.long("id_semester"), rs.string("upload_at")
  )

}
