package be.vinci.pae.utils.exception;

/**
 * Class DataIsNotRightStatus.
 */
public class DataIsNotRightStatus extends RuntimeException {

  /**
   * Method exception when if the data is not in the right status  .
   *
   * @param message the error message
   */
  public DataIsNotRightStatus(String message) {
    super(message);
  }

}
