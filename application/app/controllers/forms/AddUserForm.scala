package controllers.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
case class AddUserForm(userId: String, email: String,  userRole: String)

object AddUserForm {

  val addUserForm = Form(
    mapping(
      "userId" -> nonEmptyText,
      "email" -> email,
      "userRole" -> nonEmptyText,
    )(AddUserForm.apply)(AddUserForm.unapply)
  )
}