package be.vinci.pae.utils.exception;

/**
 * Class InvalidUserOrPasswordException.
 */
public class InvalidUserOrPasswordException extends RuntimeException {

  /**
   * Method exception when invalid user or password.
   *
   * @param message the error message
   */
  public InvalidUserOrPasswordException(String message) {
    super(message);
  }
}
