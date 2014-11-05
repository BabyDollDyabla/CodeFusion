/**
 * Builder
 *
 * @author Fabrizio Parlani
 */
package fabermaster.code.fusion.worker;

import java.util.Arrays;

import fabermaster.code.fusion.common.BuilderException;
import fabermaster.code.fusion.core.Engine;
import fabermaster.code.fusion.logging.LogManager;
import fabermaster.code.fusion.util.mappers.ITemplate.ECodeTarget;
import fabermaster.code.fusion.util.mappers.ITemplate.EServiceBlock;


/**
 * @author Fabrizio Parlani
 *
 */
public class Builder
{
  //declare logger instance
  private static final LogManager logger = LogManager.getInstance(Builder.class);
  //declare engine object
  private static Engine           engine = null;

  /**
   * Service Builder start
   * 
   * @param files
   *
   * @author Fabrizio Parlani
   */
  public static void main(String... files)
  {
    try
    {
    	
//      //create engine instance
//      engine = new Engine();
//
//      //create code target
//      engine.run("userroletype", 
//                 EServiceBlock.EXPOSURE, 
//                 Arrays.asList( new ECodeTarget[] { ECodeTarget.SERVICE,
//                                                    ECodeTarget.TRANSFORMER,
//                                                    ECodeTarget.VALIDATOR,
//                                                    ECodeTarget.ERROR_CONFIG,
//                                                    ECodeTarget.XSD} ));
      
      
//        launchAll("userroletype");
        
        launchAll("testentity");
      
    }
    catch ( BuilderException ex )
    {
      //log error
      logger.error("ATTENTION: A Builder Exception has been caught", ex);
    }
    catch ( Exception ex )
    {
      //log error
      logger.error("ATTENTION: An unexpected exception has been caught", ex);
    }
  }
  
  private static void launchAll (String entity) throws BuilderException {
	  
      //create engine instance
      engine = new Engine();

      //create code target
      engine.run(entity, 
                 EServiceBlock.EXPOSURE, 
                 Arrays.asList( new ECodeTarget[] { ECodeTarget.SERVICE,
                                                    ECodeTarget.TRANSFORMER,
                                                    ECodeTarget.VALIDATOR,
                                                    ECodeTarget.ERROR_CONFIG,
                                                    ECodeTarget.XSD} ));
      
      
      engine.run(entity, 
              EServiceBlock.API, 
              Arrays.asList( new ECodeTarget[] { ECodeTarget.API_DTO} ));

      engine.run(entity, 
      EServiceBlock.DAO, 
      Arrays.asList( new ECodeTarget[] { ECodeTarget.DAO_SERVICE,
                                         ECodeTarget.DAO_SERVICE_IMPL,
                                         ECodeTarget.DAO_VALIDATOR } ));
    
      engine.run(entity, 
      EServiceBlock.TENANT, 
      Arrays.asList( new ECodeTarget[] { ECodeTarget.TENANT_BLUEPRINT,
                                         ECodeTarget.TENANT_SERVICE_IMPL } ));
      
  }
}
