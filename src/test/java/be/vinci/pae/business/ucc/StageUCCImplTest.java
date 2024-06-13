package be.vinci.pae.business.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.dto.InternshipSupervisorDTO;
import be.vinci.pae.business.dto.StageDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.dal.service.CompanyDAO;
import be.vinci.pae.dal.service.InternshipSupervisorDAO;
import be.vinci.pae.dal.service.StageDAO;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.exception.DataInformationRequired;
import be.vinci.pae.utils.exception.DataNotExistsException;
import be.vinci.pae.utils.exception.FailureException;
import be.vinci.pae.utils.exception.FatalException;
import be.vinci.pae.utils.exception.InternshipProjectIncorrectException;
import java.util.Calendar;
import java.util.Date;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

/**
 * This class tests the StageUCCImpl class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StageUCCImplTest {

  private final ServiceLocator sl = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
  private Factory myFactory;
  private StageUCC stageUCC;
  private StageDAO stageDAO;
  private StageDTO stageDTO;
  private StageDTO stageDTOToUpdate;
  private CompanyDTO companyDTO;
  private CompanyDAO companyDAO;
  private InternshipSupervisorDTO internshipSupervisorDTO;
  private InternshipSupervisorDAO internshipSupervisorDAO;

  @BeforeAll
  void setUp() {
    Config.load("dev.properties");
    myFactory = sl.getService(Factory.class);
    stageUCC = sl.getService(StageUCC.class);
    stageDAO = sl.getService(StageDAO.class);
    stageDTO = sl.getService(StageDTO.class);
    stageDTOToUpdate = sl.getService(StageDTO.class);
    companyDTO = sl.getService(CompanyDTO.class);
    companyDAO = sl.getService(CompanyDAO.class);
    internshipSupervisorDTO = sl.getService(InternshipSupervisorDTO.class);
    internshipSupervisorDAO = sl.getService(InternshipSupervisorDAO.class);
  }

  @BeforeEach
  void setUpBeforeEach() {
    Mockito.reset(stageDAO);
    stageDTO = myFactory.getStage();
    stageDTO.setStageId(1);
    stageDTO.setSignatureDate(new Date(2021, Calendar.AUGUST, 1));
    stageDTO.setInternshipProject(null);
    stageDTO.setStudentId(1);
    stageDTO.setTradeNameCompany("Test_Testing");
    stageDTO.setInternshipSupervisorLastName("test");
    stageDTO.setInternshipSupervisorFirstName("testing");
    stageDTO.setInternshipSupervisorId(1);
    stageDTO.setVersion(1);

    stageDTOToUpdate = myFactory.getStage();
    stageDTOToUpdate.setStageId(1);
    stageDTOToUpdate.setSignatureDate(new Date(2021, Calendar.AUGUST, 1));
    stageDTOToUpdate.setInternshipProject("Mobile");
    stageDTOToUpdate.setStudentId(1);
    stageDTOToUpdate.setTradeNameCompany("Test_Testing");
    stageDTOToUpdate.setInternshipSupervisorLastName("test");
    stageDTOToUpdate.setInternshipSupervisorFirstName("testing");
    stageDTOToUpdate.setInternshipSupervisorId(1);
    stageDTOToUpdate.setVersion(1);

    companyDTO = myFactory.getCompany();
    companyDTO.setCompanyId(1);
    companyDTO.setTradeNameDesignation("Test_Testing");

    internshipSupervisorDTO = myFactory.getInternshipSupervisor();
    internshipSupervisorDTO.setId(1);
    internshipSupervisorDTO.setFirstname("testing");
    internshipSupervisorDTO.setLastname("test");
    internshipSupervisorDTO.setCompany(1);
    internshipSupervisorDTO.setEmail("test.testing@gmail.com");
    internshipSupervisorDTO.setPhone("0477777777");

  }

  /**
   * Test getStageById.
   */
  @Test
  @DisplayName("Test getStageById successfully")
  void testGetStageById() {
    Mockito.when(stageDAO.getStageById(1)).thenReturn(stageDTO);
    assertEquals(stageDTO, stageUCC.getStageById(1));
  }

  @Test
  @DisplayName("Test getStageById failed because stage not found")
  void testGetStageByIdNotFound() {
    Mockito.when(stageDAO.getStageById(-1)).thenReturn(null);
    assertThrows(DataNotExistsException.class, () -> stageUCC.getStageById(1));
  }

  /**
   * Test createStage.
   */
  @Test
  @DisplayName("Test createStage successfully")
  void testCreateStage() {
    Mockito.when(companyDAO.getCompanyByTradeNameDesignation("Test_Testing"))
        .thenReturn(companyDTO);
    Mockito.when(internshipSupervisorDAO.getInternshipSupervisorByName(
            "test", "testing", companyDTO.getCompanyId()))
        .thenReturn(internshipSupervisorDTO);

    StageDTO stage = stageUCC.createStage(stageDTO);

    assertEquals("3920-3921", stageDTO.getAcademicYear());
  }

  @Test
  @DisplayName("Test createStage failed because company not found")
  void testCreateStageWithNullCompany() {
    Mockito.when(companyDAO.getCompanyByTradeNameDesignation("Test_Testing"))
        .thenReturn(null);

    assertThrows(Exception.class, () -> stageUCC.createStage(stageDTO));
  }

  @Test
  @DisplayName("Test createStage successfully with date before August")
  void testCreateStageWithDateBeforeAugust() {
    Mockito.when(companyDAO.getCompanyByTradeNameDesignation("Test_Testing"))
        .thenReturn(companyDTO);
    Mockito.when(internshipSupervisorDAO.getInternshipSupervisorByName(
            "test", "testing", companyDTO.getCompanyId()))
        .thenReturn(internshipSupervisorDTO);

    StageDTO stage = stageUCC.createStage(stageDTO);

    assertEquals("3920-3921", stage.getAcademicYear());
  }

  @Test
  @DisplayName("Test createStage successfully with date after August")
  void testCreateStageWithDateAfterAugust() {
    stageDTO.setSignatureDate(new Date(2021, Calendar.SEPTEMBER, 2));

    Mockito.when(companyDAO.getCompanyByTradeNameDesignation("Test_Testing"))
        .thenReturn(companyDTO);
    Mockito.when(internshipSupervisorDAO.getInternshipSupervisorByName(
            "test", "testing", companyDTO.getCompanyId()))
        .thenReturn(internshipSupervisorDTO);

    stageDTO.setSignatureDate(new Date(2021, Calendar.SEPTEMBER, 2));
    StageDTO stage = stageUCC.createStage(stageDTO);

    assertEquals("3921-3922", stage.getAcademicYear());
  }

  @Test
  @DisplayName("Test createStage failed because studentId is negative")
  void testCreateStageWithNegativeStudentId() {
    stageDTO.setStudentId(-1);

    assertThrows(DataInformationRequired.class, () -> stageUCC.createStage(stageDTO));
  }

  @Test
  @DisplayName("Test createStage failed because signatureDate is null")
  void testCreateStageWithSignatureDateNull() {
    stageDTO.setSignatureDate(null);

    assertThrows(DataInformationRequired.class, () -> stageUCC.createStage(stageDTO));
  }

  @Test
  @DisplayName("Test createStage failed because tradeNameCompany is null")
  void testCreateStageWithTradeNameCompanyNull() {
    stageDTO.setTradeNameCompany(null);

    assertThrows(DataInformationRequired.class, () -> stageUCC.createStage(stageDTO));
  }

  @Test
  @DisplayName("Test createStage failed because tradeNameCompany is blank")
  void testCreateStageWithTradeNameCompanyBlank() {
    stageDTO.setTradeNameCompany("");

    assertThrows(DataInformationRequired.class, () -> stageUCC.createStage(stageDTO));
  }

  @Test
  @DisplayName("Test createStage failed because internshipSupervisorLastName is null")
  void testCreateStageWithInternshipSupervisorLastNameNull() {
    stageDTO.setInternshipSupervisorLastName(null);

    assertThrows(DataInformationRequired.class, () -> stageUCC.createStage(stageDTO));
  }

  @Test
  @DisplayName("Test createStage failed because internshipSupervisorLastName is blank")
  void testCreateStageWithInternshipSupervisorLastNameBlank() {
    stageDTO.setInternshipSupervisorLastName("");

    assertThrows(DataInformationRequired.class, () -> stageUCC.createStage(stageDTO));
  }

  @Test
  @DisplayName("Test createStage failed because internshipSupervisorFirstName is null")
  void testCreateStageWithInternshipSupervisorFirstNameNull() {
    stageDTO.setInternshipSupervisorFirstName(null);

    assertThrows(DataInformationRequired.class, () -> stageUCC.createStage(stageDTO));
  }

  @Test
  @DisplayName("Test createStage failed because internshipSupervisorFirstName is blank")
  void testCreateStageWithInternshipSupervisorFirstNameBlank() {
    stageDTO.setInternshipSupervisorFirstName("");

    assertThrows(DataInformationRequired.class, () -> stageUCC.createStage(stageDTO));
  }

  /**
   * Test getAcceptedStage.
   */
  @Test
  @DisplayName("Test getAcceptedStage successfully")
  void testGetAcceptedStageSuccessfully() {
    Mockito.when(stageDAO.getAcceptedStage(1)).thenReturn(stageDTO);
    assertEquals(stageDTO, stageUCC.getAcceptedStage(1));
  }

  @Test
  @DisplayName("Test getAcceptedStage failed")
  void testGetAcceptedStageFailed() {
    Mockito.when(stageDAO.getAcceptedStage(1))
        .thenThrow(new FatalException(new Exception("Failed")));

    assertThrows(FatalException.class,  () ->
        stageUCC.getAcceptedStage(1));
  }

  /**
   * Test updateInternshipProject.
   */
  @Test
  @DisplayName("Test updateInternshipProject successfully")
  void testUpdateInternshipProjectSuccefully() {
    Mockito.when(stageDAO.getStageById(stageDTOToUpdate.getStageId()))
        .thenReturn(stageDTO);
    Mockito.when(stageDAO.updateInternshipProject(stageDTOToUpdate))
        .thenReturn(true);
    assertTrue(stageUCC.updateInternshipProject(stageDTOToUpdate));
  }

  @Test
  @DisplayName("Test updateInternshipProject failed")
  void testUpdateInternshipProjectFailed() {
    Mockito.when(stageDAO.getStageById(stageDTOToUpdate.getStageId()))
        .thenReturn(stageDTO);
    Mockito.when(stageDAO.updateInternshipProject(stageDTOToUpdate))
        .thenThrow(new FailureException("Failed"));

    assertThrows(FailureException.class, () -> stageUCC.updateInternshipProject(stageDTOToUpdate));
  }

  @Test
  @DisplayName("Test updateInternshipProject failed because internshipProject is null")
  void testUpdateInternshipProjectWithInternshipProjectNull() {
    stageDTOToUpdate.setInternshipProject(null);

    assertThrows(InternshipProjectIncorrectException.class,
        () -> stageUCC.updateInternshipProject(stageDTOToUpdate));
  }

  @Test
  @DisplayName("Test updateInternshipProject failed because internshipProject is blank")
  void testUpdateInternshipProjectWithInternshipProjectBlank() {
    stageDTOToUpdate.setInternshipProject("");

    assertThrows(InternshipProjectIncorrectException.class,
        () -> stageUCC.updateInternshipProject(stageDTOToUpdate));
  }
}
