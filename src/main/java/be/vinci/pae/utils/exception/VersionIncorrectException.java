package be.vinci.pae.utils.exception;

/**
 * Class VersionIncorrectException.
 */
public class VersionIncorrectException extends RuntimeException {
  /**
   * Method exception when the version is incorrect .
   *
   * @param message the error message
   */
  public VersionIncorrectException(String message) {
    super(message);
  }

}
