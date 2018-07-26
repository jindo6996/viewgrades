package exceptions

case class ValueNotFoundException(msg: String) extends Exception(msg)

