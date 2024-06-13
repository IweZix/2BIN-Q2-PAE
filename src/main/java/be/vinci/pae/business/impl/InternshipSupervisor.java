package be.vinci.pae.business.impl;

import be.vinci.pae.business.dto.InternshipSupervisorDTO;

/**
 * InternshipSupervisor Interface.
 */
public interface InternshipSupervisor extends InternshipSupervisorDTO {

  /**
   * checks if the email is valid.
   *
   * @param email user email
   * @return true if the email is valid, else false
   */
  boolean isEmailValidInInternshipSupervisor(String email);

}
