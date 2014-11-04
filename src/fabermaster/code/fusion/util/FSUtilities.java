/**
 * FSUtilities
 *
 * @author Fabrizio Parlani
 */
package fabermaster.code.fusion.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import fabermaster.code.fusion.common.BuilderException;
import fabermaster.code.fusion.common.BuilderException.EErrors;
import fabermaster.code.fusion.common.PlaceholderInfo;
import fabermaster.code.fusion.logging.LogManager;
import fabermaster.code.fusion.util.mappers.ITemplate.EPlaceholderType;

/**
 * @author Fabrizio Parlani
 *
 */
public class FSUtilities
{
  //declare log manager 
  private final static LogManager logger = LogManager.getInstance(FSUtilities.class);

  /**
   * Retrieve placeholder belonging to provided <code>placeholderPrefix</code> into passed <code>placeholderFile</code>
   * 
   * @param placeholderFile
   * @param placeholderPrefix
   * @return
   * @throws Exception
   *
   * @author Fabrizio Parlani
   */
  public static Map<PlaceholderInfo, String> getPlaceholderMap(String placeholderFile, 
                                                               String placeholderPrefix)
  throws BuilderException
  {
    //declare returning object
    Map<PlaceholderInfo, String> placeholderMap = null;
    
    //declare used object(s)
    Properties                   properties     = new Properties();

    try
    {
      //log operation
      logger.info(String.format("            Reading following configuration file : [%s]", Assorted.checkNullString(placeholderFile)));
      logger.info(String.format("              - Substition Tag Filter : [%s]", Assorted.checkNullString(placeholderPrefix)));
      //load provided properties file
      properties.load(new FileInputStream(placeholderFile));
      
      //check for existing properties keys inside loaded file
      if ((properties.size() > 0) && 
          (Assorted.isNotEmpty(properties.keySet())))
      {
        //create returning object
        placeholderMap = new HashMap<PlaceholderInfo, String>(0);

        //load values into map cycling properties key set
        for (Object key : properties.keySet())
        {
          //check for specific key to put into map
          if (((String)key).contains(placeholderPrefix))
          {
            //get key without prefix
            String cleanedKey = ((String)key).replace(placeholderPrefix, "");

            //log retrieved tag substitution key and value
            logger.info(String.format("                # Key [(%s, %s)] => Value [%s]", cleanedKey, EPlaceholderType.retrieveType(cleanedKey.substring(0, 1)), properties.getProperty((String)key)));

            //insert key and value into returning map
            placeholderMap.put(new PlaceholderInfo(cleanedKey, 
                                                   EPlaceholderType.retrieveType(cleanedKey.substring(0, 1))), 
                               properties.getProperty((String)key));
          }
        }
        //log operation
        logger.info("              - Substitution Tag Map filled");
        logger.info("            Configuration File successfully read");
      }
      else
      {
        //log operation
        logger.info("            Configuration File is empty");
      }

      //return filled map or invalid object if provided configuration file is empty
      return placeholderMap;
    }
    catch ( IOException ex )
    {
      //log occurred error
      logger.error("  ATTENTION!!! Following IOException has occurred", ex);
      //throw custom exception
      throw new BuilderException(EErrors.CONFIGURATION_FILE_READING_ERROR,
                                 ex);
    }
    catch ( IllegalArgumentException ex )
    {
      //log occurred error
      logger.error("  ATTENTION!!! Following IllegalArgumentException has occurred", ex);
      //throw custom exception
      throw new BuilderException(EErrors.CONFIGURATION_FILE_ILLEGAL_ARGUMENT,
                                 ex);
    }
  }

  /**
   * Tries to get folder file name list
   * 
   * @param folder
   * @return
   *
   * @author Fabrizio Parlani
   */
  public static File[] getTemplates(String folder)
  {
    //call overloaded method properly
    return getTemplates(folder,
                        null);
  }
  /**
   * Tries to get folder filtered file name list
   * 
   * @param folder
   * @param extensionFilter
   * @return
   *
   * @author Fabrizio Parlani
   */
  public static File[] getTemplates(String folder,
                                    String extensionFilter)
  {
    try
    {
      //return the list of filtered files
      return (Assorted.isNotEmpty(extensionFilter, true)) ? (new File(folder)).listFiles(new TemplateFileFilter(extensionFilter))
                                                          : (new File(folder)).listFiles();
    }
    catch ( SecurityException ex )
    {
      //log operation
      logger.warn("  ATTENTION: Following SecurityException has occurred during folder filtered file list retrieval process", ex);
      //returns an empty file list name
      return new File[] { };
    }
  }

