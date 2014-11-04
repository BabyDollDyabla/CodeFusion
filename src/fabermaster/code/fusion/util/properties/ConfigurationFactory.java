package fabermaster.code.fusion.util.properties;

import java.util.HashMap;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import fabermaster.code.fusion.logging.LogManager;
import fabermaster.code.fusion.util.Assorted;

/**
 * <p>Title: ConfigurationFactory</p>
 *
 * <p>Description: Factory used to obtain a global configuration instance of a stored properties file</p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * @author Fabrizio Parlani
 * @version 1.0
 */
public final class ConfigurationFactory 
{
  //declare log manager
  private static LogManager                     logger  = LogManager.getInstance(ConfigurationFactory.class);
  
  //Declare object that stores available configuration instances
	private static HashMap<String, Configuration> storage = new HashMap<String, Configuration>();

  /**
   * Tries to get a configuration instance from provided fileName
   * 
   * @param fileName The name of the file to load
   * @return The probably loaded configuration from provided file
   * @throws ConfigurationException
   * @author Fabrizio Parlani
   */
  private static Configuration getInstance(String fileName) throws ConfigurationException
  {
    try
    { //try to load property configuration from provided file name
      PropertiesConfiguration pConfig = new PropertiesConfiguration(fileName);
      //return probably loaded configuration
      return new Configuration(pConfig);
    }
    catch ( ConfigurationException ex )
    { //log occurred error
      logger.error(buildLogMessage(new StringBuilder("=> ERROR : Unable to load configuration file due to : [")
                                             .append(Assorted.checkNullString(ex.getMessage(), true))
                                             .append("]")));
      //throws an exception if something goes wrong 
      throw new ConfigurationException("ATTENTION : Unable to load following configuration file [" + fileName + "]", ex);
    }
  }
    
  /**
   * Gets global configuration instance for provided file path and file name 
   * 
   * @param filePath The properties file path
   * @param fileName The properties file name
   * @return The global configuration instance for requested resource
   * @throws ConfigurationException
   * @author Fabrizio Parlani
   */
  public static synchronized Configuration getInstance(String filePath, 
                                                       String fileName) 
  throws ConfigurationException
  {
    try
    { //log begin of activity
      logger.info(buildLogMessage(new StringBuilder("[Start] : Configuration Factory - Get Instance")));

      //check over provided file path parameter
      if (!((filePath != null) && (!filePath.trim().isEmpty())))
      { //log a not set file path
        logger.warn(buildLogMessage(new StringBuilder("  ## WARNING ## => Provided property file path is invalid or not set")));
      }
      //executing common checks over mandatory objects
      checkObjects(fileName, 
                   storage);

      //log requested configuration
      logger.info(buildLogMessage(new StringBuilder("  Requested configuration for :")));
      logger.info(buildLogMessage(new StringBuilder("    - File Path [")
                                             .append(filePath)
                                             .append("]")));
      logger.info(buildLogMessage(new StringBuilder("    - File Name [")
                                             .append(fileName)
                                             .append("]")));
      
      //check for an already loaded configuration
      if (!storage.containsKey(fileName))
      { //log configuration adding when not into storing map
        logger.info(buildLogMessage(new StringBuilder("    - Configuration not yet stored.Trying to add...")));
        //adding requested configuration into storing map
        storage.put(fileName, getInstance(((filePath != null) ? filePath : "") + fileName));
        //log added configuration
        logger.info(buildLogMessage(new StringBuilder("      ...done!")));
      }

      //log returning configuration
      logger.info(buildLogMessage(new StringBuilder("  Returning requested configuration")));
      //return configuration
      return (Configuration) storage.get(fileName);
    }
    finally
    { //log finished activity
      logger.info(buildLogMessage(new StringBuilder("[End] : Configuration Factory - Get Instance")));
    }
  }

  /**
   * Gets global configuration instance for provided file path and file name
   * 
   * @param fileName The properties file name
   * @return The global configuration instance for requested resource
   * @throws ConfigurationException
   * @author Fabrizio Parlani
   */
  public static synchronized Configuration instance(String fileName) 
  throws ConfigurationException
  {
    try
    { //log begin of activity
      logger.info(buildLogMessage(new StringBuilder("[Start] : Configuration Factory - Retrieve Instance")));

      //executing common checks over mandatory objects
      checkObjects(fileName, 
                   storage);

      //log requested configuration
      logger.info(buildLogMessage(new StringBuilder("  Requested configuration for :")));
      logger.info(buildLogMessage(new StringBuilder("    - File Name [")
                                             .append(fileName)
                                             .append("]")));

      //check if requested configuration exists inside storing map
      if (!storage.containsKey(fileName))
      { //log occurred error
        logger.error(buildLogMessage(new StringBuilder("      => ERROR : Unable to get configuration instance")));
        //throws an exception if something goes wrong 
        throw new ConfigurationException("ATTENTION : Unable to get configuration instance for [" + fileName + "] file");
      }

      //log returning configuration
      logger.info(buildLogMessage(new StringBuilder("  Returning requested configuration")));
      //return configuration
      return (Configuration) storage.get(fileName);
    }
    finally
    { //log finished activity
      logger.info(buildLogMessage(new StringBuilder("[End] : Configuration Factory - Retrieve Instance")));
    }
  }

