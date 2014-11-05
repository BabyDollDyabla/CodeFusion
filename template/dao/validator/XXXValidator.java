/**
 * [EntityName]Validator
 *
 * @author [author]
 */
package com.[companyPackagePart].digital.platform.crsm.dao.validator;

import java.util.List;

import com.[companyPackagePart].digital.platform.crsm.dao.dto.common.CommonRetrieveFilter;
import com.[companyPackagePart].digital.platform.crsm.dao.dto.[packagename].data.[EntityName]RequestData;
import com.[companyPackagePart].digital.platform.crsm.dao.exception.DaoException;
import com.[companyPackagePart].digital.platform.crsm.dao.exception.DaoException.ExceptionCodes;
import com.[companyPackagePart].digital.platform.crsm.dao.utils.Utils;

/**
 * @author [author]
 *
 */
public class [EntityName]Validator extends CommonsValidator
{

  /**
   * Validates provided [collectionFieldName] request list
   * 
   * @param [collectionFieldName]
   * @throws DaoException
   *
   * @author [author]
   */
  public static void validateInsert(List<[EntityName]RequestData> [collectionFieldName], String daoName) 
  throws DaoException
  {
    //check for a valid provided object
    validateObjectExistence([collectionFieldName], daoName);

    //declare pointer of cycled [fieldNameForComment] items
    int [fieldName]Index = 1;
    
    //cycle provided [collectionFieldName] for one to one validation
    for ([EntityName]RequestData item : [collectionFieldName])
    {
      //check field(s) compulsoriness
      validateMandatory(item.getName(), 
                        false, 
                        "[EntityName] Name is missing into (" + [fieldName]Index + ") provided item", 
                        daoName);

      //increase cycled item pointer
      [fieldName]Index++;
    }
  }

  /**
   * Validates provided [collectionFieldName] request list
   * 
   * @param [collectionFieldName]
   * @throws DaoException
   *
   * @author [author]
   */
  public static void validateUpdate(List<[EntityName]RequestData> [collectionFieldName], String daoName) 
  throws DaoException
  {
    //check for a valid provided object
    validateObjectExistence([collectionFieldName], daoName);

    //declare pointer of cycled [fieldNameForComment] items
    int [fieldName]Index = 1;
    
    //cycle provided [collectionFieldName] for one to one validation
    for ([EntityName]RequestData item : [collectionFieldName])
    {
      //check field(s) compulsoriness
      validateMandatory(item.get[EntityName]Id(), 
                        "[EntityName] Id is missing into (" + [fieldName]Index + ") provided item", 
                        daoName);

      //increase cycled item pointer
      [fieldName]Index++;
    }
  }

  /**
   * Validates provided [collectionFieldName] request list and pagination filter object
   * 
   * @param [collectionFieldName]
   * @param paginator
   * @throws DaoException
   *
   * @author [author], edited by m.pavone
   */
  public static void validateRetrieveList([EntityName]RequestData  [collectionFieldName],
                                          CommonRetrieveFilter paginator, 
                                          String               daoName)
  throws DaoException
  {
    //check for a valid provided pagination object in order to perform checks on provided values
    if (Utils.notNull(paginator))
    {
      //check for a valid and not negative provided value for the number o elements to display per page
      validateGreaterZero(paginator.getFilteringPageElements(), "filteringPageElements", daoName);
      
      //check for a valid and not negative provided value for the page number
      validateGreaterZero(paginator.getFilteringPageNumber(), "filteringPageNumber", daoName);
    }
  }
}
