package be.vinci.pae.business.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.dto.ContactDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.dal.service.CompanyDAO;
import be.vinci.pae.dal.service.ContactDAO;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.exception.AlreadyBlacklisted;
import be.vinci.pae.utils.exception.DataAlreadyExistException;
import be.vinci.pae.utils.exception.DataNotExistsException;
import be.vinci.pae.utils.exception.FailureException;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

/**
 * This class tests the CompanyUCCImpl class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompanyUCCImplTest {

  private final ServiceLocator sl = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
  private Factory myFactory;
  private CompanyUCC companyUCC;
  private CompanyDTO companyDTO;
  private CompanyDAO companyDAO;
  private ContactDTO contactDTO;
  private ContactDAO contactDAO;

  @BeforeAll
  void setUpBeforeAll() {
    Config.load("dev.properties");
    myFactory = sl.getService(Factory.class);
    companyUCC = sl.getService(CompanyUCC.class);
    companyDTO = sl.getService(CompanyDTO.class);
    companyDAO = sl.getService(CompanyDAO.class);
    contactDTO = sl.getService(ContactDTO.class);
    contactDAO = sl.getService(ContactDAO.class);
  }

  @BeforeEach
  void setUpBeforeEach() {
    Mockito.reset(companyDAO);
    companyDTO = myFactory.getCompany();
    companyDTO.setCompanyId(1);
    companyDTO.setTradeName("test");
    companyDTO.setDesignation("testing");
    companyDTO.setTradeNameDesignation("test_testing");
    companyDTO.setAddress("Rue du test");
    companyDTO.setPhoneNumber("123456789");
    companyDTO.setEmail("test@test.com");
    companyDTO.setVersion(1);
    companyDTO.setBlacklist(false);

    contactDTO = myFactory.getContact();
    contactDTO.setStudentId(1);
    contactDTO.setCompanyId(1);
    contactDTO.setVersion(1);
  }

  /**
   * Test CreateCompany.
   */
  @Test
  @DisplayName("Test createCompany successfully")
  void successfullyCreateCompanyDesignationBlank() {
    Mockito.when(companyDAO.getCompanyByTradeNameDesignation("test_testing"))
        .thenReturn(null);

    assertEquals(companyDTO, companyUCC.createCompany(companyDTO));
  }

  @Test
  @DisplayName("Test createCompany successfully with designation null")
  void successfullyCreateCompanyDesignationNotBlank() {
    companyDTO.setDesignation("");

    Mockito.when(companyDAO.getCompanyByTradeNameDesignation("test_testing"))
        .thenReturn(null);

    assertEquals(companyDTO, companyUCC.createCompany(companyDTO));
  }

  @Test
  @DisplayName("Test createCompany failed because company already exist")
  void failedCreateCompany() {
    Mockito.when(companyDAO.getCompanyByTradeNameDesignation("test_testing"))
        .thenReturn(companyDTO);

    assertThrows(DataAlreadyExistException.class,
        () -> companyUCC.createCompany(companyDTO));
  }


  /**
   * Test GetAllCompany.
   */
  @Test
  @DisplayName("Test getAllCompany successfully")
  void successfullGetAllCompany() {
    List<CompanyDTO> comp = new ArrayList<>();
    comp.add(companyDTO);

    Mockito.when(companyDAO.getAllCompany()).thenReturn(comp);

    List<CompanyDTO> result = companyUCC.getAllCompany();

    assertEquals(comp, result);
  }

  @Test
  @DisplayName("Test getAllCompany successfully with tradeNameDesignation null")
  void testGetAllCompanyTradeNameDesignationNull() {
    companyDTO.setTradeNameDesignation(null);

    List<CompanyDTO> companies = new ArrayList<>();
    companies.add(companyDTO);

    Mockito.when(companyDAO.getAllCompany()).thenReturn(companies);

    List<CompanyDTO> result = companyUCC.getAllCompany();

    assertEquals("test_testing", result.get(0).getTradeNameDesignation());
  }

  @Test
  @DisplayName("Test getAllCompany successfully with tradeNameDesignation blank")
  void testGetAllCompanyTradeNameDesignationBlank() {
    companyDTO.setTradeNameDesignation("");

    List<CompanyDTO> companies = new ArrayList<>();
    companies.add(companyDTO);

    Mockito.when(companyDAO.getAllCompany()).thenReturn(companies);

    List<CompanyDTO> result = companyUCC.getAllCompany();

    assertEquals("test_testing", result.get(0).getTradeNameDesignation());
  }

  @Test
  @DisplayName("Test getAllCompany successfully with tradeNameDesignation not blank")
  void testGetAllCompanyTradeNameDesignationNotBlank() {
    List<CompanyDTO> companies = new ArrayList<>();
    companies.add(companyDTO);

    Mockito.when(companyDAO.getAllCompany()).thenReturn(companies);

    List<CompanyDTO> result = companyUCC.getAllCompany();

    assertEquals("test_testing", result.get(0).getTradeNameDesignation());
  }

  @Test
  @DisplayName("Test getAllCompany successfully with designation null")
  void testGetAllCompanyDesignationNull() {
    companyDTO.setDesignation(null);
    companyDTO.setTradeNameDesignation(null);

    List<CompanyDTO> companies = new ArrayList<>();
    companies.add(companyDTO);

    Mockito.when(companyDAO.getAllCompany()).thenReturn(companies);

    List<CompanyDTO> result = companyUCC.getAllCompany();

    assertEquals("test", result.get(0).getTradeNameDesignation());
  }

  @Test
  @DisplayName("Test getAllCompany successfully with designation blank")
  void testGetAllCompanyDesignationBlank() {
    companyDTO.setDesignation("");
    companyDTO.setTradeNameDesignation(null);

    List<CompanyDTO> companies = new ArrayList<>();
    companies.add(companyDTO);

    Mockito.when(companyDAO.getAllCompany()).thenReturn(companies);

    List<CompanyDTO> result = companyUCC.getAllCompany();

    assertEquals("test", result.get(0).getTradeNameDesignation());
  }

  @Test
  @DisplayName("Test getAllCompany failed")
  void failedGetAllCompany() {
    Mockito.when(companyDAO.getAllCompany())
        .thenThrow(IllegalArgumentException.class);

    assertThrows(IllegalArgumentException.class,
        () -> companyUCC.getAllCompany());
  }


  /**
   * Test getAllNotBlacklistedCompany.
   */
  @Test
  @DisplayName("Test getAllNotBlacklistedCompany successfully")
  void getAllNotBlacklistedCompanySuccessful() {
    List<CompanyDTO> comp = new ArrayList<>();
    comp.add(companyDTO);

    Mockito.when(companyDAO.getAllCompanyNotBlacklisted()).thenReturn(comp);
    assertEquals(comp, companyUCC.getAllNotBlacklistedCompany());
  }

  @Test
  @DisplayName("Test getAllNotBlacklistedCompany failed")
  void failedGetAllNotBlacklistedCompany() {
    Mockito.when(companyDAO.getAllCompanyNotBlacklisted())
        .thenThrow(IllegalArgumentException.class);

    assertThrows(IllegalArgumentException.class,
        () -> companyUCC.getAllNotBlacklistedCompany());
  }


  /**
   * Test getAllCompanyWithStages.
   */
  @Test
  @DisplayName("Test GetAllCompanyWithStages successfully")
  void successfullGetAllCompanyWithStages() {
    List<CompanyDTO> comp = new ArrayList<>();
    comp.add(companyDTO);

    Mockito.when(companyDAO.getAllCompanyWithStages()).thenReturn(comp);

    assertEquals(comp, companyUCC.getAllCompanyWithStages());
  }

  @Test
  @DisplayName("Test getAllCompanyWithStages failed")
  void failedGetAllCompanyWithStages() {
    Mockito.when(companyDAO.getAllCompanyWithStages())
        .thenThrow(IllegalArgumentException.class);
    assertThrows(IllegalArgumentException.class,
        () -> companyUCC.getAllCompanyWithStages());
  }


  /**
   * Test blacklistCompany.
   */
  @Test
  @DisplayName("Test blacklistCompany successfully")
  void testBlacklistCompanySuccess() {
    Mockito.when(companyDAO.getCompanyId(1)).thenReturn(companyDTO);
    Mockito.when(companyDAO.blacklistCompany(companyDTO)).thenReturn(companyDTO);

    assertEquals(companyDTO, companyUCC.blacklistCompany(companyDTO));
  }

  @Test
  @DisplayName("Test blacklistCompany failed because company not found")
  void testBlacklistCompanyCompanyNotFound() {
    Mockito.when(companyDAO.getCompanyId(1)).thenReturn(null);

    assertThrows(DataNotExistsException.class, () ->
        companyUCC.blacklistCompany(companyDTO));
  }

  @Test
  @DisplayName("Test blacklistCompany failed because company already blacklisted")
  void testBlacklistCompanyCompanyAlreadyBlacklisted() {
    companyDTO.setBlacklist(true);
    companyDTO.setMotivationBlacklist("Test");

    Mockito.when(companyDAO.getCompanyId(1)).thenReturn(companyDTO);

    assertThrows(AlreadyBlacklisted.class, () ->
        companyUCC.blacklistCompany(companyDTO));
  }

  @Test
  @DisplayName("Test blacklistCompany successfully with a contact 'pris'")
  void testBlacklistCompanyContactPris() {
    contactDTO.setContactStatus("pris");

    List<ContactDTO> contacts = new ArrayList<>();
    contacts.add(contactDTO);

    Mockito.when(companyDAO.getCompanyId(1)).thenReturn(companyDTO);
    Mockito.when(contactDAO.getAllContactForTheCompany(1)).thenReturn(contacts);

    companyUCC.blacklistCompany(companyDTO);

    assertEquals("suspendu", contactDTO.getContactStatus());
  }

  @Test
  @DisplayName("Test blacklistCompany successfully with a contact 'initie'")
  void testBlacklistCompanyContactInitie() {
    contactDTO.setContactStatus("initie");

    List<ContactDTO> contacts = new ArrayList<>();
    contacts.add(contactDTO);

    Mockito.when(companyDAO.getCompanyId(1)).thenReturn(companyDTO);
    Mockito.when(contactDAO.getAllContactForTheCompany(1)).thenReturn(contacts);

    companyUCC.blacklistCompany(companyDTO);

    assertEquals("suspendu", contactDTO.getContactStatus());
  }



  /**
   * Test getCompanyById.
   */
  @Test
  @DisplayName("Test getCompanyById successfully")
  void getCompanyByIdSuccess() {
    Mockito.when(companyDAO.getCompanyId(1)).thenReturn(companyDTO);

    assertEquals(companyDTO, companyUCC.getCompanyById(1));
  }

  @Test
  @DisplayName("Test getCompanyById Failed")
  void getCompanyByIdFailed() {
    Mockito.when(companyDAO.getCompanyId(1))
        .thenThrow(new FailureException("Failed to getCompanyId"));

    assertThrows(FailureException.class,  () ->
        companyUCC.getCompanyById(1));
  }

}