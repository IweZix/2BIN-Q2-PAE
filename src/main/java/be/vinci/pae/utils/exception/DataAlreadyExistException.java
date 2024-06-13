package be.vinci.pae.utils.exception;

/**
 * Class DataAlreadyExistException.
 */
public class DataAlreadyExistException extends RuntimeException {

  /**
   * Method exception when a data already exist.
   *
   * @param message the error message
   */
  public DataAlreadyExistException(String message) {
    super(message);
  }

}
