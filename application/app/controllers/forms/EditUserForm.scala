package controllers.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.data.validation.Constraints._
case class EditUserForm(userIdEdit: String, emailEdit: String, userRoleEdit: String, statusEdit: String)

object EditUserForm {

  val editUserForm = Form(
    mapping(
      "userIdEdit" -> nonEmptyText,
      "emailEdit" -> email,
      "userRoleEdit" -> nonEmptyText,
      "statusEdit" -> nonEmptyText
    )(EditUserForm.apply)(EditUserForm.unapply)
  )
}