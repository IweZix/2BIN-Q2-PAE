package be.vinci.pae.ihm;

import static be.vinci.pae.utils.checker.RoleChecker.checkProfesseur;
import static be.vinci.pae.utils.checker.RoleChecker.checkStudent;

import be.vinci.pae.business.dto.ContactDTO;
import be.vinci.pae.business.impl.User;
import be.vinci.pae.business.ucc.ContactUCC;
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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.server.ContainerRequest;

/**
 * Class ContactResource.
 */
@Singleton
@Path("contact")
public class ContactResource {

  /**
   * The constant PROFESSEUR.
   */
  public static final String PROFESSEUR = "professeur";

  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  ContactUCC myContactUCC;

  /**
   * Create a contact.
   *
   * @param contactDTO containing the contact's information
   * @param request    ContainerRequest containing the user context
   * @return ObjectNode indicating the success status of starting the contact
   */
  @POST
  @Path("started")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode started(ContactDTO contactDTO, @Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    checkStudent(user);
    if (contactDTO.getCompanyId() < 0) {
      throw new WebApplicationException("Company Id is incorrect",
          Response.Status.UNAUTHORIZED);
    }
    contactDTO.setStudentId(user.getUserId());
    contactDTO.setVersion(1);
    ContactDTO startContactCompany = myContactUCC.startContactCompany(contactDTO);
    boolean sucess = startContactCompany != null;
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("sucess", sucess);
    return returned;
  }

  /**
   * Get all contacts.
   *
   * @param request ContainerRequest containing the user context
   * @return ObjectNode containing all contacts
   */
  @GET
  @Path("all")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getAllContacts(@Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    checkStudent(user);

    ArrayList<ContactDTO> contactsArray = (ArrayList<ContactDTO>) myContactUCC
        .getAll(user.getUserId());

    ObjectNode returned = jsonMapper.createObjectNode();
    returned.set("contacts", jsonMapper.valueToTree(contactsArray));
    return returned;
  }

  /**
   * Change status of a contact.
   *
   * @param contactDTO containing the contact's information
   * @param request containing the user
   * @return ObjectNode indicating the success status of changing the contact status
   */
  @PUT
  @Path("meet")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode meetCompany(ContactDTO contactDTO, @Context ContainerRequest request) {
    if (contactDTO.getCompanyId() < 0) {
      throw new WebApplicationException("Company Id is incorrect",
          Response.Status.UNAUTHORIZED);
    }
    if (contactDTO.getMeetingType() == null || contactDTO.getMeetingType().isBlank()) {
      throw new WebApplicationException("You need to chose a meeting type",
          Response.Status.UNAUTHORIZED);
    }
    if (contactDTO.getVersion() <= 0) {
      throw new WebApplicationException("Version is incorrect",
          Response.Status.UNAUTHORIZED);
    }

    User user = (User) request.getProperty("user");
    checkStudent(user);
    contactDTO.setStudentId(user.getUserId());
    ContactDTO meetCompany = myContactUCC.setCompanyMeet(contactDTO);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("contact", meetCompany);
    return returned;
  }

  /**
   * Decline a contact.
   *
   * @param contactDTO containing the contact's information
   * @param request ContainerRequest containing the user context
   * @return ObjectNode indicating the success status of accepting the contact
   */
  @PUT
  @Path("decline")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode declineContact(ContactDTO contactDTO, @Context ContainerRequest request) {
    if (contactDTO.getCompanyId() < 0) {
      throw new WebApplicationException("Company Id is incorrect",
          Response.Status.UNAUTHORIZED);
    }
    if (contactDTO.getDeclineReason() == null || contactDTO.getDeclineReason().isBlank()) {
      throw new WebApplicationException("You need to give a reason",
          Response.Status.UNAUTHORIZED);
    }
    if (contactDTO.getVersion() <= 0) {
      throw new WebApplicationException("Version is incorrect",
          Response.Status.UNAUTHORIZED);
    }

    User user = (User) request.getProperty("user");
    checkStudent(user);
    contactDTO.setStudentId(user.getUserId());
    ContactDTO declineContact = myContactUCC.declineContact(contactDTO);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("contact", declineContact);
    return returned;
  }

