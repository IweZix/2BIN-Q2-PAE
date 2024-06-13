package be.vinci.pae.dal.service;

import be.vinci.pae.business.dto.StageDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.dal.DALBackendServices;
import be.vinci.pae.utils.exception.FatalException;
import be.vinci.pae.utils.exception.VersionIncorrectException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the StageDAO interface.
 */
public class StageDAOImpl implements StageDAO {

  @Inject
  private Factory myFactory;
  @Inject
  private DALBackendServices myDalService;

  @Override
  public StageDTO getStageById(int stageId) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT st.id_stage, st.date_de_signature , st.sujet_de_stage, "
            + " st.sujet_de_stage, st.annee_academique, st.etudiant , st.entreprise ,"
            + " st.responsable_du_stage, st.version "
            + " FROM pae.stages st WHERE st.id_stage = ? ")) {
      ps.setInt(1, stageId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          StageDTO stage = myFactory.getStage();
          stage.setStageId(rs.getInt("id_stage"));
          stage.setSignatureDate(rs.getDate("date_de_signature"));
          stage.setInternshipProject(rs.getString("sujet_de_stage"));
          stage.setAcademicYear(rs.getString("annee_academique"));
          stage.setStudentId(rs.getInt("etudiant"));
          stage.setCompanyId(rs.getInt("entreprise"));
          stage.setInternshipSupervisorId(rs.getInt("responsable_du_stage"));
          stage.setVersion(rs.getInt("version"));
          return stage;
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }

  @Override
  public StageDTO createStage(StageDTO stage) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "INSERT INTO pae.stages (date_de_signature, "
            + "sujet_de_stage, annee_academique, etudiant, entreprise, "
            + "responsable_du_stage,version) VALUES (?, ?, ?, ?, ?, ?,?)")) {
      ps.setDate(1, new java.sql.Date(stage.getSignatureDate().getTime()));

      ps.setString(2, stage.getInternshipProject());
      ps.setString(3, stage.getAcademicYear());
      ps.setInt(4, stage.getStudentId());
      ps.setInt(5, stage.getCompanyId());
      ps.setInt(6, stage.getInternshipSupervisorId());
      ps.setInt(7, stage.getVersion());
      ps.executeUpdate();
      return stage;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  private void compareVersion(StageDTO stage, int version) {
    if (stage.getVersion() == version + 1) {
      return;
    }
    throw new VersionIncorrectException("Version mismatch");
  }

  @Override
  public StageDTO getAcceptedStage(int studentId) {
    StageDTO stage = myFactory.getStage();
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT st.id_stage, st.date_de_signature, st.sujet_de_stage, st.annee_academique,"
            + " e.nom AS nom_entreprise,e.appellation AS appellation_entreprise,"
            + " r.nom AS nom_responsable,"
            + " r.prenom AS prenom_responsable, r.email AS email_responsable, r.num_tel ,"
            + " st.version "
            + "FROM pae.stages st "
            + "INNER JOIN pae.entreprises e ON st.entreprise = e.id_entreprise "
            + "INNER JOIN pae.responsables_de_stage r "
            + "ON st.responsable_du_stage = r.id_responsable "
            + "INNER JOIN pae.contacts c ON st.etudiant = c.etudiant "
            + " AND st.entreprise = c.entreprise "
            + "WHERE c.etat_contact = 'accepte' AND c.etudiant = ?")) {

      ps.setInt(1, studentId);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        stage.setStageId(rs.getInt("id_stage"));
        stage.setSignatureDate(rs.getDate("date_de_signature"));
        stage.setInternshipProject(rs.getString("sujet_de_stage"));
        stage.setAcademicYear(rs.getString("annee_academique"));
        stage.setTradeNameCompany(rs.getString("nom_entreprise"));
        stage.setDesignationCompany(rs.getString("appellation_entreprise"));
        stage.setInternshipSupervisorLastName(rs.getString("nom_responsable"));
        stage.setInternshipSupervisorFirstName(rs.getString("prenom_responsable"));
        stage.setInternshipSupervisorPhone(rs.getString("num_tel"));
        stage.setInternshipSupervisorEmail(rs.getString("email_responsable"));
        stage.setVersion(rs.getInt("version"));
      }
      return stage;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public boolean updateInternshipProject(StageDTO stage) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "UPDATE pae.stages SET sujet_de_stage = ?, version = ? "
            + "WHERE etudiant = ? AND version = ?")) {
      ps.setString(1, stage.getInternshipProject());
      ps.setInt(2, stage.getVersion() + 1);
      ps.setInt(3, stage.getStudentId());
      ps.setInt(4, stage.getVersion());
      int affectedRows = ps.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Updating internship project failed, no rows affected.");
      }
      StageDTO updatedStage = getStageById(stage.getStageId());
      compareVersion(updatedStage, stage.getVersion());
      return true;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }
}
