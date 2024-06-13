package be.vinci.pae.business.ucc;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.dto.ContactDTO;
import be.vinci.pae.business.impl.ContactImpl.State;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.dal.service.CompanyDAO;
import be.vinci.pae.dal.service.ContactDAO;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.exception.ContactAlreadyAcceptedException;
import be.vinci.pae.utils.exception.DataIsNotRightStatus;
import be.vinci.pae.utils.exception.DataNotExistsException;
import be.vinci.pae.utils.exception.FailureException;
import be.vinci.pae.utils.exception.InvalidIdException;
import be.vinci.pae.utils.exception.MeetingTypeIncorrectException;
import be.vinci.pae.utils.exception.VersionIncorrectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

/**
 * Test class for {@link ContactUCCImpl}. This class contains test cases for the ContactUCCImpl
 * class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactUCCImplTest {

  private final ServiceLocator sl = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
  private Factory myFactory;
  private ContactUCC contactUCC;
  private ContactDAO contactDAO;
  private CompanyDAO companyDAO;
  private ContactDTO startContact;
  private ContactDTO admittedContactRemote;
  private ContactDTO turnedDownContact;
  private ContactDTO unsupervisedContact;

  private ContactDTO acceptedContact;
  private CompanyDTO companyDTO;

  @BeforeAll
  void setUp() {
    Config.load("dev.properties");
    myFactory = sl.getService(Factory.class);
    contactUCC = sl.getService(ContactUCC.class);
    contactDAO = sl.getService(ContactDAO.class);
    companyDAO = sl.getService(CompanyDAO.class);
    startContact = sl.getService(ContactDTO.class);
    admittedContactRemote = sl.getService(ContactDTO.class);
    turnedDownContact = sl.getService(ContactDTO.class);
    unsupervisedContact = sl.getService(ContactDTO.class);
    acceptedContact = sl.getService(ContactDTO.class);
    companyDTO = sl.getService(CompanyDTO.class);
  }

  @BeforeEach
  void setUpTest() {
    Mockito.reset(contactDAO);
    Mockito.reset(companyDAO);

    startContact = myFactory.getContact();
    startContact.setStudentId(1);
    startContact.setCompanyId(1);
    startContact.setStudentName("name1");
    startContact.setStudentFirstName("firstname1");
    startContact.setStudentEmail("email1");
    startContact.setAcademicYear("2023-2024");
    startContact.setContactStatus(State.INITIE.getState());
    startContact.setVersion(1);

    admittedContactRemote = myFactory.getContact();
    admittedContactRemote.setStudentId(1);
    admittedContactRemote.setCompanyId(1);
    admittedContactRemote.setStudentName("name2");
    admittedContactRemote.setStudentFirstName("firstname2");
    admittedContactRemote.setStudentEmail("email2");
    admittedContactRemote.setAcademicYear("2023-2024");
    admittedContactRemote.setContactStatus(State.PRIS.getState());
    admittedContactRemote.setMeetingType("distance");
    admittedContactRemote.setVersion(1);

    turnedDownContact = myFactory.getContact();
    turnedDownContact.setStudentId(1);
    turnedDownContact.setCompanyId(1);
    turnedDownContact.setStudentName("name3");
    turnedDownContact.setStudentFirstName("firstname3");
    turnedDownContact.setStudentEmail("email3");
    turnedDownContact.setAcademicYear("2023-2024");
    turnedDownContact.setContactStatus(State.REFUSE.getState());
    turnedDownContact.setDeclineReason("raison_refus");
    turnedDownContact.setVersion(1);


    unsupervisedContact = myFactory.getContact();
    unsupervisedContact.setStudentId(1);
    unsupervisedContact.setCompanyId(1);
    unsupervisedContact.setAcademicYear("2023-2024");
    unsupervisedContact.setContactStatus(State.PLUS_SUIVI.getState());
    unsupervisedContact.setVersion(1);

    acceptedContact = myFactory.getContact();
    acceptedContact.setStudentId(1);
    acceptedContact.setCompanyId(1);
    acceptedContact.setAcademicYear("2023-2024");
    acceptedContact.setContactStatus(State.ACCEPTE.getState());
    acceptedContact.setVersion(1);

    companyDTO = myFactory.getCompany();
    companyDTO.setCompanyId(1);
  }

  /**
   * Test startContactCompany.
   */
  @Test
  @DisplayName("Successfully created contact ")
  void startContactCompany_contactDtoNull_throwsException() {
    Mockito.when(companyDAO.getCompanyId(1)).thenReturn(companyDTO);
    Mockito.when(contactDAO.startContactCompany(startContact)).thenReturn(startContact);

    assertEquals(startContact, contactUCC.startContactCompany(startContact));
  }

  @Test
  @DisplayName("CompanyId not exist")
  public void companyIdNotExist() {

    Mockito.when(companyDAO.getCompanyId(1)).thenReturn(null);

    assertThrows(InvalidIdException.class, () ->
        contactUCC.startContactCompany(startContact)
    );
  }

  @Test
  @DisplayName("The contact was not created successfully")
  public void contactNotCreated() {
    Mockito.when(companyDAO.getCompanyId(1)).thenReturn(companyDTO);
    Mockito.when(contactDAO.startContactCompany(Mockito.any(ContactDTO.class))).thenReturn(null);

    assertThrows(FailureException.class, () ->
        contactUCC.startContactCompany(startContact)
    );
  }


  /**
   * Test getAll.
   */
  @Test
  @DisplayName("Successfully get all contacts")
  public void getAllContacts() {
    ArrayList<ContactDTO> contactDTOS = new ArrayList<>();

    contactDTOS.add(startContact);
    Mockito.when(contactDAO.getAll(1)).thenReturn(contactDTOS);

    assertEquals(contactDTOS, contactUCC.getAll(1));
  }

  @Test
  @DisplayName("Get all contacts failed")
  public void getAllContactsFailed() {
    Mockito.when(contactDAO.getAll(1))
        .thenThrow(new FailureException("Failed to get contacts"));

    assertThrows(FailureException.class, () ->
        contactUCC.getAll(1)
    );
  }


  /**
   * Test setCompanyMeet.
   */
  @Test
  @DisplayName("Successfully set company meet")
  public void successfullySetCompanyMeet() {

    admittedContactRemote.setContactStatus("initie");
    startContact.setContactStatus("initie");
    startContact.setMeetingType("distance");

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(admittedContactRemote);

    ContactDTO actualContact = contactUCC.setCompanyMeet(startContact);

    assertEquals(admittedContactRemote, actualContact);
  }

  @Test
  @DisplayName("Company meet failed because studentId is incorrect")
  public void companyMeetFailedStudentIdIncorrect() {
    assertThrows(DataNotExistsException.class, () ->
        contactUCC.setCompanyMeet(startContact)
    );
  }

  @Test
  @DisplayName("Company meet failed because companyId is incorrect")
  public void companyMeetFailedCompanyIdIncorrect() {
    assertThrows(DataNotExistsException.class, () ->
        contactUCC.setCompanyMeet(startContact)
    );
  }

  @Test
  @DisplayName("Company meet failed because contact is null")
  public void companyMeetFailedContactNull() {
    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(null);

    assertThrows(DataNotExistsException.class, () ->
        contactUCC.setCompanyMeet(admittedContactRemote)
    );
  }


  @Test
  @DisplayName("Company meet failed because contact not in the right status")
  public void companyMeetFailedContactNotInRightStatus() {

    startContact.setMeetingType("distance");

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(admittedContactRemote);

    assertThrows(DataIsNotRightStatus.class, () ->
        contactUCC.setCompanyMeet(startContact)
    );
  }

  @Test
  @DisplayName("Company meet failed because meeting type is incorrect")
  public void companyMeetFailedMeetingTypeIncorrect() {
    admittedContactRemote.setContactStatus("initie");
    admittedContactRemote.setMeetingType("aa");

    startContact.setMeetingType("aa");

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(admittedContactRemote);

    assertThrows(MeetingTypeIncorrectException.class, () ->
        contactUCC.setCompanyMeet(startContact)
    );
  }


  /**
   * Test declineContact.
   */
  @Test
  @DisplayName("Successfully decline contact")
  public void declineContact() {

    startContact.setContactStatus("refuse");

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(admittedContactRemote);

    ContactDTO actualContact = contactUCC.declineContact(startContact);
    assertEquals(admittedContactRemote, actualContact);
  }

  @Test
  @DisplayName("Decline contact failed because studentId is incorrect")
  public void declineContactFailedStudentIdIncorrect() {
    admittedContactRemote.setStudentId(-1);

    assertThrows(DataNotExistsException.class, () ->
        contactUCC.declineContact(admittedContactRemote)
    );
  }

  @Test
  @DisplayName("Decline contact failed because companyId is incorrect")
  public void declineContactFailedCompanyIdIncorrect() {

    admittedContactRemote.setCompanyId(-1);
    assertThrows(DataNotExistsException.class, () ->
        contactUCC.declineContact(admittedContactRemote)
    );
  }

  @Test
  @DisplayName("Decline contact failed because contact is null")
  public void declineContactFailedContactNull() {
    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(null);

    assertThrows(DataNotExistsException.class, () ->
        contactUCC.declineContact(admittedContactRemote)
    );
  }

  @Test
  @DisplayName("Decline contact catch exception")
  public void declineContactCatchException() {
    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenThrow(new FailureException("Failed to get contact"));

    assertThrows(FailureException.class, () ->
        contactUCC.declineContact(admittedContactRemote)
    );
  }

  @Test
  @DisplayName("Failed Because Contact Status Is Not Right - getState : REFUSE")
  public void failedBecauseContactStatusIsNotRightDecline() {

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(turnedDownContact);

    startContact.setContactStatus("aa");

    assertThrows(DataIsNotRightStatus.class, () ->
        contactUCC.declineContact(startContact));

  }


  /**
   * Test stopFollowingCompany.
   */
  @Test
  @DisplayName("Successfully stop following")
  public void stopFollowing() {

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(admittedContactRemote);

    ContactDTO actualContact = contactUCC.stopFollowingCompany(startContact);
    assertEquals(admittedContactRemote, actualContact);
  }

  @Test
  @DisplayName("Stop following failed because studentId is incorrect")
  public void stopFollowingFailedStudentIdIncorrect() {

    startContact.setStudentId(-1);

    assertThrows(DataNotExistsException.class, () ->
        contactUCC.stopFollowingCompany(startContact)
    );
  }

  @Test
  @DisplayName("Stop following failed because companyId is incorrect")
  public void stopFollowingFailedCompanyIdIncorrect() {

    startContact.setCompanyId(-1);

    assertThrows(DataNotExistsException.class, () ->
        contactUCC.stopFollowingCompany(startContact)
    );
  }

  @Test
  @DisplayName("Stop following failed because contact is null")
  public void stopFollowingFailedContactNull() {

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(null);

    assertThrows(DataNotExistsException.class, () ->
        contactUCC.stopFollowingCompany(startContact)
    );
  }

  @Test
  @DisplayName("Stop following catch exception")
  public void stopFollowingCatchException() {
    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenThrow(new FailureException("Failed to get contact"));

    assertThrows(FailureException.class, () ->
        contactUCC.stopFollowingCompany(startContact)
    );
  }

  @Test
  @DisplayName("Failed Because Contact Status Is Not Right - getState : PLUS_SUIVI")
  public void failedBecauseContactStatusIsNotRightStopFollowing() {

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(unsupervisedContact);

    assertThrows(DataIsNotRightStatus.class, () ->
        contactUCC.stopFollowingCompany(startContact));

  }


  /**
   * Test getNumberUsersWithStage.
   */
  @Test
  @DisplayName("Successfully getNumberUsersWithStage")
  public void getNumberUsersWithStage() {
    Map<String, Integer> map = Map.of("2021-2022", 1);
    Mockito.when(contactDAO.getNumberUsersWithStage()).thenReturn(map);

    assertEquals(1, contactUCC.getNumberUsersWithStage().get("2021-2022"));
  }

  @Test
  @DisplayName("Get number users with stage failed")
  public void getNumberUsersWithStageFailed() {
    Mockito.when(contactDAO.getNumberUsersWithStage())
        .thenThrow(new FailureException("Failed to get number users with stage"));

    assertThrows(FailureException.class, () ->
        contactUCC.getNumberUsersWithStage()
    );
  }


  /**
   * Test getNumberUsersWithoutStage.
   */
  @Test
  @DisplayName("Successfully getNumberUsersWithoutStage")
  public void getNumberUsersWithoutStage() {
    Map<String, Integer> map = Map.of("2021-2022", 1);
    Mockito.when(contactDAO.getNumberUsersByYear()).thenReturn(map);

    assertEquals(1, contactUCC.getNumberUsersWithoutStage().get("2021-2022"));
  }

  @Test
  @DisplayName("Get number users without stage failed")
  public void getNumberUsersWithoutStageFailed() {
    Mockito.when(contactDAO.getNumberUsersByYear())
        .thenThrow(new FailureException("Failed to get number users without stage"));

    assertThrows(FailureException.class, () ->
        contactUCC.getNumberUsersWithoutStage()
    );
  }

  /**
   * Test accepteContact.
   */
  @Test
  @DisplayName("Successfully accept contact")
  public void acceptContact() {

    startContact.setContactStatus("pris");


    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(admittedContactRemote);

    ContactDTO actualContact = contactUCC.acceptContact(startContact);
    assertEquals(admittedContactRemote, actualContact);
  }

  @Test
  @DisplayName("Acceptation failed because it's already an accepted contact for this student")
  public void noAccecptedBecauseAlreadyAccepted() {

    Mockito.when(contactDAO.getContactAccepted(1)).thenReturn(acceptedContact);

    assertThrows(ContactAlreadyAcceptedException.class, () -> {
      contactUCC.startContactCompany(acceptedContact);
    });

  }

  @Test
  @DisplayName("Failed accepted contact because contact not found")
  public void acceptContactFailedContactNotFound() {

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(null);

    assertThrows(DataNotExistsException.class, () ->
        contactUCC.acceptContact(startContact)
    );
  }

  @Test
  @DisplayName("Already accepted Contact")
  public void alreadyAcceptedContact() {
    startContact.setContactStatus(State.ACCEPTE.getState());

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(acceptedContact);
    Mockito.when(contactDAO.getContactAccepted(1)).thenReturn(acceptedContact);

    assertThrows(ContactAlreadyAcceptedException.class, () -> {
      contactUCC.acceptContact(startContact);
    });
  }

  @Test
  @DisplayName("Failed Because Contact Status Is Not Right - getState : ACCEPTE")
  public void failedBecauseContactStatusIsNotRightAccepte() {

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(acceptedContact);

    assertThrows(DataIsNotRightStatus.class, () ->
        contactUCC.acceptContact(startContact));
  }

  @Test
  @DisplayName("Accept contact when allContacts is null")
  public void acceptContactWhenAllContactsIsNull() {

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(admittedContactRemote);
    Mockito.when(contactDAO.getAll(1)).thenReturn(null);

    contactUCC.acceptContact(startContact);
  }

  @Test
  @DisplayName("Accept a contact when a contact is already accepted")
  public void acceptContactWhenContactAlreadyAccepted() {

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(admittedContactRemote);
    Mockito.when(contactDAO.getContactAccepted(1)).thenReturn(admittedContactRemote);

    assertThrows(ContactAlreadyAcceptedException.class, () ->
        contactUCC.acceptContact(startContact)
    );
  }

  @Test
  @DisplayName("Update contacts to 'suspendu' if not already 'accepte'")
  public void updateContactsToSuspendu() {

    ArrayList<ContactDTO> allContacts = new ArrayList<>();
    allContacts.add(admittedContactRemote);
    allContacts.add(turnedDownContact);

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(admittedContactRemote);
    Mockito.when(contactDAO.getContactAccepted(1)).thenReturn(null);
    Mockito.when(contactDAO.getAll(1)).thenReturn(allContacts);

    assertEquals(admittedContactRemote, contactUCC.acceptContact(startContact));
  }

  @Test
  @DisplayName("Update contacts to 'suspendu' if not already 'accepte'")
  public void updateContactsToSuspendu2() {

    ArrayList<ContactDTO> allContacts = new ArrayList<>();
    allContacts.add(admittedContactRemote);
    allContacts.add(turnedDownContact);

    Mockito.when(contactDAO.getContactByStudentAndCompanyId(1, 1))
        .thenReturn(admittedContactRemote);
    Mockito.when(contactDAO.getContactAccepted(1)).thenReturn(null);
    Mockito.when(contactDAO.getAll(1)).thenReturn(allContacts);
    startContact.setVersion(admittedContactRemote.getVersion());

    contactUCC.acceptContact(startContact);

    admittedContactRemote.setContactStatus("suspendu");
    turnedDownContact.setContactStatus("suspendu");
  }


  /**
   * Test getAllContactForTheCompany.
   */
  @Test
  @DisplayName("Successfully get all contact for the company")
  public void getAllContactForTheCompany() {
    List<ContactDTO> listContact = new ArrayList<>();
    listContact.add(startContact);
    listContact.add(admittedContactRemote);
    listContact.add(turnedDownContact);

    Mockito.when(contactDAO.getAllContactForTheCompany(1)).thenReturn(listContact);

    assertEquals(listContact, contactUCC.getAllContactForTheCompany(1));
  }

  @Test
  @DisplayName("Get all contact for the company failed")
  public void getAllContactForTheCompanyFailed() {
    Mockito.when(contactDAO.getAllContactForTheCompany(1))
        .thenThrow(new FailureException("Failed to get all contact for the company"));

    assertThrows(FailureException.class, () ->
        contactUCC.getAllContactForTheCompany(1)
    );
  }

  /**
   * Test getAllContactForTheStudent.
   */
  @Test
  @DisplayName("Successfully get all contact for the student")
  public void getAllContactForTheStudent() {
    List<ContactDTO> listContact = new ArrayList<>();
    listContact.add(startContact);
    listContact.add(admittedContactRemote);
    listContact.add(turnedDownContact);

    Mockito.when(contactDAO.getAllContactForStudent(1)).thenReturn(listContact);

    assertEquals(listContact, contactUCC.getAllContactForStudent(1));
  }

  @Test
  @DisplayName("Get all contact for the company failed")
  public void getAllContactForTheStudentFailed() {
    Mockito.when(contactDAO.getAllContactForStudent(1))
        .thenThrow(new FailureException("Failed to get all contact for the student"));

    assertThrows(FailureException.class, () ->
        contactUCC.getAllContactForStudent(1)
    );
  }
}
