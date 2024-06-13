package be.vinci.pae.utils.exception;

/**
 * FatalException class.
 */
public class FatalException extends RuntimeException {

  /**
   * Method exception when it's a db exception.
   *
   * @param message the error message
   */

  public FatalException(Exception message) {
    super(message.getMessage());
  }

}