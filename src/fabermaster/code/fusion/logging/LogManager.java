package fabermaster.code.fusion.logging;

import java.io.Serializable;
import java.text.MessageFormat;



import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.MDC;

/**
 * <p>Title: LogManager</p>
 *
 * <p>Description: The Log System Manager implementation</p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * @author Fabrizio Parlani
 * @version 1.0
 */
public class LogManager implements Serializable 
{
	private static final long serialVersionUID     = 609785862742936012L;

  //declare used objects
  private Logger            logger;
  private String            className            = null;

  /**
   * Log4j MDC attribute names. They identify the MDC attributes that can
   * be referenced by the logger in order to appear in each log line.
   * The values of these constants are the names that must be used in the
   * appender configuration to reference these attributes, using the pattern
   * <b>%X{&lt;attribute name&gt;}</b>, e.g. <b>%X{componentName}</b>.
   */
  private static class MDC_ATTRIBUTE 
  {
    public static final String USER_NAME = "username";
    public static final String THREAD_ID = "thread_id";
  }

 
  /**
   * Gets instance of current used log from log factory
   * 
   * @param category
   * @return LogManager
   * @author Fabrizio Parlani
   */
  public static LogManager getInstance(Class<?> category)
  { //create a new instance if not already loaded
    LogManager log = new LogManager();
    //run log4j basic configuration
    BasicConfigurator.configure();
    //get log from factory
    log.logger     = Logger.getLogger(category);

    //return got logger
    return log;
  }

  /**
   * 
   * @param loggerName
   * @param invokingClass
   * @return LogManager
   * @author Fabrizio Parlani
   */
  public static LogManager getInstance(String   loggerName, 
                                       Class<?> invokingClass)
  {//create a new instance if not already loaded
    LogManager log = new LogManager();
    //run log4j basic configuration
    BasicConfigurator.configure();
    //get log from factory
    log.logger     = Logger.getLogger(loggerName);
    //set provided class name if passed object is valid
    log.className = (invokingClass != null) ? invokingClass.getName().substring(invokingClass.getName().lastIndexOf('.') + 1) : null;

    //return got logger
    return log;
  }

  /**
   * Set or Reset (if provided value is <code>null</code>) the Log MDC username 
   * 
   * @param username
   * @author Fabrizio Parlani
   */
  public void setUsername(String username)
  { //check for provided value
    if (username != null)
    { //add provided username to MDC
      MDC.put(MDC_ATTRIBUTE.USER_NAME, username);
    }
    else
    { //remove probably existing username from MDC
      MDC.remove(MDC_ATTRIBUTE.USER_NAME);
    }
  }

  /**
   * Set or Reset (if provided value is <code>null</code>) the Log MDC thread Id
   * 
   * @param threadId
   * @author Fabrizio Parlani
   */
  public void setThreadId(String userThreadId)
  { //check for provided value
    if (userThreadId != null)
    { //add provided thread Id to MDC
      MDC.put(MDC_ATTRIBUTE.THREAD_ID, userThreadId);
    }
    else
    { //remove probably existing thread Id from MDC
      MDC.remove(MDC_ATTRIBUTE.THREAD_ID);
    }
  }

  /**
   * Clears logger from attributes depending upon the request (e.g.
   * transactionIds). It should be called in the first line of every
   * "thread entry point method", e.g. in the onMessage() methods and methods on
   * the remote interface of in-bound interface EJB.
   */
  public void clear()
  { //remove all MDC attributes
    MDC.remove(MDC_ATTRIBUTE.USER_NAME);
    MDC.remove(MDC_ATTRIBUTE.THREAD_ID);
  }

  /**
   * Maps a trace log message
   * 
   * @param message
   * @author Fabrizio Parlani
   */
  public void trace(Object message)
  {
    logger.trace(message);
  }

  /**
   * Maps a trace log message
   * 
   * @param message
   * @param e
   * @author Fabrizio Parlani
   */
  public void trace(Object    message, 
                    Throwable e)
  {
    logger.trace(message, e);
  }
  
  /**
   * Maps a trace log message
   * 
   * @param message the message string to format
   * @param arguments the parameters to set 
   * 
   * <p>Usage example:</p>
   * <p><code>logger.trace("String: {0}, Integer {1}, MyObject {2}",myString, 10, myObject)</code></p>
   * @author m.pavone
   */
  public void trace(String message, Object... arguments)
  {
    logger.trace(MessageFormat.format(message,arguments));
  }

  /**
   * Maps a debug log message
   * 
   * @param message
   * @author Fabrizio Parlani
   */
  public void debug(Object message)
  {
    logger.debug(message);
  }

  /**
   * Maps a debug log message
   * 
   * @param message the message string to format
   * @param arguments the parameters to set 
   * 
   * <p>Usage example:</p>
   * <p><code>logger.debug("String: {0}, Integer {1}, MyObject {2}",myString, 10, myObject)</code></p>
   * @author m.pavone
   */
  public void debug(String message, Object... arguments)
  {
    logger.debug(MessageFormat.format(message,arguments));
  }
  
