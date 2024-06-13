package be.vinci.pae.business.ucc;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.dto.ContactDTO;
import be.vinci.pae.business.impl.Company;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.service.CompanyDAO;
import be.vinci.pae.dal.service.ContactDAO;
import be.vinci.pae.utils.exception.AlreadyBlacklisted;
import be.vinci.pae.utils.exception.DataAlreadyExistException;
import be.vinci.pae.utils.exception.DataNotExistsException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class implements the CompanyUCC interface.
 */
public class CompanyUCCImpl implements CompanyUCC {

  @Inject
  private CompanyDAO companyDAO;
  @Inject
  private ContactDAO contactDAO;
  @Inject
  private DALServices dalServices;

  @Override
  public CompanyDTO createCompany(CompanyDTO companyDTO) {

    dalServices.initTransaction();
    try {
      Company company = (Company) companyDAO.getCompanyByTradeNameDesignation(
          companyDTO.getTradeNameDesignation());

      if (company != null) {
        throw new DataAlreadyExistException("Company already exist !");
      }
      if (!companyDTO.getDesignation().isBlank()) {
        companyDTO.setTradeNameDesignation(companyDTO.getTradeName()
            + "_" + companyDTO.getDesignation());
      } else {
        companyDTO.setTradeNameDesignation(companyDTO.getTradeName());
      }
      companyDTO.setBlacklist(false);
      companyDAO.createCompany(companyDTO);
      return companyDTO;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public List<CompanyDTO> getAllCompany() {
    dalServices.initTransaction();
    try {
      List<CompanyDTO> companies = companyDAO.getAllCompany();
      for (CompanyDTO company : companies) {
        String tradeName = company.getTradeName();
        String designation = company.getDesignation();
        if (company.getTradeNameDesignation() == null
            || company.getTradeNameDesignation().isBlank()) {
          if (designation != null && !designation.isBlank()) {
            company.setTradeNameDesignation(tradeName + "_" + designation);
          } else {
            company.setTradeNameDesignation(tradeName);
          }
        }
      }

      return companies;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public List<CompanyDTO> getAllNotBlacklistedCompany() {
    dalServices.initTransaction();
    try {
      return companyDAO.getAllCompanyNotBlacklisted();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public List<CompanyDTO> getAllCompanyWithStages() {
    dalServices.initTransaction();
    try {
      return companyDAO.getAllCompanyWithStages();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public CompanyDTO blacklistCompany(CompanyDTO companyDTO) {
    dalServices.initTransaction();
    try {
      CompanyDTO company = getCompanyById(companyDTO.getCompanyId());
      if (company == null) {
        throw new DataNotExistsException("Company not found");
      }
      if (company.isBlacklist()) {
        throw new AlreadyBlacklisted("Company already blacklisted");
      }

      companyDAO.blacklistCompanyInBlacklistedTable(companyDTO);
      List<ContactDTO> contacts = contactDAO.getAllContactForTheCompany(companyDTO.getCompanyId());
      contacts.forEach(contact -> {
        if (contact.getContactStatus().equals("pris") || contact.getContactStatus()
            .equals("initie")) {
          contact.setContactStatus("suspendu");
          contactDAO.update(contact);
        }
      });
      return companyDTO;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public CompanyDTO getCompanyById(int idCompany) {
    dalServices.initTransaction();
    try {
      return companyDAO.getCompanyId(idCompany);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }
}
