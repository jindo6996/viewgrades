package controllers.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
case class EditUserForm(userIdEdit: String, emailEdit: String, entryCompanyDateEdit: String, userRoleEdit: String, departmentEdit: String, annualLeaveEdit: Float, statusEdit: String)

object EditUserForm {

  val editUserForm = Form(
    mapping(
      "userIdEdit" -> nonEmptyText,
      "emailEdit" -> email,
      "entryCompanyDateEdit" -> nonEmptyText,
      "userRoleEdit" -> nonEmptyText,
      "departmentEdit" -> nonEmptyText,
      "annualLeaveEdit" -> of[Float],
      "statusEdit" -> nonEmptyText
    )(EditUserForm.apply)(EditUserForm.unapply)
  )
}