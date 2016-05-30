/**
 * [EntityName]DaoImpl
 *
 * @author [author]
 */
package [classPackagePrefix];

import java.util.ArrayList;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.TransactionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.Constants.EXCEPTION_LOG;
import static com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.Constants.RUNTIME_EXCEPTION_LOG;
import static com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.Constants.DAO_EXCEPTION_LOG;
import static com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.Constants.ERROR_OCCURRED_LOG;
import static com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.Constants.STATUS_NOT_FOUND_ERROR_DESC;

import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.utils.LogUtils;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.api.utils.LogUtils.LogLevel;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.data.Status.EStatuses;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.dto.common.CommonRetrieveFilter;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.dto.[packagename].data.[EntityName]RequestData;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.dto.[packagename].data.[EntityName]ResponseData;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.exception.DaoException;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.exception.DaoException.ExceptionCodes;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.model.[EntityName];
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.model.RefStatus;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.service.[EntityName]Dao;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.DaoHelper;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.QueryBuilder;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.QueryBuilder.EEntity;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.QueryBuilder.EOrder;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.QueryBuilder.ERetrieveCriteria;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.utils.Utils;
import com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.validator.[EntityName]Validator;

/**
 * @author [author]
 *
 */
public class [EntityName]DaoImpl implements [EntityName]Dao
{
  //get logger
  private static final Logger             LOGGER   = LoggerFactory.getLogger([EntityName]DaoImpl.class);
  //get DAO interface name
  public static final  String             DAO_NAME = [EntityName]Dao.class.getName();

  //declare object that will be injected by blueprint
  private EntityManager                   entityManager;
  private TransactionManager              transactionManager;

  /**
   * Gets the injected entityManager object
   *
   * @return the entityManager
   * @author [author]
   */
  public EntityManager getEntityManager()
  {
    return entityManager;
  }

  /**
   * Injects the entityManager object
   *
   * @param entityManager the entityManager to set
   * @author [author]
   */
  public void setEntityManager(EntityManager entityManager)
  {
    this.entityManager = entityManager;
  }

  /**
   * Gets the injected transactionManager object
   *
   * @return the transactionManager
   * @author [author]
   */
  public TransactionManager getTransactionManager()
  {
    return transactionManager;
  }

