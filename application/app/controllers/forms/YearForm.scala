package controllers.forms

import play.api.data._
import play.api.data.Forms._

case class YearForm(year: String)

object YearForm {

  val yearForm = Form(
    mapping(
      "year" -> nonEmptyText(maxLength = 9, minLength = 9) //^[0-9]{4}+[-]{1}+[0-9]{4}$
    )(YearForm.apply)(YearForm.unapply)
  )
}

