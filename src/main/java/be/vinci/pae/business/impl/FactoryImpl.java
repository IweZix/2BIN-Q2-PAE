package be.vinci.pae.business.impl;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.dto.ContactDTO;
import be.vinci.pae.business.dto.InternshipSupervisorDTO;
import be.vinci.pae.business.dto.StageDTO;
import be.vinci.pae.business.dto.UserDTO;

/**
 * This class implements the Factory interface.
 */
public class FactoryImpl implements Factory {

  @Override
  public UserDTO getUser() {
    return new UserImpl();
  }

  @Override
  public ContactDTO getContact() {
    return new ContactImpl();
  }

  @Override
  public CompanyDTO getCompany() {
    return new CompanyImpl();
  }

  @Override
  public StageDTO getStage() {
    return new StageImpl();
  }

  @Override
  public InternshipSupervisorDTO getInternshipSupervisor() {
    return new InternshipSupervisorImpl();
  }
}
