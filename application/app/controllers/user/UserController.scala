package controllers.user

import controllers.auth.Secured
import javax.inject._
import model._
import play.api.mvc.{ AbstractController, ControllerComponents }
import controllers.forms.AddUserForm._
import controllers.forms.EditUserForm._

@Singleton
class UserController @Inject() (userRepository: UserRepository, cc: ControllerComponents) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured {
  def listUser = withAuth { email => implicit request =>

    Ok(views.html.users.userlist(userRepository.resolveAll.get, addUserForm, editUserForm))

  }

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

  def processEditUser = withAuth { email => implicit request =>
    (for {
      userInfo <- validateForm(editUserForm)

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
    Ok("sad")
  }
}
