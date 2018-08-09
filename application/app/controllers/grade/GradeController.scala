package controllers.grade

import java.nio.file.Paths

import controllers.auth.{ Authentication, Secured }
import controllers.exceptions.FormErrorException
import controllers.forms.GradeForm
import javax.inject.Inject
import model.grades.GradeRepository
import play.api.mvc.{ AbstractController, ControllerComponents }
import controllers.forms.YearForm._
import controllers.forms.GradeForm._
import dao.UserDAO
import exceptions.EntityDuplicateException

class GradeController @Inject() (gradeRepository: GradeRepository, cc: ControllerComponents, userDAO: UserDAO) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured with Authentication {
  def yourGrade = withAuth { email => implicit request =>
    Ok(views.html.grades.listGrade(gradeRepository.resolveAllByEmail(getMailInSession.get).get))
  }
  def allGrade = withAuth { email => implicit request =>
    Ok(views.html.grades.listGrade(gradeRepository.resolveAll().get))
  }
  def gradeAdmin = withAuth { email => implicit request =>
    require(request.session.get("role").get == "Admin")
    Ok(views.html.admins.listGrade(gradeRepository.resolveAll().get, yearForm))
  }
  def uploadView = withAuth { email => implicit request =>
    require(request.session.get("role").get == "Admin")
    Ok(views.html.admins.addFile(gradeRepository.getSubject.get, gradeRepository.getSemester.get, gradeRepository.getYear.get, gradeForm))
  }
  def upload = Action(parse.multipartFormData) { implicit request =>
    require(request.session.get("role").get == "Admin")
    (for {
      gradeInfo <- validateForm(gradeForm)
      //      editedUser <- userRepository.edit(User(UserId(userInfo.userIdEdit), userInfo.emailEdit, "123456", UserRole.fromString(userInfo.userRoleEdit).get, UserStatus.fromString(userInfo.statusEdit).get, ""))
    } yield {
      request.body.file("file").map { file =>
        val filename = Paths.get(file.filename).getFileName
        file.ref.moveTo(Paths.get(s"D:/scala/viewgrades/application/public/file/$filename"), replace = true)
        gradeRepository.storeGrade(filename.toString, gradeInfo.code_sub, gradeInfo.year.toLong, gradeInfo.semester.toLong)
        Redirect("/admin")
      }.getOrElse {
        BadRequest("ERROR")
      }
    }).recover {
      case formErr: FormErrorException[GradeForm] => BadRequest(views.html.admins.addFile(gradeRepository.getSubject.get, gradeRepository.getSemester.get, gradeRepository.getYear.get, formErr.formError.withGlobalError("error")))
      case e: Exception                           => BadRequest(e.getMessage)
    }.get
  }
  def delete(id: Int) = withAuth { email => implicit request =>
    require(request.session.get("role").get == "Admin")
    userDAO.delete(id)
    Redirect("/admin/grades")
  }

}
