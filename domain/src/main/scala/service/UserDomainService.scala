package service

import dto.UserDTO
import model._

object UserDomainService {
  def toEntity(userDTO: UserDTO): User =
    User(UserId(userDTO.userId), userDTO.email, userDTO.password, UserRole.fromString(userDTO.userRole).get, UserStatus.fromString(userDTO.userStatus).get, userDTO.lastModified)

  def toDataTransferObject(user: User): UserDTO =
    UserDTO(user.userId.value, user.email, user.password, user.userRole.value, user.userStatus.value, user.lastModified)
}
