package be.vinci.pae.business.ucc;

import be.vinci.pae.business.dto.ContactDTO;
import java.util.List;
import java.util.Map;

/**
 * this is the interface of ContactUCCImpl.
 */
public interface ContactUCC {

  /**
   * Create contact and set status to "started".
   *
   * @param contactDTO contact to create
   * @return the created contact and set status to "started"
   */
  ContactDTO startContactCompany(ContactDTO contactDTO);

  /**
   * Get all contacts of a student.
   *
   * @param studentId student's id
   * @return all contacts of a student
   */
  List<ContactDTO> getAll(int studentId);

  /**
   * Set that we meet a company.
   *
   * @param contactDTO the contact to set
   * @return the contact
   **/
  ContactDTO setCompanyMeet(ContactDTO contactDTO);

  /**
   * Accept a contact.
   *
   * @param contactDTO the contact to accept
   * @return the contact
   */
  ContactDTO declineContact(ContactDTO contactDTO);

  /**
   * Stop following a company.
   *
   * @param contactDTO the contact to stop following
   * @return the contact
   */
  ContactDTO stopFollowingCompany(ContactDTO contactDTO);

  /**
   * Get the number of users with a stage.
   *
   * @return JSONArray containing the number of users with a stage and year
   */
  Map<String, Integer> getNumberUsersWithStage();

  /**
   * Get the number of users without a stage.
   *
   * @return JSONArray containing the number of users without a stage and year
   */
  Map<String, Integer> getNumberUsersWithoutStage();

  /**
   * Accept a contact.
   *
   * @param contactDTO the contact to accept
   * @return the contact
   */
  ContactDTO acceptContact(ContactDTO contactDTO);

  /**
   * Get the list of contacts of the specified company.
   *
   * @param companyId the id of the company
   * @return the list of contacts
   */
  List<ContactDTO> getAllContactForTheCompany(int companyId);

  /**
   * Get the list of contacts of the specified student.
   *
   * @param studentId the id of the student
   * @return the list of contacts
   */
  List<ContactDTO> getAllContactForStudent(int studentId);

}
