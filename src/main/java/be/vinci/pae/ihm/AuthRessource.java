package be.vinci.pae.ihm;

import static be.vinci.pae.utils.checker.RoleChecker.checkProfesseurAndAdmin;

import be.vinci.pae.business.dto.UserDTO;
import be.vinci.pae.business.impl.User;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.ihm.filters.Authorize;
import be.vinci.pae.utils.TokenService;
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
 * Class AuthRessource.
 */
@Singleton
@Path("auths")
public class AuthRessource {

  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private UserUCC myUserUCC;
  @Inject
  private TokenService myTokenService;

  /**
   * login a user.
   *
   * @param userDTO containing the user's information
   * @return ObjectNode containing the user and the token
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode login(UserDTO userDTO) {

    UserDTO user = myUserUCC.login(userDTO);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.put("token", myTokenService.createToken(user.getUserId()));
    returned.putPOJO("user", user);

    return returned;
  }

  /**
   * register a user.
   *
   * @param userDTO containing the user's information
   * @return ObjectNode containing the user and the token
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode register(UserDTO userDTO) {
    UserDTO user = myUserUCC
        .register(userDTO);

    ObjectNode returned = jsonMapper.createObjectNode();
    returned.put("token", myTokenService.createToken(user.getUserId()));
    returned.putPOJO("user", user);

    return returned;
  }

  /**
   * update a user informations.
   *
   * @param userDTO containing the new user's information
   * @param request containing the user
   * @return ObjectNode containing the user and the token
   */
  @PUT
  @Path("update")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode update(UserDTO userDTO, @Context ContainerRequest request) {
    User u = (User) request.getProperty("user");
    int userId = u.getUserId();
    String userEmail = u.getEmail();
    userDTO.setUserId(userId);
    userDTO.setEmail(userEmail);

    UserDTO user = myUserUCC.update(userDTO);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.put("token", myTokenService.createToken(userId));
    returned.putPOJO("user", user);

    return returned;
  }


  /**
   * get the user information.
   *
   * @param request the request
   * @return ObjectNode containing the user
   */
  @GET
  @Path("me")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode me(@Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("user", user);

    return returned;
  }

  /**
   * get all users and all of their information.
   *
   * @param request the request
   * @return ObjectNode containing all user
   */

  @GET
  @Path("ListAllUser")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode listAllUser(@Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    checkProfesseurAndAdmin(user);

    List<UserDTO> users = myUserUCC.listAllUser();

    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("users", users);

    return returned;
  }

  /**
   * Get the details of a specific student by id.
   *
   * @param studentId The email of student
   * @return ObjectNode containing the details of the specified student
   */
  @GET
  @Path("getUserById")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getUserById(@QueryParam("studentId") int studentId) {
    UserDTO user = myUserUCC.getUserById(studentId);

    ObjectNode returned = jsonMapper.createObjectNode();
    returned.set("user", jsonMapper.valueToTree(user));

    return returned;
  }


}
