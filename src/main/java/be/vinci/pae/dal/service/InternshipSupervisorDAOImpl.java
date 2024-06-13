package be.vinci.pae.dal.service;

import be.vinci.pae.business.dto.InternshipSupervisorDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.dal.DALBackendServices;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * InternshipSupervisorDAOImpl class.
 */
public class InternshipSupervisorDAOImpl implements InternshipSupervisorDAO {

  @Inject
  private DALBackendServices myDalService;
  @Inject
  private Factory myFactory;

  @Override
  public InternshipSupervisorDTO create(InternshipSupervisorDTO internshipSupervisor) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "INSERT INTO pae.responsables_de_stage (nom, "
            + "prenom, num_tel, email, entreprise) VALUES (?,?,?,?,?)")) {
      ps.setString(1, internshipSupervisor.getLastname());
      ps.setString(2, internshipSupervisor.getFirstname());
      ps.setString(3, internshipSupervisor.getPhone());
      ps.setString(4, internshipSupervisor.getEmail());
      ps.setInt(5, internshipSupervisor.getCompany());
      ps.executeUpdate();
      return internshipSupervisor;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public InternshipSupervisorDTO getInternshipSupervisorByName(String lastName,
      String firstName,
      int id) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT * FROM pae.responsables_de_stage WHERE nom = ? "
            + "AND prenom = ? AND entreprise = ?")) {
      ps.setString(1, lastName);
      ps.setString(2, firstName);
      ps.setInt(3, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          InternshipSupervisorDTO sc = myFactory.getInternshipSupervisor();
          sc.setId(rs.getInt("id_responsable"));
          sc.setFirstname(rs.getString("prenom"));
          sc.setLastname(rs.getString("nom"));
          sc.setPhone(rs.getString("num_tel"));
          sc.setEmail(rs.getString("email"));
          sc.setCompany(rs.getInt("entreprise"));
          return sc;
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }

  @Override
  public List<InternshipSupervisorDTO> getAllInternshipSupervisorByCompanyId(int companyId) {
    List<InternshipSupervisorDTO> list = new ArrayList<>();
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT * FROM pae.responsables_de_stage WHERE entreprise = ?")) {
      ps.setInt(1, companyId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          InternshipSupervisorDTO intSup = myFactory.getInternshipSupervisor();
          intSup.setId(rs.getInt("id_responsable"));
          intSup.setFirstname(rs.getString("prenom"));
          intSup.setLastname(rs.getString("nom"));
          intSup.setPhone(rs.getString("num_tel"));
          intSup.setEmail(rs.getString("email"));
          list.add(intSup);
        }
      }
      return list;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public InternshipSupervisorDTO getInternshipSupervisor(
      InternshipSupervisorDTO internshipSupervisor) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT * FROM pae.responsables_de_stage WHERE nom = ? AND prenom = ?"
            + "AND entreprise = ?")) {
      ps.setString(1, internshipSupervisor.getLastname());
      ps.setString(2, internshipSupervisor.getFirstname());
      ps.setInt(3, internshipSupervisor.getCompany());
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          InternshipSupervisorDTO sc = myFactory.getInternshipSupervisor();
          sc.setId(rs.getInt("id_responsable"));
          sc.setFirstname(rs.getString("prenom"));
          sc.setLastname(rs.getString("nom"));
          sc.setCompany(rs.getInt("entreprise"));
          return sc;
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }

  @Override
  public InternshipSupervisorDTO getInternshipSupByEmail(String email) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT * FROM pae.responsables_de_stage WHERE email = ?")) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          InternshipSupervisorDTO intSup = myFactory.getInternshipSupervisor();
          intSup.setId(rs.getInt("id_responsable"));
          intSup.setLastname(rs.getString("nom"));
          intSup.setFirstname(rs.getString("prenom"));
          intSup.setPhone(rs.getString("num_tel"));
          intSup.setCompany(rs.getInt("entreprise"));
          intSup.setEmail(rs.getString("email"));
          return intSup;
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }

  @Override
  public InternshipSupervisorDTO getInternshipPhone(String phone) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT * FROM pae.responsables_de_stage WHERE num_tel = ?")) {
      ps.setString(1, phone);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          InternshipSupervisorDTO intSup = myFactory.getInternshipSupervisor();
          intSup.setId(rs.getInt("id_responsable"));
          intSup.setFirstname(rs.getString("prenom"));
          intSup.setLastname(rs.getString("nom"));
          intSup.setCompany(rs.getInt("entreprise"));
          intSup.setPhone(rs.getString("num_tel"));
          intSup.setEmail(rs.getString("email"));
          return intSup;
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }

}
