package be.vinci.pae.ihm;

import static be.vinci.pae.utils.checker.RoleChecker.checkProfesseur;
import static be.vinci.pae.utils.checker.RoleChecker.checkProfesseurAndAdmin;

import be.vinci.pae.business.dto.CompanyDTO;
import be.vinci.pae.business.impl.User;
import be.vinci.pae.business.ucc.CompanyUCC;
import be.vinci.pae.ihm.filters.Authorize;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * Class CompanyResource.
 */
@Singleton
@Path("company")
public class CompanyResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  CompanyUCC myCompanyUCC;

  /**
   * create a company.
   *
   * @param companyDTO containing the company's information
   * @return ObjectNode containing the company
   */
  @POST
  @Path("create")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode create(CompanyDTO companyDTO) {
    CompanyDTO company = myCompanyUCC.createCompany(companyDTO);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("company", company);
    return returned;
  }

  /**
   * Get all companys.
   *
   * @return ObjectNode containing all companys
   */
  @GET
  @Path("all")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getAllCompany() {
    List<CompanyDTO> companyArray = myCompanyUCC.getAllCompany();
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.set("Allcompanys", jsonMapper.valueToTree(companyArray));
    return returned;
  }

  /**
   * Get the details of a specific company by id.
   *
   * @param idCompany The ID of the company
   * @return ObjectNode containing the details of the specified company
   */
  @GET
  @Path("oneCompany")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getCompanyById(@QueryParam("idCompany") int idCompany) {
    CompanyDTO company = myCompanyUCC.getCompanyById(idCompany);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.set("company", jsonMapper.valueToTree(company));
    return returned;
  }

  /**
   * Get all not blacklisted companys.
   *
   * @return ObjectNode containing all not blacklisted companys
   */
  @GET
  @Path("allNotBlacklisted")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getAllNotBlacklistedCompany() {
    List<CompanyDTO> companyArray = myCompanyUCC.getAllNotBlacklistedCompany();
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.set("companys", jsonMapper.valueToTree(companyArray));
    return returned;
  }

  /**
   * Get all company with their number of stages.
   *
   * @param request containing information about the HTTP request
   * @return ObjectNode containing all companys with their number of stages.
   */
  @GET
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getAllCompanyWithStages(@Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    checkProfesseurAndAdmin(user);
    List<CompanyDTO> companyArray = myCompanyUCC.getAllCompanyWithStages();
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.set("companyByYear", jsonMapper.valueToTree(companyArray));
    return returned;
  }

  /**
   * Blacklist a company.
   *
   * @param companyDTO containing the company's information
   * @param request ContainerRequest containing information about the HTTP request
   * @return ObjectNode containing the company
   */
  @PUT
  @Path("blacklist")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode blacklistCompany(CompanyDTO companyDTO, @Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    checkProfesseur(user);
    CompanyDTO company = myCompanyUCC.blacklistCompany(companyDTO);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("company", company);
    return returned;
  }
}
