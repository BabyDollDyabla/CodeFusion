/**
 * [EntityName]Service
 *
 * @author [author]
 */
package [classPackagePrefix].[packagename];

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]RequestType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]RequestType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Update[EntityName]RequestType;

/**
 * @author [author]
 *
 */
@Path("/[fieldName]")
public interface [EntityName]Service 
{
  
  /**
   * [fieldNameForComment]s creation REST service exposure
   * 
   * @param servletRequest
   * @param request
   * @return
   *
   * @author [author]
   */
  @POST
  @Path("/create")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create[EntityName](@Context HttpServletRequest      servletRequest, 
                                        Create[EntityName]RequestType request);

  /**
   * [fieldNameForComment]s update REST service exposure
   * 
   * @param servletRequest
   * @param request
   * @return
   *
   * @author [author]
   */
  @POST
  @Path("/update")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response update[EntityName](@Context HttpServletRequest      servletRequest, 
                                        Update[EntityName]RequestType request);
  
  /**
   * Retrieve [fieldNameForComment]s REST service exposure
   * 
   * @param servletRequest
   * @param request
   * @return
   *
   * @author [author]
   */
  @POST
  @Path("/retrieve")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response retrieve[EntityName](@Context HttpServletRequest        servletRequest, 
                                          Retrieve[EntityName]RequestType request);

}