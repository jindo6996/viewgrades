package controllers.user

import controllers.auth.Secured
import javax.inject.{ Inject, Singleton }
import model.UserRepository
import play.api.mvc.{ AbstractController, ControllerComponents }
@Singleton
class UserController @Inject() (userRepository: UserRepository, cc: ControllerComponents) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured {
  def listUser = withAuth { email => implicit request =>

    Ok(views.html.users.userlist(userRepository.resolveAll.get))

  }
  def addUser = withAuth { email => implicit request =>
    Ok(views.html.users.createUser("add user"))
  }

  def editUser = withAuth { email => implicit request =>
    Ok(views.html.users.editUser("add user"))
  }
}
