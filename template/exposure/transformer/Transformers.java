/**
 * Transformers
 *
 * @author [author]
 */
package [classPackagePrefix].[packagename];

import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.utils.Configuration;

/**
 * @author [author]
 *
 */
public class Transformers
{
  //declare object where to store provided transformer configuration
  private Configuration                        transformerProps;
  //declare create [fieldNameForComment] request object transformer
  private TransformerCreate[EntityName]RequestType   create[EntityName];
  //declare update [fieldNameForComment] request object transformer
  private TransformerUpdate[EntityName]RequestType   update[EntityName];
  //declare retrieve [fieldNameForComment] request object transformer
  private TransformerRetrieve[EntityName]RequestType retrieve[EntityName];

  /**
   * Class constructor
   * 
   * @param transformerProps
   *
   * @author [author]
   */
  public Transformers(Configuration transformerProps)
  {
    this.transformerProps = transformerProps;
    this.create[EntityName]     = new TransformerCreate[EntityName]RequestType(this.transformerProps);
    this.update[EntityName]     = new TransformerUpdate[EntityName]RequestType(this.transformerProps);
    this.retrieve[EntityName]   = new TransformerRetrieve[EntityName]RequestType(this.transformerProps);
  }

  /**
   * Gets properly transformation object
   * 
   * @return
   *
   * @author [author]
   */
  public TransformerCreate[EntityName]RequestType getCreate[EntityName]()
  {
    return create[EntityName];
  }

  /**
   * Gets the update[EntityName] attribute value
   *
   * @return the update[EntityName]
   * 
   * @author [author]
   */
  public TransformerUpdate[EntityName]RequestType getUpdate[EntityName]()
  {
    return update[EntityName];
  }

  /**
   * Gets the retrieve[EntityName] attribute value
   *
   * @return the retrieve[EntityName]
   * 
   * @author [author]
   */
  public TransformerRetrieve[EntityName]RequestType getRetrieve[EntityName]()
  {
    return retrieve[EntityName];
  }

}
