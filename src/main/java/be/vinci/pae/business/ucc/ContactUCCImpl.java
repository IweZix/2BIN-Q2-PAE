package be.vinci.pae.business.ucc;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.dto.ContactDTO;
import be.vinci.pae.business.impl.Contact;
import be.vinci.pae.business.impl.ContactImpl.State;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.service.CompanyDAO;
import be.vinci.pae.dal.service.ContactDAO;
import be.vinci.pae.utils.exception.ContactAlreadyAcceptedException;
import be.vinci.pae.utils.exception.DataIsNotRightStatus;
import be.vinci.pae.utils.exception.DataNotExistsException;
import be.vinci.pae.utils.exception.FailureException;
import be.vinci.pae.utils.exception.InvalidIdException;
import be.vinci.pae.utils.exception.MeetingTypeIncorrectException;
import be.vinci.pae.utils.exception.VersionIncorrectException;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class implements the ContactUCC interface.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO contactDAO;
  @Inject
  private CompanyDAO companyDAO;
  @Inject
  private Factory myFactory;
  @Inject
  private DALServices dalServices;

  @Override
  public ContactDTO startContactCompany(ContactDTO contactDTO) {
    dalServices.initTransaction();
    try {
      ContactDTO acceptedContact = contactDAO.getContactAccepted(contactDTO.getStudentId());
      if (acceptedContact != null) {
        throw new ContactAlreadyAcceptedException(
        "There is already an accepted contact for this student");
      }
      contactDTO.setContactStatus(State.INITIE.getState());
      CompanyDTO company = companyDAO.getCompanyId(contactDTO.getCompanyId());
      if (company == null) {
        throw new InvalidIdException("The company id not exist");
      }
      Date creationDate = new Date(System.currentTimeMillis());
      String academicYear;
      if (creationDate.getMonth() >= 9) {
        academicYear = LocalDate.now().getYear() + "-" + LocalDate.now().plusYears(1).getYear();
      } else {
        academicYear = LocalDate.now().minusYears(1).getYear() + "-" + LocalDate.now().getYear();
      }
      contactDTO.setAcademicYear(academicYear);
      contactDTO = contactDAO.startContactCompany(contactDTO);
      if (contactDTO != null) {
        return contactDTO;
      } else {
        throw new FailureException("The contact's status has not been "
            + "created and set to 'started'");
      }
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public List<ContactDTO> getAll(int studentId) {
    dalServices.initTransaction();
    try {
      return contactDAO.getAll(studentId);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public ContactDTO setCompanyMeet(ContactDTO contactDTO) {
    dalServices.initTransaction();
    try {
      Contact contact = (Contact) contactDAO
          .getContactByStudentAndCompanyId(contactDTO.getStudentId(), contactDTO.getCompanyId());
      if (contact == null) {
        throw new DataNotExistsException("Contact not found");
      }
      if (!contact.checkState(contact.getContactStatus(), State.PRIS.getState())) {
        throw new DataIsNotRightStatus("The contact status is not right");
      }

      if (!contact.checkMeetingType(contactDTO.getMeetingType())) {
        throw new MeetingTypeIncorrectException("Meeting type is incorrect");
      }
      compareVersion(contact.getVersion(), contactDTO.getVersion());

      contact.setContactStatus(State.PRIS.getState());
      contact.setMeetingType(contactDTO.getMeetingType());
      contactDAO.update(contact);
      return contact;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public ContactDTO declineContact(ContactDTO contactDTO) {
    dalServices.initTransaction();
    try {
      Contact contact = (Contact) contactDAO
          .getContactByStudentAndCompanyId(contactDTO.getStudentId(), contactDTO.getCompanyId());
      if (contact == null) {
        throw new DataNotExistsException("Contact not found");
      }


      if (!contact.checkState(contact.getContactStatus(), State.REFUSE.getState())) {
        throw new DataIsNotRightStatus("The contact status is not right");
      }

      compareVersion(contact.getVersion(), contactDTO.getVersion());
      contact.setContactStatus(State.REFUSE.getState());
      contact.setDeclineReason(contactDTO.getDeclineReason());
      contactDAO.update(contact);

      return contact;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public ContactDTO stopFollowingCompany(ContactDTO contactDTO) {
    dalServices.initTransaction();
    try {
      Contact contact = (Contact) contactDAO
          .getContactByStudentAndCompanyId(contactDTO.getStudentId(), contactDTO.getCompanyId());
      if (contact == null) {
        throw new DataNotExistsException("Contact not found");
      }

      if (!contact.checkState(contact.getContactStatus(), State.PLUS_SUIVI.getState())) {
        throw new DataIsNotRightStatus("The contact status is not right");
      }

      compareVersion(contact.getVersion(), contactDTO.getVersion());

      contact.setContactStatus(State.PLUS_SUIVI.getState());
      contactDAO.update(contact);

      return contact;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public Map<String, Integer> getNumberUsersWithStage() {
    dalServices.initTransaction();
    try {
      return contactDAO.getNumberUsersWithStage();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public Map<String, Integer> getNumberUsersWithoutStage() {
    dalServices.initTransaction();
    try {
      return contactDAO.getNumberUsersByYear();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  private void compareVersion(int version, int version1) {
    if (version != version1) {
      throw new VersionIncorrectException("Version is incorrect");
    }
  }

  @Override
  public ContactDTO acceptContact(ContactDTO contactDTO) {
    dalServices.initTransaction();
    try {
      Contact contact = (Contact) contactDAO
          .getContactByStudentAndCompanyId(contactDTO.getStudentId(), contactDTO.getCompanyId());
      if (contact == null) {
        throw new DataNotExistsException("Contact not found");
      }
      ContactDTO contactAccepted = contactDAO.getContactAccepted(contactDTO.getStudentId());
      if (contactAccepted != null) {
        throw new ContactAlreadyAcceptedException("already accepted Contact");
      }

      if (!contact.checkState(contact.getContactStatus(), State.ACCEPTE.getState())) {
        throw new DataIsNotRightStatus("The contact status is not right");
      }

      compareVersion(contact.getVersion(), contactDTO.getVersion());
      contact.setContactStatus(State.ACCEPTE.getState());
      contactDAO.update(contact);

      ArrayList<ContactDTO> allContacts = contactDAO.getAll(contactDTO.getStudentId());

      if (allContacts == null || allContacts.isEmpty()) {
        return contact;
      }

      for (ContactDTO contactObject : allContacts) {
        ContactDTO otherContact = myFactory.getContact();
        otherContact.setStudentId(contactDTO.getStudentId());
        otherContact.setCompanyId(contactObject.getCompanyId());
        otherContact.setContactStatus(contactObject.getContactStatus());
        if (contactObject.getMeetingType() != null) {
          otherContact.setMeetingType(contactObject.getMeetingType());
        }
        if (contactObject.getDeclineReason() != null) {
          otherContact.setDeclineReason(contactObject.getDeclineReason());
        }
        otherContact.setVersion(contactObject.getVersion());

        if (!otherContact.getContactStatus().equals(State.ACCEPTE.getState())) {
          otherContact.setContactStatus(State.SUSPENDU.getState());
          contactDAO.update(otherContact);
        }
      }

      return contact;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public List<ContactDTO> getAllContactForTheCompany(int companyId) {
    dalServices.initTransaction();
    try {
      return contactDAO.getAllContactForTheCompany(companyId);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public List<ContactDTO> getAllContactForStudent(int studentId) {
    dalServices.initTransaction();
    try {
      return contactDAO.getAllContactForStudent(studentId);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }
}
