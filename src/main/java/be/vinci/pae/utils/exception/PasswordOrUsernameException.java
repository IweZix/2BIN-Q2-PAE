package be.vinci.pae.utils.exception;

/**
 * Class PasswordOrUsernameException.
 */
public class PasswordOrUsernameException extends RuntimeException {
  /**
   * Method exception when password incorrect or email incorrect.
   *
   * @param message the error message
   */
  public PasswordOrUsernameException(String message) {
    super(message);
  }
}
