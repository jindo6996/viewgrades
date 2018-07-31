package controllers.auth

import controllers.exceptions.FormErrorException
import controllers.forms.LoginForm.loginForm
import controllers.forms.LoginInfo
import exceptions.{ EmailNotFoundException, IdNotFoundException }
import javax.inject._
import model.UserRepository
import play.api.mvc._
import services.AccountService
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class AuthController @Inject() (userRepository: UserRepository, cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport with controllers.BaseController with Secured {

  /**
   * Create an Action to render an HTML page with a login message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def loginView = Action { implicit request =>
    if (request.session.get("email").isEmpty) {
      //      if (request.flash.get("error").isDefined) print(request.flash.get("error").get)
      Ok(views.html.login.login(loginForm))
    } else Redirect("/users")
  }

  def processLogin = Action { implicit request =>
    (for {
      loginInfo <- validateForm(loginForm)
      userInfo <- userRepository.resolveByEmail(loginInfo.email)
      if (AccountService.checkPassword(loginInfo.password, userInfo.password))
    } yield {
      Redirect("/users").withSession("email" -> userInfo.email)
    }).recover {
      case formErr: FormErrorException[LoginInfo] => Redirect("/").flashing(Flash(formErr.formError.data))
      case userErr: EmailNotFoundException        => Redirect("/").flashing(Flash(loginForm.bindFromRequest().data) + ("error" -> "User not found"))
      case userErr: IdNotFoundException           => Redirect("/").flashing(Flash(loginForm.bindFromRequest().data) + ("error" -> "User not found"))
      case e: NoSuchElementException              => Redirect("/").flashing(Flash(loginForm.bindFromRequest().data) + ("error" -> "User not found"))
    }.get
  }

  def logout = withAuth { email => implicit request =>
    Redirect(routes.AuthController.loginView).withNewSession
  }
}
