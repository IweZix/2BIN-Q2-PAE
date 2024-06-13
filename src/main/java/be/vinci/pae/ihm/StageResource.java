package be.vinci.pae.ihm;

import static be.vinci.pae.utils.checker.RoleChecker.checkStudent;

import be.vinci.pae.business.dto.StageDTO;
import be.vinci.pae.business.impl.User;
import be.vinci.pae.business.ucc.StageUCC;
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
import org.glassfish.jersey.server.ContainerRequest;

/**
 * Class StageResource.
 */
@Singleton
@Path("stage")
public class StageResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  StageUCC myStageUCC;

  /**
   * Create a stage.
   *
   * @param stageDTO containing the stage to create
   * @param request ContainerRequest containing the user context
   * @return ObjectNode containing the stage
   */
  @POST
  @Path("create")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode create(StageDTO stageDTO, @Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    checkStudent(user);

    int etudiantId;
    etudiantId = user.getUserId();
    stageDTO.setStudentId(etudiantId);
    stageDTO.setVersion(1);

    StageDTO stage = myStageUCC.createStage(stageDTO);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("stage", stage);
    return returned;
  }


  /**
   * Get all stages.
   *
   * @param request ContainerRequest containing the user context
   * @return ObjectNode containing the stages
   */
  @GET
  @Path("accepte")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getAllContacts(@Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    checkStudent(user);

    StageDTO stage = myStageUCC.getAcceptedStage(user.getUserId());

    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("stage", stage);
    return returned;
  }

  /**
   * Update the internship project.
   *
   * @param stage containing the updated internship project
   * @param request ContainerRequest containing the user context
   * @return ObjectNode indicating the success status of updating the internship project
   */
  @PUT
  @Path("internshipProject")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode updateInternshipProject(StageDTO stage, @Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    checkStudent(user);

    stage.setStudentId(user.getUserId());

    boolean success =
        myStageUCC.updateInternshipProject(stage);

    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("success", success);
    return returned;
  }

  /**
   * Get the stage of this student.
   *
   * @param studentId The ID of the student
   * @return ObjectNode containing the list of contacts of the specified company
   */
  @GET
  @Path("getStageForTheStudent")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getStageForTheStudent(@QueryParam("idStudent") int studentId) {

    StageDTO stage = myStageUCC.getAcceptedStage(studentId);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.set("stage", jsonMapper.valueToTree(stage));
    return returned;

  }
}
