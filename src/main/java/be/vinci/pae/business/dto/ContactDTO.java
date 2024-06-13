package be.vinci.pae.business.dto;

import be.vinci.pae.business.impl.ContactImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * ContactDTO class.
 */
@JsonDeserialize(as = ContactImpl.class)
public interface ContactDTO {

  /**
   * get the contact's status.
   *
   * @return the contact's status
   */
  String getContactStatus();

  /**
   * set the contat's status.
   *
   * @param contactStatus the new contact status
   */
  void setContactStatus(String contactStatus);

  /**
   * get the meeting's type.
   *
   * @return the meeting's type
   */
  String getMeetingType();

  /**
   * set the meeting's type.
   *
   * @param meetingType the new meeting type
   */
  void setMeetingType(String meetingType);

  /**
   * get the reason for refusal.
   *
   * @return the reason for refusal
   */
  String getDeclineReason();

  /**
   * set the reason for refusal.
   *
   * @param declineReason the reason for refusal
   */
  void setDeclineReason(String declineReason);

  /**
   * get the id of the student .
   *
   * @return student's id
   */
  int getStudentId();

  /**
   * Set the id of the student.
   *
   * @param studentId the id of the student
   */
  void setStudentId(int studentId);

  /**
   * get the id of the company.
   *
   * @return the company's id
   */
  int getCompanyId();

  /**
   * set the id of the company.
   *
   * @param companyId the company's id
   */
  void setCompanyId(int companyId);

  /**
   * get the version of the contact.
   *
   * @return the version of the contact
   */
  int getVersion();

  /**
   * set the version of the contact.
   *
   * @param version the new version of the contact
   */
  void setVersion(int version);

  /**
   * get the student's name.
   *
   * @return the student's name
   */
  String getStudentName();

  /**
   * set the student's name.
   *
   * @param studentName the student's name
   */
  void setStudentName(String studentName);

  /**
   * get the student's first name.
   *
   * @return the student's first name
   */
  String getStudentFirstName();

  /**
   * set the student's first name.
   *
   * @param studentFirstName the student's first name
   */
  void setStudentFirstName(String studentFirstName);

  /**
   * get the student's email.
   *
   * @return the student's email
   */
  String getStudentEmail();

  /**
   * set the student's email.
   *
   * @param studentEmail the student's email
   */
  void setStudentEmail(String studentEmail);

  /**
   * get the company's tradeName.
   *
   * @return the company's tradeName
   */
  String getCompanyTradeName();

  /**
   * set the company's tradeName.
   *
   * @param companyTradeName the company's tradeName
   */
  void setCompanyTradeName(String companyTradeName);

  /**
   * get the company's designation.
   *
   * @return the company's designation
   */
  String getCompanyDesignation();

  /**
   * set the company's designation.
   *
   * @param companyDesignation the company's designation
   */
  void setCompanyDesignation(String companyDesignation);

  /**
   * get the company's trade name and designation.
   *
   * @return the company's trade name and designation
   */
  String getCompanyTradeNameDesignation();

  /**
   * set the company's trade name and designation.
   *
   * @param companyTradeNameDesignation the company's trade name and designation
   */
  void setCompanyTradeNameDesignation(String companyTradeNameDesignation);

  /**
   * get the company's phone.
   *
   * @return the company's phone
   */
  String getCompanyPhone();

  /**
   * set the company's phone.
   *
   * @param phone the company's phone
   */
  void setCompanyPhone(String phone);

  /**
   * get the contact's academicYear.
   *
   * @return the contact's academicYear
   */
  String getAcademicYear();

  /**
   * set the contact's academicYear.
   *
   * @param academicYear the contact's academicYear
   */
  void setAcademicYear(String academicYear);
}
