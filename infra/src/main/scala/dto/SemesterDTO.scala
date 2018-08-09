package dto

import scalikejdbc._

case class SemesterDTO(id: Long, semester: String)
object SemesterDTO extends SQLSyntaxSupport[SemesterDTO] {
  def apply(rs: WrappedResultSet) = new SemesterDTO(
    rs.long("id"), rs.string("semester")
  )
}