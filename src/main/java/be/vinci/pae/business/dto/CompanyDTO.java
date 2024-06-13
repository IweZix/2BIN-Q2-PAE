package be.vinci.pae.business.dto;

import be.vinci.pae.business.impl.CompanyImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * CompanyDTO class.
 */
@JsonDeserialize(as = CompanyImpl.class)
public interface CompanyDTO {

  /**
   * get the company's id.
   *
   * @return the company's id
   */
  int getCompanyId();

  /**
   * set the company's id.
   *
   * @param companyId the new company id
   */
  void setCompanyId(int companyId);

  /**
   * get the company's trade name.
   *
   * @return the company's trade name
   */
  String getTradeName();

  /**
   * set the company's trade name.
   *
   * @param tradeName the new company trade name
   */
  void setTradeName(String tradeName);

  /**
   * get the company's designation.
   *
   * @return the company's designation
   */
  String getDesignation();

  /**
   * set the company's designation.
   *
   * @param designation the new company designation
   */
  void setDesignation(String designation);

  /**
   * get the company's trade name and the designation.
   *
   * @return the company's trade name and the designation
   */
  String getTradeNameDesignation();

  /**
   * set the company's trade name and the designation.
   *
   * @param tradeNameDesignation the new company trade name and the designation
   */
  void setTradeNameDesignation(String tradeNameDesignation);

  /**
   * check if the company is blacklisted.
   *
   * @return true if the company is blacklisted, else false
   */
  boolean isBlacklist();

  /**
   * set if the company is added to the blacklist.
   *
   * @param blacklist true if the company should be added to the blacklist, else false
   */
  void setBlacklist(boolean blacklist);

  /**
   * get the company's address.
   *
   * @return the company's address
   */
  String getAddress();

  /**
   * set the company's address.
   *
   * @param address the new company address
   */
  void setAddress(String address);

  /**
   * get the company's phone.
   *
   * @return the new company phone's
   */
  String getPhoneNumber();

  /**
   * set the company's phone.
   *
   * @param phoneNumber the new company phone number.
   */
  void setPhoneNumber(String phoneNumber);

  /**
   * get the company email.
   *
   * @return the company email
   */
  String getEmail();

  /**
   * set the company email.
   *
   * @param email the new company email
   */
  void setEmail(String email);

  /**
  * get the company version.
  *
  * @return the company version
  */
  int getVersion();

  /**
   * set the company version.
   *
   * @param version the new company version
   */
  void setVersion(int version);

  /**
   * Get the motivation of blacklist one company.
   *
   * @return the motivation
   */
  String getMotivationBlacklist();

  /**
   * Set the motivation of blacklist one company.
   *
   * @param motivationBlacklist the motivation
   */
  void setMotivationBlacklist(String motivationBlacklist);

  /**
   * get the company's stage count.
   *
   * @return the company's stage count
   */
  int getCountStage();

  /**
   * set the company's stage count.
   *
   * @param countStage the new company stage count
   */
  void setCountStage(int countStage);

  /**
   * get academic year.
   *
   * @return the academic year
   */
  String getAcademicYear();

  /**
   * set academic year.
   *
   * @param academicYear the new academic year
   */
  void setAcademicYear(String academicYear);

  /**
   * get the student id.
   *
   * @return the student id
   */
  int getStudentId();

  /**
   * set the student id.
   *
   * @param studentId the new student id
   */
  void setStudentId(int studentId);
}
