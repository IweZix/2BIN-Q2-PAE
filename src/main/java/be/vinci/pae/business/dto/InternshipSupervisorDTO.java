package be.vinci.pae.business.dto;

import be.vinci.pae.business.impl.InternshipSupervisorImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * InternshipSupervisorDTO interface represents data transfer object for a internship supervisor.
 */
@JsonDeserialize(as = InternshipSupervisorImpl.class)
public interface InternshipSupervisorDTO {

  /**
   * Get the id of the internship supervisor.
   *
   * @return the internship supervisor id
   */
  int getId();

  /**
   * Set the id of the internship supervisor.
   *
   * @param id the id of the internship supervisor
   */
  void setId(int id);

  /**
   * Get the firstname of the internship supervisor.
   *
   * @return the firstname of the internship supervisor
   */
  String getFirstname();

  /**
   * Set the firstname of the internship supervisor.
   *
   * @param firstname the firstname of the internship supervisor
   */
  void setFirstname(String firstname);

  /**
   * Get the lastname of the internship supervisor.
   *
   * @return the lastname of the internship supervisor
   */
  String getLastname();

  /**
   * Set the lastname of the internship supervisor.
   *
   * @param lastname the lastname of the internship supervisor
   */
  void setLastname(String lastname);

  /**
   * Get the phone of the internship supervisor.
   *
   * @return the phone of the internship supervisor
   */
  String getPhone();

  /**
   * Set the phone of the internship supervisor.
   *
   * @param phone the phone of the internship supervisor
   */
  void setPhone(String phone);

  /**
   * Get the email of the internship supervisor.
   *
   * @return the email of the internship supervisor
   */
  String getEmail();

  /**
   * Set the email of the internship supervisor.
   *
   * @param email the email of the internship supervisor
   */
  void setEmail(String email);

  /**
   * Get the company of the internship supervisor.
   *
   * @return the company of the internship supervisor
   */
  int getCompany();

  /**
   * Set the company of the internship supervisor.
   *
   * @param company the company of the internship supervisor
   */
  void setCompany(int company);
}
