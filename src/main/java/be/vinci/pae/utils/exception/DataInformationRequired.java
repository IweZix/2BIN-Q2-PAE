package be.vinci.pae.utils.exception;

/**
 * Class DataInformatioRequired.
 */
public class DataInformationRequired extends RuntimeException {

  /**
   * Method exception when a data information required.
   *
   * @param message the error message
   */
  public DataInformationRequired(String message) {
    super(message);
  }
}
