/**
 * [EntityName]FacadeImpl
 *
 * @author [author]
 */
package [classPackagePrefix].[packagename];

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.[companyPackagePart].digital.platform.crsm.api.data.ExposureCodes.EStatusCode;
import com.[companyPackagePart].digital.platform.crsm.api.transformer.AbstractTransformer;
import com.[companyPackagePart].digital.platform.crsm.api.transformer.TransformerException;
import com.[companyPackagePart].digital.platform.crsm.api.utils.LogUtils;
import com.[companyPackagePart].digital.platform.crsm.api.utils.ErrorCodeGenerator.EEntity;
import com.[companyPackagePart].digital.platform.crsm.api.utils.LogUtils.LogLevel;
import com.[companyPackagePart].digital.platform.crsm.api.validator.AbstractValidator;
import com.[companyPackagePart].digital.platform.crsm.api.validator.Errors;
import com.[companyPackagePart].digital.platform.crsm.api.validator.ValidatorException;
import com.[companyPackagePart].digital.platform.crsm.dao.dto.common.CommonRetrieveFilter;
import com.[companyPackagePart].digital.platform.crsm.dao.dto.[packagename].data.[EntityName]RequestData;
import com.[companyPackagePart].digital.platform.crsm.dao.dto.[packagename].data.[EntityName]ResponseData;
import com.[companyPackagePart].digital.platform.crsm.dao.exception.DaoException;
import com.[companyPackagePart].digital.platform.crsm.dao.service.[EntityName]Dao;
import com.[companyPackagePart].digital.platform.crsm.dao.utils.tracker.DaoTrackerCustomizer;
import com.[companyPackagePart].digital.platform.crsm.exposure.common.TenantAwareExposure;
import com.[companyPackagePart].digital.platform.crsm.exposure.common.TenantException;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]RequestType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]ResponseType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]RequestType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]ResponseType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Update[EntityName]RequestType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Update[EntityName]ResponseType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].transformer.[packagename].Transformers;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].validator.[packagename].Validators;


/**
 * @author [author]
 *
 */
