package service

import dto.UserDTO
import model._

object UserDomainService {
  def toEntity(userDTO: UserDTO): User =
    User(UserId(userDTO.userId), userDTO.email, userDTO.password, userDTO.entryCompanyDate, UserRole(userDTO.userRole),
      Department(userDTO.department), userDTO.annualLeave, UserStatus(userDTO.userStatus))

  def toDataTransferObject(user: User): UserDTO =
    UserDTO(user.userId.value, user.email, user.password, user.entryCompanyDate, user.userRole.value, user.department.name,
      user.annualLeave, user.userStatus.value)
}
