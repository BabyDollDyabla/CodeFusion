/**
 * Engine
 *
 * @author Fabrizio Parlani
 */
package fabermaster.code.fusion.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;





import org.apache.commons.configuration.ConfigurationException;

import fabermaster.code.fusion.common.BuilderException;
import fabermaster.code.fusion.common.BuilderException.EErrors;
import fabermaster.code.fusion.common.PlaceholderInfo;
import fabermaster.code.fusion.common.TargetInfo;
import fabermaster.code.fusion.logging.LogManager;
import fabermaster.code.fusion.util.Assorted;
import fabermaster.code.fusion.util.FSUtilities;
import fabermaster.code.fusion.util.mappers.IProperty.EParameters;
import fabermaster.code.fusion.util.mappers.ITemplate;
import fabermaster.code.fusion.util.mappers.ITemplate.ECodeTarget;
import fabermaster.code.fusion.util.mappers.ITemplate.EPlaceholderType;
import fabermaster.code.fusion.util.mappers.ITemplate.EServiceBlock;
import fabermaster.code.fusion.util.properties.ConfigurationFactory;

/**
 * @author Fabrizio Parlani
 *
 */
public class Engine implements IEngine
{

  private static final long       serialVersionUID    = 2013167186854880427L;

  //declare logger instance
  private final static LogManager logger              = LogManager.getInstance(Engine.class);

  //declare variable where to store placeholder file(s) path and base output folder
  private String                  propertiesFileName  = null;

  /**
   * Class constructor
   * 
   * @author Fabrizio Parlani
   */
  public Engine()
  throws BuilderException
  {
    //log operation
    logger.info("[Start] - System properties retrieval process");
    //get configuration properties file fully qualified file name
    propertiesFileName = loadSystemProperties(ITemplate.CONFIGURATION_FILE);
    //log operation
    logger.info("[End] - System properties retrieval process");
    
    //check for a valid provided properties file name
    if (Assorted.isNotEmpty(propertiesFileName, true))
    {
      try
      {
        //log operation
        logger.info("[Start] - Loading specified properties file");
        //try to load provided properties file
        ConfigurationFactory.getInstance(null, 
                                         propertiesFileName);
        //log operation
        logger.debug("[Start] - properties done!");
      }
      catch ( ConfigurationException ex )
      {
        //log occurred error
        logger.error(String.format("  ATTENTION: A ConfigurationException has occurred during [%s] properties file loading", propertiesFileName), ex);
        //throw custom exception
        throw new BuilderException(EErrors.GENERAL_PROPERTIES_FILE_CONFIGURATION_ERROR, ex);
      }
    }
    else
    {
      //log occurred error
      logger.error(String.format("  ATTENTION: An invalid or empty value has been provided for the following mandatory system property [%s]", ITemplate.CONFIGURATION_FILE));
      //throw custom exception
      throw new BuilderException(EErrors.GENERAL_PROPERTIES_FILE_INVALID);
    }
    
  }

  /* (non-Javadoc)
   * @see fabermaster.code.fusion.core.IEngine#run(fabermaster.code.fusion.util.IMappers.EServiceBlock, java.util.List)
   */
  @Override
  public void run(String            placeholderFile,
                  EServiceBlock     serviceBlock, 
                  List<ECodeTarget> targets)
  {
    //declare object where to put the targets to process
    List<ECodeTarget> codeTargets = new ArrayList<ECodeTarget>(0);

    //check for a valid provided service block
    if (serviceBlock != null)
    {
      //log operation
      logger.info(String.format("  Processing service block [%s]", serviceBlock.getBlockPrefix()));
      
      //get targets to process
      codeTargets = Assorted.isEmpty(targets) ? serviceBlock.getTargets()
                                              : targets;
      
      //cycle across provided service block targets
      for (ECodeTarget target : codeTargets)
      {
        //check if cycled code target belongs to provided service block
        if (serviceBlock.isCodeTargetAMember(target))
        {
          //log operation
          logger.info(String.format("    Start working with code target [%s]", target.getTargetSuffix()));

          //create specific code template block
          buildTemplates(placeholderFile,
                         serviceBlock.getTargetConfig(target));

          //log operation
          logger.info(String.format("    Code target [%s] has been worked", target.getTargetSuffix()));
        }
        else
        {
          //log operation
          logger.warn(String.format("    ATTENTION: Provided code target [%s] doesn't belong to processing service block [%s]", target.getTargetSuffix(), serviceBlock.getBlockPrefix()));
        }
      }

      //log operation
      logger.info(String.format("  Service block [%s] has been processed", serviceBlock.getBlockPrefix()));
    }
    else
    {
      //log operation
      logger.error("  An invalid service block to process has been provided");
    }
  }

  /* (non-Javadoc)
   * @see fabermaster.code.fusion.core.IEngine#run(fabermaster.code.fusion.util.IMappers.EServiceBlock, fabermaster.code.fusion.util.IMappers.ECodeTarget)
   */
  @Override
  public void run(String        placeholderFile,
                  EServiceBlock serviceBlock, 
                  ECodeTarget   target)
  {
    //call main method properly
    run(placeholderFile,
        serviceBlock,
        (target != null) ? Arrays.asList( new ECodeTarget[] { target } )
                         : (List<ECodeTarget>)null);
  }

  /* (non-Javadoc)
   * @see fabermaster.code.fusion.core.IEngine#run(fabermaster.code.fusion.util.IMappers.EServiceBlock)
   */
  @Override
  public void run(String        placeholderFile,
                  EServiceBlock serviceBlock)
  {
    //call main method properly
    run(placeholderFile,
        serviceBlock,
        (List<ECodeTarget>)null);
  }