  /**
   * Modifies the configuration instance for the specified file name with the new provided one.
   * If provided configuration instance is <i>null</i> the configuration instance related to
   * provided file name will be removed  
   * 
   * @param fileName The configuration file name to modify
   * @param newInstance The new configuration instance to alter.
   * @author Fabrizio Parlani
   */
  public static synchronized void setInstance(String        fileName, 
                                              Configuration newInstance)
  throws ConfigurationException
  {
    try
    { //log begin of activity
      logger.info(buildLogMessage(new StringBuilder("[Start] : Configuration Factory - Set Instance")));

      //executing common checks over mandatory objects
      checkObjects(fileName, 
                   storage);

      //check for operation to perform
      if (newInstance == null)
      { //log delete operation
        logger.info(buildLogMessage(new StringBuilder("  Remove configuration for :")));
        logger.info(buildLogMessage(new StringBuilder("    - File Name [")
                                               .append(fileName)
                                               .append("]")));
        //remove configuration
        storage.remove(fileName);
        //log operation completed
        logger.info(buildLogMessage(new StringBuilder("  Removed.")));
      }
      else
      { //log update operation
        logger.info(buildLogMessage(new StringBuilder("  Alter configuration for :")));
        logger.info(buildLogMessage(new StringBuilder("    - File Name [")
                                               .append(fileName)
                                               .append("]")));
        //insert/update configuration
        storage.put(fileName, newInstance);
        //log operation completed
        logger.info(buildLogMessage(new StringBuilder("  Altered.")));
      }
    }
    finally
    { //log finished activity
      logger.info(buildLogMessage(new StringBuilder("[End] : Configuration Factory - Set Instance")));
    }
  }

  /**
   * Refreshes the configuration instance for the specified file name
   * 
   * @param fileName
   * @throws ConfigurationException
   * @author Fabrizio Parlani
   */
  public static synchronized void refreshInstance(String fileName)
  throws ConfigurationException
  {
    try
    { //log begin of activity
      logger.info(buildLogMessage(new StringBuilder("[Start] : Configuration Factory - Refresh Instance")));

      //executing common checks over mandatory objects
      checkObjects(fileName, 
                   storage);

      //check if requested configuration exists inside storing map
      if (!storage.containsKey(fileName))
      { //log occurred error
        logger.error(buildLogMessage(new StringBuilder("      => ERROR : Unable to get configuration instance to refresh")));
        //throws an exception if something goes wrong 
        throw new ConfigurationException("ATTENTION : Unable to get configuration instance to refresh for [" + fileName + "] file");
      }

      //log instance to refresh
      logger.info(buildLogMessage(new StringBuilder("  Refreshing configuration instance for [")
                                             .append(fileName)
                                             .append("]...")));
      //refresh configuration
      storage.get(fileName).refresh();
      //log operation completed
      logger.info(buildLogMessage(new StringBuilder("  ...done!")));
    }
    finally
    { //log finished activity
      logger.info(buildLogMessage(new StringBuilder("[End] : Configuration Factory - Refresh Instance")));
    }
  }

  /**
   * Refreshes all configuration instances inside storing map
   * 
   * @throws ConfigurationException
   * @author Fabrizio Parlani
   */
  public static synchronized void refreshAll()
  throws ConfigurationException
  {
    try
    { //log begin of activity
      logger.info(buildLogMessage(new StringBuilder("[Start] : Configuration Factory - Refresh All Instances")));

      //check if storing configurations map exists
      if (storage == null)
      { //log a not valid storing configurations map 
        logger.error(buildLogMessage(new StringBuilder("  ##  ERROR  ## => Map of configuration instances is invalid")));
        //throw an exception for invalid storing configurations map
        throw new ConfigurationException("##  ERROR  ## => Map of configuration instances is invalid");
      }

      //check for existing configurations instances inside storing map
      if (!storage.isEmpty())
      { //cycle across stored configuration instances
        for (String configFile : storage.keySet())
        { //log current cycled instance to refresh
          logger.info(buildLogMessage(new StringBuilder("  Refresh configuration instance for [")
                                                 .append("configFile")
                                                 .append("]")));
          //refresh configuration
          storage.get(configFile).refresh();
        }
      }
      else
      { //log no configuration(s) to refresh
        logger.warn(buildLogMessage(new StringBuilder("  WARNING : Configurations Map is empty so there is nothing to refresh")));
      }
    }
    finally
    { //log finished activity
      logger.info(buildLogMessage(new StringBuilder("[End] : Configuration Factory - Refresh All Instances")));
    }
  }

  /**
   * Checks if provided file name and storing configuration map are valid
   * 
   * @param fileName
   * @param storage
   * @throws ConfigurationException
   * @author Fabrizio Parlani
   */
  private static void checkObjects(String                         fileName,
                                   HashMap<String, Configuration> storage)
  throws ConfigurationException
  {
    //check over provided file name parameter
    if (!((fileName != null) && (!fileName.trim().isEmpty())))
    { //log a not set file name 
      logger.error(buildLogMessage(new StringBuilder("  ##  ERROR  ## => Provided property file name is invalid or not set")));
      //throw an exception for invalid file name
      throw new ConfigurationException("##  ERROR  ## => Provided property file name is invalid or not set");
    }
    //check over provided storing configuration map
    if (storage == null)
    { //log a not valid storing configurations map 
      logger.error(buildLogMessage(new StringBuilder("  ##  ERROR  ## => Map of configuration instances is invalid")));
      //throw an exception for invalid storing configurations map
      throw new ConfigurationException("##  ERROR  ## => Map of configuration instances is invalid");
    }
  }

  /**
   * Build properly formatted log message
   * 
   * @param message
   * @return
   * @author Fabrizio Parlani
   */
  private static String buildLogMessage(StringBuilder message)
  { //return properly formatted log message
    return String.format("%-8s%s", "", message.toString());
  }
}
