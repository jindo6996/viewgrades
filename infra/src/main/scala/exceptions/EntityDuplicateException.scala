package exceptions

case class EntityDuplicateException[T](message: String, entity: T) extends Exception(message)