  /**
   * Injects the transactionManager object
   *
   * @param transactionManager the transactionManager to set
   * @author [author]
   */
  public void setTransactionManager(TransactionManager transactionManager)
  {
    this.transactionManager = transactionManager;
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.service.[EntityName]Dao#insert(java.util.List)
   */
  public List<[EntityName]ResponseData> insert(List<[EntityName]RequestData> [collectionFieldName]) 
  throws DaoException
  {
    //set current method name
    String                     methodName   = "insert";
    //get start method milliseconds
    long                       startMillis  = LogUtils.logStart(LOGGER, 
                                                                LogLevel.DEBUG, 
                                                                methodName, 
                                                                [collectionFieldName]);
    //get current date to set item(s) creation date
    Date                       now          = new Date();
    //create response object
    List<[EntityName]ResponseData> responseList = new ArrayList<[EntityName]ResponseData>(0);

    //log operation start
    LOGGER.debug("START [EntityName](s) insertion with provided request [{}]", [collectionFieldName]);

    try
    {
      //validate provided request object
      [EntityName]Validator.validateInsert([collectionFieldName], DAO_NAME);

      //cycle across request list
      for ([EntityName]RequestData item : [collectionFieldName])
      {
        //create entity object to insert
        [EntityName]     [fieldName]  = new [EntityName]();

        //setting [fieldNameForComment] flat values
        [fieldName].setName(item.getName());
        [fieldName].setDescription(item.getDescription());

        //setting [fieldNameForComment] foreign keys
          //Attribute Status
        [fieldName].setRefStatus(DaoHelper.find(entityManager, 
                                             RefStatus.class, 
                                             Utils.isNull(item.getStatusId()) ? EStatuses.ACTIVE.getStatusId() : item.getStatusId().shortValue(), 
                                             DAO_NAME, 
                                             String.format(STATUS_NOT_FOUND_ERROR_DESC, Utils.isNull(item.getStatusId()) ? EStatuses.ACTIVE.getStatusId() : item.getStatusId().shortValue()) ) );
        //set [fieldNameForComment] creation date
        [fieldName].setCreatedDate(now);

        //save set [fieldNameForComment] entity
        DaoHelper.persist(entityManager,
                          [fieldName],
                          true,
                          DAO_NAME,
                          "A [fieldName] with name '%s' already exists",
                          new Object[] { [fieldName].getName() }); 

        //add inserted [fieldNameForComment] to returning response list
        responseList.add(new [EntityName]ResponseData([fieldName].get[EntityName]Id(),
                                                  [fieldName].getName()));
      }
    }
    catch ( DaoException de )
    {
      //log occurred error
      LOGGER.error(DAO_EXCEPTION_LOG, de);
      //roll-back transaction
      DaoHelper.setRollbackOnly(transactionManager);
      //throw back caught exception
      throw de;
    }
    catch ( RuntimeException ex )
    {
      //log occurred error
      LOGGER.error(RUNTIME_EXCEPTION_LOG, ex);
      //roll-back transaction
      DaoHelper.setRollbackOnly(transactionManager);
      //throw custom generated exception
      throw new DaoException(ExceptionCodes.GENERIC.getCode(), 
    		  ERROR_OCCURRED_LOG, 
              DAO_NAME, 
              ex);
    }
    catch ( Exception ex )
    { 
        //log occurred error
        LOGGER.error(EXCEPTION_LOG, ex);
        //roll-back transaction
        DaoHelper.setRollbackOnly(getTransactionManager());
        //throw custom generated exception
        throw new DaoException(ExceptionCodes.GENERIC.getCode(), 
        		               ERROR_OCCURRED_LOG, 
                               DAO_NAME, 
                               ex);
    }
    finally 
    {
      //log operation end
      LOGGER.debug("END [EntityName](s) insertion");
      //finalize log
      LogUtils.logEnd(LOGGER, 
                      LogLevel.DEBUG, 
                      startMillis, 
                      methodName);
    }

    //return response object
    return responseList;
  }
  
  /* (non-Javadoc)
   * @see com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.service.[EntityName]Dao#update(java.util.List)
   */
  public List<[EntityName]ResponseData> update(List<[EntityName]RequestData> [collectionFieldName]) 
  throws DaoException
  {
    //set current method name
    String                     methodName   = "update";
    //get start method milliseconds
    long                       startMillis  = LogUtils.logStart(LOGGER, 
                                                                LogLevel.DEBUG, 
                                                                methodName, 
                                                                [collectionFieldName]);
    //get current date to set item(s) update date
    Date                       now          = new Date();
    //create response object
    List<[EntityName]ResponseData> responseList = new ArrayList<[EntityName]ResponseData>(0);

    //log operation start
    LOGGER.debug("START [EntityName](s) update with provided request [{}]", [collectionFieldName]);

    try
    {
      //validate provided request object
      [EntityName]Validator.validateUpdate([collectionFieldName], DAO_NAME);

      //cycle across request list
      for ([EntityName]RequestData item : [collectionFieldName])
      {
        
        //gets the entity to update
        [EntityName]  [fieldName]  = DaoHelper.find(entityManager, 
                                                [EntityName].class, 
                                                item.get[EntityName]Id(), 
                                                DAO_NAME, 
                                                "[EntityName] with id [" + item.get[EntityName]Id() + "] not found");

        //check for a requested logical deletion of currently cycled item
        if (!Utils.isDelete(item.getStatusId()))
        {
          //updates [fieldNameForComment] name with newest provided value
          [fieldName].setName(item.getName());
          //updates [fieldNameForComment] description with newest provided value
          [fieldName].setDescription(item.getDescription());
        }

        //checks for an updated [fieldNameForComment] status foreign key (this block of code is always executed either for update or deletion required operation)
        if (Utils.updatedForeignKey(item.getStatusId(), 
                                    ([fieldName].getRefStatus() != null) ? [fieldName].getRefStatus().getStatusId()
                                                                      : null))
        {
          //updates [fieldNameForComment] status foreign key
          [fieldName].setRefStatus(DaoHelper.find(entityManager, 
                                               RefStatus.class, 
                                               item.getStatusId(), 
                                               DAO_NAME, 
                                               String.format(STATUS_NOT_FOUND_ERROR_DESC, item.getStatusId())));
        }

        //set [fieldNameForComment] update date
        [fieldName].setUpdatedDate(now);

        //save updated [fieldNameForComment] entity
        DaoHelper.merge(entityManager,
                        [fieldName],
                        true,
                        DAO_NAME,
                        "A [fieldName] with name '%s' already exists",
                        new Object[] { [fieldName].getName() });

        //add updated [fieldNameForComment] to returning response list
        responseList.add(new [EntityName]ResponseData([fieldName].get[EntityName]Id(),
                                                  [fieldName].getName()));
      }
    }
    catch ( DaoException de )
    {
      //log occurred error
      LOGGER.error(DAO_EXCEPTION_LOG, de);
      //roll-back transaction
      DaoHelper.setRollbackOnly(transactionManager);
      //throw back caught exception
      throw de;
    }
    catch ( RuntimeException ex )
    {
      //log occurred error
      LOGGER.error(RUNTIME_EXCEPTION_LOG, ex);
      //roll-back transaction
      DaoHelper.setRollbackOnly(transactionManager);
      //throw custom generated exception
      throw new DaoException(ExceptionCodes.GENERIC.getCode(), 
    		                 ERROR_OCCURRED_LOG, 
                             DAO_NAME, 
                             ex);
    }
    catch ( Exception ex )
    { 
        //log occurred error
        LOGGER.error(EXCEPTION_LOG, ex);
        //roll-back transaction
        DaoHelper.setRollbackOnly(getTransactionManager());
        //throw custom generated exception
        throw new DaoException(ExceptionCodes.GENERIC.getCode(), 
        		               ERROR_OCCURRED_LOG, 
                               DAO_NAME, 
                               ex);
    }
    finally 
    {
      //log operation end
      LOGGER.debug("END [EntityName](s) update");
      //finalize log
      LogUtils.logEnd(LOGGER, 
                      LogLevel.DEBUG, 
                      startMillis, 
                      methodName);
    }

    //return response object
    return responseList;
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.service.[EntityName]Dao#retrieveList(com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.dto.[fieldName].data.[EntityName]RequestData, com.[companyPackagePart].cpaas.dcpp.enabler.crsm.dao.dto.common.CommonRetrieveFilter)
   */
  public List<[EntityName]ResponseData> retrieveList([EntityName]RequestData request, 
                                                 CommonRetrieveFilter paginator) 
  throws DaoException
  {
    //set current method name
    String                          methodName   = "retrieveList";
    //get start method milliseconds
    long                            startMillis  = LogUtils.logStart(LOGGER, 
                                                                     LogLevel.DEBUG, 
                                                                     methodName, 
                                                                     request,
                                                                     paginator);
    //create response object
    List<[EntityName]ResponseData>      responseList = new ArrayList<[EntityName]ResponseData>(0);
    //create sorting object where to put the default sort order
    Map<ERetrieveCriteria, EOrder>  sortings     = new LinkedHashMap<ERetrieveCriteria, EOrder>(0);

    //log operation start
    LOGGER.debug("START [EntityName](s) retrieve List with provided request [{}]", request);

    try 
    {
      //validate provided request and pagination objects 
      [EntityName]Validator.validateRetrieveList(request, 
                                             paginator, 
                                             DAO_NAME);

      //set default sorting criteria
      sortings.put(ERetrieveCriteria.LANGUAGE_NAME, EOrder.ASCENDING);
      
      //execute retrieval query
      List<[EntityName]> results = (List<[EntityName]>) DaoHelper.executeHqlQuery([EntityName].class,
                                                                                QueryBuilder.buildMePlease(entityManager, 
                                                                                                           EEntity.REF_LANGUAGE, 
                                                                                                           buildConditions(request), 
                                                                                                           sortings, 
                                                                                                           paginator, 
                                                                                                           DAO_NAME),
                                                                                DAO_NAME,
                                                                                true);

      //checks for retrieved data
      if (Utils.notEmpty(results))
      {
        //cycle across retrieved result(s)
        for ([EntityName] result : results)
        {
          //add currently cycled result into returning list
          responseList.add(new [EntityName]ResponseData(result.get[EntityName]Id(),
                                                    result.getName(),
                                                    result.getDescription(),
                                                    ((result.getRefStatus() != null) &&
                                                     (result.getRefStatus().getStatusId() != null)) ? result.getRefStatus().getStatusId().intValue() 
                                                                                                    : null));
        }

        //log retrieved items
        LOGGER.debug("  retrieveList found [{}] result(s). Response is [{}]", responseList.size(), responseList);
      }
      else
      {
        //log no items retrieved
        LOGGER.debug("  RetrieveList founds no items with selected criteria");
      }
    }
    catch ( RuntimeException ex ) 
    {
      //log occurred error
      LOGGER.error(RUNTIME_EXCEPTION_LOG, ex);
      //throw custom generated exception
      throw new DaoException(ExceptionCodes.GENERIC.getCode(), 
                             ExceptionCodes.GENERIC.getDefaultDescription(), 
                             DAO_NAME, 
                             ex);
    } 
    finally 
    {
      //log operation end
      LOGGER.debug("END [EntityName](s) retrieve List");
      //finalize log
      LogUtils.logEnd(LOGGER, 
                      LogLevel.DEBUG, 
                      startMillis, 
                      methodName);
    }

    //return response object
    return responseList;
  }

  /**
   * Composes retrieval query filter conditions if provided
   * 
   * @param request
   * @return
   *
   * @author [author]
   */
  private Map<ERetrieveCriteria, Object> buildConditions([EntityName]RequestData request)
  {
    //declare returning object
    Map<ERetrieveCriteria, Object> filters = new HashMap<ERetrieveCriteria, Object>(0);
    
    //check for provided filters
    if (request != null)
    {
      //set filters
      setRetrievalFilter(filters, ERetrieveCriteria.LANGUAGE_NAME,          request.getName());
      setRetrievalFilter(filters, ERetrieveCriteria.LANGUAGE_DESCRIPTION,   request.getDescription());
      setRetrievalFilter(filters, ERetrieveCriteria.LANGUAGE_STATUS,        request.getStatusId() != null ? request.getStatusId().shortValue() : null);
    }
    else
    {
      //no filters have been specified
      filters = null;
    }

    //return probably found filters
    return filters;
  }

  /**
   * Set retrieval filter by reference
   * 
   * @param filters
   * @param field
   * @param value
   *
   * @author [author]
   */
  private void setRetrievalFilter(Map<ERetrieveCriteria, Object> filters,
                                  ERetrieveCriteria              field,
                                  String                         value)
  {
    //check for provided requested filter
    if (Utils.notEmpty(value))
    {
      //add filter
      filters.put(field, value);
    }
  }
  
  /**
   * Set retrieval filter by reference
   * 
   * @param filters
   * @param field
   * @param value
   *
   * @author [author]
   */
  private void setRetrievalFilter(Map<ERetrieveCriteria, Object> filters,
                                  ERetrieveCriteria              field,
                                  Object                         value)
  {
    //check for provided requested filter
    if (Utils.notNull(value))
    {
      //add filter
      filters.put(field, value);
    }
  }
}
