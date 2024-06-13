package be.vinci.pae.business.impl;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.dto.ContactDTO;
import be.vinci.pae.business.dto.InternshipSupervisorDTO;
import be.vinci.pae.business.dto.StageDTO;
import be.vinci.pae.business.dto.UserDTO;

/**
 * This is the interface of FactoryImpl.
 */
public interface Factory {

  /**
   * Creates a new UserImpl.
   *
   * @return a new UserImpl
   */
  UserDTO getUser();

  /**
   * Create a new ContactImpl.
   *
   * @return a new contact
   */
  ContactDTO getContact();

  /**
   * Create a new CompanyImpl.
   *
   * @return a new company
   */
  CompanyDTO getCompany();

  /**
   * Create a new StageImpl.
   *
   * @return a new stageDTO
   */
  StageDTO getStage();

  /**
   * Create a new InternshipSupervisorImpl.
   *
   * @return a new InternshipSupervisorDTO
   */
  InternshipSupervisorDTO getInternshipSupervisor();

}
