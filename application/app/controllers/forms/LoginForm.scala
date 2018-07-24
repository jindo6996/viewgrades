package controllers.forms

import play.api.data._
import play.api.data.Forms._

case class LoginInfo(email: String, password: String)

object LoginForm {

  val loginForm = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(LoginInfo.apply)(LoginInfo.unapply)
  )
}

