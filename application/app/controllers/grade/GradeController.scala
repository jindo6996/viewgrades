package controllers.grade

import controllers.auth.{ Authentication, Secured }
import javax.inject.Inject
import model.grades.GradeRepository
import play.api.mvc.{ AbstractController, ControllerComponents }

class GradeController @Inject() (gradeRepository: GradeRepository, cc: ControllerComponents) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured with Authentication {
  def yourGrade = withAuth { email => implicit request =>
    Ok(views.html.grades.yourgrade(gradeRepository.resolveAllByEmail(getMailInSession.get).get))
  }

}
