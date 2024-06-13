package be.vinci.pae.dal.service;

import be.vinci.pae.business.dto.ContactDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.dal.DALBackendServices;
import be.vinci.pae.utils.exception.FatalException;
import be.vinci.pae.utils.exception.VersionIncorrectException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to obtain the implementation of the interface ContactDAO.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DALBackendServices myDalService;
  @Inject
  private Factory myFactory;

  @Override
  public ContactDTO startContactCompany(ContactDTO contact) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "INSERT INTO pae.contacts (etudiant, entreprise, etat_contact, annee_academique, version) "
            + "VALUES (?,?,?,?,?)")) {
      ps.setInt(1, contact.getStudentId());
      ps.setInt(2, contact.getCompanyId());
      ps.setString(3, contact.getContactStatus());
      ps.setString(4, contact.getAcademicYear());
      ps.setInt(5, contact.getVersion());
      ps.executeUpdate();
      return contact;
    } catch (Exception e) {
      throw new FatalException(e);

    }
  }

  @Override
  public ArrayList<ContactDTO> getAll(int studentId) {
    ArrayList<ContactDTO> contacts = new ArrayList<>();
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT en.nom, en.appellation,en.nom_appellation, en.num_tel, co.etat_contact, "
            + " co.entreprise, co.type_de_rencontre, co.raison_refus, co.version "
            + "FROM pae.contacts co JOIN pae.entreprises en ON co.entreprise = en.id_entreprise "
            + "WHERE etudiant = ?")) {
      ps.setInt(1, studentId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ContactDTO contact = myFactory.getContact();
          contact.setCompanyTradeName(rs.getString("nom"));
          contact.setCompanyDesignation(rs.getString("appellation"));
          contact.setCompanyTradeNameDesignation(rs.getString("nom_appellation"));
          contact.setCompanyPhone(rs.getString("num_tel"));
          contact.setContactStatus(rs.getString("etat_contact"));
          contact.setMeetingType(rs.getString("type_de_rencontre"));
          contact.setDeclineReason(rs.getString("raison_refus"));
          contact.setVersion(rs.getInt("version"));
          contact.setCompanyId(rs.getInt("entreprise"));
          contacts.add(contact);
        }
      }
      return contacts;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public ContactDTO update(ContactDTO contact) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "UPDATE pae.contacts "
            + "SET etat_contact = ?, type_de_rencontre = ?, raison_refus = ?, version = ?"
            + " WHERE etudiant = ? AND entreprise = ? AND version = ?")) {
      ps.setString(1, contact.getContactStatus());
      ps.setString(2, contact.getMeetingType());
      ps.setString(3, contact.getDeclineReason());
      ps.setInt(4, contact.getVersion() + 1);
      ps.setInt(5, contact.getStudentId());
      ps.setInt(6, contact.getCompanyId());
      ps.setInt(7, contact.getVersion());
      int affectedRows = ps.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Updating contact failed, no rows affected.");
      }
      // Get the updated contact
      ContactDTO updatedContact = (ContactDTO) getContactByStudentAndCompanyId(
          contact.getStudentId(),
          contact.getCompanyId());
      // Use compareVersion function
      compareVersion(updatedContact, contact.getVersion());
      return updatedContact;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public Object getContactByStudentAndCompanyId(int studentId, int companyId) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT ct.etudiant, ct.entreprise, "
            + "ct.etat_contact, ct.type_de_rencontre , ct.raison_refus, ct.version "
            + "FROM pae.contacts ct WHERE ct.etudiant = ? AND ct.entreprise = ?")) {
      ps.setInt(1, studentId);
      ps.setInt(2, companyId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          ContactDTO contact = myFactory.getContact();
          contact.setStudentId(rs.getInt("etudiant"));
          contact.setCompanyId(rs.getInt("entreprise"));
          contact.setContactStatus(rs.getString("etat_contact"));
          contact.setMeetingType(rs.getString("type_de_rencontre"));
          contact.setDeclineReason(rs.getString("raison_refus"));
          contact.setVersion(rs.getInt("version"));
          return contact;
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }

  @Override
  public Map<String, Integer> getNumberUsersWithStage() {
    Map<String, Integer> map = new HashMap<>();
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT s.annee_academique, COUNT(*) as nb_etudiants\n"
            + "FROM pae.stages s LEFT JOIN pae.utilisateurs u\n"
            + "    on s.etudiant = u.id_utilisateur\n"
            + "WHERE u.type = 'étudiant' AND s.etudiant IS NOT NULL\n"
            + "GROUP BY s.annee_academique;")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          map.put(rs.getString(1), rs.getInt(2));
        }
      }
      return map;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public Map<String, Integer> getNumberUsersByYear() {
    Map<String, Integer> map = new HashMap<>();
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT u.annee_academique, COUNT(u.id_utilisateur) as nombre_etudiant\n"
            + "FROM pae.utilisateurs u\n"
            + "WHERE u.type = 'étudiant'\n"
            + "GROUP BY u.annee_academique")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          map.put(rs.getString(1), rs.getInt(2));
        }
      }
      return map;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  private ContactDTO compareVersion(ContactDTO contact, int version) {
    if (contact.getVersion() == version + 1) {
      return contact;
    }
    throw new VersionIncorrectException("Version mismatch");
  }

  @Override
  public ContactDTO getContactAccepted(int studentId) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT ct.etudiant, ct.entreprise, "
            + "ct.etat_contact, ct.type_de_rencontre , ct.raison_refus, ct.version "
            + "FROM pae.contacts ct WHERE ct.etudiant = ? AND ct.etat_contact = 'accepte'")) {
      ps.setInt(1, studentId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          ContactDTO contactAccepte = myFactory.getContact();
          contactAccepte.setStudentId(rs.getInt("etudiant"));
          contactAccepte.setCompanyId(rs.getInt("entreprise"));
          contactAccepte.setContactStatus(rs.getString("etat_contact"));
          contactAccepte.setMeetingType(rs.getString("type_de_rencontre"));
          contactAccepte.setDeclineReason(rs.getString("raison_refus"));
          contactAccepte.setVersion(rs.getInt("version"));
          return contactAccepte;
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }

  @Override
  public List<ContactDTO> getAllContactForTheCompany(int companyId) {
    List<ContactDTO> companys = new ArrayList<>();
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT co.etudiant, co.entreprise, ut.nom, ut.prenom, ut.email,  "
            + " co.etat_contact, co.type_de_rencontre, co.raison_refus, co.version "
            + "FROM pae.contacts co, pae.utilisateurs ut "
            + "WHERE co.entreprise = ? AND ut.id_utilisateur = co.etudiant")) {
      ps.setInt(1, companyId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ContactDTO contact = myFactory.getContact();
          contact.setStudentId(rs.getInt("etudiant"));
          contact.setCompanyId(rs.getInt("entreprise"));
          contact.setStudentName(rs.getString("nom"));
          contact.setStudentFirstName(rs.getString("prenom"));
          contact.setStudentEmail(rs.getString("email"));
          contact.setContactStatus(rs.getString("etat_contact"));
          contact.setMeetingType(rs.getString("type_de_rencontre"));
          contact.setDeclineReason(rs.getString("raison_refus"));
          contact.setVersion(rs.getInt("version"));
          companys.add(contact);
        }
      }
      return companys;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public List<ContactDTO> getAllContactForStudent(int studentId) {
    List<ContactDTO> students = new ArrayList<>();
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT en.nom, en.appellation, en.num_tel, "
            + "co.etat_contact, co.type_de_rencontre, co.raison_refus, co.version "
            + "FROM pae.contacts co, pae.utilisateurs ut, pae.entreprises en "
            + "WHERE ut.id_utilisateur = co.etudiant AND co.entreprise = en.id_entreprise "
            + "AND ut.id_utilisateur = ? ")) {
      ps.setInt(1, studentId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ContactDTO contact = myFactory.getContact();
          contact.setCompanyTradeName(rs.getString("nom"));
          contact.setCompanyDesignation(rs.getString("appellation"));
          contact.setCompanyPhone(rs.getString("num_tel"));
          contact.setContactStatus(rs.getString("etat_contact"));
          contact.setMeetingType(rs.getString("type_de_rencontre"));
          contact.setDeclineReason(rs.getString("raison_refus"));
          contact.setVersion(rs.getInt("version"));
          students.add(contact);
        }
      }
      return students;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

}
