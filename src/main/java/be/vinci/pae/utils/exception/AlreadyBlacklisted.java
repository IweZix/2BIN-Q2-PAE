package be.vinci.pae.utils.exception;

/**
 * Class AlreadyBlacklisted.
 */
public class AlreadyBlacklisted extends RuntimeException {

  /**
   * Method exception when a company already exist.
   *
   * @param message the error message
   */
  public AlreadyBlacklisted(String message) {
    super(message);
  }

}
