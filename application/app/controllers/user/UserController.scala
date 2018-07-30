package controllers.user

import controllers.auth.Secured
import dao.UserDAO
import javax.inject.{ Inject, Singleton }
import play.api.mvc.{ AbstractController, ControllerComponents }
@Singleton
class UserController @Inject() (userDAO: UserDAO, cc: ControllerComponents) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured {
  def listUser = withAuth { email => implicit request =>
    Ok(views.html.users.userlist(""))
  }
  def addUser = withAuth { email => implicit request =>
    Ok(views.html.users.userlist("add user"))
  }
}
