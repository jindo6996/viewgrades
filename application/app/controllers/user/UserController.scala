package controllers.user

import controllers.auth.Secured
import controllers.exceptions.FormErrorException
import controllers.forms.{ AddUserForm, EditUserForm }
import javax.inject._
import model._
import play.api.mvc.{ AbstractController, ControllerComponents }
import controllers.forms.AddUserForm._
import controllers.forms.EditUserForm._
import exceptions.EntityDuplicateException
import play.api.libs.mailer.{ Email, MailerClient }
import services.AccountService._

@Singleton
class UserController @Inject() (userRepository: UserRepository, cc: ControllerComponents) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured {
  @Inject var mailerClient: MailerClient = null
  def listUser = withAuth { email => implicit request =>
    require(request.session.get("role").get == "Admin")
    Ok(views.html.users.userlist(userRepository.resolveAll.get, addUserForm, editUserForm))
  }

  def processAddUser = withAuth { email => implicit request =>
    require(request.session.get("role").get == "Admin")
    val password = randomString
    (for {
      userInfo <- validateForm(addUserForm)
      addToDB <- userRepository.store(User(UserId(userInfo.userId), userInfo.email, password, userInfo.entryCompanyDate, UserRole.fromString(userInfo.userRole).get, Department(userInfo.department), userInfo.annualLeave, UserStatus.Active, ""))
    } yield {
      sendEmail(userInfo.email, password)
      Redirect("/users")
    }).recover {
      case formErr: FormErrorException[AddUserForm]        => BadRequest(views.html.users.userlist(userRepository.resolveAll.get, formErr.formError.withGlobalError("Form Error"), editUserForm))
      case entityDuplicate: EntityDuplicateException[User] => BadRequest(views.html.users.userlist(userRepository.resolveAll.get, addUserForm.bindFromRequest().withGlobalError(entityDuplicate.message), editUserForm))
    }.get
  }

  def processEditUser() = withAuth { email => implicit request =>
    require(request.session.get("role").get == "Admin")
    (for {
      userInfo <- validateForm(editUserForm)
      editedUser <- userRepository.edit(User(UserId(userInfo.userIdEdit), userInfo.emailEdit, "123456", userInfo.entryCompanyDateEdit, UserRole.fromString(userInfo.userRoleEdit).get, Department(userInfo.departmentEdit), userInfo.annualLeaveEdit, UserStatus.fromString(userInfo.statusEdit).get, ""))
    } yield {
      Redirect("/users")
    }).recover {
      case formErr: FormErrorException[EditUserForm]       => BadRequest(views.html.users.userlist(userRepository.resolveAll.get, addUserForm, formErr.formError.withGlobalError("error")))
      case entityDuplicate: EntityDuplicateException[User] => BadRequest(views.html.users.userlist(userRepository.resolveAll.get, addUserForm, editUserForm.bindFromRequest().withGlobalError(entityDuplicate.message)))
    }.get
  }
  private def sendEmail(toMail: String, pass: String) = {
    val mailSend = toMail.split("@")(0)
    val email = Email(
      "Password of Timesheet",
      "<septenitimesheetmanager@gmail.com>",
      Seq(s"<$toMail>"),
      bodyText = Some(s"Dear $mailSend \nThis your passwork for website timesheet manager: $pass\nThank you and best regards,\nManager".stripMargin)
    )
    mailerClient.send(email)
  }
}
