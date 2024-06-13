package be.vinci.pae.business.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.dto.InternshipSupervisorDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.dal.service.CompanyDAO;
import be.vinci.pae.dal.service.InternshipSupervisorDAO;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.exception.DataAlreadyExistException;
import be.vinci.pae.utils.exception.DataInformationRequired;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InternshipSupervisorUCCImplTest {

  private final ServiceLocator sl = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
  private Factory myFactory;

  private InternshipSupervisorUCC internshipSupervisorUCC;

  private InternshipSupervisorDTO internshipSupervisorDTO;
  private InternshipSupervisorDTO internshipSupervisorDTO2;
  private InternshipSupervisorDAO internshipSupervisorDAO;

  @BeforeAll
  void setup() {
    Config.load("dev.properties");
    myFactory = sl.getService(Factory.class);
    internshipSupervisorUCC = sl.getService(InternshipSupervisorUCC.class);
    internshipSupervisorDAO = sl.getService(InternshipSupervisorDAO.class);
    internshipSupervisorDTO = sl.getService(InternshipSupervisorDTO.class);
    internshipSupervisorDTO2 = sl.getService(InternshipSupervisorDTO.class);
  }

  @BeforeEach
  void setUpBeforeEach() {
    Mockito.reset(internshipSupervisorDAO);
    internshipSupervisorDTO = myFactory.getInternshipSupervisor();
    internshipSupervisorDTO.setId(1);
    internshipSupervisorDTO.setLastname("Test");
    internshipSupervisorDTO.setFirstname("Testing");
    internshipSupervisorDTO.setEmail("testing.test@gmail.com");
    internshipSupervisorDTO.setPhone("123456789");
    internshipSupervisorDTO.setCompany(1);

    internshipSupervisorDTO2 = myFactory.getInternshipSupervisor();
    internshipSupervisorDTO2.setId(2);
    internshipSupervisorDTO2.setLastname("Test2");
    internshipSupervisorDTO2.setFirstname("Testing2");
    internshipSupervisorDTO2.setEmail("testing2.test2@gmail.com");
    internshipSupervisorDTO2.setPhone("223456789");
    internshipSupervisorDTO2.setCompany(1);


  }

  @Test
  @DisplayName("Test create InternshipSupervisor")
  void testCreateInternshipSupervisor() {

    Mockito.when(internshipSupervisorDAO.create(internshipSupervisorDTO))
        .thenReturn(internshipSupervisorDTO);
    assertEquals(internshipSupervisorDTO, internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with null lastname")
  void testCreateInternshipSupervisorWithNullLastname() {
    internshipSupervisorDTO.setLastname(null);

    assertThrows(DataInformationRequired.class, () -> internshipSupervisorUCC
        .create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with null firstname")
  void testCreateInternshipSupervisorWithNullFirstname() {
    internshipSupervisorDTO.setFirstname(null);

    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with null email")
  void testCreateInternshipSupervisorWithNullEmail() {
    internshipSupervisorDTO.setEmail(null);

    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with null phone")
  void testCreateInternshipSupervisorWithNullPhone() {
    internshipSupervisorDTO.setPhone(null);

    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with blanck LastName")
  void testCreateInternshipSupervisorWithBlankLastName() {
    internshipSupervisorDTO.setLastname("");

    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with blanck FirstName")
  void testCreateInternshipSupervisorWithBlankFirstName() {
    internshipSupervisorDTO.setFirstname("");

    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with blanck Email")
  void testCreateInternshipSupervisorWithBlankEmail() {

    internshipSupervisorDTO.setEmail("");

    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with blanck Phone")
  void testCreateInternshipSupervisorWithBlankPhone() {
    internshipSupervisorDTO.setPhone("");
    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with < 0  Company")
  void testCreateInternshipSupervisorWithBlankCompany() {

    internshipSupervisorDTO.setCompany(-1);
    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with Invalid Email without @")
  void testCreateInternshipSupervisorWithInvalidEmail() {

    internshipSupervisorDTO.setEmail("test.be");

    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }


  @Test
  @DisplayName("Test create InternshipSupervisor with Invalid Email without .2caracteres")
  void testCreateInternshipSupervisorWithInvalidEmail2() {
    internshipSupervisorDTO.setEmail("test@me.b");

    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with Invalid Email without caractere before @")
  void testCreateInternshipSupervisorWithInvalidEmail3() {
    internshipSupervisorDTO.setEmail("@me.be");

    assertThrows(DataInformationRequired.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with Email Already exist")
  void testCreateInternshipSupervisorWithEmailAlreadyExist() {

    Mockito.when(internshipSupervisorDAO
            .getInternshipSupByEmail(internshipSupervisorDTO.getEmail()))
            .thenReturn(internshipSupervisorDTO);
    assertThrows(DataAlreadyExistException.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with InternshipSupervisor Already exist")
  void testCreateInternshipSupervisorWithInternshipSupervisorAlreadyExist() {
    Mockito.when(internshipSupervisorDAO.getInternshipSupervisor(internshipSupervisorDTO))
        .thenReturn(internshipSupervisorDTO);
    assertThrows(DataAlreadyExistException.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("get all InternshipSupervisors for a company ")
  void testGetAllInternshipSupervisors() {

    List<InternshipSupervisorDTO> list = new ArrayList();
    list.add(internshipSupervisorDTO);
    list.add(internshipSupervisorDTO2);
    Mockito.when(internshipSupervisorDAO.getAllInternshipSupervisorByCompanyId(1))
        .thenReturn(list);
    assertEquals(list, internshipSupervisorUCC.getAllInternshipSupervisors(1));
  }

  @Test
  @DisplayName("getAllInternshipSupervisors Failed")
  void testGetAllInternshipSupervisorsFailed() {
    Mockito.when(internshipSupervisorDAO.getAllInternshipSupervisorByCompanyId(1))
        .thenThrow(new RuntimeException("Failed to get all internship supervisors"));
    assertThrows(RuntimeException.class, ()
        -> internshipSupervisorUCC.getAllInternshipSupervisors(1));
  }

  @Test
  @DisplayName("Test getInternshipSupervisor")
  void testGetInternshipSupervisor() {

    Mockito.when(internshipSupervisorDAO.getInternshipSupervisor(internshipSupervisorDTO))
        .thenReturn(internshipSupervisorDTO);
    assertEquals(internshipSupervisorDTO,
        internshipSupervisorUCC.getInternshipSupervisor(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test getInternshipSupervisor Failed")
  void testGetInternshipSupervisorFailed() {

    Mockito.when(internshipSupervisorDAO.getInternshipSupervisor(internshipSupervisorDTO))
        .thenThrow(new RuntimeException("Failed to get internship supervisor"));
    assertThrows(RuntimeException.class, ()
        -> internshipSupervisorUCC.getInternshipSupervisor(internshipSupervisorDTO));
  }

  @Test
  @DisplayName("Test create InternshipSupervisor with Phone Already exist")
  void testCreateInternshipSupervisorWithPhoneAlreadyExist() {

    Mockito.when(internshipSupervisorDAO
            .getInternshipPhone(internshipSupervisorDTO.getPhone()))
            .thenReturn(internshipSupervisorDTO);
    assertThrows(DataAlreadyExistException.class, ()
        -> internshipSupervisorUCC.create(internshipSupervisorDTO));
  }

}
