/**
 * IProperty
 *
 * @author Fabrizio Parlani
 */
package fabermaster.code.fusion.util.mappers;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import fabermaster.code.fusion.logging.LogManager;
import fabermaster.code.fusion.util.Assorted;
import fabermaster.code.fusion.util.properties.ConfigurationFactory;



/**
 * @author Fabrizio Parlani
 *
 */
public interface IProperty
{
  
  /**
   * Enumeration used to map internal component used properties file(s)
   * 
   * @author Fabrizio Parlani
   */
  public enum EComponentProperties
  {
    PROPERTIES_FILE       ("CF-PropertiesFile",   System.getProperty(ITemplate.CONFIGURATION_FILE));
    
    //declare enumeration attributes
    private String parameterName;
    private String fileKeyName;
    
    /**
     * Enumeration constructor
     * 
     * @param parameter
     * @param keyName
     * @author Fabrizio Parlani
     */
    private EComponentProperties(String parameter,
                                 String keyName)
    {
      this.parameterName = parameter;
      this.fileKeyName   = keyName;
    }

    /**
     * @return the parameterName
     * @author Fabrizio Parlani
     */
    public String getParameterName()
    {
      return parameterName;
    }

    /**
     * @return the fileKeyName
     * @author Fabrizio Parlani
     */
    public String getFileKeyName()
    {
      return fileKeyName;
    }
  }
  
  /**
   * Enumeration used to map customizable parameters in order to get attribute value from properties file(s)
   * 
   * @author Fabrizio Parlani
   */
  public enum EParameters
  {
    PLACEHOLDER_BASE_PATH                 ("The placheholder files base path",                EComponentProperties.PROPERTIES_FILE,  "code.fusion.placeholder.base.path"),
    TEMPLATES_BASE_PATH                   ("The templates files base path",                   EComponentProperties.PROPERTIES_FILE,  "code.fusion.templates.base.path"),
    OUTPUT_BASE_PATH                      ("The generated output files base path",            EComponentProperties.PROPERTIES_FILE,  "code.fusion.output.base.path"),
    SERVICE_OUTPUT_PACKAGE                ("The Service Exposure Output Package - up",        EComponentProperties.PROPERTIES_FILE,  "code.fusion.service.output.package"),
    SERVICE_BLUEPRINT_OUTPUT_PACKAGE      ("The Service Exposure Blueprint Repository folder",EComponentProperties.PROPERTIES_FILE,  "code.fusion.service.blueprint.repository.output.folder"),
    TRANSFORMER_OUTPUT_PACKAGE            ("The Service Transformer Output Package - up",     EComponentProperties.PROPERTIES_FILE,  "code.fusion.transformer.output.package"),
    VALIDATOR_OUTPUT_PACKAGE              ("The Service Validator Output Package - up",       EComponentProperties.PROPERTIES_FILE,  "code.fusion.validator.output.package"),
    ERRORS_OUTPUT_FOLDER                  ("The Service Errors Configuration Output Folder",  EComponentProperties.PROPERTIES_FILE,  "code.fusion.error.configuration.output.folder"),
    XSD_OUTPUT_PACKAGE                    ("The Service XSD Repository folder",               EComponentProperties.PROPERTIES_FILE,  "code.fusion.xsd.repository.output.folder"),
    TENANT_BLUEPRINT_OUTPUT_PACKAGE       ("The Tenant Blueprint Repository folder",          EComponentProperties.PROPERTIES_FILE,  "code.fusion.tenant.blueprint.repository.output.folder"),
    TENANT_SERVICE_IMPL_OUTPUT_PACKAGE    ("The Tenant Service Impl Repository folder",       EComponentProperties.PROPERTIES_FILE,  "code.fusion.tenant.service.impl.output.package"),
    API_DTO_OUTPUT_PACKAGE                ("The Api dto Repository folder",                   EComponentProperties.PROPERTIES_FILE,  "code.fusion.api.dto.output.package"),
    DAO_SERVICE_OUTPUT_PACKAGE            ("The Dao Service Repository folder",               EComponentProperties.PROPERTIES_FILE,  "code.fusion.dao.service.output.package"),
    DAO_SERVCIE_IMPL_OUTPUT_PACKAGE       ("The Dao Service Impl Repository folder",          EComponentProperties.PROPERTIES_FILE,  "code.fusion.dao.service.impl.output.package"),
    DAO_VALIDATOR_OUTPUT_PACKAGE          ("The Dao Validator Repository folder",             EComponentProperties.PROPERTIES_FILE,  "code.fusion.dao.validator.output.package")
    ;

