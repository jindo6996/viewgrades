package model

case class UserId(value: String)
case class UserRole(value: String)
case class UserStatus(value: String)
case class Department(name: String)

case class User(userId: UserId, email: String, password: String, entryCompanyDate: String,
                userRole: UserRole, department: Department, annualLeave: Long, userStatus: UserStatus)