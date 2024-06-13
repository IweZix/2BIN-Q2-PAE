package be.vinci.pae.ihm.filters;

import be.vinci.pae.business.dto.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

/**
 * This class ensures that only authenticated and authorized users can access protected resources.
 */
@Singleton
@Provider
@Authorize
public class UserAuthorizationRequestFilter implements ContainerRequestFilter {

  @Inject
  private UserUCC userUCC;
  @Inject
  private DALServices dalServices;
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm)
      .withIssuer("auth0").build();


  @Override
  public void filter(ContainerRequestContext requestContext) {
    try {
      String token = requestContext.getHeaderString("Authorization");
      if (token == null) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
            .entity("A token is needed to access this resource").build());
      } else {
        DecodedJWT decodedToken = null;
        try {
          decodedToken = this.jwtVerifier.verify(token);
        } catch (Exception e) {
          throw new WebApplicationException("Invalid token",
              Status.UNAUTHORIZED);
        }
        UserDTO authenticatedUser = userUCC
            .getUserById(decodedToken.getClaim("id").asInt());
        if (authenticatedUser == null) {
          requestContext.abortWith(Response.status(Status.FORBIDDEN)
              .entity("You are forbidden to access this resource").build());
        }
        requestContext.setProperty("user", authenticatedUser);
      }
    } catch (Exception e) {
      throw e;
    }
  }

}