package model

case class UserId(value: String)
case class Department(name: String)

case class User(userId: UserId, email: String, password: String, entryCompanyDate: String,
                userRole: UserRole, department: Department, annualLeave: Float, userStatus: UserStatus, lastModified: String)