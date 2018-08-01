package controllers.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
case class AddUserForm(userId: String, email: String, entryCompanyDate: String, userRole: String, department: String, annualLeave: Float)

object AddUserForm {

  val addUserForm = Form(
    mapping(
      "userId" -> nonEmptyText,
      "email" -> email,
      "entryCompanyDate" -> nonEmptyText,
      "userRole" -> nonEmptyText,
      "department" -> nonEmptyText,
      "annualLeave" -> of[Float]
    )(AddUserForm.apply)(AddUserForm.unapply)
  )
}