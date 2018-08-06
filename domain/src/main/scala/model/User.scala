package model

case class UserId(value: String)

case class User(userId: UserId, email: String, password: String, userRole: UserRole, userStatus: UserStatus, lastModified: String)