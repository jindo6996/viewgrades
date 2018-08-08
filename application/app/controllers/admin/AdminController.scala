package controllers.admin

import controllers.auth.{ Authentication, Secured }
import controllers.exceptions.FormErrorException
import exceptions.EntityDuplicateException
import javax.inject.Inject
import model.grades.GradeRepository
import play.api.mvc.{ AbstractController, ControllerComponents }
import controllers.forms.YearForm._
import controllers.forms.YearForm

class AdminController @Inject() (gradeRepository: GradeRepository, cc: ControllerComponents) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured with Authentication {
  def processAddYear = withAuth { email => implicit request =>
    require(request.session.get("role").get == "Admin")
    (for {
      yearInfo <- validateForm(yearForm)
      addToDB <- gradeRepository.storeYear(yearInfo.year)
    } yield {
      Redirect("/admin")
    }).recover {
      case formErr: FormErrorException[YearForm] => BadRequest(views.html.admins.listGrade(gradeRepository.resolveAll().get, formErr.formError))
      //      case entityDuplicate: EntityDuplicateException[User] => BadRequest(views.html.users.userlist(userRepository.resolveAll.get, addUserForm.bindFromRequest().withGlobalError(entityDuplicate.message), editUserForm))
    }.get
  }

}
