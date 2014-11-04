/**
 * TargetInfo
 *
 * @author Fabrizio Parlani
 */
package fabermaster.code.fusion.common;

/**
 * @author Fabrizio Parlani
 *
 */
public class TargetInfo
{
  private String  templateFolder;
  private String  propertiesPrefix;
  private String  outputFolder;
  private boolean packageFolderToAdd;

  /**
   * Default class constructor 
   *
   * @author Fabrizio Parlani
   */
  public TargetInfo()
  {
    
  }

  /**
   * Class constructor
   * 
   * @param templateFolder
   * @param propertiesPrefix
   * @param outputFolder
   * @param packageFolderToAdd
   *
   * @author Fabrizio Parlani
   */
  public TargetInfo(String  templateFolder, 
                    String  propertiesPrefix, 
                    String  outputFolder,
                    boolean packageFolderToAdd)
  {
    super();
    this.templateFolder     = templateFolder;
    this.propertiesPrefix   = propertiesPrefix;
    this.outputFolder       = outputFolder;
    this.packageFolderToAdd = packageFolderToAdd;
  }

  /**
   * Gets the templateFolder attribute value
   *
   * @return the templateFolder
   * @author Fabrizio Parlani
   */
  public String getTemplateFolder()
  {
    return templateFolder;
  }

  /**
   * Sets the templateFolder attribute value
   *
   * @param templateFolder the templateFolder to set
   * @author Fabrizio Parlani
   */
  public void setTemplateFolder(String templateFolder)
  {
    this.templateFolder = templateFolder;
  }

  /**
   * Gets the propertiesPrefix attribute value
   *
   * @return the propertiesPrefix
   * @author Fabrizio Parlani
   */
  public String getPropertiesPrefix()
  {
    return propertiesPrefix;
  }

  /**
   * Sets the propertiesPrefix attribute value
   *
   * @param propertiesPrefix the propertiesPrefix to set
   * @author Fabrizio Parlani
   */
  public void setPropertiesPrefix(String propertiesPrefix)
  {
    this.propertiesPrefix = propertiesPrefix;
  }

  /**
   * Gets the outputFolder attribute value
   *
   * @return the outputFolder
   * @author Fabrizio Parlani
   */
  public String getOutputFolder()
  {
    return outputFolder;
  }

  /**
   * Sets the outputFolder attribute value
   *
   * @param outputFolder the outputFolder to set
   * @author Fabrizio Parlani
   */
  public void setOutputFolder(String outputFolder)
  {
    this.outputFolder = outputFolder;
  }


  /**
   * Gets the packageFolderToAdd attribute value
   *
   * @return the packageFolderToAdd
   * @author Fabrizio Parlani
   */
  public boolean isPackageFolderToAdd()
  {
    return packageFolderToAdd;
  }

  /**
   * Sets the packageFolderToAdd attribute value
   *
   * @param packageFolderToAdd the packageFolderToAdd to set
   * @author Fabrizio Parlani
   */
  public void setPackageFolderToAdd(boolean packageFolderToAdd)
  {
    this.packageFolderToAdd = packageFolderToAdd;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("TargetInfo {templateFolder=[");
    builder.append(templateFolder);
    builder.append("], propertiesPrefix=[");
    builder.append(propertiesPrefix);
    builder.append("], outputFolder=[");
    builder.append(outputFolder);
    builder.append("], packageFolderToAdd=[");
    builder.append(packageFolderToAdd);
    builder.append("}");
    return builder.toString();
  }

}
