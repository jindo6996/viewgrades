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
    //Ok(views.html.users.userlist(""))
    Ok(views.html.login.login(loginForm))
  }

  def processLogin = Action { implicit request =>
    (for {
      loginInfo <- validateForm(loginForm)
      userInfo <- userDAO.getByEmail(loginInfo.email)
      if (userDAO.checkPassword(loginInfo.password, userInfo.password))
    } yield {
      Redirect("/users").withSession("email" -> userInfo.email)
    }).recover {
      case formErr: FormErrorException[LoginInfo] => BadRequest(views.html.login.login(formErr.formError))
      case userErr: EmailNotFoundException        => BadRequest(views.html.login.login(loginForm.bindFromRequest().withGlobalError("User not found")))
      case e: NoSuchElementException              => BadRequest(views.html.login.login(loginForm.bindFromRequest().withGlobalError("User not found")))
    }.get
  }

  def logout = withAuth { email => implicit request =>
    Redirect(routes.AuthController.loginView).withNewSession
  }
}
