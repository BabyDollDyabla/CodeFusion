/**
 * ValidatorCreate[EntityName]RequestType
 *
 * @author [author]
 */
package [classPackagePrefix].[packagename];

import java.util.List;
import java.util.Map;

import com.[companyPackagePart].digital.platform.crsm.api.utils.Configuration;
import com.[companyPackagePart].digital.platform.crsm.api.validator.Errors;
import com.[companyPackagePart].digital.platform.crsm.api.validator.ValidatorException;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].Create[EntityName]RequestType;
import com.[companyPackagePart].digital.platform.crsm.exposure.[exposureTypePackagePart].data.[packagename].[EntityName]CreateObjectType;
import [classPackagePrefix].ValidatorErrorHandlerBase;
import [classPackagePrefix].ValidatorRequestType;

/**
 * @author [author]
 *
 */
public class ValidatorCreate[EntityName]RequestType extends ValidatorErrorHandlerBase
{

  /**
   * Class constructor
   * 
   * @param object
   *
   * @author [author]
   */
  public ValidatorCreate[EntityName]RequestType(String object)
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
  public ValidatorCreate[EntityName]RequestType(String        object,
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
      Create[EntityName]RequestType request = Create[EntityName]RequestType.class.cast(sourceRequest);

      //validate compulsoriness of [collectionFieldNameForComment] creation object (it contains inside the list of [collectionFieldNameForComment] to create)
      validateRequired("[fieldName]CreateList", 
                       request.get[EntityName]CreateList(), 
                       errors);

      //checks again for a valid provided creation object in order to avoid successive checks
      if (request.get[EntityName]CreateList() != null)
      {
        //get the list of provided [collectionFieldNameForComment] to create
        List<[EntityName]CreateObjectType> [collectionFieldName] = request.get[EntityName]CreateList().get[CollectionEntityName]();
  
        //validate list compulsoriness
        validateRequired("[collectionFieldName]",
                         [collectionFieldName],
                         errors);

        //checks again for a valid provided [collectionFieldNameForComment] list
        if ([collectionFieldName] != null)
        {
          //validate [collectionFieldNameForComment] list emptiness
          validateListSize(sourceRequest, 
                           "[collectionFieldName]", 
                           [collectionFieldName], 
                           errors);

          //cycle provided [collectionFieldNameForComment] list in order to check mandatory fields item by item
          for ([EntityName]CreateObjectType [fieldName] : [collectionFieldName])
          {
            //validate compulsoriness of cycled item name attribute
            validateRequired("[fieldName].name", [fieldName].getName(), errors);
            //validate length of provided cycled item name attribute
            validateStringLength(request, "[fieldName].name", [fieldName].getName(), errors);

            //validate length of probably provided cycled item description attribute
            validateStringLength(request, "[fieldName].description", [fieldName].getDescription(), errors);
          }
        }
      }

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
