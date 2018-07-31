package service

import dto.UserDTO
import model._

object UserDomainService {
  def toEntity(userDTO: UserDTO): User =
    User(UserId(userDTO.userId), userDTO.email, userDTO.password, userDTO.entryCompanyDate, UserRole.fromString(userDTO.userRole).get,
      Department(userDTO.department), userDTO.annualLeave, UserStatus.fromString(userDTO.userStatus).get, userDTO.lastModified)

  def toDataTransferObject(user: User): UserDTO =
    UserDTO(user.userId.value, user.email, user.password, user.entryCompanyDate, user.userRole.value, user.department.name,
      user.annualLeave, user.userStatus.value, user.lastModified)
}
