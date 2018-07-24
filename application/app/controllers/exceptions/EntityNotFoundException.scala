package controllers.exceptions

case class EntityNotFoundException[T](message: String, entity: T) extends Exception(message)
