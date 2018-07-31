package services

import org.mindrot.jbcrypt.BCrypt

object AccountService {
  def checkPassword(password: String, passwordHash: String): Boolean =
    BCrypt.checkpw(password, passwordHash)
}
