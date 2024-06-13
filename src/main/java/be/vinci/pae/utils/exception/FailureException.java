package be.vinci.pae.utils.exception;

/**
 * FailureException class.
 */
public class FailureException extends RuntimeException {

  /**
   * Method exception when the object is not succeful created.
   *
   * @param message the error message
   */
  public FailureException(String message) {
    super(message);
  }

}
