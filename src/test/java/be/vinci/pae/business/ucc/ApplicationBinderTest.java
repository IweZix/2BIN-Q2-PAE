package be.vinci.pae.business.ucc;

import be.vinci.pae.business.impl.Factory;
import be.vinci.pae.business.impl.FactoryImpl;
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
import be.vinci.pae.utils.ApplicationBinder;
import jakarta.inject.Singleton;
import org.mockito.Mockito;

class ApplicationBinderTest extends ApplicationBinder {

  protected void configure() {
    bind(FactoryImpl.class).to(Factory.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(CompanyUCCImpl.class).to(CompanyUCC.class).in(Singleton.class);
    bind(StageUCCImpl.class).to(StageUCC.class).in(Singleton.class);
    bind(InternshipSupervisorUCCImpl.class).to(InternshipSupervisorUCC.class).in(Singleton.class);

    bind(Mockito.mock(ContactDAOImpl.class)).to(ContactDAO.class);
    bind(Mockito.mock(CompanyDAOImpl.class)).to(CompanyDAO.class);
    bind(Mockito.mock(UserDAOImpl.class)).to(UserDAO.class);
    bind(Mockito.mock(StageDAOImpl.class)).to(StageDAO.class);
    bind(Mockito.mock(InternshipSupervisorDAOImpl.class)).to(InternshipSupervisorDAO.class);

    bind(Mockito.mock(DALServicesImpl.class)).to(DALBackendServices.class).to(DALServices.class);
  }
}