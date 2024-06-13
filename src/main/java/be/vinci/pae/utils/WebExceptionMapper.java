package be.vinci.pae.utils;

import be.vinci.pae.utils.exception.AlreadyBlacklisted;
import be.vinci.pae.utils.exception.ConnectionNullExeption;
import be.vinci.pae.utils.exception.ContactAlreadyAcceptedException;
import be.vinci.pae.utils.exception.DataAlreadyExistException;
import be.vinci.pae.utils.exception.DataInformationRequired;
import be.vinci.pae.utils.exception.DataIsNotRightStatus;
import be.vinci.pae.utils.exception.DataNotExistsException;
import be.vinci.pae.utils.exception.FailureException;
import be.vinci.pae.utils.exception.FatalException;
import be.vinci.pae.utils.exception.InternshipProjectIncorrectException;
import be.vinci.pae.utils.exception.InvalidIdException;
import be.vinci.pae.utils.exception.InvalidUserOrPasswordException;
import be.vinci.pae.utils.exception.MeetingTypeIncorrectException;
import be.vinci.pae.utils.exception.PasswordOrUsernameException;
import be.vinci.pae.utils.exception.RoleInvalidException;
import be.vinci.pae.utils.exception.UserNullException;
import be.vinci.pae.utils.exception.VersionIncorrectException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import java.util.logging.Level;

/**
 * WebExceptionMapper class.
 */
public class WebExceptionMapper implements ExceptionMapper<Exception> {

  /**
   * Method exception mapper.
   *
   * @param exception the exception to map
   * @return the response
   */
  private static Response exceptionMapper(Exception exception) {
    if (exception instanceof InvalidUserOrPasswordException) {
      return Response.status(Status.UNAUTHORIZED)
        .entity(exception.getMessage())
        .build();
    }
    if (exception instanceof DataAlreadyExistException) {
      return Response.status(Status.CONFLICT)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof DataNotExistsException) {
      return Response.status(Status.NOT_FOUND)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof PasswordOrUsernameException) {
      return Response.status(Status.UNAUTHORIZED)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof RoleInvalidException) {
      return Response.status(Status.UNAUTHORIZED)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof InvalidIdException) {
      return Response.status(Status.BAD_REQUEST)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof FailureException) {
      return Response.status(Status.INTERNAL_SERVER_ERROR)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof AlreadyBlacklisted) {
      return Response.status(Status.CONFLICT)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof ContactAlreadyAcceptedException) {
      return Response.status(Status.CONFLICT)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof DataIsNotRightStatus) {
      return Response.status(Status.CONFLICT)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof MeetingTypeIncorrectException) {
      return Response.status(Status.UNAUTHORIZED)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof VersionIncorrectException) {
      return Response.status(Status.UNAUTHORIZED)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof DataInformationRequired) {
      return Response.status(Status.UNAUTHORIZED)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof InternshipProjectIncorrectException) {
      return Response.status(Status.UNAUTHORIZED)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof FatalException) {
      return Response.status(Status.INTERNAL_SERVER_ERROR)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof ConnectionNullExeption) {
      return Response.status(Status.INTERNAL_SERVER_ERROR)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof UserNullException) {
      return Response.status(Status.NOT_FOUND)
          .entity(exception.getMessage())
          .build();
    }

    return Response.status(Status.NOT_FOUND)
        .entity(exception.getMessage())
        .build();
  }

  /**
   * Method toResponse.
   *
   * @param exception the exception to map
   * @return the response
   */
  @Override
  public Response toResponse(Exception exception) {
    PrivateLogger.writeError(Level.SEVERE, exception);
    return exceptionMapper(exception);
  }
}
