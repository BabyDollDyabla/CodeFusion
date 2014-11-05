/**
 * ValidatorRetrieve[EntityName]RequestType
 *
 * @author [author]
 */
package [classPackagePrefix].[packagename];

import java.util.Map;

import com.[companyPackagePart].digital.platform.crsm.api.utils.Configuration;
import com.[companyPackagePart].digital.platform.crsm.api.validator.Errors;
import com.[companyPackagePart].digital.platform.crsm.api.validator.ValidatorException;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Retrieve[EntityName]RequestType;
import [classPackagePrefix].ValidatorErrorHandlerBase;
import [classPackagePrefix].ValidatorRequestType;

/**
 * @author [author]
 *
 */
public class ValidatorRetrieve[EntityName]RequestType extends ValidatorErrorHandlerBase
{

  /**
   * Class constructor
   * 
   * @param object
   *
   * @author [author]
   */
  public ValidatorRetrieve[EntityName]RequestType(String object)
  {
    super(object);
  }

  /**
   * Class constructor
   * 
   * @param object
   * @param validationProps
   * @param errorProps
   *
   * @author [author]
   */
  public ValidatorRetrieve[EntityName]RequestType(String        object,
                                            Configuration validationProps, 
                                            Configuration errorProps) 
  {
    super(object, 
          validationProps, 
          errorProps);
  }

  /* (non-Javadoc)
   * @see com.[companyPackagePart].digital.platform.crsm.api.validator.AbstractValidator#validate(java.lang.Object, java.util.Map)
   */
  @Override
  public Errors validate(Object              sourceRequest, 
                         Map<String, Object> context) 
  throws ValidatorException
  {
    //declare returning object
    Errors errors = null;

    try
    {
      //create common validation object for provided request 
      ValidatorRequestType validatorCommonRequest = new ValidatorRequestType(form, 
                                                                             getValidationProps(), 
                                                                             getErrorProps());

      //validate provided bean (the request) passing private passed context
      errors = validatorCommonRequest.validate(sourceRequest, 
                                               context);

      //checks for failed common request validation(s)
      if (errors != null)
      {
        //return caught error(s)
        return errors;
      }

      //here commons request validation has been passed so we initialize the returning object
      errors = new Errors();

      //cast provided object into properly request object
      Retrieve[EntityName]RequestType request = Retrieve[EntityName]RequestType.class.cast(sourceRequest);

      //validate length of provided item name attribute
      validateStringLength(request, "[fieldName].name", request.getName(), errors);
      //validate length of probably provided item description attribute
      validateStringLength(request, "[fieldName].description", request.getDescription(), errors);
//      //validate for a numeric provided value of number of items per page
//      validateIsANumber("[fieldName].items.per.page", String.valueOf(request.getItemsPerPage()), errors);
      //validate if provided value of  number of items per page is an acceptable one
      validateNumberGreaterThan(request, "[fieldName].items.per.page", request.getFilteringPageElements(), errors);
//      //validate for a numeric provided value of page number
//      validateIsANumber("[fieldName].page.number", String.valueOf(request.getPageNumber()), errors);
      //validate if provided value of  number of items per page is an acceptable one
      validateNumberGreaterThan(request, "[fieldName].page.number", request.getFilteringPageNumber(), errors);
      //validate page number that is required if items per page value has been provided
      validateRequiredIf("[fieldName].page.number", request.getFilteringPageNumber(), ((request.getFilteringPageElements() != null) ? true : false), errors);

      //check for caught validation errors      
      if (errors.size() > 0)
      {
        //log found validation error(s)
        logger.debug("Errors from validation [{}]", errors);
        //return caught error(s)
        return errors;
      }

      //no validation errors have been caught 
      return null;
    }
    catch ( Exception ex )
    {
      //log occurred validation exception
      logger.error("Error during validation", ex);

      //compose properly error to return (a generic error)
      errors.put("GENERIC", 
                 new Errors.ErrorManagement(getErrorCodeGeneric(), 
                                            getErrorCodeGeneric()));

      //return caught error, wrapped under a generic error
      return errors;
    }
  }
}
