package controllers.auth

import play.api.mvc.Request

trait Authentication {
  def getMailInSession(implicit request: Request[Any]) = request.session.get("email")
}
