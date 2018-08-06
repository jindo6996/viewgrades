package controllers.auth

import play.api.mvc.Results.Forbidden
import play.api.mvc.{ Request, Result }
import play.api.mvc._

import scala.concurrent.Future

trait Secured {
  private def sessionWithEmail(header: RequestHeader): Option[String] = header.session.get("email")

  private def sessionWithRole(header: RequestHeader): Option[String] = header.session.get("role")

  private def withUnAuthorized(request: RequestHeader) = Results.Redirect(controllers.auth.routes.AuthController.loginView())

  def withAuth(f: => String => Request[AnyContent] => Result) =
    Security.Authenticated(sessionWithRole, withUnAuthorized) { sid =>
      Action(request => f(sid)(request))
    }
}
