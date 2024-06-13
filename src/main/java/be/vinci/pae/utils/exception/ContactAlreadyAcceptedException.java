package be.vinci.pae.utils.exception;

/**
 * Class ContactAlreadyAcceptedException.
 */
public class ContactAlreadyAcceptedException extends RuntimeException {

  /**
   * Method exception when a contact is already accepted.
   *
   * @param message the error message
   */
  public ContactAlreadyAcceptedException(String message) {
    super(message);
  }

}
