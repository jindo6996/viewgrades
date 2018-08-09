package service

import dto.{ GradeDTO, SemesterDTO, SubjectDTO, YearDTO }
import model.grades._

object GradeDomainService {
  def toEntity(gradeDTO: GradeDTO): Grade =
    Grade(gradeDTO.id, gradeDTO.codeSubject, gradeDTO.nameSubject, gradeDTO.file, gradeDTO.year, gradeDTO.semester, gradeDTO.upload_at)

  def toSubject(subjectDTO: SubjectDTO): Subject =
    Subject(subjectDTO.code, subjectDTO.name)

  def toYear(yearDTO: YearDTO): Year =
    Year(yearDTO.id, yearDTO.year)

  def toSemester(semesterDTO: SemesterDTO): Semester =
    Semester(semesterDTO.id, semesterDTO.semester)

  //  def toDataTransferObject(user: User): UserDTO =
  //    UserDTO(user.userId.value, user.email, user.password, user.userRole.value, user.userStatus.value, user.lastModified)
}
