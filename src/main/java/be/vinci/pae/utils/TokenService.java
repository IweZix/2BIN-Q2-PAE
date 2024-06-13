package be.vinci.pae.utils;

/**
 * This is the interface of TokenServiceImpl.
 */
public interface TokenService {

  /**
   * Create a new token.
   *
   * @param id the user id
   * @return the token string
   */
  String createToken(int id);


}
