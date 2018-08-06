package controllers.exceptions

import play.api.data.Form

case class FormErrorException[T](message: String, formError: Form[T]) extends Exception(message)