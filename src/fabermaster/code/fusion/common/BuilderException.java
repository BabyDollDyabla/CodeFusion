/**
 * BuilderException
 *
 * @author Fabrizio Parlani
 */
package fabermaster.code.fusion.common;

import fabermaster.code.fusion.util.Assorted;
import fabermaster.code.fusion.util.mappers.ITemplate;

/**
 * @author Fabrizio Parlani
 *
 */
public class BuilderException extends Exception
{
  private static final long serialVersionUID = -6529476242726785956L;

  //declare attributes
  private String errorCode;
  private String errorDescription;
  
  public static enum EErrors
  {
    GENERIC_ERROR                                     ("BLD-GEN-00001", "An error has occurred"),
    GENERAL_PROPERTIES_FILE_INVALID                   ("BLD-GPF-00101", "An invalid or empty properties file has been passed. Please specify the fully qualified path within the following system properties '" + ITemplate.CONFIGURATION_FILE + "'"),
    GENERAL_PROPERTIES_FILE_CONFIGURATION_ERROR       ("BLD-GPF-00102", "Error during provided properties file loading process"),
    CONFIGURATION_FILE_READING_ERROR                  ("BLD-CFG-10001", "IO Error has occurred during configuration file reading process"),
    CONFIGURATION_FILE_ILLEGAL_ARGUMENT               ("BLD-CFG-10002", "An illegat argument has been provided for configuration file reading process"),
    TEMPLATE_FILE_NOT_FOUND                           ("BLD-TPL-10101", "Provided template file has not been found"),
    TEMPLATE_FILE_READING_ERROR                       ("BLD-TPL-10102", "Unable to close opened template file"),
    DESTINATION_FILE_NOT_FOUND                        ("BLD-TPL-10103", "Unable to create provided destination file. Probably destination path has not been found"),
    DESTINATION_FILE_WRITING_ERROR                    ("BLD-TPL-10104", "Unable to write into destination file due to an I/O issue");
    
    //declare attributes
    private String errorCode;
    private String errorDescription;

    /**
     * Enumerator constructor
     * 
     * @param errorCode
     * @param errorDescscription
     *
     * @author Fabrizio Parlani
     */
    private EErrors(String errorCode,
                    String errorDescscription)
    {
      this.errorCode        = errorCode;
      this.errorDescription = errorDescscription;
    }

    /**
     * Gets the errorCode attribute value
     *
     * @return the errorCode
     * @author Fabrizio Parlani
     */
    public String getErrorCode()
    {
      return errorCode;
    }

    /**
     * Gets the errorDescription attribute value
     *
     * @return the errorDescription
     * @author Fabrizio Parlani
     */
    public String getErrorDescription()
    {
      return errorDescription;
    }

    /**
     * Tries to retrieve an error instance from provided error code
     * 
     * @param errorCode
     * @return
     *
     * @author Fabrizio Parlani
     */
    public static EErrors fromCode(String errorCode)
    {
      //check for provided error code
      if (Assorted.isNotEmpty(errorCode))
      {
        //cycle across enumerator items
        for (EErrors item : EErrors.values())
        {
          //check cycled item against provided code
          if (item.getErrorCode().equalsIgnoreCase(errorCode))
          {
            //return found enumerator instance
            return item;
          }
        }
      }

      //here provided code is either invalid or has not been found
      return null;
    }
  }

  /**
   * Default constructor 
   *
   * @author Fabrizio Parlani
   */
  public BuilderException()
  {
    super();
  }

  /**
   * Class constructor
   * 
   * @param cause
   *
   * @author Fabrizio Parlani
   */
  public BuilderException(Throwable cause)
  {
    super(cause);
  }
  
  /**
   * Class constructor
   * 
   * @param message
   *
   * @author Fabrizio Parlani
   */
  public BuilderException(String message)
  {
    super(message);
    this.errorDescription = message;
  }
  
  /**
   * Class constructor
   * 
   * @param message
   * @param cause
   *
   * @author Fabrizio Parlani
   */
  public BuilderException(String    message,
                          Throwable cause)
  {
    super(message, 
          cause);
    this.errorDescription = message;
  }

  /**
   * Class constructor
   * 
   * @param code
   * @param message
   *
   * @author Fabrizio Parlani
   */
  public BuilderException(String code, 
                          String message)
  {
    super(message);
    this.errorCode        = code;
    this.errorDescription = message;
  }

  /**
   * Class constructor
   * 
   * @param code
   * @param message
   * @param cause
   *
   * @author Fabrizio Parlani
   */
  public BuilderException(String    code, 
                          String    message,
                          Throwable cause)
  {
    super(message, 
          cause);
    this.errorCode        = code;
    this.errorDescription = message;
  }

  /**
   * Class constructor
   * 
   * @param error
   *
   * @author Fabrizio Parlani
   */
  public BuilderException(EErrors error)
  {
    this(error.getErrorCode(), 
         error.getErrorDescription());
  }
  
  /**
   * Class constructor
   * 
   * @param error
   * @param cause
   *
   * @author Fabrizio Parlani
   */
  public BuilderException(EErrors   error,
                          Throwable cause)
  {
    this(error.getErrorCode(), 
         error.getErrorDescription(),
         cause);
  }

  /**
   * Gets the errorCode attribute value
   *
   * @return the errorCode
   * @author Fabrizio Parlani
   */
  public String getErrorCode()
  {
    return errorCode;
  }

  /**
   * Sets the errorCode attribute value
   *
   * @param errorCode the errorCode to set
   * @author Fabrizio Parlani
   */
  public void setErrorCode(String errorCode)
  {
    this.errorCode = errorCode;
  }

  /**
   * Gets the errorDescription attribute value
   *
   * @return the errorDescription
   * @author Fabrizio Parlani
   */
  public String getErrorDescription()
  {
    return errorDescription;
  }

  /**
   * Sets the errorDescription attribute value
   *
   * @param errorDescription the errorDescription to set
   * @author Fabrizio Parlani
   */
  public void setErrorDescription(String errorDescription)
  {
    this.errorDescription = errorDescription;
  }
}