  private String loadSystemProperties(String key)
  {
    //declare returning object
    String returningValue = null;
    
    try
    {
      //get system property value
      returningValue = Assorted.checkNullString(System.getProperty(key));
      
      //check for a not empty retrieved property value
      if (Assorted.isEmpty(returningValue, true))
      {
        //log operation
        logger.warn(String.format("  ATTENTION: An empty value has been provided for the [%s] system property", key));
      }
      else
      {
        //log operation
        logger.info(String.format("  Provided [%s] system property has value [%s]", key, returningValue));
      }
    }
    catch ( SecurityException ex )
    {
      //log error
      logger.error(String.format("  ATTENTION: Following SecurityException has occurred during [%s] system property reading", key), ex);
      //set invalid returning value
      returningValue = null;
    }
    catch ( NullPointerException ex )
    {
      //log error
      logger.error(String.format("  ATTENTION: A NullPointerException has occurred during [%s] system property reading", key), ex);
      //set invalid returning value
      returningValue = null;
    }
    catch ( IllegalArgumentException ex )
    {
      //log error
      logger.error(String.format("  ATTENTION: A IllegalArgumentException has occurred during [%s] system property reading", key), ex);
      //set invalid returning value
      returningValue = null;
    }

    //return retrieved system property value or empty value if something went wrong or if provided value is empty
    return Assorted.checkNullString(returningValue);
  }

  /**
   * Builds template(s) for provided code target getting placeholder from passed placeholder file
   * 
   * @param placeholderFile
   * @param targetInfo
   *
   * @author Fabrizio Parlani
   */
  private void buildTemplates(String     placeholderFile,
                              TargetInfo targetInfo)
  {
    try
    {
    	logger.info(String.format("targetInfo is [%s]", (targetInfo != null ? "not null" : "null")));
    	
    	String templateFolder = new StringBuilder(EParameters.TEMPLATES_BASE_PATH.getAttributeValue()).append(targetInfo.getTemplateFolder()).toString();
    	logger.info(String.format("processing template folder [%s]", templateFolder));
    	
    	
      //cycle template folder for file
      for (File template : FSUtilities.getTemplates(templateFolder))
      {
        logger.info(String.format("cycled file is [%s]", template.getName()));
        //check if current cycled item is a normal file type
        if (template.isFile())
        {
          //declare object where to store probably retrieved placeholder map
          Map<PlaceholderInfo, String> placeholderMap = FSUtilities.getPlaceholderMap(new StringBuilder(EParameters.PLACEHOLDER_BASE_PATH.getAttributeValue())
                                                                                                .append(File.separator)
                                                                                                .append(placeholderFile)
                                                                                                .append(PLACEHOLDER_FILE_SUFFIX)
                                                                                                .toString(), 
                                                                                      targetInfo.getPropertiesPrefix());

          //processing current template getting proper substitution tag list from provided placeholder file and writing destination file
          FSUtilities.stringToDestination(composeOutputFolder(targetInfo.getOutputFolder(),
                                                              targetInfo.isPackageFolderToAdd(),
                                                              placeholderFile)
                                                       .append(template.getName().replace("XXX", placeholderMap.get(new PlaceholderInfo("[fileNamePrefix]", EPlaceholderType.SIMPLE))))
                                                       .toString(), 
                                          processTemplate(template,
                                                          placeholderMap));

          //emptying used placeholder map
          placeholderMap.clear();
          //destroy used object
          placeholderMap = null;
        }
      }
    }
    catch ( BuilderException ex )
    {
      //log occurred error
      logger.error(String.format("        ATTENTION: following error has occurred: code [%s] - message [%s]", ex.getErrorCode(), ex.getErrorDescription()));
    }
  }

  /**
   * Processes provided template replacing tag from passed placeholder map object
   * 
   * @param template
   * @param placeholder
   * @return
   * @throws BuilderException
   *
   * @author Fabrizio Parlani
   */
  /**
   */
  private String processTemplate(File                         template,
                                 Map<PlaceholderInfo, String> placeholder)
  throws BuilderException
  {
    //get template file content
    String templateContent = FSUtilities.templateToString(template);

    //substitute placeholder tags
    for (PlaceholderInfo key : placeholder.keySet())
    {
    
      logger.info(String.format("      processing placeholder [%s]", key));
      //check for placeholder kind
      switch (key.getType())
      {
        case SIMPLE:
        {
          //log operation
          logger.info(String.format("      Replacement of simple tag [%s] with value [%s]", key.getName(), placeholder.get(key)));
          //simple tag substitution
          templateContent = templateContent.replace(key.getName(), 
                                                    placeholder.get(key));
          //exit branch
          break;
        }
        case LIST:
        {
          //nothing to do at the moment
        }
        default:
        {
          //log operation
          logger.info(String.format("      A not recognized placeholder type has been found [%s]", key.getType().name()));
          //nothing to do...
        }
      }
    }

    //return processed template
    return templateContent;
  }

  /**
   * Properly composes destination file output folder
   * 
   * @param outputFolder
   * @param isPackageFolderToAdd
   * @param packageName
   * @return
   *
   * @author Fabrizio Parlani
   */
  private StringBuilder composeOutputFolder(String  outputFolder,
                                            boolean isPackageFolderToAdd,
                                            String  packageName)
  {
    //return properly composed output folder
    StringBuilder outFolder = new StringBuilder(EParameters.OUTPUT_BASE_PATH.getAttributeValue())
                                        .append(File.separator)
                                        .append(outputFolder)
                                        .append(isPackageFolderToAdd ? new StringBuilder(packageName)
                                                                                 .append(File.separator)
                                                                     : "");
    //log operation
    logger.info(String.format("      Destination file will be written into the following out path [%s]", outFolder));

    //return composed output folder
    return outFolder;
  }
}
