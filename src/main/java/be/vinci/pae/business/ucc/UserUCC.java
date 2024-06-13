package be.vinci.pae.business.ucc;

import be.vinci.pae.business.dto.UserDTO;
import java.util.List;


/**
 * This is the interface of UserUCCImpl.
 */
public interface UserUCC {

  /**
   * Get a user by email.
   *
   * @param email the email of the user
   * @return the user
   */
  UserDTO getUserByEmail(String email);

  /**
   * Login a User.
   *
   * @param userDTO user to log in
   * @return the logged-in user
   */
  UserDTO login(UserDTO userDTO);

  /**
   * Register a user.
   *
   * @param userDTO user to register
   * @return the created user
   */
  UserDTO register(UserDTO userDTO);

  /**
   * Update a user.
   *
   * @param userDTO new user information
   * @return the updated user
   */
  UserDTO update(UserDTO userDTO);

  /**
   * List all users.
   *
   * @return the list of all users
   */
  List<UserDTO> listAllUser();

  /**
   * Get a user by id.
   *
   * @param studentId the id of the user
   * @return the user
   */
  UserDTO getUserById(int studentId);
}
