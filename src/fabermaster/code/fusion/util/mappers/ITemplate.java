/**
 * ITemplate
 *
 * @author Fabrizio Parlani
 */
package fabermaster.code.fusion.util.mappers;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import fabermaster.code.fusion.common.TargetInfo;
import fabermaster.code.fusion.logging.LogManager;
import fabermaster.code.fusion.util.Assorted;
import fabermaster.code.fusion.util.mappers.IProperty.EParameters;


/**
 * @author Fabrizio Parlani
 *
 */
public interface ITemplate
{
  //declare used constants
  public static final String CONFIGURATION_FILE = "code.fusion.properties.file";
  
  /**
   * Enumeration used to map Service Logical Blocks
   * 
   * @author Fabrizio Parlani
   */
  public static enum EServiceBlock
  {
    API          ("api",      Arrays.asList( new ECodeTarget[] { ECodeTarget.API_DTO } )),
    DAO          ("dao",      Arrays.asList( new ECodeTarget[] { ECodeTarget.DAO_SERVICE,
                                                                 ECodeTarget.DAO_SERVICE_IMPL,
                                                                 ECodeTarget.DAO_VALIDATOR } )),
    TENANT       ("tenant",   Arrays.asList( new ECodeTarget[] { ECodeTarget.TENANT_BLUEPRINT,
                                                                 ECodeTarget.TENANT_SERVICE_IMPL } )),
    EXPOSURE     ("exposure", Arrays.asList( new ECodeTarget[] { ECodeTarget.SERVICE,
                                                                 ECodeTarget.VALIDATOR,
                                                                 ECodeTarget.TRANSFORMER,
                                                                 ECodeTarget.ERROR_CONFIG,
                                                                 ECodeTarget.XSD,
                                                                 ECodeTarget.SERVICE_BLUEPRINT} ));

    //declare logger instance
    private static final LogManager logger       = LogManager.getInstance(ITemplate.EServiceBlock.class);
    
    //declare attribute(s)
    private String                  blockPrefix;
    private List<ECodeTarget>       targets;

    /**
     * Enumerator constructor
     * 
     * @param prefix
     *
     * @author Fabrizio Parlani
     */
    private EServiceBlock (String            prefix,
                           List<ECodeTarget> targets)
    {
      this.blockPrefix = prefix;
      this.targets     = targets;
    }

    /**
     * Gets the blockPrefix attribute value
     *
     * @return the blockPrefix
     * @author Fabrizio Parlani
     */
    public String getBlockPrefix()
    {
      return blockPrefix;
    }

    /**
     * Gets the targets attribute value
     *
     * @return the targets
     * @author Fabrizio Parlani
     */
    public List<ECodeTarget> getTargets()
    {
      return targets;
    }

    /**
     * Returns specific code target info
     * 
     * @param target
     * @return
     *
     * @author Fabrizio Parlani
     */
    public TargetInfo getTargetConfig(ECodeTarget target)
    {
      //declare object to return
      TargetInfo info = null;

      //check for a valid provided target to process
      if (target != null)
      {
        //compose returning object
        info = new TargetInfo(new StringBuilder(File.separator)
                                        .append(this.getBlockPrefix())
                                        .append(File.separator)
                                        .append(target.getTargetSuffix())
                                        .append(File.separator)
                                        .toString(), 
                              new StringBuilder(this.getBlockPrefix())
                                        .append(".")
                                        .append(target.getTargetSuffix())
                                        .append(".")
                                        .toString(), 
                              new StringBuilder(this.getBlockPrefix())
                                        .append(File.separator)
                                        .append(target.isReplaceOrEditFlag() ? "CopyAndReplace" : "Edit")
                                        .append(File.separator)
                                        .append(target.getCodeOutFolder())
                                        .toString(),
                              target.isPackageFolderToAddFlag());

        //log composed code target info
        logger.info(String.format("        Composed Code Target Info Object {%s}", info));
      }

      //returning filled or invalid object 
      return info;
    }

    /**
     * Checks if provided code target belongs to service block instance
     * 
     * @param target
     * @return
     *
     * @author Fabrizio Parlani
     */
    public boolean isCodeTargetAMember(ECodeTarget target)
    {
      //check for instance existing targets
      if (Assorted.isNotEmpty(getTargets()))
      {
        //cycle across instance code targets
        for (ECodeTarget item : getTargets())
        {
          //check cycled code target against provided one
          if (item.getTargetSuffix().equals(target.getTargetSuffix()))
          {
            //provided target belongs to this service block instance
            return true;
          }
        }
      }
      
      //here either service block targets is invalid or provided target is not a member of this instance
      return false;
    }

    /**
     * Tries to retrieve a service block instance from provided block prefix
     * 
     * @param blockPrefix
     * @return
     *
     * @author Fabrizio Parlani
     */
    public static EServiceBlock fromPrefix(String blockPrefix)
    {
      //check for provided block prefix
      if (Assorted.isNotEmpty(blockPrefix))
      {
        //cycle across enumerator items
        for (EServiceBlock item : EServiceBlock.values())
        {
          //check cycled item against provided block prefix
          if (item.getBlockPrefix().equalsIgnoreCase(blockPrefix))
          {
            //return found enumerator instance
            return item;
          }
        }
      }

      //here provided block prefix is either invalid or has not been found
      return null;
    }
  }