    //declare logger instance
    private static LogManager          logger         = LogManager.getInstance(EParameters.class);
    
    //declare attribute
    private        String               description;
    private        EComponentProperties fileKey;
    private        String               propertyKey;

    /**
     * Enumeration constructor
     * 
     * @param description
     * @param fileKey
     * @param propertyKey
     * @author Fabrizio Parlani
     */
    private EParameters(String               description,
                        EComponentProperties fileKey,
                        String               propertyKey)
    {
      this.description = description;
      this.fileKey     = fileKey;
      this.propertyKey = propertyKey;
    }

    /**
     * Gets stored <code>description</code> parameter value
     *
     * @return the description
     * @author Fabrizio Parlani
     */
    public String getDescription()
    {
      return description;
    }

    /**
     * Gets stored <code>fileKey</code> parameter value
     *
     * @return the fileKey
     * @author Fabrizio Parlani
     */
    private EComponentProperties getFileKey()
    {
      return fileKey;
    }

    /**
     * Gets stored <code>propertyKey</code> parameter value
     *
     * @return the propertyKey
     * @author Fabrizio Parlani
     */
    private String getPropertyKey()
    {
      return propertyKey;
    }

    /**
     * Gets property attribute value from file
     * 
     * @return
     * @author Fabrizio Parlani
     */
    public String getAttributeValue()
    { //Tries to retrieve mapped attribute value from properties file 
      try
      { //return probably retrieve attribute value from properties file
        return ConfigurationFactory.getInstance(null, getFileKey().getFileKeyName())
                                   .getSetting(getPropertyKey());
      }
      catch ( ConfigurationException ex )
      { //log occurred error
        logger.error("    - ATTENTION : Unable to get [" + getPropertyKey() + "] attribute value from [" + getFileKey().getFileKeyName() + "] properties file", ex);
        //return invalid object
        return null;
      }
    }
    
    /**
     * Gets property attribute values from file
     * 
     * @return
     * @author Fabrizio Parlani
     */
    public List<String> getAttributeValues()
    { //Tries to retrieve mapped attribute values from properties file 
      try
      { //return probably retrieve attribute values from properties file
        return ConfigurationFactory.getInstance(null, getFileKey().getFileKeyName())
                                   .getListValues(getPropertyKey());
      }
      catch ( ConfigurationException ex )
      { //log occurred error
        logger.error("    - ATTENTION : Unable to get [" + getPropertyKey() + "] attribute values from [" + getFileKey().getFileKeyName() + "] properties file", ex);
        //return invalid object
        return null;
      }
    }

    /**
     * Tries to return a probably existing enumeration instance that matches provided property key
     * 
     * @param propertyKey
     * @return
     * @author Fabrizio Parlani
     */
    public static EParameters fromPropertyKey(String propertyKey)
    { //check for a valid provided key
      if (Assorted.isNotEmpty(propertyKey, true))
      { //cycle across enumeration items
        for (EParameters item : EParameters.values())
        { //check for cycled item property key equals to provided one
          if (Assorted.equals(item.getPropertyKey(), propertyKey))
          { //return retrieved item
            return item;
          }
        }
      }

      //here provided key has not been found or is invalid, so an invalid object will be returned
      return null;
    }
  }
}
