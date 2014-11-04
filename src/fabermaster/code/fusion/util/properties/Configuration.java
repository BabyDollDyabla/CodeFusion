package fabermaster.code.fusion.util.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import fabermaster.code.fusion.logging.LogManager;
import fabermaster.code.fusion.util.Assorted;



/**
 * <p>Title: Configuration</p>
 *
 * <p>Description: Retrieve properties values from provided properties configuration</p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * @author Fabrizio Parlani
 * @version 1.0
 */
public class Configuration 
{
  //declare log manager
  private static LogManager              logger                     = LogManager.getInstance(Configuration.class);
  //declare used object
  private        PropertiesConfiguration configuration              = null;

  //declare list and key/value list dividers
  public static final String             PROPERTY_LIST_DIVIDER      = ",";
  public static final String             PROPERTY_KEY_VALUE_DIVIDER = "|";

  /**
   * Class constructor
   * 
   * @param configuration
   * @throws ConfigurationException
   */
  public Configuration(PropertiesConfiguration configuration)
  {
    this.configuration = configuration;
    //set reloading changed file strategy
    this.configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
  }

  /**
   * Retrieves a value from provided setting name.<br/>
   * Returns retrieved value if found, otherwise returns <i>null</i>
   * 
   * @param name Name of the setting to be retrieved
   * @return The value bound to provided setting name
   * @author Fabrizio Parlani
   */
  public String getSetting(String name)
  { //call overload method properly
    return getSetting(name, null);
  }

  /**
   * Retrieves a value from provided setting name.<br/>
   * Returns retrieved value if found, otherwise provided default value
   * 
   * @param name the name of the setting to be retrieved
   * @param defaultValue the returning value if provided setting has not been retrieved or doesn't exists
   * @return The value bound to provided setting name or the default provided one if setting hasn't been retrieved
   * @author Fabrizio Parlani
   */
  public String getSetting(String name,
                           String defaultValue)
  {
    //check for an invalid properties configuration
    if (configuration == null)
    { //log not loaded configuration
      logger.error("         ATTENTION : Properties configuration is invalid");
      //return an invalid value
      return defaultValue;
    }
    else
    { //declare object where to store probably retrieved value to return
      String value = configuration.getString(name);
      //return retrieved value checking its validity
      return ((value != null) && (!value.trim().isEmpty())) ? value.trim() : defaultValue;
    }
  }

  /**
   * Retrieves a value as Integer from provided setting name.<br/>
   * 
   * @param name the name of the setting to be retrieved
   * @return The value bound to provided setting name 
   * @author m.pavone
   */
  public Integer getIntSetting(String name) {
      Integer temp = null;
      try {
    	  temp = configuration.getInt(name);
      } catch (NoSuchElementException ex) {
    	  logger.error("An error occurred while retrieving an int value from properties file",ex);
      }
      return temp;
  }
  
  /**
   * Retrieves a collection of values from provided setting name.<br/>
   * Returns retrieved collection of values if found, otherwise returns <i>null</i>
   * 
   * @param name Name of the collection of settings to be retrieved
   * @return The collection of values bound to provided setting name
   * @author Fabrizio Parlani
   */
  public String[] getSettings(String name)
  {
    //check for an invalid properties configuration
    if (configuration == null)
    { //log not loaded configuration
      logger.error("         ATTENTION : Properties configuration is invalid");
      //return an invalid values collection
      return null;
    }
    else
    { //declare object where to store probably retrieved values collection to return
      String[] values = configuration.getStringArray(name);
      //return retrieved values collection checking its validity
      return ((values != null) && (!(values.length == 0))) ? values : null;
    }
  }
  
  /**
   * Gets configuration properties keys set
   * 
   * @return The configuration properties keys set
   * @author Fabrizio Parlani
   */
  @SuppressWarnings("unchecked")
  public Set<String> getKeys()
  {
    //check for an invalid properties configuration
    if (configuration == null)
    { //log not loaded configuration
      logger.error("         ATTENTION : Properties configuration is invalid");
      //return an invalid values collection
      return null;
    }
    else
    { //return cast iterator into a set of properties key
      return (Set<String>)configuration.getKeys();
    }
  }

