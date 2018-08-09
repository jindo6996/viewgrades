package dto

import scalikejdbc._

case class SubjectDTO(code: String, name: String)
object SubjectDTO extends SQLSyntaxSupport[SubjectDTO] {
  def apply(rs: WrappedResultSet) = new SubjectDTO(
    rs.string("code"), rs.string("name")
  )
}