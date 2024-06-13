package be.vinci.pae.utils.exception;

/**
 * Class RoleInvalidException.
 */
public class RoleInvalidException extends RuntimeException {

  /**
      * Method exception when the role is invalid because the mail doesn't match .
      *
      * @param message the error message
   */
  public RoleInvalidException(String message) {
    super(message);
  }

}
