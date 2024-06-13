package be.vinci.pae.business.dto;

import be.vinci.pae.business.impl.StageImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

/**
 * StageDTO interface represents data transfer object for a stage.
 */
@JsonDeserialize(as = StageImpl.class)
public interface StageDTO {

  /**
   * Get the id of the stage.
   *
   * @return the stage id
   */
  int getStageId();

  /**
   * Get the signature date of the stage.
   *
   * @return the signature date
   */
  Date getSignatureDate();

  /**
   * Get the internship project of the stage.
   *
   * @return the internship project
   */
  String getInternshipProject();

  /**
   * Get the academic year of the stage.
   *
   * @return the academic year
   */
  String getAcademicYear();

  /**
   * Get the id of the student.
   *
   * @return the student id
   */
  int getStudentId();

  /**
   * Get the id of the company.
   *
   * @return the company id
   */
  int getCompanyId();

  /**
   * Get the id of the internship supervisor.
   *
   * @return the internship supervisor id
   */
  int getInternshipSupervisorId();

  /**
   * Set the id of the stage.
   *
   * @param stageId the stage id
   */
  void setStageId(int stageId);

  /**
   * Set the signature date of the stage.
   *
   * @param signatureDate the signature date
   */
  void setSignatureDate(Date signatureDate);

  /**
   * Set the internship project of the stage.
   *
   * @param internshipProject the internship project
   */
  void setInternshipProject(String internshipProject);

  /**
   * Set the academic year of the stage.
   *
   * @param academicYear the academic year
   */
  void setAcademicYear(String academicYear);

  /**
   * Set the id of the student.
   *
   * @param studentId the student id
   */
  void setStudentId(int studentId);

  /**
   * Set the id of the company.
   *
   * @param companyId the company id
   */
  void setCompanyId(int companyId);

  /**
   * Set the id of the internship supervisor.
   *
   * @param internshipSupervisorId the internship supervisor id
   */
  void setInternshipSupervisorId(int internshipSupervisorId);

  /**
   * Get the version of the stage.
   *
   * @return the version of the stage
   */
  int getVersion();

  /**
   * Set the version of the stage with the one passed in parameter.
   *
   * @param version the version of the stage to set
   */
  void setVersion(int version);

  /**
   * Get the trade name of the company.
   *
   * @return the trade name of the company
   */
  String getTradeNameCompany();

  /**
   * Set the trade name of the company with the one passed in parameter.
   *
   * @param tradeNameCompany the trade name of the company to set
   */
  void setTradeNameCompany(String tradeNameCompany);


  /**
   * Get the designation of the company.
   *
   * @return the designation of the company
   */
  String getDesignationCompany();

  /**
   * Set the designation of the company with the one passed in parameter.
   *
   * @param designationCompany the designation of the company to set
   */
  void setDesignationCompany(String designationCompany);

  /**
   * Get the name of the internship supervisor.
   *
   * @return the name of the internship supervisor
   */
  String getInternshipSupervisorLastName();

  /**
   * Set the name of the internship supervisor with the one passed in parameter.
   *
   * @param internshipSupervisorLastName the name of the internship supervisor to set
   */
  void setInternshipSupervisorLastName(String internshipSupervisorLastName);

  /**
   * Get the first name of the internship supervisor.
   *
   * @return the first name of the internship supervisor
   */
  String getInternshipSupervisorFirstName();

  /**
   * Set the first name of the internship supervisor with the one passed in parameter.
   *
   * @param internshipSupervisorFirstName the first name of the internship supervisor to set
   */
  void setInternshipSupervisorFirstName(String internshipSupervisorFirstName);

  /**
   * Get the email of the internship supervisor.
   *
   * @return the email of the internship supervisor
   */
  String getInternshipSupervisorEmail();

  /**
   * Set the email of the internship supervisor with the one passed in parameter.
   *
   * @param internshipSupervisorEmail the email of the internship supervisor to set
   */
  void setInternshipSupervisorEmail(String internshipSupervisorEmail);

  /**
   * Get the phone of the internship supervisor.
   *
   * @return the phone of the internship supervisor
   */
  String getInternshipSupervisorPhone();

  /**
   * Set the phone of the internship supervisor with the one passed in parameter.
   *
   * @param internshipSupervisorPhone the phone of the internship supervisor to set
   */
  void setInternshipSupervisorPhone(String internshipSupervisorPhone);
}
