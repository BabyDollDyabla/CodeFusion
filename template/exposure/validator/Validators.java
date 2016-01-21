/**
 * Validators
 *
 * @author [author]
 */
package [classPackagePrefix].[packagename];

import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.utils.Configuration;

/**
 * @author [author]
 *
 */
public class Validators
{
  //declare validation request object
  private ValidatorCreate[EntityName]RequestType   create[EntityName];
  private ValidatorUpdate[EntityName]RequestType   update[EntityName];
  private ValidatorRetrieve[EntityName]RequestType retrieve[EntityName];

  /**
   * Class constructor
   * 
   * @param validationProps
   * @param errorProps
   *
   * @author [author]
   */
  public Validators(Configuration validationProps, 
                    Configuration errorProps)
  {
    //initialize request validation object for creation flow
    this.create[EntityName]   = new ValidatorCreate[EntityName]RequestType("create[EntityName]", 
                                                               validationProps, 
                                                               errorProps);
    //initialize request validation object for update flow
    this.update[EntityName]   = new ValidatorUpdate[EntityName]RequestType("update[EntityName]", 
                                                               validationProps, 
                                                               errorProps);
    //initialize request validation object for retrieve flow
    this.retrieve[EntityName] = new ValidatorRetrieve[EntityName]RequestType("retrieve[EntityName]", 
                                                                 validationProps, 
                                                                 errorProps);
  }

  /**
   * Gets created validation object for creation flow
   * 
   * @return the create[EntityName]
   *
   * @author [author]
   */
  public ValidatorCreate[EntityName]RequestType getCreate[EntityName]()
  {
    //return initialized validation object
    return create[EntityName];
  }

  /**
   * Gets created validation object for update flow
   *
   * @return the update[EntityName]
   * 
   * @author [author]
   */
  public ValidatorUpdate[EntityName]RequestType getUpdate[EntityName]()
  {
    return update[EntityName];
  }

  /**
   * Gets created validation object for retrieve flow
   *
   * @return the retrieve[EntityName]
   * 
   * @author [author]
   */
  public ValidatorRetrieve[EntityName]RequestType getRetrieve[EntityName]()
  {
    return retrieve[EntityName];
  }
}
