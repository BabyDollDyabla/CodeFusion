/**
 * IEngine
 *
 * @author Fabrizio Parlani
 */
package fabermaster.code.fusion.core;

import java.io.Serializable;
import java.util.List;

import fabermaster.code.fusion.util.mappers.ITemplate.ECodeTarget;
import fabermaster.code.fusion.util.mappers.ITemplate.EServiceBlock;

/**
 * @author Fabrizio Parlani
 *
 */
public interface IEngine extends Serializable
{
  public static final String PLACEHOLDER_FILE_SUFFIX = ".template.properties";
  
  /**
   * Processes provided code targets list into passed service block.
   * If targets have not been provided, method processes all existing 
   * targets contained into passed service block.
   * Placeholder substitution tags will be get from provided placeholder file
   * 
   * @param placeholderFile
   * @param serviceBlock
   * @param targets
   *
   * @author Fabrizio Parlani
   */
  public void run(String            placeholderFile,
                  EServiceBlock     serviceBlock,
                  List<ECodeTarget> targets);

  /**
   * Processes provided code target into passed service block.
   * Placeholder substitution tags will be get from provided placeholder file
   * 
   * @param placeholderFile
   * @param serviceBlock
   * @param targets
   *
   * @author Fabrizio Parlani
   */
  public void run(String        placeholderFile,
                  EServiceBlock serviceBlock,
                  ECodeTarget   target);

  /**
   * Processes all existing service block code target(s),
   * Placeholder substitution tags will be get from provided placeholder file
   * 
   * @param placeholderFile
   * @param serviceBlock
   *
   * @author Fabrizio Parlani
   */
  public void run(String        placeholderFile,
                  EServiceBlock serviceBlock);

  
}
