package controllers.forms

import play.api.data._
import play.api.data.Forms._

case class AddUserForm(email: String, password: String)

object AddUserForm {

  val addUserForm = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(AddUserForm.apply)(AddUserForm.unapply)
  )
}