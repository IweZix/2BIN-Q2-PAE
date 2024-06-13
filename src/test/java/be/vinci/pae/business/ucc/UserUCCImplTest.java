package be.vinci.pae.business.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.vinci.pae.business.dto.UserDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.business.impl.User;
import be.vinci.pae.dal.service.UserDAO;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.exception.DataAlreadyExistException;
import be.vinci.pae.utils.exception.DataInformationRequired;
import be.vinci.pae.utils.exception.FailureException;
import be.vinci.pae.utils.exception.FatalException;
import be.vinci.pae.utils.exception.InvalidUserOrPasswordException;
import be.vinci.pae.utils.exception.PasswordOrUsernameException;
import be.vinci.pae.utils.exception.RoleInvalidException;
import be.vinci.pae.utils.exception.UserNullException;
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
class UserUCCImplTest {

  private Factory myFactory;
  private UserUCC userUCC;
  private UserDAO userDAO;
  private UserDTO user;
  private UserDTO userLogin;
  private UserDTO userRegister;
  private UserDTO userUpdate;
  private User hasher;

  @BeforeAll
  void setUp() {
    Config.load("dev.properties");
    ServiceLocator sl = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    myFactory = sl.getService(Factory.class);
    userUCC = sl.getService(UserUCC.class);
    userDAO = sl.getService(UserDAO.class);
  }

  @BeforeEach
  void init() {
    Mockito.reset(userDAO);

    hasher = (User) myFactory.getUser();
    hasher.setPassword(hasher.hashPassword("wrongPassword"));

    user = myFactory.getUser();
    user.setUserId(1);
    user.setEmail("admin.admin@vinci.be");
    user.setPassword(hasher.hashPassword("admin"));
    user.setFirstName("admin");
    user.setLastName("admin");
    user.setPhone("123456789");
    user.setUtilisateurType("professeur");
    user.setVersion(1);

    userLogin = myFactory.getUser();
    userLogin.setUserId(1);
    userLogin.setEmail("admin.admin@vinci.be");
    userLogin.setPassword("admin");

    userRegister = myFactory.getUser();
    userRegister.setFirstName("register");
    userRegister.setLastName("register");
    userRegister.setEmail("register.register@vinci.be");
    userRegister.setPhone("123456789");
    userRegister.setPassword(hasher.hashPassword("register"));
    userRegister.setUtilisateurType("professeur");
    userRegister.setVersion(1);

    userUpdate = myFactory.getUser();
    userUpdate.setUserId(2);
    userUpdate.setEmail("update.update@vinci.be");
    userUpdate.setPassword(hasher.hashPassword("update"));
    userUpdate.setFirstName("update");
    userUpdate.setLastName("update");
    userUpdate.setPhone("0499999999");
    userUpdate.setVersion(1);
  }

  /**
   * Test getUserByEmail.
   */
  @Test
  @DisplayName("Test getUserByEmail successfully")
  public void getUserByEmail() {
    Mockito.when(userDAO.getUserByEmail("admin.admin@vinci.be")).thenReturn(user);

    assertEquals(user, userUCC.getUserByEmail("admin.admin@vinci.be"));
  }

  @Test
  @DisplayName("Test getUserByEmail failed")
  public void getUserByEmailFailed() {
    Mockito.when(userDAO.getUserByEmail("admin.admin@vinci.be"))
        .thenThrow(new FailureException("failed"));

    assertThrows(FailureException.class, () ->
        userUCC.getUserByEmail("admin.admin@vinci.be")
    );
  }


  /**
   * Test login.
   */
  @Test
  @DisplayName("Test login successfully")
  public void successfulLogin() {
    Mockito.when(userDAO.getUserByEmail("admin.admin@vinci.be")).thenReturn(user);
    assertEquals(user.getEmail(), userUCC.login(userLogin).getEmail());
  }

  @Test
  @DisplayName("Test login failed")
  public void userDTONull() {
    assertThrows(DataInformationRequired.class,
        () -> userUCC.login(null)
    );
  }