  /**
   * Stop following a company.
   *
   * @param contactDTO containing the contact's information
   * @param request ContainerRequest containing the user context
   * @return ObjectNode indicating the success status of stopping following the company
   */
  @PUT
  @Path("stop")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode stopFollowingCompany(ContactDTO contactDTO, @Context ContainerRequest request) {
    User user = (User) request.getProperty("user");
    checkStudent(user);
    contactDTO.setStudentId(user.getUserId());
    if (contactDTO.getCompanyId() < 0) {
      throw new WebApplicationException("Company Id is incorrect",
          Response.Status.UNAUTHORIZED);
    }
    if (contactDTO.getVersion() <= 0) {
      throw new WebApplicationException("Version is incorrect",
          Response.Status.UNAUTHORIZED);
    }

    ContactDTO declineContact = myContactUCC.stopFollowingCompany(contactDTO);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("contact", declineContact);
    return returned;
  }

  /**
   * Get the number of users with a stage.
   *
   * @param request ContainerRequest containing the user context
   * @return ObjectNode containing the number of users with a stage
   */
  @GET
  @Path("users-stages")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getNumberUsersStage(@Context ContainerRequest request) {
    User user = (User) request.getProperty("user");

    if (user == null) {
      throw new WebApplicationException("User not found",
          Response.Status.UNAUTHORIZED);
    }

    checkProfesseur(user);

    Map<String, Integer> nbWithStage = myContactUCC.getNumberUsersWithStage();
    Map<String, Integer> nbWithoutStage = myContactUCC.getNumberUsersWithoutStage();

    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("studentWithStage", nbWithStage);
    returned.putPOJO("studentWithoutStage", nbWithoutStage);
    return returned;
  }

  /**
   * Accept a contact.
   *
   * @param contactDTO containing the contact's information
   * @param request ContainerRequest containing the user context
   * @return ObjectNode indicating the success status of accepting the contact
   */
  @PUT
  @Path("accepte")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode acceptContact(ContactDTO contactDTO, @Context ContainerRequest request) {
    if (contactDTO.getCompanyId() < 0) {
      throw new WebApplicationException("Company Id is incorrect",
          Response.Status.UNAUTHORIZED);
    }
    if (contactDTO.getVersion() <= 0) {
      throw new WebApplicationException("Version is incorrect",
          Response.Status.UNAUTHORIZED);
    }
    User user = (User) request.getProperty("user");
    checkStudent(user);
    contactDTO.setStudentId(user.getUserId());
    ContactDTO declineContact = myContactUCC.acceptContact(contactDTO);
    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("contact", declineContact);
    return returned;
  }

  /**
   * Get the list of  contacts of this company.
   *
   * @param idCompany The ID of the company
   * @return ObjectNode containing the list of contacts of the specified company
   */
  @GET
  @Path("getAllContactForTheCompany")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getAllContactForTheCompany(@QueryParam("idCompany") int idCompany) {

    List<ContactDTO> contactList = myContactUCC.getAllContactForTheCompany(idCompany);

    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("contacts", contactList);
    return returned;
  }

  /**
   * Get the list of  contacts of this student.
   *
   * @param studentId The id of the student
   * @return ObjectNode containing the list of contacts of the specified student
   */
  @GET
  @Path("getAllContactForTheStudent")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getAllContactForStudent(@QueryParam("studentId") int studentId) {

    List<ContactDTO> contactList = myContactUCC.getAllContactForStudent(studentId);

    ObjectNode returned = jsonMapper.createObjectNode();
    returned.putPOJO("contacts", contactList);
    return returned;
  }
}
