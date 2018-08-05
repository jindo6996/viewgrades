package services

import org.apache.commons.lang3.RandomStringUtils
import org.mindrot.jbcrypt.BCrypt

object AccountService {
  def checkPassword(password: String, passwordHash: String): Boolean =
    BCrypt.checkpw(password, passwordHash)
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
}