  @Test
  @DisplayName("Test login failed because email is empty")
  public void emailEmpty() {
    user.setEmail("");
    assertThrows(DataInformationRequired.class,
        () -> userUCC.login(user)
    );
  }

  @Test
  @DisplayName("Test login failed because email is null")
  public void emailNull() {
    user.setEmail(null);
    assertThrows(DataInformationRequired.class,
        () -> userUCC.login(user)
    );
  }

  @Test
  @DisplayName("Test login failed because password is null")
  public void passwordNull() {
    user.setPassword(null);
    assertThrows(PasswordOrUsernameException.class,
        () -> userUCC.login(user)
    );
  }

  @Test
  @DisplayName("Test login failed because password is empty")
  public void passwordEmpty() {
    user.setPassword("");
    assertThrows(PasswordOrUsernameException.class,
        () -> userUCC.login(user)
    );
  }

  @Test
  @DisplayName("Test login failed because user not found")
  public void passwordIncorrect() {
    Mockito.when(userDAO.getUserByEmail("admin.admin@vinci.be")).thenReturn(user);
    userLogin.setPassword("wrongPassword");
    assertThrows(PasswordOrUsernameException.class,
        () -> userUCC.login(userLogin)
    );
  }

  @Test
  @DisplayName("Test login failed because email is incorrect")
  public void emailIncorrect() {
    Mockito.when(userDAO.getUserByEmail("admin.admin@vinci.be")).thenReturn(user);
    userLogin.setEmail("wrongEmail");
    assertThrows(InvalidUserOrPasswordException.class,
        () -> userUCC.login(userLogin)
    );
  }


  /**
   * Test register.
   */
  @Test
  @DisplayName("Test register successfully")
  public void successfulRegister() {
    assertEquals(user.getEmail(),
        userUCC.register(user).getEmail());
  }

  @Test
  @DisplayName("Test register failed because user already exist")
  public void registerUserAlreadyExist() {
    Mockito.when(userDAO.getUserByEmail(user.getEmail()))
        .thenReturn(user);

    assertThrows(DataAlreadyExistException.class,
        () -> userUCC.register(user));
  }

  @Test
  @DisplayName("Test register failed because user is null")
  public void registerWithNullUser() {
    assertThrows(DataInformationRequired.class,
        () -> userUCC.register(null));
  }

  @Test
  @DisplayName("Test register failed because email is invalid")
  public void registerWithInvalidEmail() {
    user.setEmail("invalid");

    assertThrows(InvalidUserOrPasswordException.class,
        () -> userUCC.register(user));
  }

  @Test
  @DisplayName("Test register failed because role is invalid")
  public void registerWithInvalidRole() {
    user.setUtilisateurType("invalid");

    assertThrows(RoleInvalidException.class,
        () -> userUCC.register(user));
  }


  /**
   * Test update.
   */
  @Test
  @DisplayName("Test update successfully")
  public void updateUser() {
    Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(userUpdate);

    assertEquals(userUCC.update(userUpdate), userUpdate);
  }

  @Test
  @DisplayName("Test update failed because user is null")
  public void updateUserWithNullUser() {
    assertThrows(DataInformationRequired.class,
        () -> userUCC.update(null));
  }

  @Test
  @DisplayName("Test update failed because user not found")
  public void updateUserNotFound() {
    Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(null);

    assertThrows(InvalidUserOrPasswordException.class,
        () -> userUCC.update(userUpdate));
  }

  @Test
  @DisplayName("Test update successfully without null informations")
  public void updateUserTestWithoutNullInformations() {
    userUpdate.setFirstName(null);
    userUpdate.setLastName(null);
    userUpdate.setPhone(null);

    Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(user);

    assertAll(
        () -> assertEquals(user.getFirstName(), userUCC.update(userUpdate).getFirstName()),
        () -> assertEquals(user.getLastName(), userUCC.update(userUpdate).getLastName()),
        () -> assertEquals(user.getEmail(), userUCC.update(userUpdate).getEmail())
    );
  }

