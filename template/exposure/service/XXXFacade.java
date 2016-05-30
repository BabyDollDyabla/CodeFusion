/**
 * [EntityName]Facade
 *
 * @author [author]
 */
package [classPackagePrefix].[packagename];

import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]RequestType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]ResponseType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]RequestType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]ResponseType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Update[EntityName]RequestType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Update[EntityName]ResponseType;

/**
 * @author [author]
 *
 */
public interface [EntityName]Facade
{
  /**
   * Tries to create a new [fieldNameForComment] from provided request 
   * 
   * @param request
   * @return
   *
   * @author [author]
   */
  public Create[EntityName]ResponseType create[EntityName](Create[EntityName]RequestType request);
  
  /**
   * Tries to update [fieldNameForComment]s from provided request 
   * 
   * @param request
   * @return
   *
   * @author [author]
   */
  public Update[EntityName]ResponseType update[EntityName](Update[EntityName]RequestType request);
  
  /**
   * Tries to retrieve [fieldNameForComment]s from provided request 
   * 
   * @param request
   * @return
   *
   * @author [author]
   */
  public Retrieve[EntityName]ResponseType retrieve[EntityName](Retrieve[EntityName]RequestType request);
  
}
