package controllers.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
case class EditUserForm(userId: String, email: String, entryCompanyDate: String, userRole: String, department: String, annualLeave: Float, status: String)

object EditUserForm {

  val editUserForm = Form(
    mapping(
      "userId" -> nonEmptyText,
      "email" -> email,
      "entryCompanyDate" -> nonEmptyText,
      "userRole" -> nonEmptyText,
      "department" -> nonEmptyText,
      "annualLeave" -> of[Float],
      "status" -> nonEmptyText
    )(EditUserForm.apply)(EditUserForm.unapply)
  )
}