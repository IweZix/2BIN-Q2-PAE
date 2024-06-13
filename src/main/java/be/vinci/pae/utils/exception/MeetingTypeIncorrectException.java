package be.vinci.pae.utils.exception;

/**
 * Class MeetingTypeIncorrectException.
 */
public class MeetingTypeIncorrectException extends RuntimeException {

  /**
   * Method exception when the meeting type is incorrect .
   *
   * @param message the error message
   */
  public MeetingTypeIncorrectException(String message) {
    super(message);
  }

}
