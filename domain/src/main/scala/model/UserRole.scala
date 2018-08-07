package model

import exceptions.ValueNotFoundException

import scala.util.Try

sealed abstract class UserRole(val value: String)

object UserRole {

  case object Admin extends UserRole("ADMIN")

  case object Student extends UserRole("STUDENT")

  def fromString(value: String): Try[UserRole] = Try {
    value match {
      case "ADMIN"   => UserRole.Admin
      case "STUDENT" => UserRole.Student
      case _         => throw new ValueNotFoundException("warn.userRoleWrongFormat")
    }
  }
}
