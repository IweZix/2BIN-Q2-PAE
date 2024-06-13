package be.vinci.pae.business.ucc;

import static be.vinci.pae.utils.checker.VersionChecker.compareVersion;

import be.vinci.pae.business.dto.UserDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.business.impl.User;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.service.UserDAO;
import be.vinci.pae.utils.exception.DataAlreadyExistException;
import be.vinci.pae.utils.exception.DataInformationRequired;
import be.vinci.pae.utils.exception.InvalidUserOrPasswordException;
import be.vinci.pae.utils.exception.PasswordOrUsernameException;
import be.vinci.pae.utils.exception.RoleInvalidException;
import be.vinci.pae.utils.exception.UserNullException;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * This class implements the UserUCC interface.
 */
public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDAO;
  @Inject
  private Factory myFactory;
  @Inject
  private DALServices dalServices;

  @Override
  public UserDTO getUserByEmail(String email) {
    dalServices.initTransaction();
    try {
      return userDAO.getUserByEmail(email);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public UserDTO login(UserDTO userDTO) {
    dalServices.initTransaction();
    try {
      if (userDTO == null) {
        throw new DataInformationRequired("User cannot be null");
      }
      if (userDTO.getEmail() == null
          || userDTO.getEmail().isBlank()) {
        throw new DataInformationRequired("Email cannot be null or empty");
      }
      if (userDTO.getPassword() == null
          || userDTO.getPassword().isBlank()) {
        throw new PasswordOrUsernameException("Password cannot be null or empty");
      }
      userDTO.setEmail(userDTO.getEmail().toLowerCase());
      User user = (User) userDAO.getUserByEmail(userDTO.getEmail());
      if (user == null) {
        throw new InvalidUserOrPasswordException("User not found ! ");
      }

      if (!user.checkPassword(userDTO.getPassword())) {
        throw new PasswordOrUsernameException("Password invalid");
      }

      return user;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public UserDTO register(UserDTO userDTO) {
    dalServices.initTransaction();
    try {
      if (userDTO == null) {
        throw new DataInformationRequired("User cannot be null");
      }
      userDTO.setEmail(userDTO.getEmail().toLowerCase());
      User user = (User) userDAO.getUserByEmail(userDTO.getEmail());

      if (user != null) {
        throw new DataAlreadyExistException("User already exists ! ");
      }

      user = (User) myFactory.getUser();

      if (!user.isEmailValid(userDTO.getEmail())) {
        throw new InvalidUserOrPasswordException("Email or password invalid");
      }

      if (!user.isRoleValid(userDTO.getUtilisateurType())) {
        throw new RoleInvalidException("Role invalid");
      }

      user.setEmail(userDTO.getEmail().toLowerCase());
      user.setFirstName(userDTO.getFirstName());
      user.setLastName(userDTO.getLastName());
      user.setPhone(userDTO.getPhone());
      user.setUtilisateurType(userDTO.getUtilisateurType());
      user.setPassword(user.hashPassword(userDTO.getPassword()));
      user.setVersion(1);

      Date registrationDate = new Date(System.currentTimeMillis());
      String academicYear;
      if (registrationDate.getMonth() >= 9) {
        academicYear = LocalDate.now().getYear() + "-" + LocalDate.now().plusYears(1).getYear();
      } else {
        academicYear = LocalDate.now().minusYears(1).getYear() + "-" + LocalDate.now().getYear();
      }

      user.setAcademicYear(academicYear);
      user.setRegistrationDate(registrationDate);

      userDAO.register(user);

      return user;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public UserDTO update(UserDTO userDTO) {
    dalServices.initTransaction();
    try {
      if (userDTO == null) {
        throw new DataInformationRequired("User cannot be null");
      }
      userDTO.setEmail(userDTO.getEmail().toLowerCase());
      User user = (User) userDAO.getUserByEmail(userDTO.getEmail());
      if (user == null) {
        throw new InvalidUserOrPasswordException("User not found ! ");
      }
      compareVersion(user.getVersion(), userDTO.getVersion());
      if (userDTO.getFirstName() != null) {
        user.setFirstName(userDTO.getFirstName());
      }
      if (userDTO.getLastName() != null) {
        user.setLastName(userDTO.getLastName());
      }
      if (userDTO.getPhone() != null) {
        user.setPhone(userDTO.getPhone());
      }
      if (userDTO.getPassword() != null && userDTO.getNewPassword() != null && userDTO
          .getNewPasswordConfirmation() != null) {
        if (!user.checkPassword(userDTO.getPassword())) {
          throw new PasswordOrUsernameException("Password invalid");
        }
        if (!userDTO.getNewPassword().equals(userDTO.getNewPasswordConfirmation())) {
          throw new PasswordOrUsernameException("Passwords do not match");
        }
        user.setPassword(user.hashPassword(userDTO.getNewPassword()));
      }
      userDAO.update(user);
      return user;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public List<UserDTO> listAllUser() {
    dalServices.initTransaction();
    try {
      return userDAO.listAllUser();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public UserDTO getUserById(int studentId) {
    dalServices.initTransaction();
    try {
      User user = (User) userDAO.getUserById(studentId);

      if (user == null) {
        throw new UserNullException("User not found ! ");
      }

      return user;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }
}