  @Test
  @DisplayName("Test update successfully with new password")
  public void updateUserTestWithNewPassword() {
    Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(user);

    userUpdate.setPassword("admin");
    userUpdate.setNewPassword("newPassword");
    userUpdate.setNewPasswordConfirmation("newPassword");

    UserDTO userUpdated = userUCC.update(userUpdate);

    hasher.setPassword(userUpdated.getPassword());

    assertTrue(hasher.checkPassword("newPassword"));
  }

  @Test
  @DisplayName("Test update failed because password is incorrect")
  public void updateUserTestWithIncorrectPassword() {
    Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(user);

    userUpdate.setPassword("wrongPassword");
    userUpdate.setNewPassword("newPassword");
    userUpdate.setNewPasswordConfirmation("newPassword");

    assertThrows(PasswordOrUsernameException.class,
        () -> userUCC.update(userUpdate));
  }

  @Test
  @DisplayName("Test update failed because new password and confirmation are different")
  public void updateUserTestWithDifferentNewPasswordAndConfirmation() {
    Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(user);

    userUpdate.setPassword("admin");
    userUpdate.setNewPassword("newPassword");
    userUpdate.setNewPasswordConfirmation("differentPassword");

    assertThrows(PasswordOrUsernameException.class,
        () -> userUCC.update(userUpdate));
  }

  @Test
  @DisplayName("Test update failed because password is null")
  public void updateUserTestWithNullPassword() {
    Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(user);

    userUpdate.setPassword(null);
    userUpdate.setNewPassword("newPassword");
    userUpdate.setNewPasswordConfirmation("newPassword");

    UserDTO userUpdated = userUCC.update(userUpdate);

    hasher.setPassword(userUpdated.getPassword());

    assertTrue(hasher.checkPassword("admin"));
  }

  @Test
  @DisplayName("Test update failed because new password is null")
  public void updateUserTestWithNullNewPassword() {
    Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(user);

    userUpdate.setPassword("admin");
    userUpdate.setNewPassword(null);
    userUpdate.setNewPasswordConfirmation("newPassword");

    UserDTO userUpdated = userUCC.update(userUpdate);

    hasher.setPassword(userUpdated.getPassword());

    assertTrue(hasher.checkPassword("admin"));
  }

  @Test
  @DisplayName("Test update failed because new password confirmation is null")
  public void updateUserTestWithNullNewPasswordConfirmation() {
    Mockito.when(userDAO.getUserByEmail(userUpdate.getEmail())).thenReturn(user);

    userUpdate.setPassword("admin");
    userUpdate.setNewPassword("newPassword");
    userUpdate.setNewPasswordConfirmation(null);

    UserDTO userUpdated = userUCC.update(userUpdate);

    hasher.setPassword(userUpdated.getPassword());

    assertTrue(hasher.checkPassword("admin"));
  }

  /**
   * Test listAllUsers.
   */
  @Test
  @DisplayName("Test listAllUsers")
  public void listAllUsers() {
    List<UserDTO> list = List.of(user, userRegister, userUpdate);
    Mockito.when(userDAO.listAllUser()).thenReturn(list);
    assertEquals(list, userUCC.listAllUser());
  }

  @Test
  @DisplayName("Test listAllUsers catch")
  public void listAllUsersCatch() {
    Mockito.when(userDAO.listAllUser()).thenThrow(new IllegalArgumentException());
    assertThrows(IllegalArgumentException.class, () -> userUCC.listAllUser());
  }

  /**
   * Test getUserById.
   */
  @Test
  @DisplayName("Test getting user by valid ID")
  public void getUserByIdValidId() {
    Mockito.when(userDAO.getUserById(1)).thenReturn(user);

    assertEquals(user, userUCC.getUserById(1));
  }

  @Test
  @DisplayName("Test getting user by invalid ID")
  public void getUserByIdInvalidId() {
    Mockito.when(userDAO.getUserById(0)).thenThrow(FatalException.class);

    assertThrows(FatalException.class, () -> userUCC.getUserById(0));
  }

  @Test
  @DisplayName("Test getUserBId with user is null")
  public void getUserByIdWithNullUser() {
    Mockito.when(userDAO.getUserById(1)).thenReturn(null);
    assertThrows(UserNullException.class,
        () -> userUCC.getUserById(1));
  }
}
