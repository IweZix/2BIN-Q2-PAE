package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.exception.ConnectionNullExeption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * This class provides basic services to interact with the db.
 */
public class DALServicesImpl implements DALServices, DALBackendServices {

  private final ThreadLocal<Connection> tlConnection;
  private final ThreadLocal<Integer> integerThreadLocal;
  private final BasicDataSource dataSource;

  /**
   * Create connection in the constructor.
   */
  public DALServicesImpl() {
    this.tlConnection = new ThreadLocal<>();
    this.integerThreadLocal = new ThreadLocal<>();
    this.dataSource = dataSource();
  }

  /**
   * Create a data source.
   *
   * @return a data source
   */
  public static BasicDataSource dataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setUrl(Config.getProperty("urlBd"));
    ds.setUsername(Config.getProperty("userDb"));
    ds.setPassword(Config.getProperty("pswDb"));
    ds.setMaxTotal(1);
    return ds;
  }

  private Connection getConnection() {
    try {
      Connection conn = tlConnection.get();
      if (conn == null || conn.isClosed()) {
        conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        tlConnection.set(conn);
      }
      return conn;
    } catch (SQLException e) {
      throw new IllegalStateException("Error while getting connection", e);
    }
  }

  @Override
  public PreparedStatement getPreparedStatement(String sqlQuery) {
    try {
      return getConnection().prepareStatement(sqlQuery);
    } catch (SQLException e) {
      throw new IllegalStateException("Error while creating prepared statement", e);
    }
  }

  @Override
  public void initTransaction() {
    if (integerThreadLocal.get() == null) {
      try {
        integerThreadLocal.set(1);
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        tlConnection.set(conn);
      } catch (SQLException e) {
        throw new ConnectionNullExeption(e.getMessage());
      }
    } else {
      integerThreadLocal.set(integerThreadLocal.get() + 1);
    }
  }

  @Override
  public void commitTransaction() {
    if (integerThreadLocal.get() == 1 && tlConnection.get() != null) {
      integerThreadLocal.remove();
      Connection conn = tlConnection.get();
      try {
        conn.commit();
        conn.setAutoCommit(true);
        tlConnection.remove();
        conn.close();
      } catch (SQLException e) {
        throw new ConnectionNullExeption(e.getMessage());
      }
    } else {
      integerThreadLocal.set(integerThreadLocal.get() - 1);
    }
  }

  @Override
  public void rollbackTransaction() {
    Connection connection = tlConnection.get();

    if (integerThreadLocal.get() == null) {
      try {
        tlConnection.remove();

        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        throw new ConnectionNullExeption(e.getMessage());
      }
    } else {
      integerThreadLocal.set(integerThreadLocal.get() - 1);
      try {
        tlConnection.remove();
        if (connection != null) {
          connection.rollback();
          connection.setAutoCommit(true);
          connection.close();
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
