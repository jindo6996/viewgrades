package exceptions

case class IdNotFoundException(msg: String) extends Exception(msg)
