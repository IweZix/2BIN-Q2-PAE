package be.vinci.pae.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logger implement class.
 */
public class PrivateLogger {

  private static final Logger logger = Logger.getLogger(PrivateLogger.class.getName());
  private static final int MAX_LOG_FILE_SIZE = 1024 * 1024 * 5; // 5242880 bytes or 5 MB
  private static final int MAX_LOG_FILES = 10;
  private static FileHandler fileHandler;


  /**
   * Logger setup.
   */
  public static void setup() {
    try {
      File logDirectory = new File("./logRecord");
      if (!logDirectory.exists() && !logDirectory.mkdirs()) {
        throw new IOException("Unable to create log directory");
      }

      try {
        fileHandler = new FileHandler("./logRecord/log.%g.log", MAX_LOG_FILE_SIZE,
            MAX_LOG_FILES, true);
        // Supprime tous les gestionnaires (handlers) associés au Logger par défaut.
        logger.setUseParentHandlers(false);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      SimpleFormatter simpleFormatter = new SimpleFormatter();
      fileHandler.setFormatter(simpleFormatter);
      logger.addHandler(fileHandler);
      logger.setLevel(Level.INFO);
      logger.info("Logger initialized");
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Exception during logger initialization", e);
    }
  }

  /**
   * Write the error in the log.
   *
   * @param level     Error strength.
   * @param exception the exception to log.
   */
  public static void writeError(Level level, Throwable exception) {
    if (fileHandler != null && fileHandler.isLoggable(
        new LogRecord(level, exception.getMessage()))) {
      logger.log(level, exception.getMessage(), exception);
    } else {
      setup();
      logger.log(level, exception.getMessage(), exception);
    }
  }

  /**
   * Write the error in the log.
   *
   * @param level     Error strength.
   * @param message the message to log.
   */
  public static void writeMessage(Level level, String message) {
    if (fileHandler != null && fileHandler.isLoggable(
        new LogRecord(level, message))) {
      logger.log(level, message);
    } else {
      setup();
      logger.log(level, message);
    }
  }
}
