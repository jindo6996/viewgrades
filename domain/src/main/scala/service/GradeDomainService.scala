package service

import dto.GradeDTO
import model.grades._

object GradeDomainService {
  def toEntity(gradeDTO: GradeDTO): Grade =
    Grade(gradeDTO.id, gradeDTO.nameSubject, gradeDTO.file, gradeDTO.year, gradeDTO.semester, gradeDTO.upload_at)

  //  def toDataTransferObject(user: User): UserDTO =
  //    UserDTO(user.userId.value, user.email, user.password, user.userRole.value, user.userStatus.value, user.lastModified)
}
