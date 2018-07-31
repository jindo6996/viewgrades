package controllers.user

import controllers.auth.Secured
import controllers.exceptions.FormErrorException
import controllers.forms.LoginForm.loginForm
import controllers.forms.LoginInfo
import exceptions.{ EmailNotFoundException, IdNotFoundException }
import javax.inject.{ Inject, Singleton }
import model.UserRepository
import play.api.mvc.{ AbstractController, ControllerComponents, Flash }
import services.AccountService
@Singleton
class UserController @Inject() (userRepository: UserRepository, cc: ControllerComponents) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured {
  def listUser = withAuth { email => implicit request =>

    Ok(views.html.users.userlist(userRepository.resolveAll.get))

  }
  def addUser = withAuth { email => implicit request =>
    import org.apache.commons.lang3.RandomStringUtils
    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~"

    val pwd = RandomStringUtils.random(8, characters)
    Ok(views.html.users.createUser(pwd))
  }

  //  def processAddUser = withAuth { email => implicit request =>
  //    (for {
  //      loginInfo <- validateForm(loginForm)
  //      userInfo <- userRepository.resolveByEmail(loginInfo.email)
  //      if (AccountService.checkPassword(loginInfo.password, userInfo.password))
  //    } yield {
  //      Redirect("/users").withSession("email" -> userInfo.email)
  //    }).recover {
  //      case formErr: FormErrorException[LoginInfo] => Redirect("/").flashing(Flash(formErr.formError.data))
  //      case userErr: EmailNotFoundException        => Redirect("/").flashing(Flash(loginForm.bindFromRequest().data) + ("error" -> "User not found"))
  //      case userErr: IdNotFoundException           => Redirect("/").flashing(Flash(loginForm.bindFromRequest().data) + ("error" -> "User not found"))
  //      case e: NoSuchElementException              => Redirect("/").flashing(Flash(loginForm.bindFromRequest().data) + ("error" -> "User not found"))
  //    }.get
  //  }

  def editUser = withAuth { email => implicit request =>
    Ok(views.html.users.editUser("add user"))
  }
}
