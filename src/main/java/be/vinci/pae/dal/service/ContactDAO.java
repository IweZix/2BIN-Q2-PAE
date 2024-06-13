package be.vinci.pae.dal.service;

import be.vinci.pae.business.dto.ContactDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the interface of ContactDAO.
 */
public interface ContactDAO {

  /**
   * Update the contact status to "started".
   *
   * @param contact The ContactDTO object representing the contact whose status needs to be
   *                updated.
   * @return The ContactDTO object updated with the details of the contact started with the company.
   */
  ContactDTO startContactCompany(ContactDTO contact);

  /**
   * Get all contacts of a student.
   *
   * @param studentId the student's id
   * @return the list of contacts
   */
  ArrayList<ContactDTO> getAll(int studentId);

  /**
   * Update a contact.
   *
   * @param contact the ContactDTO representing the contact information to be updated
   * @return the updated contact return null if contact not found
   */
  ContactDTO update(ContactDTO contact);

  /**
   * Get a contact by student and company id.
   *
   * @param studentId the student's id
   * @param companyId the company's id
   * @return the list of contacts
   */
  Object getContactByStudentAndCompanyId(int studentId, int companyId);

  /**
   * Get the number of users with a stage.
   *
   * @return the number of users with a stage
   */
  Map<String, Integer> getNumberUsersWithStage();

  /**
   * Get the number of users by year.
   *
   * @return the number of users without a stage
   */
  Map<String, Integer> getNumberUsersByYear();

  /**
   * Get the contact accepted.
   *
   * @param studentId the student's id
   * @return the contact accepted
   */
  ContactDTO getContactAccepted(int studentId);

  /**
   * Get all company contacts.
   *
   * @param companyId the company's id
   * @return the list of contactc of this company
   */
  List<ContactDTO> getAllContactForTheCompany(int companyId);

  /**
   * Get all student contacts.
   *
   * @param studentId the student's id
   * @return the list of contactc of this student
   */
  List<ContactDTO> getAllContactForStudent(int studentId);
}