  /**
   * Adds the provided setting into the global configuration object
   * 
   * @param name The name of the setting to be added
   * @param value The value for the setting
   * @author Fabrizio Parlani
   */
  public void addSetting(String name,
                         String value)
  {
    //check for an invalid properties configuration
    if (configuration == null)
    { //log not loaded configuration
      logger.error("         ATTENTION : Properties configuration is invalid");
    }
    else
    { //try to add provided setting with given value
      configuration.addProperty(name, value);
    }
  }

  /**
   * Modifies a setting in the global configuration object with provided value.<br/>
   * Overwrites any existing settings with the same name
   * 
   * @param name The name of the setting to be altered
   * @param value The new value for the setting
   * @author Fabrizio Parlani
   */
  public void setSetting(String name, 
                         String value)
  {
    //check for an invalid properties configuration
    if (configuration == null)
    { //log not loaded configuration
      logger.error("         ATTENTION : Properties configuration is invalid");
    }
    else
    { //try to set provided setting with given value
      configuration.setProperty(name, value);
    }
  }

  /**
   * Removes the provided setting from the global configuration object
   * 
   * @param name The name of the setting to remove
   * @author Fabrizio Parlani
   */
  public void removeSetting(String name)
  {
    //check for an invalid properties configuration
    if (configuration == null)
    { //log not loaded configuration
      logger.error("         ATTENTION : Properties configuration is invalid");
    }
    else
    { //try to remove provided setting
      configuration.clearProperty(name);
    }
  }

  /**
   * Checks if provided setting exists inside the global configuration object
   * 
   * @param name The name of the setting to check
   * @return <i>true</i> if setting exists otherwise <i>false</i>
   * @author Fabrizio Parlani
   */
  public boolean existsSetting(String name)
  {
    //check for an invalid properties configuration
    if (configuration == null)
    { //log not loaded configuration
      logger.error("         ATTENTION : Properties configuration is invalid");
      //return setting not found
      return false;
    }
    else
    { //return check about existence of provided setting
      return configuration.containsKey(name);
    }
  }

  /**
   * Reloads the global configuration object
   * 
   * @author Fabrizio Parlani
   */
  public void refresh()
  {
  //check for an invalid properties configuration
    if (configuration == null)
    { //log not loaded configuration
      logger.error("         ATTENTION : Properties configuration is invalid");
    }
    else
    { //reload properties configuration
      configuration.reload();
    }
  }

  /**
   * Returns a list of values retrieved from a properties file comma separated list
   * 
   * @param propertiesKey to search for
   * @return the retrieved list of Strings
   * @author m.pavone
   */
  public List<String> getListValues(String propertiesKey)
  { //declare object where to store retrieved values
    List<String> values = null;
    try
    { //try to values from properties configuration
      values = Arrays.asList(configuration.getStringArray(propertiesKey));
    }
    catch ( Exception ex )
    { 
    	logger.error("         ATTENTION : Properties configuration is invalid");
    }
    //return list
    return values;
  }

  /**
   * Returns a sorted values list from a set of values given from properties file 
   * or from a default stored set if properties file is not existing 
   * 
   * @param propertiesFileName
   * @param propertiesKey
   * @param defaultKey
   * @return
   * @author Fabrizio Parlani
   */
  public static List<String> getListValues(String propertiesFileName,
                                           String propertiesKey,
                                           String defaultList)
  { //declare object where to store retrieved values
    List<String> values = null;

    try
    { //try to get enterprise types from properties configuration
      values = Arrays.asList(ConfigurationFactory.instance(Assorted.checkNullString(propertiesFileName, true))
                                                  .getSettings(propertiesKey));
    }
    catch ( Exception ex )
    { //log occurred error
      logger.warn("    ATTENTION!!!  Unable to get list values fro provided key [" + propertiesKey + "], so we try to return list from probably provided default values list string [" + defaultList + "] dividing it with [" + Configuration.PROPERTY_LIST_DIVIDER + "] divider");
      //fall-back to default values
      if(Assorted.isNotEmpty(defaultList, true))
      { 
        try
        { //return default list split by default delimiter
          values = Arrays.asList(defaultList.split(Configuration.PROPERTY_LIST_DIVIDER));
        }
        catch ( PatternSyntaxException pse )
        { //log occurred error
          logger.error("    ATTENTION!!! An empty values list will be returned because method is unable to get list values from provided default list [" + defaultList + "] due to following Pattern Syntax Exception", pse);
          //set an empty list
          values = new ArrayList<String>(0);
        }
      }
    }

    //return list
    return values;
  }

