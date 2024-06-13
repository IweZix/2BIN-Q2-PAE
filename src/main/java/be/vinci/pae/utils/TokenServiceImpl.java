package be.vinci.pae.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

/**
 * This class implements the TokenService interface.
 */
public class TokenServiceImpl implements TokenService {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));

  @Override
  public String createToken(int id) {
    long expirationTime = System.currentTimeMillis() + (60 * 60 * 1000);

    return JWT.create().withIssuer("auth0")
        .withClaim("id", id)
        .withExpiresAt(new Date(expirationTime))
        .sign(this.jwtAlgorithm);
  }

}
