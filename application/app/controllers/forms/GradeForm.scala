package controllers.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
case class GradeForm(code_sub: String, year: String,  semester: String)

object GradeForm {

  val gradeForm = Form(
    mapping(
      "code_sub" -> nonEmptyText,
      "year" -> nonEmptyText,
      "semester" -> nonEmptyText,
    )(GradeForm.apply)(GradeForm.unapply)
  )
}