  /**
   * Converts provided template file into a {@link String} object
   * 
   * @param template
   * @return
   * @throws BuilderException
   *
   * @author Fabrizio Parlani
   */
  public static String templateToString(File template)
  throws BuilderException
  {
    //check for a valid provided object
    if (template != null)
    {
      //check for provided template
      if (template.isFile())
      {
        //declare reader object
        BufferedReader reader = null;
        
        //read provided template file
        try
        {
          //declare used objects
          StringBuilder   buffer   = new StringBuilder();
          String          fileLine = null;
  
          //open template file
          reader = new BufferedReader(new FileReader(template));
  
          //opened template file reading cycle
          while ((fileLine = reader.readLine()) != null)
          {
            //append read file line
            buffer.append(fileLine).append("\n");
          }
  
          //return converted file into String object        
          return buffer.toString();
        }
        catch ( FileNotFoundException ex )
        {
          //log occurred error
          logger.error(String.format("  ATTENTION: A FileNotFoundException has occurred for [%s]", template.getName()), ex);
          //throw custom exception
          throw new BuilderException(EErrors.TEMPLATE_FILE_NOT_FOUND, 
                                     ex);
        }
        catch ( IOException ex )
        {
          //log occurred error
          logger.error(String.format("  ATTENTION: A IOException has occurred for [%s]", template.getName()), ex);
          //throw custom exception
          throw new BuilderException(EErrors.TEMPLATE_FILE_READING_ERROR, 
                                     ex);
        }
        finally
        {
          //however close probably opened file reader
          if (reader != null)
          {
            try
            {
              //close reader
              reader.close();
            }
            catch ( IOException ex )
            {
              //log error
              logger.error("  ATTENTION: Unable to close opened file due to following occurred IOException", ex);
            }
          }
          //destroy used object
          reader = null;
        }
      }
    }

    //here provided file is not a normal file type so we return an invalid conversion object
    return null;
  }

  /**
   * Writes provided file content obtained after a tag substitution into a {@link File} object
   * 
   * @param destinationFile
   * @param content
   * @throws BuilderException
   *
   * @author Fabrizio Parlani
   */
  public static void stringToDestination(String destinationFile,
                                         String content)
  throws BuilderException
  {
    //declare used writer object
    PrintWriter writer      = null;

    try
    {
      //create probably not existing file or override previously existing one putting into provided content   
      FileUtils.writeStringToFile(new File(destinationFile), 
                                  content);
    }
    catch ( FileNotFoundException ex )
    {
      //log occurred error
      logger.error(String.format("  ATTENTION: A FileNotFoundException has occurred for [%s]", destinationFile), ex);
      //throw custom exception
      throw new BuilderException(EErrors.DESTINATION_FILE_NOT_FOUND, 
                                 ex);
    }
    catch ( SecurityException ex )
    {
      //log occurred error
      logger.error(String.format("  ATTENTION: A SecurityException has occurred for [%s]", destinationFile), ex);
      //throw custom exception
      throw new BuilderException(EErrors.DESTINATION_FILE_WRITING_ERROR, 
                                 ex);
    }
    catch ( IOException ex )
    {
      //log occurred error
      logger.error(String.format("  ATTENTION: A IOException has occurred for [%s]", destinationFile), ex);
      //throw custom exception
      throw new BuilderException(EErrors.DESTINATION_FILE_WRITING_ERROR, 
                                 ex);
    }
    finally
    {
      //however close probably opened file writer
      if (writer != null)
      {
        try
        {
          //close writer
          writer.close();
        }
        catch ( Exception ex )
        {
          //log error
          logger.error("  ATTENTION: Unable to close created file due to following occurred Exception", ex);
        }
      }
      //destroy used object
      writer = null;
    }
  }

  /**
   * Class that implements the {@link FilenameFilter} interface
   * 
   * @author Fabrizio Parlani
   *
   */
  private static class TemplateFileFilter implements FilenameFilter
  {
    //declare attribute
    private String extension;

    /**
     * Class constructor
     * 
     * @param extension
     *
     * @author Fabrizio Parlani
     */
    public TemplateFileFilter(String extension)
    {
      this.extension = extension;
    }
      
    /* (non-Javadoc)
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    @Override
    public boolean accept(File   dir, 
                          String name)
    {
      //return template file name filter result
      return name.toLowerCase().endsWith(extension);
    }
  }
}