  /**
   * Maps a debug log message
   * 
   * @param message
   * @param e
   * @author Fabrizio Parlani
   */
  public void debug(Object    message, 
                    Throwable e)
  {
    logger.debug(message, e);
  }

  /**
   * Maps an info log message
   * 
   * @param message
   * @author Fabrizio Parlani
   */
  public void info(Object message)
  {
    logger.info(message);
  }

  /**
   * Maps an info log message
   * 
   * @param message the message string to format
   * @param arguments the parameters to set 
   * 
   * <p>Usage example:</p>
   * <p><code>logger.info("String: {0}, Integer: {1}, MyObject: {2}",myString, 10, myObject)</code></p>
   * @author m.pavone
   */
  public void info(String message, Object... arguments)
  {
    logger.info(MessageFormat.format(message,arguments));
  }

  /**
   * Maps an info log message
   * 
   * @param message
   * @param e
   * @author Fabrizio Parlani
   */
  public void info(Object    message, 
                   Throwable e)
  {
    logger.info(message, e);
  }

  /**
   * Maps a warning log message
   * 
   * @param message
   * @author Fabrizio Parlani
   */
  public void warn(Object message)
  {
    logger.warn(message);
  }
  
  /**
   * Maps a warning log message
   * 
   * @param message the message string to format
   * @param arguments the parameters to set 
   * 
   * <p>Usage example:</p>
   * <p><code>logger.warn("String: {0}, Integer: {1}, MyObject: {2}",myString, 10, myObject)</code></p>
   * @author m.pavone
   */
  public void warn(String message, Object... arguments)
  {
    logger.warn(MessageFormat.format(message,arguments));
  }

  /**
   * Maps a warning log message
   * 
   * @param message the message string to format
   * @param arguments the parameters to set 
   * @param exception the exception to print
   * 
   * <p>Usage example:</p>
   * <p><code>logger.warn("String: {0}, Integer {1}, MyObject {2}", exception, myString, 10, myObject)</code></p>
   * @author m.pavone
   */
  public void warn(String message, Throwable exception, Object... arguments)
  {
    logger.warn(MessageFormat.format(message,arguments), exception);
  }
  
  /**
   * Maps a warning log message
   * 
   * @param message
   * @param e
   * @author Fabrizio Parlani
   */
  public void warn(Object    message, 
                   Throwable e)
  {
    logger.warn(message, e);
  }

  /**
   * Maps an error log message
   * 
   * @param message
   * @author Fabrizio Parlani
   */
  public void error(Object message)
  {
    logger.error(message);
  }

  /**
   * Maps an error log message
   * 
   * @param message
   * @param e
   * @author Fabrizio Parlani
   */
  public void error(Object    message, 
                    Throwable e)
  {
    logger.error(message, e);
  }

  /**
   * Maps an error log message
   * 
   * @param message the message string to format
   * @param arguments the parameters to set 
   * @param exception the exception to print
   * 
   * <p>Usage example:</p>
   * <p><code>logger.error("String: {0}, Integer {1}, MyObject {2}", exception, myString, 10, myObject)</code></p>
   * @author m.pavone
   */
  public void error(String message, Throwable exception, Object... arguments)
  {
    logger.error(MessageFormat.format(message,arguments), exception);
  }
  
  /**
   * Maps a fatal log message
   * 
   * @param message
   * @author Fabrizio Parlani
   */
  public void fatal(Object message)
  {
    logger.fatal(message);
  }

  /**
   * Maps a fatal log message
   * 
   * @param message
   * @param e
   * @author Fabrizio Parlani
   */
  public void fatal(Object    message, 
                    Throwable e)
  {
    logger.fatal(message, e);
  }

  /**
   * Checks if TRACE log level is enabled
   * 
   * @return
   * @author Fabrizio Parlani
   */
  public boolean isTraceEnabled()
  {
    return logger.isTraceEnabled();
  }

  /**
   * Checks if DEBUG log level is enabled
   * 
   * @return
   * @author Fabrizio Parlani
   */
  public boolean isDebugEnabled()
  {
    return logger.isDebugEnabled();
  }

  /**
   * Checks if INFO log level is enabled
   * 
   * @return
   * @author Fabrizio Parlani
   */
  public boolean isInfoEnabled()
  {
    return logger.isInfoEnabled();
  }

//  /**
//   * Checks if WARN log level is enabled
//   * 
//   * @return
//   * @author Fabrizio Parlani
//   */
//  public boolean isWarnEnabled()
//  {
//    return logger.isWarnEnabled();
//  }
//
//  /**
//   * Checks if ERROR log level is enabled
//   * 
//   * @return
//   * @author Fabrizio Parlani
//   */
//  public boolean isErrorEnabled()
//  {
//    return logger.isErrorEnabled();
//  }
//
//  /**
//   * Checks if FATAL log level is enabled
//   * 
//   * @return
//   * @author Fabrizio Parlani
//   */
//  public boolean isFatalEnabled()
//  {
//    return logger.isFatalEnabled();
//  }

  /**
   * Gets the set logger class name
   * 
   * @return the className
   * @author Fabrizio Parlani
   */
  public String getClassName()
  { //get stored value
    return className;
  }
}
