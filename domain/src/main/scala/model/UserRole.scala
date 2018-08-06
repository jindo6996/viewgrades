package model

import exceptions.ValueNotFoundException

import scala.util.Try

sealed abstract class UserRole(val value: String)

object UserRole {

  case object Admin extends UserRole("ADMIN")

  case object Staff extends UserRole("STAFF")

  def fromString(value: String): Try[UserRole] = Try {
    value match {
      case "ADMIN" => UserRole.Admin
      case "STAFF" => UserRole.Staff
      case _       => throw new ValueNotFoundException("warn.userRoleWrongFormat")
    }
  }
}
