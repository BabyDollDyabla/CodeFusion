/**
 * [EntityName]ServiceImpl
 *
 * @author [author]
 */
package com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].service.[packagename];

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.utils.RESTOperationResult;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.utils.RESTOperationResult.ECRUDType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]RequestType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]RequestType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]ResponseType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Update[EntityName]RequestType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].service.ServiceResponseBuilder;

/**
 * @author [author]
 *
 */
public class [EntityName]ServiceImpl 	extends    ServiceResponseBuilder
									implements [EntityName]Service 
{
  //get logger instance  
  private static final Logger LOGGER = LoggerFactory.getLogger([EntityName]ServiceImpl.class);

  //declare object that will be injected by blueprint 
  private [EntityName]Facade        facade;

  /**
   * Gets the injected facade attribute value
   *
   * @return the facade
   * @author [author]
   */
  public [EntityName]Facade getFacade()
  {
    return facade;
  }

  /**
   * Sets the injected facade attribute value
   *
   * @param facade the facade to set
   * @author [author]
   */
  public void setFacade([EntityName]Facade facade)
  {
    this.facade = facade;
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].service.[packagename].[EntityName]Service#create[EntityName](javax.servlet.http.HttpServletRequest, com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]RequestType)
   */
  public Response create[EntityName](HttpServletRequest      servletRequest, 
                               Create[EntityName]RequestType request)
  {
    //return properly created response for provided request
//    return Response.ok(facade.create[EntityName](request)).build();

	  //return properly created response for provided request
	  return getResponseWithHttpCode(facade.create[EntityName](request), 
	                                 new RESTOperationResult(ECRUDType.CREATE, 
	                                                         false));	  
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].service.[packagename].[EntityName]Service#update[EntityName](javax.servlet.http.HttpServletRequest, com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]RequestType)
   */
  public Response update[EntityName](HttpServletRequest      servletRequest, 
                               Update[EntityName]RequestType request)
  {
    //return properly created response for provided request
//    return Response.ok(facade.update[EntityName](request)).build();
	  
	  //return properly created response for provided request
	  return getResponseWithHttpCode(facade.update[EntityName](request), 
	                                 new RESTOperationResult(ECRUDType.UPDATE, 
	                                                         false));	  
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].service.[packagename].[EntityName]Service#retrieve[EntityName](javax.servlet.http.HttpServletRequest, com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]RequestType)
   */
  public Response retrieve[EntityName](HttpServletRequest        servletRequest, 
                                 Retrieve[EntityName]RequestType request)
  {
    //return properly created response for provided request
//    return Response.ok(facade.retrieve[EntityName](request)).build();

	  //Invoke retrieve service and get returned response object
	  Retrieve[EntityName]ResponseType response = facade.retrieve[EntityName](request);
	    
	  //return properly created response for provided request
	  return getResponseWithHttpCode(response, 
	                                (response.get[EntityName]RetrievedList() != null) ? new RESTOperationResult(ECRUDType.RETRIEVE, 
	                                                                                                        response.get[EntityName]RetrievedList().get[EntityName]s().isEmpty())
	                                                                              : new RESTOperationResult(ECRUDType.RETRIEVE, 
	                                                                                                        true));	  
  }
}
