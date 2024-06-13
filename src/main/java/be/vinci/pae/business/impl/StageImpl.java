package be.vinci.pae.business.impl;

import java.util.Date;
import java.util.Objects;

/**
 * StageImpl class.
 */
public class StageImpl implements Stage {

  private int stageId;
  private Date signatureDate;
  private String internshipProject;
  private String academicYear;
  private int studentId;
  private int companyId;
  private int internshipSupervisorId;
  private int version;

  private String tradeNameCompany;
  private String designationCompany;
  private String internshipSupervisorLastName;
  private String internshipSupervisorFirstName;
  private String internshipSupervisorEmail;
  private String internshipSupervisorPhone;

  /**
   * pojo Stage.
   */
  public StageImpl() {
  }

  @Override
  public void setStageId(int stageId) {
    this.stageId = stageId;
  }

  @Override
  public Date getSignatureDate() {
    return signatureDate;
  }

  @Override
  public void setSignatureDate(Date signatureDate) {
    this.signatureDate = signatureDate;
  }

  @Override
  public String getInternshipProject() {
    return internshipProject;
  }

  @Override
  public void setInternshipProject(String internshipProject) {
    this.internshipProject = internshipProject;
  }

  @Override
  public String getAcademicYear() {
    return academicYear;
  }

  @Override
  public void setAcademicYear(String academicYear) {
    this.academicYear = academicYear;
  }

  @Override
  public int getStudentId() {
    return studentId;
  }

  @Override
  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  @Override
  public int getCompanyId() {
    return companyId;
  }

  @Override
  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  @Override
  public int getInternshipSupervisorId() {
    return internshipSupervisorId;
  }

  @Override
  public void setInternshipSupervisorId(int internshipSupervisorId) {
    this.internshipSupervisorId = internshipSupervisorId;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String getTradeNameCompany() {
    return tradeNameCompany;
  }

  @Override
  public void setTradeNameCompany(String tradeNameCompany) {
    this.tradeNameCompany = tradeNameCompany;
  }

  public String getDesignationCompany() {
    return designationCompany;
  }

  public void setDesignationCompany(String designationCompany) {
    this.designationCompany = designationCompany;
  }

  @Override
  public String getInternshipSupervisorLastName() {
    return internshipSupervisorLastName;
  }

  @Override
  public void setInternshipSupervisorLastName(String internshipSupervisorLastName) {
    this.internshipSupervisorLastName = internshipSupervisorLastName;
  }

  @Override
  public String getInternshipSupervisorFirstName() {
    return internshipSupervisorFirstName;
  }

  @Override
  public void setInternshipSupervisorFirstName(String internshipSupervisorFirstName) {
    this.internshipSupervisorFirstName = internshipSupervisorFirstName;
  }

  @Override
  public void setInternshipSupervisorEmail(String internshipSupervisorEmail) {
    this.internshipSupervisorEmail = internshipSupervisorEmail;
  }

  @Override
  public String getInternshipSupervisorEmail() {
    return internshipSupervisorEmail;
  }

  @Override
  public void setInternshipSupervisorPhone(String internshipSupervisorPhone) {
    this.internshipSupervisorPhone = internshipSupervisorPhone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StageImpl stage = (StageImpl) o;
    return stageId == stage.stageId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(stageId);
  }

  @Override
  public int getStageId() {
    return stageId;
  }

  @Override
  public String getInternshipSupervisorPhone() {
    return internshipSupervisorPhone;
  }
}
