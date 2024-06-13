package be.vinci.pae.business.impl;

import be.vinci.pae.business.dto.UserDTO;

/**
 * User class.
 */
public interface User extends UserDTO {

  /**
   * checks if the password entered by the user matches his hashed password.
   *
   * @param password input password
   * @return true if the password is checked, else false
   */
  boolean checkPassword(String password);

  /**
   * hash the password in params.
   *
   * @param password user password
   * @return the hashed password
   */
  String hashPassword(String password);

  /**
   * checks if the email is valid.
   *
   * @param email user email
   * @return true if the email is valid, else false
   */
  boolean isEmailValid(String email);

  /**
   * checks if the role is valid.
   *
   * @param role user role
   * @return true if the role is valid, else false
   */
  boolean isRoleValid(String role);
}