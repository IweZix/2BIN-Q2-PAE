package be.vinci.pae.ihm;

import static be.vinci.pae.utils.checker.RoleChecker.checkStudent;

import be.vinci.pae.business.dto.InternshipSupervisorDTO;
import be.vinci.pae.business.impl.User;
import be.vinci.pae.business.ucc.InternshipSupervisorUCC;
import be.vinci.pae.ihm.filters.Authorize;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * Class InternshipSupervisorResource.
 */
@Singleton
@Path("internshipsupervisor")
@Produces(MediaType.APPLICATION_JSON)
public class InternshipSupervisorResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private InternshipSupervisorUCC myInternshipSupervisorUCC;

  /**
   * Create an internship supervisor.
   *
   * @param internshipSupervisor to create
   * @param request    ContainerRequest containing information about the HTTP request
   * @return ObjectNode containing the internship supervisor
   */
  @POST
  @Authorize
  @Path("create")
  public ObjectNode create(InternshipSupervisorDTO internshipSupervisor,
      @Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    checkStudent(user);

    InternshipSupervisorDTO sc = myInternshipSupervisorUCC.create(internshipSupervisor);
    ObjectNode node = jsonMapper.createObjectNode();
    node.putPOJO("internshipSupervisor", sc);

    return node;
  }

  /**
   * Get all internship supervisors by company id.
   *
   * @param companyId the company's id
   * @param request   ContainerRequest containing information about the HTTP request
   * @return ObjectNode containing the list of internship supervisors
   */
  @GET
  @Path("all")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getAllInternshipSupervisor(@QueryParam("companyId") int companyId,
      @Context ContainerRequest request) {

    User user = (User) request.getProperty("user");
    checkStudent(user);

    List<InternshipSupervisorDTO> internshipSupervisorList = myInternshipSupervisorUCC
        .getAllInternshipSupervisors(companyId);

    ObjectNode returned = jsonMapper.createObjectNode();
    returned.set("AllInternshipSupervisor", jsonMapper
        .valueToTree(internshipSupervisorList));
    return returned;
  }
}
