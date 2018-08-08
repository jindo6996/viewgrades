package controllers.grade

import controllers.auth.{ Authentication, Secured }
import javax.inject.Inject
import model.grades.GradeRepository
import play.api.mvc.{ AbstractController, ControllerComponents }
import controllers.forms.YearForm._

class GradeController @Inject() (gradeRepository: GradeRepository, cc: ControllerComponents) extends AbstractController(cc)
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

}
