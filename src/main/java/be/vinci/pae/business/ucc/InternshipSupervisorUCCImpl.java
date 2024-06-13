package be.vinci.pae.business.ucc;

import be.vinci.pae.business.dto.InternshipSupervisorDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.business.impl.InternshipSupervisor;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.service.InternshipSupervisorDAO;
import be.vinci.pae.utils.exception.DataAlreadyExistException;
import be.vinci.pae.utils.exception.DataInformationRequired;
import jakarta.inject.Inject;
import java.util.List;

/**
 * InternshipSupervisorUCCImpl class.
 */
public class InternshipSupervisorUCCImpl implements InternshipSupervisorUCC {

  @Inject
  private InternshipSupervisorDAO internshipSupervisorDAO;
  @Inject
  private Factory myFactory;
  @Inject
  private DALServices dalServices;

  @Override
  public InternshipSupervisorDTO create(InternshipSupervisorDTO internshipSupervisor) {
    dalServices.initTransaction();
    try {
      if (internshipSupervisor.getLastname() == null || internshipSupervisor.getFirstname() == null
          || internshipSupervisor.getLastname().isBlank()
          || internshipSupervisor.getFirstname().isBlank()
          || internshipSupervisor.getPhone() == null || internshipSupervisor.getEmail() == null
          || internshipSupervisor.getPhone().isBlank()
          || internshipSupervisor.getEmail().isBlank()
          || internshipSupervisor.getCompany() < 0) {
        throw new DataInformationRequired("Internship Supervisor Information required");
      }
      InternshipSupervisor intSup = (InternshipSupervisor) myFactory.getInternshipSupervisor();
      if (!intSup.isEmailValidInInternshipSupervisor(internshipSupervisor.getEmail())) {
        throw new DataInformationRequired("Email is not valid");
      }
      if (internshipSupervisorDAO.getInternshipSupervisor(internshipSupervisor) != null) {
        throw new DataAlreadyExistException("Internship Supervisor already exists");
      }
      if (internshipSupervisorDAO.getInternshipSupByEmail(internshipSupervisor
          .getEmail()) != null) {
        throw new DataAlreadyExistException("Email already exists");
      }
      if (internshipSupervisorDAO.getInternshipPhone(internshipSupervisor.getPhone()) != null) {
        throw new DataAlreadyExistException("Phone already exists");
      }
      internshipSupervisor = internshipSupervisorDAO.create(internshipSupervisor);
      return internshipSupervisor;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public List<InternshipSupervisorDTO> getAllInternshipSupervisors(int companyId) {
    dalServices.initTransaction();
    try {
      List<InternshipSupervisorDTO> internshipSupervisors = internshipSupervisorDAO
          .getAllInternshipSupervisorByCompanyId(companyId);
      return internshipSupervisors;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public InternshipSupervisorDTO getInternshipSupervisor(
      InternshipSupervisorDTO internshipSupervisor) {
    dalServices.initTransaction();
    try {
      InternshipSupervisorDTO intSup
          = internshipSupervisorDAO.getInternshipSupervisor(internshipSupervisor);
      return intSup;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }
}
