package be.vinci.pae.dal.service;

import be.vinci.pae.business.dto.CompanyDTO;
import java.util.List;

/**
 * This is the interface of CompanyDAO.
 */
public interface CompanyDAO {

  /**
   * Get one company by the company's tradeName with designation.
   *
   * @param tradeNameDesignation the company's tradeName with designation
   * @return return company if found return null if not found
   */
  CompanyDTO getCompanyByTradeNameDesignation(String tradeNameDesignation);

  /**
   * Create a company.
   *
   * @param company The CompanyDTO object representing the company
   * @return true if the company is created, else false.
   */
  CompanyDTO createCompany(CompanyDTO company);

  /**
   * Get one company by id.
   *
   * @param companyId the company's id
   * @return company if found, else null
   */
  CompanyDTO getCompanyId(int companyId);

  /**
   * Get all companys.
   *
   * @return all companys
   */
  List<CompanyDTO> getAllCompany();

  /**
   * Get all company not blacklisted.
   *
   * @return all company not blacklisted
   */
  List<CompanyDTO> getAllCompanyNotBlacklisted();

  /**
   * Get all company with their number of stages.
   *
   * @return all company with their number of stages
   */
  List<CompanyDTO> getAllCompanyWithStages();

  /**
   * Blacklist a company in Company table.
   *
   * @param companyDTO the company to blacklist
   * @return the company if blacklisted or null if not
   */
  CompanyDTO blacklistCompany(CompanyDTO companyDTO);

  /**
   * Blacklist a company in blacklist table.
   *
   * @param company the company to blacklist
   * @return the company if blacklisted or null if not
   */
  CompanyDTO blacklistCompanyInBlacklistedTable(CompanyDTO company);
}
