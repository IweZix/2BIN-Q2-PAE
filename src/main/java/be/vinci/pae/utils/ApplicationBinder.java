package be.vinci.pae.utils;

import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.business.impl.FactoryImpl;
import be.vinci.pae.business.ucc.CompanyUCC;
import be.vinci.pae.business.ucc.CompanyUCCImpl;
import be.vinci.pae.business.ucc.ContactUCC;
import be.vinci.pae.business.ucc.ContactUCCImpl;
import be.vinci.pae.business.ucc.InternshipSupervisorUCC;
import be.vinci.pae.business.ucc.InternshipSupervisorUCCImpl;
import be.vinci.pae.business.ucc.StageUCC;
import be.vinci.pae.business.ucc.StageUCCImpl;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.business.ucc.UserUCCImpl;
import be.vinci.pae.dal.DALBackendServices;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.DALServicesImpl;
import be.vinci.pae.dal.service.CompanyDAO;
import be.vinci.pae.dal.service.CompanyDAOImpl;
import be.vinci.pae.dal.service.ContactDAO;
import be.vinci.pae.dal.service.ContactDAOImpl;
import be.vinci.pae.dal.service.InternshipSupervisorDAO;
import be.vinci.pae.dal.service.InternshipSupervisorDAOImpl;
import be.vinci.pae.dal.service.StageDAO;
import be.vinci.pae.dal.service.StageDAOImpl;
import be.vinci.pae.dal.service.UserDAO;
import be.vinci.pae.dal.service.UserDAOImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Binding class.
 */
@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(FactoryImpl.class).to(Factory.class).in(Singleton.class);
    bind(UserDAOImpl.class).to(UserDAO.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(DALServicesImpl.class).to(DALBackendServices.class)
        .to(DALServices.class).in(Singleton.class);
    bind(ContactDAOImpl.class).to(ContactDAO.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(CompanyDAOImpl.class).to(CompanyDAO.class).in(Singleton.class);
    bind(CompanyUCCImpl.class).to(CompanyUCC.class).in(Singleton.class);
    bind(StageDAOImpl.class).to(StageDAO.class).in(Singleton.class);
    bind(StageUCCImpl.class).to(StageUCC.class).in(Singleton.class);

    bind(InternshipSupervisorDAOImpl.class).to(InternshipSupervisorDAO.class).in(Singleton.class);
    bind(InternshipSupervisorUCCImpl.class).to(InternshipSupervisorUCC.class).in(Singleton.class);

    bind(TokenServiceImpl.class).to(TokenService.class).in(Singleton.class);
  }
}
