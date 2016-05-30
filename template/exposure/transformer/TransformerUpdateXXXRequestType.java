/**
 * TransformerUpdate[EntityName]RequestType
 *
 * @author [author]
 */
package [classPackagePrefix].[packagename];

import java.math.BigInteger;
import java.util.ArrayList;
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
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].[EntityName]UpdateObjectType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].[EntityName]UpdatedListType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].[EntityName]UpdatedObjectType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Update[EntityName]RequestType;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.exposure.[exposureTypePackagePart].data.[packagename].Update[EntityName]ResponseType;

/**
 * @author [author]
 *
 */
public class TransformerUpdate[EntityName]RequestType extends AbstractTransformer
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
  public TransformerUpdate[EntityName]RequestType(Configuration transformerProps)
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
    List<[EntityName]RequestData> daoRequest = new ArrayList<[EntityName]RequestData>();
    //create proper request object casting provided source object
    Update[EntityName]RequestType request    = (Update[EntityName]RequestType) source;

    //check for a valid provided object
    if (request.get[EntityName]UpdateList() == null)
    {
      //throw custom transformation exception
      throw new TransformerException("[EntityName]UpdateListType cannot be null");
    }

    //check for a valid and not empty [collectionFieldNameForComment] list
    if ((request.get[EntityName]UpdateList().get[CollectionEntityName]() == null) ||
        (request.get[EntityName]UpdateList().get[CollectionEntityName]().isEmpty()))
    {
    //throw custom transformation exception
      throw new TransformerException("[CollectionEntityName] list cannot be null or empty");
    }

    //cycle across [collectionFieldNameForComment] list
    for ([EntityName]UpdateObjectType item : request.get[EntityName]UpdateList().get[CollectionEntityName]())
    {
      //add cycled [fieldNameForComment] to returning transformed [collectionFieldNameForComment] list
      daoRequest.add(new [EntityName]RequestData(item.get[EntityName]Id(),
                                           item.getName(),
                                           item.getDescription(),
                                           item.getStatusId()));
    }

    //log composed DAO request
    LOGGER.debug("TransformerUpdate[EntityName]RequestType to [{}]", daoRequest);

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
    Update[EntityName]ResponseType response = new Update[EntityName]ResponseType();
    //create proper response object casting provided source object
    List<[EntityName]ResponseData> daoResponse = (List<[EntityName]ResponseData>) source;
    
    //create the returning updated [collectionFieldNameForComment] list
    response.set[EntityName]UpdatedList(new [EntityName]UpdatedListType());
    
    //cycle across provided updated [collectionFieldNameForComment] list
    for([EntityName]ResponseData item : daoResponse) 
    {
      //declare [fieldNameForComment] item to add to returning list of updated [collectionFieldNameForComment] 
      [EntityName]UpdatedObjectType [fieldName] = new [EntityName]UpdatedObjectType();
      //set currently cycled updated [fieldNameForComment] response attributes 
      [fieldName].set[EntityName]Id(BigInteger.valueOf(item.get[EntityName]Id()));
      [fieldName].setName(item.getName());

      //add cycled updated [fieldNameForComment] into returning updated [collectionFieldNameForComment] list
      response.get[EntityName]UpdatedList().get[CollectionEntityName]().add([fieldName]);
      
    }

    //set response succeeded operation return code
    response.setReturnCode(ExposureCodes.EStatusCode.OK.getCode().toString());
    //set response transaction id got from provided context
    response.setTransactionId((String) context.get("transactionId"));

    //log composed service response
    LOGGER.debug("TransformerUpdate[EntityName]ResponseType from [{}]", response);

    //return composed service response
    return response;
  }
}
