package be.vinci.pae.dal.service;

import be.vinci.pae.business.dto.UserDTO;
import java.sql.ResultSet;
import java.util.List;


/**
 * This is the interface of UserDataServiceImpl.
 */
public interface UserDAO {

  /**
   * Set user.
   *
   * @param rs the ResultSet representing the user information
   * @return the user
   * @throws Exception the exception
   */
  UserDTO setUser(ResultSet rs) throws Exception;

  /**
   * Get one user by email.
   *
   * @param email the user's email
   * @return return user if found return null if not found
   */
  UserDTO getUserByEmail(String email);

  /**
   * Register a user.
   *
   * @param user the UserDTO representing the user information to be registered
   * @return the created user, return null if user already exist or can't be created
   */
  boolean register(UserDTO user);

  /**
   * Update a user.
   *
   * @param user the UserDTO representing the user information to be updated
   * @return the updated user, return null if user not found or can't be updated
   */
  UserDTO update(UserDTO user);

  /**
   * List all users by their email.
   *
   * @return the list of all users or null if no user found
   */
  List<UserDTO> listAllUser();

  /**
   * Get one user by id.
   *
   * @param studentId the user's id
   * @return return user if found, else return null
   */
  UserDTO getUserById(int studentId);

}