public class [EntityName]FacadeImpl extends    TenantAwareExposure
                                    implements [EntityName]Facade
{
  //get logger instance
  private static final Logger LOGGER          = LoggerFactory.getLogger([EntityName]FacadeImpl.class);

  //declare used objects
  private Validators          validators;
  private Transformers        transformers;

  /* (non-Javadoc)
   * @see com.[companyPackagePart].digital.platform.crsm.exposure.common.TenantAwareExposure#initServiceTracker()
   */
  @Override
  public ServiceTracker initServiceTracker()
  {
    //tries to get current bundle context
    BundleContext ctx = FrameworkUtil.getBundle(this.getClass())
                                     .getBundleContext();

    //returns the retrieved service tracker object
    return new ServiceTracker(ctx, 
                              [EntityName]Dao.class, 
                              new DaoTrackerCustomizer(getServiceMap()));
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].digital.platform.crsm.exposure.common.ConfigurationAwareExposure#configureExposure()
   */
  @Override
  public void configureExposure()
  {
    //initializes validation object
    this.validators   = new Validators(this.getValidationProps(), 
                                       this.getErrorProps());
    //initializes transformer object
    this.transformers = new Transformers(this.getTransformerProps());
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].digital.platform.crsm.exposure.common.ConfigurationAwareExposure#disposeExposure()
   */
  @Override
  public void disposeExposure()
  {
    //Nothing to do
  }

  /* (non-Javadoc)
   * @see [classPackagePrefix].[packagename].[EntityName]Facade#create[EntityName](com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]RequestType)
   */
  @SuppressWarnings("unchecked")
  public Create[EntityName]ResponseType create[EntityName](Create[EntityName]RequestType request)
  {
    //save operation start time
    long         startTime = System.currentTimeMillis();
    //save called method name
    final String method    = "create[EntityName]";

    //log provided request
    LOGGER.debug("create[EntityName] request [{}]", request);
    //check for a valid provided request
    if (request != null)
    {
      //log provided transaction id
      LOGGER.info("  - create[EntityName] transactionId [{}]", request.getTransactionId());
    }

    //obtains properly validation object
    AbstractValidator   validator   = validators.getCreate[EntityName]();
    //obtains properly transformation object
    AbstractTransformer transformer = transformers.getCreate[EntityName]();

    //declare map object used as internal context
    Map<String,Object>  context     = new HashMap<String, Object>();

    try
    {
      //put currently executed API belonging Entity Id into internal context object
      context.put("entityId", 
                  EEntity.USER);
        
      //Validate provided request against properly retrieved validation object
      Errors errors = validator.validate(request, 
                                         context);
      //check for discovered validation errors
      if (errors != null)
      {
        //return properly composed response after validation detected error(s)
        return ((Create[EntityName]ResponseType) validator.mapErrorToResponse(errors,
                                                                        context, 
                                                                        EStatusCode.KO));
      }

      //put provided transaction id (from request) into internal context object
      context.put("transactionId", 
                  request.getTransactionId());

      //get object will use to do the job
      [EntityName]Dao                dao            = this.getService(request.getTenant(), 
                                                                [EntityName]Dao.class);

      //create the DAO request object transforming current provided request
      List<[EntityName]RequestData>  daoRequest     = (List<[EntityName]RequestData>) transformer.to(request, 
                                                                                         context);
      //get operation response
      List<[EntityName]ResponseData> insertResponse = dao.insert(daoRequest);

      //compose returning response transforming returned DAO operation response
      Create[EntityName]ResponseType response       = (Create[EntityName]ResponseType) transformer.from(insertResponse, 
                                                                                            context);

      //return operation response
      return response;
      
    }
    catch ( ValidatorException ex )
    {
      //log occurred error
      LOGGER.error("Cannot validate request", ex);
      //return properly composed response
      return Create[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                  context, 
                                                                                  EStatusCode.KO));
    }
    catch ( DaoException ex )
    {
      //log occurred error
      LOGGER.error("Error while executing DAO request", ex);
      //return properly composed response
      return Create[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                  context, 
                                                                                  EStatusCode.KO));
    }
    catch ( TransformerException ex )
    {
      //log occurred error
      LOGGER.error("Unable to transform request", ex);
      //return properly composed response
      return Create[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                  context, 
                                                                                  EStatusCode.KO));
    }
    catch ( TenantException ex )
    {
      //log occurred error
      LOGGER.error("Tenant Exposure error", ex);
      //return properly composed response
      return Create[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                  context, 
                                                                                  EStatusCode.KO));
    }
    finally
    {
      //log operation end
      LogUtils.logEnd(LOGGER, 
                      LogLevel.DEBUG, 
                      startTime, 
                      method);
    }
  }

  /* (non-Javadoc)
   * @see [classPackagePrefix].[packagename].[EntityName]Facade#update[EntityName](com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]RequestType)
   */
  @SuppressWarnings("unchecked")
  public Update[EntityName]ResponseType update[EntityName](Update[EntityName]RequestType request)
  {
    //save operation start time
    long         startTime = System.currentTimeMillis();
    //save called method name
    final String method    = "update[EntityName]";

    //log provided request
    LOGGER.debug("update[EntityName] request [{}]", request);
    //check for a valid provided request
    if (request != null)
    {
      //log provided transaction id
      LOGGER.info("  - update[EntityName] transactionId [{}]", request.getTransactionId());
    }

    //obtains properly validation object
    AbstractValidator   validator   = validators.getUpdate[EntityName]();
    //obtains properly transformation object
    AbstractTransformer transformer = transformers.getUpdate[EntityName]();

    //declare map object used as internal context
    Map<String,Object>  context     = new HashMap<String, Object>();

    try
    {
      //put currently executed API belonging Entity Id into internal context object
      context.put("entityId", 
                  EEntity.USER);
        
      //Validate provided request against properly retrieved validation object
      Errors errors = validator.validate(request, 
                                         context);
      //check for discovered validation errors
      if (errors != null)
      {
        //return properly composed response after validation detected error(s)
        return ((Update[EntityName]ResponseType) validator.mapErrorToResponse(errors,
                                                                        context, 
                                                                        EStatusCode.KO));
      }

      //put provided transaction id (from request) into internal context object
      context.put("transactionId", 
                  request.getTransactionId());

      //get object will use to do the job
      [EntityName]Dao                dao            = this.getService(request.getTenant(), 
                                                                [EntityName]Dao.class);

      //create the DAO request object transforming current provided request
      List<[EntityName]RequestData>  daoRequest     = (List<[EntityName]RequestData>) transformer.to(request, 
                                                                                         context);
      //get operation response
      List<[EntityName]ResponseData> updateResponse = dao.update(daoRequest);

      //compose returning response transforming returned DAO operation response
      Update[EntityName]ResponseType response       = (Update[EntityName]ResponseType) transformer.from(updateResponse, 
                                                                                            context);

      //return operation response
      return response;
      
    }
    catch ( ValidatorException ex )
    {
      //log occurred error
      LOGGER.error("Cannot validate request", ex);
      //return properly composed response
      return Update[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                  context, 
                                                                                  EStatusCode.KO));
    }
    catch ( DaoException ex )
    {
      //log occurred error
      LOGGER.error("Error while executing DAO request", ex);
      //return properly composed response
      return Update[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                  context, 
                                                                                  EStatusCode.KO));
    }
    catch ( TransformerException ex )
    {
      //log occurred error
      LOGGER.error("Unable to transform request", ex);
      //return properly composed response
      return Update[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                  context, 
                                                                                  EStatusCode.KO));
    }
    catch ( TenantException ex )
    {
      //log occurred error
      LOGGER.error("Tenant Exposure error", ex);
      //return properly composed response
      return Update[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                  context, 
                                                                                  EStatusCode.KO));
    }
    finally
    {
      //log operation end
      LogUtils.logEnd(LOGGER, 
                      LogLevel.DEBUG, 
                      startTime, 
                      method);
    }
  }

  /* (non-Javadoc)
   * @see [classPackagePrefix].[packagename].[EntityName]Facade#retrieve[EntityName](com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]RequestType)
   */
  public Retrieve[EntityName]ResponseType retrieve[EntityName](Retrieve[EntityName]RequestType request)
  {
    //save operation start time
    long         startTime = System.currentTimeMillis();
    //save called method name
    final String method    = "retrieve[EntityName]";

    //log provided request
    LOGGER.debug("retrieve[EntityName] request [{}]", request);
    //check for a valid provided request
    if (request != null)
    {
      //log provided transaction id
      LOGGER.info("  - retrieve[EntityName] transactionId [{}]", request.getTransactionId());
    }

    //obtains properly validation object
    AbstractValidator   validator   = validators.getRetrieve[EntityName]();
    //obtains properly transformation object
    AbstractTransformer transformer = transformers.getRetrieve[EntityName]();

    //declare map object used as internal context
    Map<String,Object>  context     = new HashMap<String, Object>();

    try
    {
      //put currently executed API belonging Entity Id into internal context object
      context.put("entityId", 
                  EEntity.USER);
        
      //Validate provided request against properly retrieved validation object
      Errors errors = validator.validate(request, 
                                         context);
      //check for discovered validation errors
      if (errors != null)
      {
        //return properly composed response after validation detected error(s)
        return ((Retrieve[EntityName]ResponseType) validator.mapErrorToResponse(errors,
                                                                         context, 
                                                                         EStatusCode.KO));
      }

      //put provided transaction id (from request) into internal context object
      context.put("transactionId", 
                  request.getTransactionId());

      //get object will use to do the job
      [EntityName]Dao            dao         = this.getService(request.getTenant(), 
                                                         [EntityName]Dao.class);

      //create the DAO request object transforming current provided request
      [EntityName]RequestData    daoRequest  = ([EntityName]RequestData) transformer.to(request, 
                                                                            context);
      //create the retrieve pagination object to fill if paging values have been provided
      CommonRetrieveFilter paginator   = null;
      
      //create paging range object to pass if values have been provided
      if ((request.getFilteringPageElements() != null) && 
          (request.getFilteringPageNumber() != null))
      {
        //fill pagination object
        paginator = new CommonRetrieveFilter(request.getFilteringPageElements(),
                                             request.getFilteringPageNumber());
      }

      //get operation response
      List<[EntityName]ResponseData>   retrieveResponse = dao.retrieveList(daoRequest, 
                                                                     paginator);

      //compose returning response transforming returned DAO operation response
      Retrieve[EntityName]ResponseType response         = (Retrieve[EntityName]ResponseType) transformer.from(retrieveResponse, 
                                                                                                  context);

      //return operation response
      return response;
      
    }
    catch ( ValidatorException ex )
    {
      //log occurred error
      LOGGER.error("Cannot validate request", ex);
      //return properly composed response
      return Retrieve[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                    context, 
                                                                                    EStatusCode.KO));
    }
    catch ( DaoException ex )
    {
      //log occurred error
      LOGGER.error("Error while executing DAO request", ex);
      //return properly composed response
      return Retrieve[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                    context, 
                                                                                    EStatusCode.KO));
    }
    catch ( TransformerException ex )
    {
      //log occurred error
      LOGGER.error("Unable to transform request", ex);
      //return properly composed response
      return Retrieve[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                    context, 
                                                                                    EStatusCode.KO));
    }
    catch ( TenantException ex )
    {
      //log occurred error
      LOGGER.error("Tenant Exposure error", ex);
      //return properly composed response
      return Retrieve[EntityName]ResponseType.class.cast(validator.mapExceptionToResponse(ex, 
                                                                                    context, 
                                                                                    EStatusCode.KO));
    }
    finally
    {
      //log operation end
      LogUtils.logEnd(LOGGER, 
                      LogLevel.DEBUG, 
                      startTime, 
                      method);
    }
  }
}
