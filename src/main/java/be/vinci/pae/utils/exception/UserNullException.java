package be.vinci.pae.utils.exception;

/**
 * Class UserNullException.
 */
public class UserNullException extends RuntimeException {

  /**
   * Method exception when a user is null.
   *
   * @param message the error message
   */
  public UserNullException(String message) {
    super(message);
  }
}
