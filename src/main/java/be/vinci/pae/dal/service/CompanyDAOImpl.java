package be.vinci.pae.dal.service;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.dal.DALBackendServices;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to obtain the implementation of the interfaces.
 */
public class CompanyDAOImpl implements CompanyDAO {

  @Inject
  private Factory myFactory;
  @Inject
  private DALBackendServices myDalService;

  @Override
  public CompanyDTO getCompanyByTradeNameDesignation(String tradeNameDesignation) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT et.* "
            + "FROM pae.entreprises et WHERE et.nom_appellation = ?")) {
      ps.setString(1, tradeNameDesignation);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          CompanyDTO company = myFactory.getCompany();
          company.setCompanyId(rs.getInt("id_entreprise"));
          company.setTradeName(rs.getString("nom"));
          company.setDesignation(rs.getString("appellation"));
          company.setTradeNameDesignation(rs.getString("nom_appellation"));
          company.setBlacklist(rs.getBoolean("blacklist"));
          company.setAddress(rs.getString("adresse"));
          company.setPhoneNumber(rs.getString("num_tel"));
          company.setEmail(rs.getString("email"));
          company.setVersion(rs.getInt("version"));
          return company;
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }

  @Override
  public CompanyDTO createCompany(CompanyDTO company) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "INSERT INTO pae.entreprises (nom, "
            + "appellation, nom_appellation, blacklist, adresse, num_tel, email, version)"
            + " VALUES (?,?,?,?,?,?,?,?)")) {

      ps.setString(1, company.getTradeName());
      ps.setString(2, company.getDesignation());
      ps.setString(3, company.getTradeNameDesignation());
      ps.setBoolean(4, company.isBlacklist());
      ps.setString(5, company.getAddress());
      ps.setString(6, company.getPhoneNumber());
      ps.setString(7, company.getEmail());
      ps.setInt(8, 1);
      ps.executeUpdate();
      return company;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public CompanyDTO getCompanyId(int companyId) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT e.id_entreprise, e.nom, e.appellation, e.blacklist, e.adresse, "
            + "e.email, e.num_tel, ep.motivation, e.version "
            + "FROM pae.entreprises e LEFT JOIN pae.entreprises_problematiques ep "
            + "ON e.id_entreprise = ep.entreprise "
            + "WHERE e.id_entreprise = ?")) {
      ps.setInt(1, companyId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          CompanyDTO company = myFactory.getCompany();
          company.setCompanyId(rs.getInt("id_entreprise"));
          company.setTradeName(rs.getString("nom"));
          company.setDesignation(rs.getString("appellation"));
          company.setBlacklist(rs.getBoolean("blacklist"));
          company.setAddress(rs.getString("adresse"));
          company.setEmail(rs.getString("email"));
          company.setPhoneNumber(rs.getString("num_tel"));
          company.setMotivationBlacklist(rs.getString("motivation"));
          company.setVersion(rs.getInt("version"));
          return company;
        }
      }
    } catch (Exception e) {
      throw new FatalException(e);
    }
    return null;
  }

  @Override
  public List<CompanyDTO> getAllCompany() {
    List<CompanyDTO> companys = new ArrayList<>();
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT et.*,count(st.id_stage) as nb_stages\n"
            + "FROM pae.entreprises et\n"
            + "LEFT JOIN pae.stages st ON et.id_entreprise = st.entreprise\n"
            + "group by  et.id_entreprise;")) {
      return getCompanyPSToList(companys, ps);
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public List<CompanyDTO> getAllCompanyNotBlacklisted() {
    List<CompanyDTO> companys = new ArrayList<>();
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT et.*,count(st.id_stage) as nb_stages\n"
            + "FROM pae.entreprises et\n"
            + "LEFT JOIN pae.stages st ON et.id_entreprise = st.entreprise\n"
            + "WHERE et.blacklist = false\n"
            + "group by  et.id_entreprise;")) {
      return getCompanyPSToList(companys, ps);
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public List<CompanyDTO> getAllCompanyWithStages() {
    List<CompanyDTO> list = new ArrayList<>();
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "SELECT et.*,count(st.id_stage) as nb_stages,st.annee_academique, "
            + "et.nom_appellation, et.version\n"
            + "FROM pae.entreprises et\n"
            + "LEFT JOIN pae.stages st ON et.id_entreprise = st.entreprise\n"
            + "group by  et.id_entreprise,st.annee_academique, et.nom_appellation")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          CompanyDTO company = myFactory.getCompany();
          company.setCompanyId(rs.getInt("id_entreprise"));
          company.setTradeName(rs.getString("nom"));
          company.setDesignation(rs.getString("appellation"));
          company.setPhoneNumber(rs.getString("num_tel"));
          company.setCountStage(rs.getInt("nb_stages"));
          company.setBlacklist(rs.getBoolean("blacklist"));
          company.setAcademicYear(rs.getString("annee_academique"));
          company.setTradeNameDesignation(rs.getString("nom_appellation"));
          company.setVersion(rs.getInt("version"));
          list.add(company);
        }
      }
      return list;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public CompanyDTO blacklistCompany(CompanyDTO companyDTO) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "UPDATE pae.entreprises SET blacklist = true, version = ?"
        + "WHERE id_entreprise = ? AND version = ?")) {
      ps.setInt(1, companyDTO.getVersion() + 1);
      ps.setInt(2, companyDTO.getCompanyId());
      ps.setInt(3, companyDTO.getVersion());
      int affectedRows = ps.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Blacklisting company failed, no rows affected.");
      }
      CompanyDTO company = getCompanyId(companyDTO.getCompanyId());
      company.setBlacklist(true);
      return company;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  @Override
  public CompanyDTO blacklistCompanyInBlacklistedTable(CompanyDTO company) {
    try (PreparedStatement ps = myDalService.getPreparedStatement(
        "INSERT INTO pae.entreprises_problematiques (entreprise, utilisateur, date, "
            + "motivation) VALUES (?,?,?,?)")) {
      ps.setInt(1, company.getCompanyId());
      ps.setInt(2, company.getStudentId());
      ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
      ps.setString(4, company.getMotivationBlacklist());
      ps.executeUpdate();
      return blacklistCompany(company);
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }

  /**
   * Transorm the result of the query to a list of CompanyDTO
   * for getAllCompany and getAllCompanyNotBlacklisted.
   *
   * @param companys the list of CompanyDTO
   * @param ps the PreparedStatement
   * @return the list of CompanyDTO or null if an exception is thrown
   */
  private List<CompanyDTO> getCompanyPSToList(List<CompanyDTO> companys, PreparedStatement ps) {
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          CompanyDTO company = myFactory.getCompany();
          company.setCompanyId(rs.getInt("id_entreprise"));
          company.setTradeName(rs.getString("nom"));
          company.setDesignation(rs.getString("appellation"));
          company.setTradeNameDesignation(rs.getString("nom_appellation"));
          company.setBlacklist(rs.getBoolean("blacklist"));
          company.setEmail(rs.getString("email"));
          company.setPhoneNumber(rs.getString("num_tel"));
          company.setAddress(rs.getString("adresse"));
          company.setCountStage(rs.getInt("nb_stages"));
          company.setVersion(rs.getInt("version"));
          companys.add(company);
        }
      }
      return companys;
    } catch (Exception e) {
      throw new FatalException(e);
    }
  }
}
