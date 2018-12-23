package sms.utils

import org.mindrot.jbcrypt.BCrypt

object StringUtils {
  def hash(value: String): String = {
    BCrypt.hashpw(value, BCrypt.gensalt())
  }

  def checkHash(value: String, hash: String): Boolean = {
    BCrypt.checkpw(value, hash)
  }
}
