/**
 * [EntityName]Dao
 *
 * @author [author]
 */
package [classPackagePrefix];

import java.util.List;

import com.[companyPackagePart].digital.platform.crsm.dao.common.CrsmDao;
import com.[companyPackagePart].digital.platform.crsm.dao.dto.common.CommonRetrieveFilter;
import com.[companyPackagePart].digital.platform.crsm.dao.dto.[packagename].data.[EntityName]RequestData;
import com.[companyPackagePart].digital.platform.crsm.dao.dto.[packagename].data.[EntityName]ResponseData;
import com.[companyPackagePart].digital.platform.crsm.dao.exception.DaoException;

/**
 * @author [author]
 *
 */
public interface [EntityName]Dao extends CrsmDao {
  
  /**
   * Inserts provided [fieldNameForComment](s) list
   * 
   * @param [collectionFieldName]
   * @return
   * @throws DaoException
   *
   * @author [author]
   */
  public List<[EntityName]ResponseData> insert(List<[EntityName]RequestData> [collectionFieldName]) 
  throws DaoException;

  /**
   * Updates provided [fieldNameForComment](s) list
   * 
   * @param [collectionFieldName]
   * @return
   * @throws DaoException
   *
   * @author [author]
   */
  public List<[EntityName]ResponseData> update(List<[EntityName]RequestData> [collectionFieldName]) 
  throws DaoException;

  /**
   * Retrieves [fieldNameForComment](s) list paged with provided paginator filter
   * 
   * @param request
   * @param commonFilter
   * @return
   * @throws DaoException
   *
   * @author [author]
   */
  public List<[EntityName]ResponseData> retrieveList([EntityName]RequestData  request, 
                                                 CommonRetrieveFilter paginator) 
  throws DaoException;

}
