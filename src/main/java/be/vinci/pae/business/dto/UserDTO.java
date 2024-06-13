package be.vinci.pae.business.dto;

import be.vinci.pae.business.impl.UserImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

/**
 * UserDTO class.
 */
@JsonDeserialize(as = UserImpl.class)
public interface UserDTO {

  /**
   * get the user's id.
   *
   * @return the user's id
   */
  int getUserId();

  /**
   * set the user's id.
   *
   * @param userId the new user's id
   */
  void setUserId(int userId);

  /**
   * get the user's email.
   *
   * @return user email
   */
  String getEmail();

  /**
   * change the user's email by the one passed in parameter.
   *
   * @param email the new email
   */
  void setEmail(String email);

  /**
   * get the user's lastname.
   *
   * @return user lastname
   */
  String getLastName();

  /**
   * change the user's lastname by the one passed in parameter.
   *
   * @param lastName the new lastName
   */
  void setLastName(String lastName);

  /**
   * get the user's firstName.
   *
   * @return user firstname
   */
  String getFirstName();

  /**
   * change the user's firstname by the one passed in parameter.
   *
   * @param firstName the new user's firstname
   */
  void setFirstName(String firstName);

  /**
   * get the user's telephone number.
   *
   * @return user telephone number
   */
  String getPhone();

  /**
   * change the user's telephone number by the one passed in parameter.
   *
   * @param phone the new user's telephone number
   */
  void setPhone(String phone);

  /**
   * get the user's registration Date.
   *
   * @return user's registration Date
   */
  Date getRegistrationDate();

  /**
   * change the user's registration Date by the one passed in parameter.
   *
   * @param registrationDate the new user's registration Date
   */
  void setRegistrationDate(Date registrationDate);

  /**
   * get the user's academic Date.
   *
   * @return user's academic Date
   */
  String getAcademicYear();

  /**
   * change the user's academic year by the one passed in parameter.
   *
   * @param academicYear the new user's academic year
   */
  void setAcademicYear(String academicYear);

  /**
   * get the user's type.
   *
   * @return user's type
   */
  String getUtilisateurType();

  /**
   * change the user's type by the one passed in parameter.
   *
   * @param utilisateurType the new user's type
   */
  void setUtilisateurType(String utilisateurType);

  /**
   * get the user's password.
   *
   * @return user password
   */
  String getPassword();

  /**
   * change the user's password by the one passed in parameter.
   *
   * @param password the new user's password
   */
  void setPassword(String password);

  /**
   * get the new user's password.
   *
   * @return the new user's password
   */
  String getNewPassword();

  /**
   * get the confirmation of new user's password.
   *
   * @return the confirmation of new user's password
   */
  String getNewPasswordConfirmation();


  /**
   * get the user's version.
   *
   * @return user version
   */
  int getVersion();

  /**
   * set the user's version.
   *
   * @param version the user's version
   */
  void setVersion(int version);

  /**
   * get the user's stage id.
   *
   * @return the user's stage id
   */
  int getStageId();

  /**
   * set the user's stage id.
   *
   * @param stageId the user's stage id
   */
  void setStageId(int stageId);

  /**
   * set the new user's password.
   *
   * @param newPassword the new user's password
   */
  void setNewPassword(String newPassword);

  /**
   * set the confirmation of new user's password.
   *
   * @param newPasswordConfirmation the confirmation of new user's password
   */
  void setNewPasswordConfirmation(String newPasswordConfirmation);


}