  /**
   * Enumeration that specifies code blocks
   * 
   * @author Fabrizio Parlani
   */
  public static enum ECodeTarget
  {
    SERVICE      ("service",             new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append("java")
                                                   .append(File.separator).append(EParameters.SERVICE_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              true,  true),
    VALIDATOR    ("validator",           new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append("java")
                                                   .append(File.separator).append(EParameters.VALIDATOR_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              true,  true),
    TRANSFORMER  ("transformer",         new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append("java")
                                                   .append(File.separator).append(EParameters.TRANSFORMER_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              true,  true),
    ERROR_CONFIG ("errors",              new StringBuilder(EParameters.ERRORS_OUTPUT_FOLDER.getAttributeValue())
                                                   .append(File.separator).toString(),              false, false),
    XSD          ("xsd",                 new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append(EParameters.XSD_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              true,  false),
    SERVICE_BLUEPRINT("blueprint",       new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append("resources")
                                                   .append(File.separator).append("OSGI-INF")
                                                   .append(File.separator).append(EParameters.SERVICE_BLUEPRINT_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              false,  true),  
    API_DTO    ("dto",                   new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append("java")
                                                   .append(File.separator).append(EParameters.API_DTO_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              true,  true),                                     
    DAO_SERVICE("service",               new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append("java")
                                                   .append(File.separator).append(EParameters.DAO_SERVICE_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              true,  false),                                           
    DAO_SERVICE_IMPL("impl",             new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append("java")
                                                   .append(File.separator).append(EParameters.DAO_SERVCIE_IMPL_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              true,  false),
    DAO_VALIDATOR("validator",           new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append("java")
                                                   .append(File.separator).append(EParameters.DAO_VALIDATOR_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              true,  false),
    TENANT_BLUEPRINT("blueprint",        new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append("resources")
                                                   .append(File.separator).append("OSGI-INF")
                                                   .append(File.separator).append(EParameters.TENANT_BLUEPRINT_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              false,  true),                                           
    TENANT_SERVICE_IMPL("service.impl",  new StringBuilder("src")
                                                   .append(File.separator).append("main")
                                                   .append(File.separator).append("java")
                                                   .append(File.separator).append(EParameters.TENANT_SERVICE_IMPL_OUTPUT_PACKAGE.getAttributeValue())
                                                   .append(File.separator).toString(),              true,  true);

    //declare attributes
    private String  templateFolderSuffix;
    private String  codeOutFolder;
    private boolean replaceOrEditFlag;
    private boolean packageFolderToAddFlag;

    /**
     * Enumerator constructor
     * 
     * @param folderSuffix
     * @param outputFolder
     * @param replaceOrEdit
     * @param packageFolderToAddFlag
     *
     * @author Fabrizio Parlani
     */
    private ECodeTarget(String  folderSuffix,
                        String  outputFolder,
                        boolean replaceOrEdit,
                        boolean packageFolderToAddFlag)
    {
      this.templateFolderSuffix   = folderSuffix;
      this.codeOutFolder          = outputFolder;
      this.replaceOrEditFlag      = replaceOrEdit;
      this.packageFolderToAddFlag = packageFolderToAddFlag;
    }

    /**
     * Gets the targetSuffix attribute value
     *
     * @return the targetSuffix
     * @author Fabrizio Parlani
     */
    public String getTargetSuffix()
    {
      return templateFolderSuffix;
    }

    /**
     * Gets the codeOutFolder attribute value
     *
     * @return the codeOutFolder
     * @author Fabrizio Parlani
     */
    public String getCodeOutFolder()
    {
      return codeOutFolder;
    }

    /**
     * Gets the replaceOrEditFlag attribute value
     *
     * @return the replaceOrEditFlag
     * @author Fabrizio Parlani
     */
    public boolean isReplaceOrEditFlag()
    {
      return replaceOrEditFlag;
    }

    /**
     * Gets the packageFolderToAddFlag attribute value
     *
     * @return the packageFolderToAddFlag
     * @author Fabrizio Parlani
     */
    public boolean isPackageFolderToAddFlag()
    {
      return packageFolderToAddFlag;
    }

    /**
     * Tries to retrieve a code target instance from provided target suffix
     * 
     * @param targetSuffix
     * @return
     *
     * @author Fabrizio Parlani
     */
    public static ECodeTarget fromTarget(String targetSuffix)
    {
      //check for provided target suffix
      if (Assorted.isNotEmpty(targetSuffix))
      {
        //cycle across enumerator items
        for (ECodeTarget item : ECodeTarget.values())
        {
          //check cycled item against provided target suffix
          if (item.getTargetSuffix().equalsIgnoreCase(targetSuffix))
          {
            //return found enumerator instance
            return item;
          }
        }
      }

      //here provided target suffix is either invalid or has not been found
      return null;
    }
  }

  /**
   * Enumeration used to define placeholder types
   * 
   * @author Fabrizio Parlani
   */
  public static enum EPlaceholderType
  {
    SIMPLE ("["),
    LIST   ("@");
    
    //declare attribute(s)
    private String placeholderTypePrefix;

    /**
     * Enumerator constructor
     * 
     * @param prefix
     *
     * @author Fabrizio Parlani
     */
    private EPlaceholderType(String prefix)
    {
      this.placeholderTypePrefix = prefix;
    }

    /**
     * Gets the placeholderTypePrefix attribute value
     *
     * @return the placeholderTypePrefix
     * @author Fabrizio Parlani
     */
    public String getPlaceholderTypePrefix()
    {
      return placeholderTypePrefix;
    }

    /**
     * Tries to retrieve placeholder type from provided prefix
     * 
     * @param prefix
     * @return
     *
     * @author Fabrizio Parlani
     */
    public static EPlaceholderType retrieveType(String prefix)
    {
      //check for provided object
      if (prefix != null)
      {
        //cycle across placeholder types
        for (EPlaceholderType item : EPlaceholderType.values())
        {
          //check for retrieved item 
          if (item.getPlaceholderTypePrefix().equals(prefix))
          {
            //return retrieved item
            return item;
          }
        }
      }

      //here returns invalid placeholder type
      return null;
    }
  }
}
