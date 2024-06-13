package be.vinci.pae.dal.service;

import be.vinci.pae.business.dto.UserDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.dal.DALBackendServices;
import be.vinci.pae.utils.exception.FatalException;
import be.vinci.pae.utils.exception.VersionIncorrectException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is used to obtain the implementation of the interfaces.
 */
public class UserDAOImpl implements UserDAO {

  @Inject
  private Factory myFactory;
  @Inject
  private DALBackendServices myDalService;

  @Override
  public UserDTO setUser(ResultSet rs) throws SQLException {
    UserDTO user = myFactory.getUser();
    user.setUserId(rs.getInt("id_utilisateur"));
    user.setEmail(rs.getString("email"));
    user.setPassword(rs.getString("mot_de_passe"));
    user.setLastName(rs.getString("nom"));
    user.setFirstName(rs.getString("prenom"));
    user.setPhone(rs.getString("num_tel"));
    user.setAcademicYear(rs.getString("annee_academique"));
    user.setUtilisateurType(rs.getString("type"));
    user.setRegistrationDate(rs.getDate("date_inscription"));
    user.setVersion(rs.getInt("version"));
    return user;
  }

  @Override
  public UserDTO getUserByEmail(String email) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT ut.id_utilisateur, ut.email, "
            + "ut.mot_de_passe, ut.nom , ut.prenom, ut.num_tel, "
            + "ut.annee_academique, ut.type, ut.date_inscription, ut.version "
            + "FROM pae.utilisateurs ut WHERE LOWER(ut.email) = LOWER(?)")) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return setUser(rs);
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }

  @Override
  public boolean register(UserDTO user) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "INSERT INTO pae.utilisateurs (email, "
            + "mot_de_passe, nom, prenom, num_tel, date_inscription,"
            + "annee_academique, type, version) VALUES (?,?,?,?,?,?,?,?,?)")) {

      ps.setString(1, user.getEmail());
      ps.setString(2, user.getPassword());
      ps.setString(3, user.getLastName());
      ps.setString(4, user.getFirstName());
      ps.setString(5, user.getPhone());
      ps.setDate(6, new java.sql.Date(user.getRegistrationDate().getTime()));
      ps.setString(7, user.getAcademicYear());
      ps.setString(8, user.getUtilisateurType());
      ps.setInt(9, 1);
      ps.executeUpdate();
      return true;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public UserDTO update(UserDTO user) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "UPDATE pae.utilisateurs "
            + "SET nom = ?, prenom = ?, num_tel = ?, email = ?, mot_de_passe = ?, version = ?"
            + "WHERE id_utilisateur = ? AND version = ?")) {
      ps.setString(1, user.getLastName());
      ps.setString(2, user.getFirstName());
      ps.setString(3, user.getPhone());
      ps.setString(4, user.getEmail());
      ps.setString(5, user.getPassword());
      ps.setInt(6, user.getVersion() + 1);
      ps.setInt(7, user.getUserId());
      ps.setInt(8, user.getVersion());
      int affectedRows = ps.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Updating user failed, no rows affected.");
      }

      UserDTO updatedUser = getUserByEmail(user.getEmail());
      compareVersion(updatedUser, user.getVersion());
      return updatedUser;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public List<UserDTO> listAllUser() {
    List<UserDTO> users = new ArrayList<>();

    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT ut.id_utilisateur,st.etudiant, "
            + " ut.nom , ut.prenom, ut.num_tel, ut.email, "
            + "ut.annee_academique, ut.type, st.id_stage, ut.version "
            + "FROM pae.utilisateurs ut "
            + "Left JOIN pae.stages st ON ut.id_utilisateur = st.etudiant ")) {
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        UserDTO user = myFactory.getUser();
        user.setUserId(rs.getInt("id_utilisateur"));
        user.setLastName(rs.getString("nom"));
        user.setFirstName(rs.getString("prenom"));
        user.setAcademicYear(rs.getString("annee_academique"));
        user.setUtilisateurType(rs.getString("type"));
        user.setPhone(rs.getString("num_tel"));
        user.setEmail(rs.getString("email"));
        user.setStageId(rs.getInt("id_stage"));
        user.setVersion(rs.getInt("version"));
        users.add(user);
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return users;
  }

  private UserDTO compareVersion(UserDTO user, int version) {
    if (user.getVersion() == version + 1) {
      return user;
    }
    throw new VersionIncorrectException("Version mismatch");
  }

  @Override
  public UserDTO getUserById(int studentId) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT ut.id_utilisateur, ut.email, "
            + "ut.mot_de_passe, ut.nom , ut.prenom, ut.num_tel, "
            + "ut.annee_academique, ut.type, ut.date_inscription, ut.version "
            + "FROM pae.utilisateurs ut WHERE ut.id_utilisateur = ?")) {
      ps.setInt(1, studentId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return setUser(rs);
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }
}
