package be.vinci.pae.business.impl;

import java.util.Objects;

/**
 * CompagnyImpl class.
 */
public class CompanyImpl implements Company {

  private int companyId;
  private String tradeName;
  private String designation;
  private String tradeNameDesignation;
  private boolean blacklist;
  private String address;
  private String phoneNumber;
  private String email;
  private int version;
  private String motivationBlacklist;

  private int countStage;

  private String academicYear;

  private int studentId;


  /**
   * pojo Company.
   */
  public CompanyImpl() {
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
  public String getTradeName() {
    return tradeName;
  }

  @Override
  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }

  @Override
  public String getDesignation() {
    return designation;
  }

  @Override
  public void setDesignation(String designation) {
    this.designation = designation;
  }

  @Override
  public String getTradeNameDesignation() {
    return tradeNameDesignation;
  }

  @Override
  public void setTradeNameDesignation(String tradeNameDesignation) {
    this.tradeNameDesignation = tradeNameDesignation;
  }

  @Override
  public boolean isBlacklist() {
    return blacklist;
  }

  @Override
  public void setBlacklist(boolean blacklist) {
    this.blacklist = blacklist;
  }

  @Override
  public String getAddress() {
    return address;
  }

  @Override
  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Override
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompanyImpl company = (CompanyImpl) o;
    return Objects.equals(tradeName, company.tradeName) && Objects.equals(
        designation, company.designation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tradeName, designation);
  }

  @Override
  public String getMotivationBlacklist() {
    return motivationBlacklist;
  }

  @Override
  public void setMotivationBlacklist(String motivationBlacklist) {
    this.motivationBlacklist = motivationBlacklist;
  }

  @Override
  public void setCountStage(int countStage) {
    this.countStage = countStage;
  }

  @Override
  public int getCountStage() {
    return countStage;
  }

  @Override
  public String  getAcademicYear() {
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


}
