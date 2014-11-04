/**
 * PlaceholderInfo
 *
 * @author Fabrizio Parlani
 */
package fabermaster.code.fusion.common;

import fabermaster.code.fusion.util.mappers.ITemplate.EPlaceholderType;

/**
 * @author Fabrizio Parlani
 *
 */
public class PlaceholderInfo
{
  //declare attributes
  private String           name;
  private EPlaceholderType type;

  /**
   * Default class constructor
   *
   * @author Fabrizio Parlani
   */
  public PlaceholderInfo()
  {
    
  }

  /**
   * Class constructor
   * 
   * @param name
   * @param type
   *
   * @author Fabrizio Parlani
   */
  public PlaceholderInfo(String           name,
                         EPlaceholderType type)
  {
    this.name = name;
    this.type = type;
  }

  /**
   * Gets the name attribute value
   *
   * @return the name
   * @author Fabrizio Parlani
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the name attribute value
   *
   * @param name the name to set
   * @author Fabrizio Parlani
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Gets the type attribute value
   *
   * @return the type
   * @author Fabrizio Parlani
   */
  public EPlaceholderType getType()
  {
    return type;
  }

  /**
   * Sets the type attribute value
   *
   * @param type the type to set
   * @author Fabrizio Parlani
   */
  public void setType(EPlaceholderType type)
  {
    this.type = type;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj)
  {
    //check for provided object
    if ((obj != null) && (obj instanceof PlaceholderInfo))
    {
      //check invalid name
      if (getName() == null)
      {
        //return null comparison with provided object
        return (((PlaceholderInfo) obj).getName() == null);
      }
      else
      {
        //return equals match result with provided object name
        return getName().equals(((PlaceholderInfo) obj).getName());
      }
    }

    //here provided object is not equals to this instance
    return false;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("PlaceholderInfo {name=[");
    builder.append(name);
    builder.append("], type=[");
    builder.append(type);
    builder.append("}");
    return builder.toString();
  }

}
