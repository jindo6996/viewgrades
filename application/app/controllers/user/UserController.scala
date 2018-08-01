package controllers.user

import controllers.auth.Secured
import controllers.exceptions.FormErrorException
import controllers.forms.AddUserForm
import controllers.forms.AddUserForm.addUserForm
import exceptions.{ EmailNotFoundException, IdNotFoundException }
import javax.inject.{ Inject, Singleton }
import model._
import play.api.mvc.{ AbstractController, ControllerComponents, Flash }
import services.AccountService
@Singleton
class UserController @Inject() (userRepository: UserRepository, cc: ControllerComponents) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured {
  def listUser = withAuth { email => implicit request =>

    Ok(views.html.users.userlist(userRepository.resolveAll.get, addUserForm))

  }
  //  def addUser = withAuth { email => implicit request =>
  //    import org.apache.commons.lang3.RandomStringUtils
  //    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~"
  //
  //    val pwd = RandomStringUtils.random(8, characters)
  //    Ok(views.html.users.createUser(pwd))
  //  }

  def processAddUser = withAuth { email => implicit request =>
    (for {
      userInfo <- validateForm(addUserForm)
      userInfo <- userRepository.store(User(UserId(userInfo.userId), userInfo.email, "123456", userInfo.entryCompanyDate, UserRole.fromString(userInfo.userRole).get, Department(userInfo.department), userInfo.annualLeave, UserStatus.Active, ""))
    } yield {
      Redirect("/users")
    }).recover {
      //      case formErr: FormErrorException[AddUserForm] => Redirect("/").flashing(Flash(formErr.formError.data))
      //      case userErr: EmailNotFoundException          => Redirect("/").flashing(Flash(loginForm.bindFromRequest().data) + ("error" -> "User not found"))
      //      case userErr: IdNotFoundException             => Redirect("/").flashing(Flash(loginForm.bindFromRequest().data) + ("error" -> "User not found"))
      //      case e: NoSuchElementException                => Redirect("/").flashing(Flash(loginForm.bindFromRequest().data) + ("error" -> "User not found"))
      case e: Exception => BadRequest(e.toString)
    }.get
  }

  def editUser = withAuth { email => implicit request =>
    Ok(views.html.users.editUser("add user"))
  }
}
