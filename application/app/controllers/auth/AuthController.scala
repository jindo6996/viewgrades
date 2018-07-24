package controllers.auth

import controllers.exceptions.FormErrorException
import controllers.forms.LoginForm.loginForm
import controllers.forms.LoginInfo
import dao.UserDAO
import exceptions.EmailNotFoundException
import javax.inject._
import play.api.mvc._
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class AuthController @Inject() (userDAO: UserDAO, cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport with controllers.BaseController with Secured {

  /**
   * Create an Action to render an HTML page with a login message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def loginView = Action { implicit request =>
    Ok(views.html.login.loginIndex(loginForm))
  }

  def processLogin = Action { implicit request =>
    (for {
      loginInfo <- validateForm(loginForm)
      userInfo <- userDAO.getByEmail(loginInfo.email)
    } yield {
      Ok("login success").withSession("email" -> userInfo.email)
    }).recover {
      case formErr: FormErrorException[LoginInfo] => BadRequest(views.html.login.loginIndex(formErr.formError))
      case userErr: EmailNotFoundException        => BadRequest(views.html.login.loginIndex(loginForm.bindFromRequest().withGlobalError("User not found")))
    }.get
  }
}
