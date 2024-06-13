package be.vinci.pae.utils.checker;

import be.vinci.pae.utils.exception.VersionIncorrectException;

/**
 * VersionChecker class.
 */
public class VersionChecker {

  /**
   * Compare two versions.
   *
   * @param version1 version1
   * @param version2 version2
   */
  public static void compareVersion(int version1, int version2) {
    if (version1 != version2) {
      throw new VersionIncorrectException("Version incorrect");
    }
  }

}
