package be.vinci.pae.business.ucc;

import be.vinci.pae.business.dto.CompanyDTO;
import java.util.List;


/**
 * this is the interface of CompanyUCCImpl.
 */
public interface CompanyUCC {

  /**
   * Create company.
   *
   * @param companyDTO the company's information
   * @return the created company
   */
  CompanyDTO createCompany(CompanyDTO companyDTO);

  /**
   * Get all companys.
   *
   * @return all companys
   */
  List<CompanyDTO> getAllCompany();

  /**
   * Get all not blacklisted companys.
   *
   * @return all not blacklisted companys
   */
  List<CompanyDTO> getAllNotBlacklistedCompany();

  /**
   * Get all company with their number of stages.
   *
   * @return all company with their number of stages
   */
  List<CompanyDTO> getAllCompanyWithStages();

  /**
   * Blacklist a company.
   *
   * @param companyDTO the company to blacklist
   * @return the company if blacklisted or null if not
   */
  CompanyDTO blacklistCompany(CompanyDTO companyDTO);

  /**
   * Get company by id.
   *
   * @param idCompany the company's id
   * @return the company if found, else null
   */
  CompanyDTO getCompanyById(int idCompany);


}
