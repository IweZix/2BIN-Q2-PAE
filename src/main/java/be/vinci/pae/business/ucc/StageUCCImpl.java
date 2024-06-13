package be.vinci.pae.business.ucc;

import static be.vinci.pae.utils.checker.VersionChecker.compareVersion;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.dto.InternshipSupervisorDTO;
import be.vinci.pae.business.dto.StageDTO;
import be.vinci.pae.business.impl.Stage;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.service.CompanyDAO;
import be.vinci.pae.dal.service.InternshipSupervisorDAO;
import be.vinci.pae.dal.service.StageDAO;
import be.vinci.pae.utils.exception.DataInformationRequired;
import be.vinci.pae.utils.exception.DataNotExistsException;
import be.vinci.pae.utils.exception.InternshipProjectIncorrectException;
import jakarta.inject.Inject;

/**
 * This class implements the StageUCC interface.
 */
public class StageUCCImpl implements StageUCC {

  @Inject
  private StageDAO stageDAO;
  @Inject
  private CompanyDAO companyDAO;
  @Inject
  private InternshipSupervisorDAO internshipSupervisorDAO;
  @Inject
  private DALServices dalServices;

  @Override
  public StageDTO getStageById(int stageId) {
    Stage stage = (Stage) stageDAO.getStageById(stageId);
    if (stage == null) {
      throw new DataNotExistsException("Stage not found ! ");
    }
    return stage;
  }

  @Override
  public StageDTO createStage(StageDTO stage) {
    dalServices.initTransaction();
    try {

      if (stage.getSignatureDate() == null
          || stage.getStudentId() < 0
          || stage.getTradeNameCompany() == null || stage.getTradeNameCompany().isBlank()
          || stage.getInternshipSupervisorLastName() == null
          || stage.getInternshipSupervisorLastName().isBlank()
          || stage.getInternshipSupervisorFirstName() == null
          || stage.getInternshipSupervisorFirstName().isBlank()) {
        throw new DataInformationRequired("Stage Information required");

      }

      CompanyDTO company = companyDAO.getCompanyByTradeNameDesignation(stage.getTradeNameCompany());
      if (company == null) {
        throw new DataNotExistsException("Company not found ! ");
      }
      int companyId = company.getCompanyId();
      stage.setCompanyId(companyId);

      InternshipSupervisorDTO internshipSupervisor =
          internshipSupervisorDAO.getInternshipSupervisorByName(
          stage.getInternshipSupervisorLastName(),
          stage.getInternshipSupervisorFirstName(), companyId);
      int internshipSupervisorId = internshipSupervisor.getId();
      stage.setInternshipSupervisorId(internshipSupervisorId);

      int signatureYear = stage.getSignatureDate().getYear() + 1900;

      // Months are indexed from zero
      int signatureMonth = stage.getSignatureDate().getMonth() + 1;
      String academicYear;
      if (signatureMonth > 8) {
        academicYear = signatureYear + "-" + (signatureYear + 1);
      } else {
        academicYear = (signatureYear - 1) + "-" + signatureYear;
      }

      stage.setAcademicYear(academicYear);
      stageDAO.createStage(stage);
      return stage;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public StageDTO getAcceptedStage(int studentId) {
    dalServices.initTransaction();
    try {
      return stageDAO.getAcceptedStage(studentId);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public boolean updateInternshipProject(StageDTO stage) {
    dalServices.initTransaction();
    try {
      if (stage.getInternshipProject() == null
          || stage.getInternshipProject().isBlank()) {
        throw new InternshipProjectIncorrectException("Internship Project is incorrect");
      }

      Stage temp = (Stage) stageDAO.getStageById(stage.getStageId());
      compareVersion(temp.getVersion(), stage.getVersion());


      return stageDAO.updateInternshipProject(stage);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

}
