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
import org.apache.commons.lang3.RandomStringUtils

@Singleton
class UserController @Inject() (userRepository: UserRepository, cc: ControllerComponents) extends AbstractController(cc)
  with play.api.i18n.I18nSupport with controllers.BaseController with Secured {

  def randomString: String = {
    val upperChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val lowerChar = "abcdefghijklmnopqrstuvwxyz"
    val number = "0123456789"
    val specialChar = "!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~"

    val start = 8
    val end = 20
    val rnd = new scala.util.Random
    val randomLenght = start + rnd.nextInt((end - start) + 1)

    val pwd = RandomStringUtils.random(randomLenght, upperChar + lowerChar + number + specialChar)
    if (pwd.exists(upperChar.contains(_)) && pwd.exists(lowerChar.contains(_)) && pwd.exists(number.contains(_)) && pwd.exists(specialChar.contains(_)))
      pwd
    else
      randomString
  }

  def listUser = withAuth { email => implicit request =>
    Ok(views.html.users.userlist(userRepository.resolveAll.get, addUserForm, editUserForm))
  }

  def processAddUser = withAuth { email => implicit request =>
    (for {
      userInfo <- validateForm(addUserForm)
      addToDB <- userRepository.store(User(UserId(userInfo.userId), userInfo.email, "123456", userInfo.entryCompanyDate, UserRole.fromString(userInfo.userRole).get, Department(userInfo.department), userInfo.annualLeave, UserStatus.Active, ""))
    } yield {
      Redirect("/users")
    }).recover {
      case formErr: FormErrorException[AddUserForm] => BadRequest(views.html.users.userlist(userRepository.resolveAll.get, formErr.formError.withGlobalError("error"), editUserForm))
      //      case entityDuplicate: EntityDuplicateException[User] => BadRequest(views.html.users.userlist(userRepository.resolveAll.get, addUserForm.fill(AddUserForm(
      //        entityDuplicate.entity.userId.value,
      //        entityDuplicate.entity.email, entityDuplicate.entity.entryCompanyDate, entityDuplicate.entity.userRole.value, entityDuplicate.entity.department.name, entityDuplicate.entity.annualLeave
      //      )).withGlobalError(entityDuplicate.message), editUserForm))

    }.get
  }

  def processEditUser() = withAuth { email => implicit request =>
    (for {
      userInfo <- validateForm(editUserForm)

    } yield {
      Redirect("/users")
    }).recover {
      case formErr: FormErrorException[EditUserForm] => BadRequest(views.html.users.userlist(userRepository.resolveAll.get, addUserForm, formErr.formError.withGlobalError("error")))
      case e: Exception                              => BadRequest(e.toString)
    }.get
  }

  def editUser = withAuth { email => implicit request =>
    Ok("sad")
  }
}
