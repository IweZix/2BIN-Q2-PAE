package be.vinci.pae.utils.checker;

import be.vinci.pae.business.impl.User;
import be.vinci.pae.business.impl.UserImpl.UtilisateurType;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * RoleChecker class.
 */
public class RoleChecker {

  /**
   * Check if the user is a professor or an admin.
   *
   * @param user the user
   */
  public static void checkProfesseurAndAdmin(User user) {
    if (!user.getUtilisateurType().equals(UtilisateurType.PROFESSOR.getType())
        && !user.getUtilisateurType().equals(UtilisateurType.ADMIN.getType())) {
      throw new WebApplicationException("You are not allowed",
          Response.Status.UNAUTHORIZED);
    }
  }

  /**
   * Check if the user is a student.
   *
   * @param user the user
   */
  public static void checkStudent(User user) {
    if (!user.getUtilisateurType().equals(UtilisateurType.STUDENT.getType())) {
      throw new WebApplicationException("You are not allowed",
          Response.Status.UNAUTHORIZED);
    }
  }

  /**
   * Check if the user is a professor.
   *
   * @param user the user
   */
  public static void checkProfesseur(User user) {
    if (!user.getUtilisateurType().equals(UtilisateurType.PROFESSOR.getType())) {
      throw new WebApplicationException("You are not allowed",
          Response.Status.UNAUTHORIZED);
    }
  }

}
