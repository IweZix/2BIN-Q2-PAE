package be.vinci.pae.dal;

/**
 * This is the interface of DalServiceImpl.
 */
public interface DALServices {

  /**
   * Start a transaction.
   */
  void initTransaction();

  /**
   * Commit the transaction.
   */
  void commitTransaction();

  /**
   * Rollback the transaction.
   */
  void rollbackTransaction();
}