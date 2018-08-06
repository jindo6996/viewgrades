package exceptions

case class EmailNotFoundException(msg: String) extends Exception(msg)
