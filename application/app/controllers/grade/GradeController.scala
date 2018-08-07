package controllers.grade

import controllers.auth.Secured
import javax.inject.Inject
import model.UserRepository
import play.api.mvc.{ AbstractController, ControllerComponents }

class GradeController @Inject() (userRepository: UserRepository, cc: ControllerComponents) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured {
  def yourGrade = Action { implicit request =>
    Ok(views.html.grades.yourgrade())
  }

}
