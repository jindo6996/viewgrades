package dto

import scalikejdbc._

case class YearDTO(id: Long, year: String)
object YearDTO extends SQLSyntaxSupport[YearDTO] {
  def apply(rs: WrappedResultSet) = new YearDTO(
    rs.long("id"), rs.string("year")
  )
}