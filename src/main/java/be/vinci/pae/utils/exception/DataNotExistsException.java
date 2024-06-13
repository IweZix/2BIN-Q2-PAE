package be.vinci.pae.utils.exception;

/**
 * CompanyNotExistsException class.
 */
public class DataNotExistsException extends RuntimeException {

  /**
   * Method exception when the company is not exists.
   *
   * @param message the error message
   */
  public DataNotExistsException(String message) {
    super(message);
  }
}
