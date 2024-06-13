package be.vinci.pae.utils.exception;

/**
 * Class ConnectionNullExeption.
 */
public class ConnectionNullExeption extends RuntimeException {

  /**
   * Method exception when a connection is null.
   *
   * @param message the error message
   */
  public ConnectionNullExeption(String message) {
    super(message);
  }
}
