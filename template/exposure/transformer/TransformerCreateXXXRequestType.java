/**
 * TransformerCreate[EntityName]RequestType
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

import com.[companyPackagePart].digital.platform.crsm.api.data.ExposureCodes;
import com.[companyPackagePart].digital.platform.crsm.api.transformer.AbstractTransformer;
import com.[companyPackagePart].digital.platform.crsm.api.transformer.TransformerException;
import com.[companyPackagePart].digital.platform.crsm.api.utils.Configuration;
import com.[companyPackagePart].digital.platform.crsm.dao.dto.[packagename].data.[EntityName]RequestData;
import com.[companyPackagePart].digital.platform.crsm.dao.dto.[packagename].data.[EntityName]ResponseData;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]RequestType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]ResponseType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].[EntityName]CreateObjectType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].[EntityName]CreatedListType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].[EntityName]CreatedObjectType;

/**
 * @author [author]
 *
 */
public class TransformerCreate[EntityName]RequestType extends AbstractTransformer
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
  public TransformerCreate[EntityName]RequestType(Configuration transformerProps)
  {
    super(transformerProps);
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].digital.platform.crsm.api.transformer.AbstractTransformer#to(java.lang.Object, java.util.Map)
   */
  @Override
  public Object to(Object              source, 
                   Map<String, Object> context) 
  throws TransformerException
  {
    //declare returning object
    List<[EntityName]RequestData> daoRequest = new ArrayList<[EntityName]RequestData>();
    //create proper request object casting provided source object
    Create[EntityName]RequestType request    = (Create[EntityName]RequestType) source;

    //check for a valid provided object
    if (request.get[EntityName]CreateList() == null)
    {
      //throw custom transformation exception
      throw new TransformerException("[EntityName]CreateListType cannot be null");
    }

    //check for a valid and not empty [collectionFieldNameForComment] list
    if ((request.get[EntityName]CreateList().get[CollectionEntityName]() == null) ||
        (request.get[EntityName]CreateList().get[CollectionEntityName]().isEmpty()))
    {
    //throw custom transformation exception
      throw new TransformerException("[collectionFieldNameForComment] list cannot be null or empty");
    }

    //cycle across [collectionFieldNameForComment] list
    for ([EntityName]CreateObjectType item : request.get[EntityName]CreateList().get[CollectionEntityName]())
    {
      //add cycled [fieldNameForComment] to returning transformed [collectionFieldNameForComment] list
      daoRequest.add(new [EntityName]RequestData(item.getName(),
                                           item.getDescription(),
                                           item.getStatusId()));
    }

    //log composed DAO request
    LOGGER.debug("TransformerCreate[EntityName]RequestType to [{}]", daoRequest);

    //return composed DAO request
    return daoRequest;
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].digital.platform.crsm.api.transformer.AbstractTransformer#from(java.lang.Object, java.util.Map)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object from(Object              source, 
                     Map<String, Object> context) 
  throws TransformerException
  {
    //declare returning object
    Create[EntityName]ResponseType response = new Create[EntityName]ResponseType();
    //create proper response object casting provided source object
    List<[EntityName]ResponseData> daoResponse = (List<[EntityName]ResponseData>) source;
    
    //create the returning created [collectionFieldNameForComment] list
    response.set[EntityName]CreatedList(new [EntityName]CreatedListType());
    
    //cycle across provided created [collectionFieldNameForComment] list
    for([EntityName]ResponseData item : daoResponse) 
    {
      //declare [fieldNameForComment] item to add to returning list of created [collectionFieldNameForComment] 
      [EntityName]CreatedObjectType [fieldName] = new [EntityName]CreatedObjectType();
      //set currently cycled created [fieldNameForComment] response attributes 
      [fieldName].set[EntityName]Id(BigInteger.valueOf(item.get[EntityName]Id()));
      [fieldName].setName(item.getName());

      //add cycled created [fieldNameForComment] into returning created [collectionFieldNameForComment] list
      response.get[EntityName]CreatedList().get[CollectionEntityName]().add([fieldName]);
      
    }

    //set response succeeded operation return code
    response.setReturnCode(ExposureCodes.EStatusCode.OK.getCode().toString());
    //set response transaction id got from provided context
    response.setTransactionId((String) context.get("transactionId"));

    //log composed service response
    LOGGER.debug("TransformerCreate[EntityName]ResponseType from [{}]", response);

    //return composed service response
    return response;
  }
}