  /**
   * Retrieves a properties contains a list of values and tries to cast it into a list object
   * 
   * @param propertiesFileName
   * @param propertyName
   * @param propertyDescription
   * @return
   * @author Fabrizio Parlani
   */
  public static List<String> getMappedListProperty(String propertiesFileName,
                                                   String propertyName,
                                                   String propertyDescription)
  {
    try
    { //get properties file where to search provided property key list value and retruen probably retrieved list of values
      return Configuration.getListValues(propertiesFileName, 
                                         propertyName, 
                                         "");
    }
    catch ( Exception ex )
    { //log occurred error
      logger.error("    ATTENTION!!! An error has occurred during " + propertyDescription + " retrieval process from properties file through provided key [" + propertyName + "]");
      //return an empty object
      return new ArrayList<String>(0);
    }
  }

  /**
   * Retrieves a properties contains a key/value list of values and tries to cast it into a map object
   * 
   * @param propertiesFileName
   * @param propertyName
   * @param propertyDescription
   * @return
   * @author Fabrizio Parlani
   */
  public static  Map<String, String> getMappedKeyValueProperty(String propertiesFileName,
                                                               String propertyName,
                                                               String propertyDescription)
  { //declare returning object
    Map<String, String> result = new LinkedHashMap<String, String>(0);

    try
    { //get raw list of key/value values to process, probably occurred error has been handled by called method
      List<String> rawList =  getMappedListProperty(propertiesFileName,
                                                    propertyName,
                                                    propertyDescription);
      //check for retrieved raw key/value values
      if (Assorted.isNotEmpty(rawList))
      { //cycling filled list and build returning object
        for (String rawItem : rawList)
        { //check for an existing raw item key/value divider
          if (rawItem.indexOf(Configuration.PROPERTY_KEY_VALUE_DIVIDER) > -1)
          { 
            try
            { //store key/value item dividing key and value from cycled raw item 
              result.put(rawItem.substring(0, rawItem.indexOf(Configuration.PROPERTY_KEY_VALUE_DIVIDER)), 
                         rawItem.substring(rawItem.indexOf(Configuration.PROPERTY_KEY_VALUE_DIVIDER) + Configuration.PROPERTY_KEY_VALUE_DIVIDER.length()));
            }
            catch ( IndexOutOfBoundsException ex )
            { //log occurred error 
              logger.error("    ATTENTION!!! An Index Out Of Bound Exception has occurred during raw item [" + rawItem + "] processing for " + propertyDescription + " attribute retrieved from properties file through provided key [" + propertyName + "] so the same value will be stored for key and value", ex);
              //however store the key/value item with the same value
              result.put(rawItem, rawItem);
            }
          }
          else
          { //log attribute divider not found so we fill the key value item with the same value
            logger.warn("    ATTENTION!!! Cycled raw item retrieved for " + propertyDescription + " attribute from properties file through provided key [" + propertyName + "] doesn't contains a key/value divider so the same value will be stored for key and value");
            //store key/value item with the same values
            result.put(rawItem, rawItem);
          }
        }
        //return built map
        return result;
      }
      else
      { //log empty list retrieved
        logger.warn("    ATTENTION!!! An empty list for " + propertyDescription + " attribute has been retrieved from properties file through provided key [" + propertyName + "]");
        //return an empty object
        return result;
      }
    }
    catch ( Exception ex )
    { //log occurred error
      logger.error("    ATTENTION!!! An error has occurred during " + propertyDescription + " retrieval process from properties file through provided key [" + propertyName + "]", ex);
      //return an empty object
      return new LinkedHashMap<String, String>(0);
    }
  }
}
