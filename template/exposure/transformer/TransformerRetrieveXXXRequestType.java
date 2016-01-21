/**
 * TransformerRetrieve[EntityName]RequestType
 *
 * @author [author]
 */
package [classPackagePrefix].[packagename];

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.data.ExposureCodes;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.transformer.AbstractTransformer;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.transformer.TransformerException;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.utils.Configuration;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.dto.[packagename].data.[EntityName]RequestData;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.dto.[packagename].data.[EntityName]ResponseData;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]RequestType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]ResponseType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].[EntityName]RetrievedListType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].[EntityName]RetrievedObjectType;

/**
 * @author [author]
 *
 */
public class TransformerRetrieve[EntityName]RequestType extends AbstractTransformer
{
  //get logger instance
  private static final Logger LOGGER = LoggerFactory.getLogger(TransformerCreate[EntityName]RequestType.class);

  /**
   * Class constructor
   * 
   * @param transformerProps
   *
   * @author [author]
   */
  public TransformerRetrieve[EntityName]RequestType(Configuration transformerProps)
  {
    super(transformerProps);
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.transformer.AbstractTransformer#to(java.lang.Object, java.util.Map)
   */
  @Override
  public Object to(Object              source, 
                   Map<String, Object> context) 
  throws TransformerException
  {
    //declare returning object
    [EntityName]RequestData         daoRequest = null;

    //create proper request object casting provided source object
    Retrieve[EntityName]RequestType request    = (Retrieve[EntityName]RequestType) source;

    //create returning DAO request
    daoRequest = new [EntityName]RequestData(request.getName(),
                                       request.getDescription(),
                                       request.getStatusId());

    //log composed DAO request
    LOGGER.debug("TransformerRetrieve[EntityName]RequestType to [{}]", daoRequest);

    //return composed DAO request
    return daoRequest;
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.transformer.AbstractTransformer#from(java.lang.Object, java.util.Map)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object from(Object              source, 
                     Map<String, Object> context) 
  throws TransformerException
  {
    //declare returning object
    Retrieve[EntityName]ResponseType response   = new Retrieve[EntityName]ResponseType();

    //create proper response object casting provided source object
    List<[EntityName]ResponseData>   daoResponse = (List<[EntityName]ResponseData>) source;
    
    //create the returning retrieve [collectionFieldNameForComment] list
    response.set[EntityName]RetrievedList(new [EntityName]RetrievedListType());
    
    //cycle across provided retrieved [collectionFieldNameForComment] list
    for([EntityName]ResponseData item : daoResponse) 
    {
      //declare [fieldNameForComment] item to add to returning list of retrieved [collectionFieldNameForComment] 
      [EntityName]RetrievedObjectType [fieldName] = new [EntityName]RetrievedObjectType();
      //set currently cycled retrieved [fieldNameForComment] response attributes 
      [fieldName].set[EntityName]Id(item.get[EntityName]Id());
      [fieldName].setName(item.getName());
      [fieldName].setDescription(item.getDescription());
      [fieldName].setStatusId(item.getStatusId());

      //add cycled retrieved [fieldNameForComment] into returning retrieved [collectionFieldNameForComment] list
      response.get[EntityName]RetrievedList().get[CollectionEntityName]().add([fieldName]);
    }

    //set response succeeded operation return code
    response.setReturnCode(ExposureCodes.EStatusCode.OK.getCode().toString());
    //set response transaction id got from provided context
    response.setTransactionId((String) context.get("transactionId"));

    //log composed service response
    LOGGER.debug("TransformerRetrieve[EntityName]ResponseType from [{}]", response);

    //return composed service response
    return response;
  }
}
