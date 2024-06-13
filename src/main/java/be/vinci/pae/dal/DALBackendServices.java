package be.vinci.pae.dal;

import java.sql.PreparedStatement;

/**
 * This is the interface of DalServiceImpl.
 */
public interface DALBackendServices {

  /**
   * Returns a PreparedStatement object corresponding to the specified SQL query.
   *
   * @param sqlQuery SQL Query
   * @return a PreparedStatement object corresponding to the SQL query
   */
  PreparedStatement getPreparedStatement(String sqlQuery);
}
