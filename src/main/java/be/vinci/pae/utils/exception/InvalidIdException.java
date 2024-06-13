package be.vinci.pae.utils.exception;

/**
 * InvalidIdException class.
 */
public class InvalidIdException extends RuntimeException {

  /**
   * Method exception when invalid id.
   *
   * @param message the error message
   */
  public InvalidIdException(String message) {
    super(message);
  }
}
