package model

import exceptions.ValueNotFoundException

import scala.util.Try

sealed abstract class UserStatus(val value: String)

object UserStatus {

  case object Active extends UserStatus("ACTIVE")

  case object Inactive extends UserStatus("INACTIVE")

  def fromString(value: String): Try[UserStatus] = Try {
    value match {
      case "ACTIVE"   => UserStatus.Active
      case "INACTIVE" => UserStatus.Inactive
      case _          => throw new ValueNotFoundException("warn.userStatusWrongFormat")
    }
  }
}
