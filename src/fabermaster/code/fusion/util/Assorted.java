package fabermaster.code.fusion.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * <p>Title: Assorted</p>
 *
 * <p>Description: Assorted static utility methods</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 * 
 * @author Fabrizio Parlani (from its own framework)
 * @version 1.0
 */
public class Assorted 
{
  /*
   * Mostly used regular expression 
  */
//  public static final String isMobile       = "(" +                                       //start international prefix composition
//                                              "((\\+[1-9]{2})([-\\s]?){0,1})|" +            //international prefix with an optional hyphen or a space like:                                  [+39 ] | [+39-] | [+39]
//                                              "((00[1-9]{2})([-\\s]?){0,1})" +              //international prefix with double 00 initials and an optional hyphen or a space like:           [0039 ] | [0039-] | [0039]
//                                              ")" +                                       //end international prefix composition
//                                              "(((3[0-9]{2}){1})(([-\\s]?){0,1}))" +      //mobile prefix starting with a 3 and an optional hyphen or a space like:                          [300 ] | [300-] | [300]
//                                              "((\\d{3})(([-\\s]?){0,1})(\\d{3,5}))";     //mobile number can be composed with a sequence from 6 to 8 digits or with a sequence of 3 digits
//                                                                                          //and an optional hyphen or space and another sequence of digits from 3 to 5 like:                 [000000(00)] | [000 000(00)] | [000-000(00)]
//
//  public static final String isPhone        = "(" +                                       //start international prefix composition
//                                              "((\\+[1-9]{2})([-\\s]?){0,1})|" +            //international prefix with an optional hyphen or a space like:                                  [+39 ] | [+39-] | [+39]
//                                              "((00[1-9]{2})([-\\s]{1}){0,1})" +            //international prefix with double 00 initials and an optional hyphen or a space like:           [0039 ] | [0039-] | [0039]
//                                              ")" +                                       //end international prefix composition
//                                              "(((0[1-9]{1,3}){1})(([-\\s]{1}){0,1}))" +  //phone prefix starting with a 0 and an optional hyphen or a space like:                           [0774 ] | [0774-] | [0774]
//                                              "(\\d{5,9})";                               //phone number is composed by a digit sequence whose length is between 5 and 9 digits like         [12345] | [123456789]

  public static final String isMobile       = "((\\+)|(00))?\\d{8,15}";

  public static final String isPhone        = "((\\+)|(00))?\\d{8,15}";

  public static final String isMail         = "("+ 
                                              "([^<>()[\\\\]\\.,;:\\s@\\\"]+(\\.[^<>()[\\\\]\\.,;:\\s@\\\"]+)*)|" + 
                                              "(\\\".+\\\")" +
                                              ")" +
                                              "@" +
                                              "(" +
                                              "(\\[(2([0-4]\\d|5[0-5])|1?\\d{1,2})(\\.(2([0-4]\\d|5[0-5])|1?\\d{1,2})){3} \\\\])|"+ 
                                              "(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,})" + 
                                              ")";

  public static final String isURL          = "(http[s]?:\\/\\/|ftp:\\/\\/)?" +
  		                                        "(www\\.)?[a-zA-Z0-9-\\.]+\\.(com|org|net|mil|edu|ca|co.uk|com.au|gov|eu)?" +
  		                                        "((:{0,1}([0-9]{2,5}))+){0,1}" +
  		                                        "((\\/)?([a-zA-Z0-9_\\.%#\\*\\?\\&\\=\\^-]+)?)+";

  public static final String isVersion      = "\\d+(\\.\\d+)*";                           //version number check                                                                             [0.4.5] | [10.0.4]

  public static final String clearString    = "[a-zA-Z0-9_]+";                            //clear string from awkward characters only leave these characters set (a-z) (A-Z) (0-9) and the _ character
  
  /*
   * Used enumerations 
  */
  //enumeration to check directories creation result
  public static enum EDirsCheck
  { DIRS_CREATION_SUCCESS, 
    DIRS_CREATION_FAILURE, 
    DIRS_NOT_EXISTING, 
    DIRS_EXISTING , 
    DIRS_NAME_INVALID, 
    DIRS_SECURITY_EXCEPTION,
    DIRS_GENERIC_EXCEPTION }
  
  //enumeration to set date match result
  public static enum EDatesMatch 
  { BEFORE, 
    EQUALS, 
    AFTER, 
    INVALID }

  //enumeration to specify date difference measure returning
  public static enum EDatesDifference
  { 
    MILLISECONDS (1),
    SECONDS      (1000),
    MINUTES      ((60 * 1000)),
    HOURS        ((60 * 60 * 1000)),
    DAYS         ((24 * 60 * 60 * 1000)),
    MONTHS       ((12 * 24 * 60 * 60 * 1000));

    //enumerator attribute
    private long divider;

    /**
     * Enumerator constructor
     * 
     * @param divider
     */
    private EDatesDifference(long divider)
    {
      this.divider = divider;
    }

    /**
     * @return the divider
     */
    public long getDivider()
    {
      return divider;
    }
  }
  
  /**
    * Checks a null value for a String and returns empty string if provided value is null
    * 
    * @param valueToCheck
    *   Value to check
    * @return 
    *   Checked string or empty string if passed one is null or invalid
    * @author Fabrizio Parlani
  */
  public final static String checkNullString(String valueToCheck)
  {
    //return value provided by properly overloaded method call
    return checkNullString(valueToCheck, 
                           false);
  }

  /**
    * Checks a null value for a String specifying if returning value must be trimmed.
    * Method returns empty string if provided value is null
    * 
    * @param valueToCheck
    *   Value to check
    * @param trimValue
    *   Flag to determinate if returning string must be trimmed
    * @return 
    *   Checked string (eventually trimmed) or empty string if passed one is null or invalid
    * @author Fabrizio Parlani
  */
  public final static String checkNullString(String  valueToCheck,
                                             boolean trimValue)
  {
    try 
    {
      //return passed value or empty string if passed string is null
      return ((valueToCheck == null) ? "" : ((trimValue) ? valueToCheck.trim() : valueToCheck));
    } 
    catch(Exception ex) 
    {
      //return empty string
      return ("");
    } 
  }

  /**
   * Returns a probably cut value if its length exceeds provided length limit value. If value
   * is smaller than provided limit the entire value will be returned 
   * 
   * @param valueToCheck
   *   Value to check
   * @param lengthLimit
   *   The maximum provided value length to avoid string cutting
   * @param cutValueSuffix
   *   The cut string suffix to append
   * @return
   *   Cut string if value exceeded provided limit, otherwise the value itself (in either cases value will be trimmed)
   * @author Fabrizio Parlani
   */
  public static String checkDisplayString(String valueToCheck,
                                          int    lengthLimit,
                                          String cutValueSuffix)
  {
    //check for an existing provided value
    if ((Assorted.isNotEmpty(valueToCheck, true)) && (Assorted.checkNullString(valueToCheck, true).length() > lengthLimit))
    { //returning properly cut value
      return new StringBuilder(checkNullString(valueToCheck, true).substring(0, lengthLimit))
                       .append(cutValueSuffix)
                       .toString();
    }
    
    //here value is smaller than cutLength value or is empty
    return checkNullString(valueToCheck, true);
  }
  
  /**
    * Checks if passed value (previously checked and trimmed if set) is not an empty string
    * 
    * @param valueToCheck
    *   Value to check
    * @param trimValue
    *   Flag to determinate if passed string must be trimmed
    * @return
    *   <i>true</i> if checked and probably trimmed passed value is not empty, otherwise <i>false</i> 
    * @author Fabrizio Parlani
  */
  public final static boolean isNotEmpty(String  valueToCheck,
                                         boolean trimValue)
  {
    //return check test value
    return (!checkNullString(valueToCheck, trimValue).equalsIgnoreCase("")) ? true : false;
  }

  /**
    * Checks if passed value (previously checked and not trimmed) is not an empty string
    * 
    * @param valueToCheck
    *   Value to check
    * @return
    *   <i>true</i> if checked and not trimmed passed value is not empty, otherwise <i>false</i> 
    * @author Fabrizio Parlani
  */
  public final static boolean isNotEmpty(String valueToCheck)
  {
    //return check test value
    return isNotEmpty(valueToCheck, false);
  }

  /**
    * Checks if passed value (previously checked and not trimmed) is an empty string
    * 
    * @param valueToCheck
    *   Value to check
    * @return
    *   <i>true</i> if checked and not trimmed passed value is empty, otherwise <i>false</i> 
    * @author Fabrizio Parlani
  */
  public final static boolean isEmpty(String valueToCheck)
  { return !isNotEmpty(valueToCheck); }

  /**
    * Checks if passed value (previously checked and trimmed if set) is an empty string
    * 
    * @param valueToCheck
    *   Value to check
    * @param trimValue
    *   Flag to determinate if passed string must be trimmed
    * @return
    *   <i>true</i> if checked and probably trimmed passed value is empty, otherwise <i>false</i> 
    * @author Fabrizio Parlani
  */
  public final static boolean isEmpty(String  valueToCheck,
                                      boolean trimValue)
  { return !isNotEmpty(valueToCheck, trimValue); }

  /**
    * Checks if passed values matches
    * 
    * @param firstValue
    *   First value to match
    * @param secondValue
    *   Second value to match
    * @param trimValues
    *   Flag to determinate if passed strings must be trimmed
    * @param ignoreCase
    *   Flag to determinate if executed values match must be case-sensitive or not
    * @return
    *   <i>true</i> if passed values matches, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static boolean equals(String  firstValue,
                                     String  secondValue,
                                     boolean trimValues,
                                     boolean ignoreCase)
  { //return the executed string match result depending on selected switches :
    //  - Trim passed values
    //  - Ignore values case
    return ((ignoreCase) ? 
            checkNullString(firstValue, trimValues).equalsIgnoreCase(checkNullString(secondValue, trimValues)) :
            checkNullString(firstValue, trimValues).equals(checkNullString(secondValue, trimValues)));
  }

  /**
    * Checks if passed values matches forcing values trimming flag
    * 
    * @param firstValue
    *   First value to match
    * @param secondValue
    *   Second value to match
    * @param ignoreCase
    *   Flag to determinate if executed values match must be case-sensitive or not
    * @return
    *   <i>true</i> if passed values matches, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static boolean equals(String  firstValue,
                                     String  secondValue,
                                     boolean ignoreCase)
  { return equals(firstValue, secondValue, true, ignoreCase); }

  /**
    * Checks if passed values match forcing values trimming and case insensitive flags
    * 
    * @param firstValue
    *   First value to match
    * @param secondValue
    *   Second value to match
    * @return
    *   <i>true</i> if passed values matches, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */ 
  public final static boolean equals(String  firstValue,
                                     String  secondValue)
  { return equals(firstValue, secondValue, true, true); }

  /**
    * Checks if passed values don't matches
    * 
    * @param firstValue
    *   First value to match
    * @param secondValue
    *   Second value to match
    * @param trimValues
    *   Flag to determinate if passed strings must be trimmed
    * @param ignoreCase
    *   Flag to determinate if executed values match must be case-sensitive or not
    * @return
    *   <i>true</i> if passed values don't matches, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static boolean notEquals(String  firstValue,
                                        String  secondValue,
                                        boolean trimValues,
                                        boolean ignoreCase)
  { return !equals(firstValue, secondValue, trimValues, ignoreCase); }

  /**
    * Checks if passed values don't match forcing values trimming flag
    * 
    * @param firstValue
    *   First value to match
    * @param secondValue
    *   Second value to match
    * @param ignoreCase
    *   Flag to determinate if executed values match must be case-sensitive or not
    * @return
    *   <i>true</i> if passed values don't matches, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static boolean notEquals(String  firstValue,
                                        String  secondValue,
                                        boolean ignoreCase)
  { return !equals(firstValue, secondValue, true, ignoreCase); }

  /**
    * Checks if passed values don't match forcing values trimming and case insensitive flags
    * 
    * @param firstValue
    *   First value to match
    * @param secondValue
    *   Second value to match
    * @return
    *   <i>true</i> if passed values don't matches, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */ 
  public final static boolean notEquals(String  firstValue,
                                        String  secondValue)
  { return !equals(firstValue, secondValue, true, true); }

  /**
    * Checks if passed value comply to specified regular expression
    * 
    * @param valueToCheck
    *   Value to check
    * @param regex
    *   The regular expression to apply
    * @return
    *   <i>true</i> if passed value matches the regular expression, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static boolean isWellFormatted(String valueToCheck,
                                              String regex)
  {
    try
    {
      //declare a Pattern where to compile passed regular expression string
      Pattern p = Pattern.compile(regex);
      //got a matcher for string to check
      Matcher m = p.matcher(valueToCheck);
      //return formatting result
      return m.matches();
    }
    catch (Exception ex )
    {
      //if something wrong we'll return not well formatted string
      return false;
    }
  }

  /**
    * Tries to clean a passed string leaving only characters sequences
    * that complies to passed regular expression. Passed string is previously
    * checked for emptiness. If something goes wrong the source string will
    * be returned without doing cleaning process. The process may fails if
    * passed regular expression is empty, invalid or misspelled 
    * 
    * @param valueToCheck
    *   The value to clean
    * @param regex
    *   The regular expression to apply for leaving characters
    * @return
    *   The cleaned string or an empty string if passed one is invalid or empty.
    *   Instead if something goes wrong, the source value will be returned
    * @author Fabrizio Parlani
  */
  public final static String removeAwkwardChars(String valueToClean,
                                                String regex)
  {
    try
    {
      //check for a valid value passed
      if (isNotEmpty(valueToClean))
      {
        //declare object used to compose cleaned string
        StringBuilder sb = new StringBuilder();

        //declare a Pattern where to compile the passed 
        //regular expression used to clear the source value
        Pattern p = Pattern.compile(clearString);
        //got a matcher for string to check
        Matcher m = p.matcher(valueToClean);

        //cycle to clean the source string
        while (m.find())
        { //compose cleaned string
          sb.append(m.group());
        }

        //return cleaned string
        return sb.toString();
      }
      else
      {
        //if an invalid object will be passed, we return an empty string
        return "";
      }
    }
    catch (Exception e)
    {
      //when something goes wrong we return the input object as is
      return valueToClean;
    }
  }

  /**
    * Tries to clean a passed string leaving only characters sequences
    * that complies to the default cleaning expression defined in this 
    * class. This expression leaves only the following characters sets:<br/>
    * <ul>
    *   <li>from 'a' through 'z'</li>
    *   <li>from 'A' through 'Z'</li>
    *   <li>from '0' through '9'</li>
    *   <li>the '_' character</li>
    * </ul>
    * </br>
    * 
    * 
    * @param valueToClean
    *   The value to clean
    * @return
    *   The cleaned string or an empty string if passed one is invalid or empty.
    *   Instead if something goes wrong, the source value will be returned
    * @author Fabrizio Parlani
  */
  public final static String removeAwkwardChars(String valueToClean)
  {
    //return properly overloaded method call
    return removeAwkwardChars(valueToClean, 
                              clearString);
  }

  /**
    * Checks if passed object (derived from a java.util.Collection) 
    * is not valid or empty
    * 
    * @param collection
    *   the object to check
    * @return
    *   <i>true</i> if object is either invalid or empty, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static boolean isEmpty(Collection<?> collection)
  {
    //check for object existence and emptiness
    return ((collection == null) || collection.isEmpty());
  }
  
  /**
    * Checks if passed object (derived from a java.util.Collection) 
    * is valid and not empty
    * 
    * @param collection
    *   the object to check
    * @return
    *   <i>true</i> if object is either valid and not empty, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static boolean isNotEmpty(Collection<?> collection)
  {
    //check for object existence and filling
    return (!isEmpty(collection));
  }

  /**
    * Checks if passed object (derived from a java.util.Map) 
    * is not valid or empty
    * 
    * @param map
    *   the object to check
    * @return
    *   <i>true</i> if object is either invalid or empty, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static <K, V> boolean isEmpty(Map<K, V> map)
  {
    //check for object existence and emptiness
    return ((map == null) || map.isEmpty());
  }

  /**
    * Checks if passed object (derived from a java.util.Map) 
    * is valid and not empty
    * 
    * @param collection
    *   the object to check
    * @return
    *   <i>true</i> if object is either valid and not empty, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static <K, V> boolean isNotEmpty(Map<K, V> map)
  {
    //check for object existence and filling
    return (!isEmpty(map));
  }

  /**
    * Checks if passed object (derived from a java.util.Enumeration) 
    * is not valid or contains no more elements
    * 
    * @param enumeration
    *   the enumeration to check
    * @return
    *   <i>true</i> if object is either invalid or contains no more elements, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static boolean isEmpty(Enumeration<?> enumeration)
  {
    //check for object existence and emptiness
    return ((enumeration == null) || (!enumeration.hasMoreElements()));
  }

  /**
    * Checks if passed object (derived from a java.util.Enumeration) 
    * is valid and contains elements to iterate
    * 
    * @param enumeration
    *   the enumeration to check
    * @return
    *   <i>true</i> if object is either valid and contains elements to iterate, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static boolean isNotEmpty(Enumeration<?> enumeration)
  {
    //check for object existence and with elements to iterate
    return (!isEmpty(enumeration));
  }

  /**
    * Checks if passed object (a generic array) 
    * is not valid or contains no items
    * 
    * @param <T>
    *   the generic passed type
    * @param array
    *   the array to check
    * @return
    *   <i>true</i> if object is either invalid or contains no items, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static <T> boolean isEmpty(T[] array)
  {
    //check for object existence and emptiness
    return ((array == null) || (array.length == 0));
  }

  /**
    * Checks if passed object (a generic array) 
    * is valid and not empty
    * 
    * @param <T>
    *   the generic passed type
    * @param array
    *   the array to check
    * @return
    *   <i>true</i> if object is either valid and contains items, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static <T> boolean isNotEmpty(T[] array)
  {
    //check for object existence and filling
    return (!isEmpty(array));
  }

  /**
    * Checks if passed object (a generic array) 
    * is valid, not empty and contains at least one valid item 
    * 
    * @param <T>
    *   the generic passed type
    * @param array
    *   the array to check
    * @return
    *   <i>true</i> if object is either valid, contains items and at least one of these items is valid, otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public final static <T> boolean containsItems(T[] array)
  {
    //first of all check for a valid passed object
    if (isNotEmpty(array))
    {
      //cycle across items
      for (T item : array)
      { //validate current item
        if (item != null)
        { //object contains at least one valid item so it's valid
          return true;
        }
      }
    }
    //here we are sure that passed object is invalid/empty or contains no valid items
    return false;
  }
  
  /**
    * Tries to format provided date into supplied pattern,
    * if passed date and/or passed format is not valid, an
    * empty String will be returned instead of formatted date
    * 
    * @param date
    *   the date to format
    * @param formatPattern
    *   the format pattern to apply (ex: dd/MM/yyyy)
    * @return
    *   The formatted date into provided pattern or an empty string if an error occurs
    * @author Fabrizio Parlani
  */ 
  public static String dateToString(Date   date,
                                    String formatPattern)
  {
    try
    {
      //tries to return formatted date object into provided pattern
      return (new SimpleDateFormat(formatPattern)).format(date);
    }
    catch(Exception e)
    {
      //return empty string
      return ("");
    }
  }

  /**
    * Tries to parse provided string into a date object. If parsing
    * process fails, an invalid object will be returned 
    * 
    * @param date
    *   the string to parse
    * @param datePattern
    *   the provided date pattern (ex: dd/MM/yyyy or yyyy-MM-dd and so on)
    * @param lenientParsing
    *   when set to <i>true</i> the parser may use heuristics to interpret inputs 
    *   that do not precisely match this object's format. Otherwise when set to
    *   <i>false</i> (that means strict parsing), inputs must match provided object's 
    *   format 
    * @return
    *   parsed date or invalid object
    * @author Fabrizio Parlani
  */
  public static Date stringToDate(String  date,
                                  String  datePattern,
                                  boolean lenientParsing)
  {
    try
    {
      //get a date formatter
      DateFormat df     = new SimpleDateFormat (datePattern);
      //set lenient flag
      df.setLenient(lenientParsing);
      //return parsed string into date
      return df.parse(date);
    }
    catch(Exception e)
    {
      //return invalid object
      return null;
    }
  }

  /**
    * Tries to parse provided string into a date object. If parsing
    * process fails, an invalid object will be returned.
    * Here the lenient flag for the casting is set to <i>false</i> 
    * so source date must match strictly the provided date pattern
    * 
    * @param date
    *   the string to parse
    * @param datePattern
    *   the provided date pattern (ex: dd/MM/yyyy or yyyy-MM-dd and so on)
    * @return
    *    parsed date or invalid object
    * @author Fabrizio Parlani
  */
  public static Date stringToDate(String  date,
                                  String  datePattern)
  { return stringToDate(date, datePattern, false); }

  /**
    * Tries to check if provided source string (that should be a date representation) 
    * is a valid date according to provided date pattern used for the cast operation 
    * 
    * @param source
    *   the string to check (a date representation)
    * @param datePattern
    *   the pattern used to cast passed source string
    * @param lenientParsing
    *   when set to <i>true</i> the parser may use heuristics to interpret inputs 
    *   that do not precisely match this object's format. Otherwise when set to
    *   <i>false</i> (that means strict parsing), inputs must match provided object's 
    *   format 
    * @return
    *   <i>true</i> if passed source string is a date representation according to specified
    *   date pattern, otherwise <i>false</i> even if one of provided parameters is invalid
    * @author Fabrizio Parlani
  */
  public static boolean isDate(String  source,
                               String  datePattern,
                               boolean lenientParsing)
  {
    try
    {
      //check for a valid and not empty provided source string
      if (isEmpty(source, true))
      { //source date string hasn't been provided
        return false;
      }
      //check for a valid and not empty provided date pattern use for casting
      if (isEmpty(datePattern, true))
      { //specified date pattern to use for casting is empty
        return false;
      }

      //return check response
      return ((stringToDate(source, 
                            datePattern, 
                            lenientParsing) != null) ? true : false);
    }
    catch(Exception e)
    {
      //when something wrong happens, it means that provided source string 
      //is not a date representation according to provided pattern
      return false;
    }
  }

  /**
    * Tries to check if provided source string (that should be a date representation) 
    * is a valid date according to provided date pattern used for the cast operation.
    * Here the lenient flag for the casting is set to <i>false</i> so source date must
    * match strictly the provided date pattern
    * 
    * @param source
    *   the string to check (a date representation)
    * @param datePattern
    *   the pattern used to cast passed source string
    * @return
    *   <i>true</i> if passed source string is a date representation according to specified
    *   date pattern, otherwise <i>false</i> even if one of provided parameters is invalid
    * @author Fabrizio Parlani
  */
  public static boolean isDate(String source,
                               String datePattern)
  { return isDate(source, datePattern, false); }

  /**
    * Tries to match provided dates. Possible returns are:<br/>
    * <ul>
    *   <li><i>EDatesMatch.BEFORE</i> if first date is smaller than second date</li>
    *   <li><i>EDatesMatch.EQUALS</i> if provided dates match</li>
    *   <li><i>EDatesMatch.AFTER</i> if first date is greater than second date</li>
    *   <li><i>EDatesMatch.INVALID</i> if one or either provided dates are invalid</li>
    * </ul> 
    * 
    * @param firstDate
    *   the first date to match
    * @param secondDate
    *   the second date to match
    * @return
    *   the date comparison result or an invalid result if either or all dates are invalid
    * @author Fabrizio Parlani
  */
  public static EDatesMatch matchDates(Date firstDate,
                                       Date secondDate) 
  {
    try
    {
      //match passed dates for equality
      if (firstDate.equals(secondDate))
        return EDatesMatch.EQUALS;

      //match passed dates for first date before second date
      if (firstDate.before(secondDate))
        return EDatesMatch.BEFORE;

      //match passed dates for first date after second date
      if (firstDate.after(secondDate))
        return EDatesMatch.AFTER;
      
      //invalid dates match as default return
      return EDatesMatch.INVALID;
    }
    catch (Exception e)
    { return EDatesMatch.INVALID; }
  }

  /**
    * Tries to match provided dates. Possible returns are:<br/>
    * <ul>
    *   <li><i>EDatesMatch.BEFORE</i> if first date is smaller than second date</li>
    *   <li><i>EDatesMatch.EQUALS</i> if provided dates match</li>
    *   <li><i>EDatesMatch.AFTER</i> if first date is greater than second date</li>
    *   <li><i>EDatesMatch.INVALID</i> if either or all provided dates are invalid</li>
    * </ul> 
    * 
    * @param firstDate
    *   the first date string to parse
    * @param firstDatePattern
    *   the first date string pattern
    * @param firstDateLenientParsing
    *   the flag used to set parsing engine method (lenient or strict) for the first date
    * @param secondDate
    *   the second date string to parse
    * @param secondDatePattern
    *   the second date string pattern
    * @param secondDateLenientParsing
    *   the flag used to set parsing engine method (lenient or strict) for the second date
    * @return
    *   the date comparison result or an invalid result if either or all dates are invalid
    * @author Fabrizio Parlani
  */
  public static EDatesMatch matchDates(String  firstDate,
                                       String  firstDatePattern,
                                       boolean firstDateLenientParsing,
                                       String  secondDate,
                                       String  secondDatePattern,
                                       boolean secondDateLenientParsing)
  {
    try
    {
      //try to get the first date from passed values
      Date fstDate = stringToDate(firstDate,
                                  firstDatePattern,
                                  firstDateLenientParsing);
      //check for a valid parsed date
      if (fstDate == null)
      { //return invalid match
        return EDatesMatch.INVALID;
      }

      //try to get the second date from passed values
      Date scnDate = stringToDate(secondDate, 
                                  secondDatePattern, 
                                  secondDateLenientParsing);
      //check for a valid parsed date
      if (scnDate == null)
      { //return invalid match
        return EDatesMatch.INVALID;
      }

      //return dates match
      return matchDates(fstDate,
                        scnDate);
    }
    catch(Exception e)
    { return EDatesMatch.INVALID; }
  }

  /**
    * Tries to match provided dates. Possible returns are:<br/>
    * <ul>
    *   <li><i>EDatesMatch.BEFORE</i> if first date is smaller than second date</li>
    *   <li><i>EDatesMatch.EQUALS</i> if provided dates match</li>
    *   <li><i>EDatesMatch.AFTER</i> if first date is greater than second date</li>
    *   <li><i>EDatesMatch.INVALID</i> if either or all provided dates are invalid</li>
    * </ul>
    * 
    * @param firstDate
    *   the first date string to parse
    * @param secondDate
    *   the second date string to parse
    * @param datesPattern
    *   provided dates string pattern
    * @param lenientParsing
    *   the flag used to set parsing engine method (lenient or strict) for provided dates
    * @return
    * @author Fabrizio Parlani
  */
  public static EDatesMatch matchDates(String  firstDate,
                                       String  secondDate,
                                       String  datesPattern,
                                       boolean lenientParsing)
  {
    try
    {
      //try to get the first date from passed values
      Date fstDate = stringToDate(firstDate,
                                  datesPattern,
                                  lenientParsing);
      //check for a valid parsed date
      if (fstDate == null)
      { //return invalid match
        return EDatesMatch.INVALID;
      }

      //try to get the second date from passed values
      Date scnDate = stringToDate(secondDate,
                                  datesPattern,
                                  lenientParsing);
      //check for a valid parsed date
      if (scnDate == null)
      { //return invalid match
        return EDatesMatch.INVALID;
      }

      //return dates match
      return matchDates(fstDate,
                        scnDate);
    }
    catch(Exception e)
    { return EDatesMatch.INVALID; }
  }

  /**
   * Tries to calculate provided dates difference using following expression:<br/>
   * <ul>
   *   <li> <i>result = <b>lastDate</b> - <b>firstDate</b></li>
   * </ul>
   * Possible return value may vary depending on selected returning measure that could be one of the following :<br/>
   * <ul>
   *   <li><i>EDatesDifference.MILLISECONDS</i> dates difference expressed in millisecond(s)</li>
   *   <li><i>EDatesDifference.SECONDS</i> dates difference expressed in second(s)</li>
   *   <li><i>EDatesDifference.MINUTES</i> dates difference expressed in minute(s)</li>
   *   <li><i>EDatesDifference.HOURS</i> dates difference expressed in hour(s)</li>
   *   <li><i>EDatesDifference.DAYS</i> dates difference expressed in day(s)</li>
   *   <li><i>EDatesDifference.MONTHS</i> dates difference expressed in month(s)</li>
   * </ul>
   * 
   * @param firstDate
   *   The first date that will be subtracted from provided second date
   * @param lastDate
   *   The date from which will be subtracted provided first date
   * @param measure
   *   The returning dates gap measure. It's possible to choose one of the following returning type :<br/>
   *   <ul>
   *     <li>MILLISECONDS</li>
   *     <li>SECONDS</li>
   *     <li>MINUTES</li>
   *     <li>HOURS</li>
   *     <li>DAYS</li>
   *     <li>MONTHS</li>
   *   </ul>
   * @return
   * @author Fabrizio Parlani
   */
  public static long datesDiff(Date             firstDate,
                               Date             lastDate,
                               EDatesDifference measure)
  { //get two calendar instances
    Calendar firstCalendar  = Calendar.getInstance();
    Calendar secondCalendar = Calendar.getInstance();
    //set date from provided values
    firstCalendar.setTime(firstDate);
    secondCalendar.setTime(lastDate);

    //return appropriate dates difference expressed in requested measure
    return ((secondCalendar.getTimeInMillis() - firstCalendar.getTimeInMillis()) / measure.getDivider());
  }

  /**
    * Check file content type with allowed set or with a generic content type composed by '*' wild char
    * such as application/*, image/*, text/* and so on
    * 
    * @param contentTypesSet
    *   the set of allowed content type(s)
    * @param fileContentType
    *   the actual file content type
    * @return
    *   <i>true</i> if provided content type is in the list of allowed, otherwise </i>false</i>
    * @author Fabrizio Parlani
  */
  public static boolean allowedContentType(Collection<String> contentTypesSet, 
                                           String             fileContentType)
  {
    // check if passed content type exists into passed set if no content types
    // with wild chars have been specified
    if (contentTypesSet.contains(fileContentType))
    { // return allowed content type
      return true;
    }

    // check for a content type passed with wild char (example: application/*,
    // image/*, text/* and so on)
    if (contentTypesSet.contains(fileContentType.substring(0, fileContentType.indexOf("/") + 1) + "*"))
    { // return allowed content type checking it's prefix with an allowed
      // MIME-type with wild char
      return true;
    }

    // here we are sure that passed content type is forbidden
    return false;
  }

  /**
    * Check provided file extension with allowed set
    * 
    * @param extensionsSet
    *   the set of allowed extension(s)
    * @param fileName
    *   the actual file name
    * @return
    *   <i>true</i> if provided file has an extension in the provided allowed set,
    *   otherwise <i>false</i>
    * @author Fabrizio Parlani
  */
  public static boolean allowedExtension(Collection<String> extensionsSet, 
                                         String             fileName)
  {
    // cycle to check if provided file name extension is a provided one
    for (String extension : extensionsSet)
    { // check for allowed extension
      if (fileName.toLowerCase().endsWith(extension))
      { // return allowed extension
        return true;
      }
    }

    // here we are sure that file extension is forbidden
    return false;
  }
  
  /**
    * Checks if passed path is an existing one, and when is not existent, folder(s)
    * will be created if <i>create</i> flag has been set 
    * 
    * @param path
    *   The path to check
    * @param create
    *   The flag to set creation folder when it's not existing
    * @return
    *   One of the values of EDirCheck enumeration depending on operation and checks
    *   result
    * @author Fabrizio Parlani
  */
  public static EDirsCheck checkDirs(String  path,
                                     boolean create)
  {
    try
    {
      //check for a valid and not empty passed path
      if (isNotEmpty(path, true))
      {
        //define object to check folder existence
        File folders = new File(path);
        //check for path non-existence
        if (!folders.exists())
        {
          //check for creation flag
          if (create)
          { //return folder(s) creation result
            return (folders.mkdirs()) ? EDirsCheck.DIRS_CREATION_SUCCESS : EDirsCheck.DIRS_CREATION_FAILURE;
          }
          else
          { //folder doesn't exists
            return EDirsCheck.DIRS_NOT_EXISTING;
          }
        }
        else
        { //folder already exists
          return EDirsCheck.DIRS_EXISTING;
        }
      }
      else
      { //passed path is empty or not valid
        return EDirsCheck.DIRS_NAME_INVALID;
      }
    }
    catch ( SecurityException e)
    { //return security exception error
      return EDirsCheck.DIRS_SECURITY_EXCEPTION;
    }
    catch ( Exception e )
    { //some error occurred
      return EDirsCheck.DIRS_GENERIC_EXCEPTION;
    }
  }
  
  /**
    * Tries to write passed content into specified file
    * 
    * @param fileName
    *   The fully qualified file name 
    * @param content
    *   The content to write into the file
    * @throws Exception
    *   When something goes in the wrong way
    * @author Fabrizio Parlani
  */
  public static void writeFile(String fileName, 
                               byte[] content) 
  throws Exception
  {
    // declare object used to create file from given name
    FileOutputStream     fos = null;
    // declare object used to write file
    BufferedOutputStream bos = null;

    try
    {
      //strip file path
      String path = fileName.substring(0, fileName.lastIndexOf(File.separator));    
      
      //check for folder(s) to create
      if ((checkDirs(path, false).equals(EDirsCheck.DIRS_EXISTING)) || 
          (checkDirs(path, true).equals(EDirsCheck.DIRS_CREATION_SUCCESS)))
      {
        // declare a file output stream with an instance of file object related to
        // the passed file name
        fos = new FileOutputStream(new File(fileName));
        // create the object used to write passed content into created file output
        // stream
        bos = new BufferedOutputStream(fos);
  
        // write passed content into opened stream
        bos.write(content);
      }
      else
      { //an error has occurred during folder creation, so run a IOException
        throw new IOException("Error during folder(s) creation");
      }
    }
    catch ( FileNotFoundException fnfe )
    { //throw occurred exception
      throw fnfe;     
    }
    catch ( IOException ioe )
    { //throw occurred exception
      throw ioe;
    }
    finally
    {
      // check for still opened output stream
      if (bos != null)
      {
        try
        {
          // finalize content write operation
          bos.flush();
          // close the stream
          bos.close();
        }
        catch ( Exception e )
        { //throw occurred exception
          throw e;
        }
      }
    }
  }

  /**
    * Tries to write passed content into specified file
    * 
    * @param fileName
    *   The fully qualified file name 
    * @param content
    *   The content to write into the file
    * @throws Exception
    *   When something goes in the wrong way
    * @author Fabrizio Parlani
  */
  public static void writeFile(String      fileName, 
                               InputStream content) 
  throws Exception
  {
    // declare object used to create file from given name
    FileOutputStream     fos    = null;
    // declare object used to write file
    BufferedOutputStream bos    = null;
    // declare object used to read input stream
    byte[]               buffer = new byte[1024];

    try
    {
      //strip file path
      String path = fileName.substring(0, fileName.lastIndexOf(File.separator));

      //check for folder(s) to create
      if ((checkDirs(path, false) == EDirsCheck.DIRS_EXISTING) || 
          (checkDirs(path, true)  == EDirsCheck.DIRS_CREATION_SUCCESS))
      {
        // declare a file output stream with an instance of file object related to
        // the passed file name
        fos = new FileOutputStream(new File(fileName));
        // create the object used to write passed content into created file output
        // stream
        bos = new BufferedOutputStream(fos);
  
        //check for a valid input stream
        if (content != null)
        {
          //[Start] : writing stream process
            int byteRead;
            while ((byteRead = content.read(buffer)) != -1)
            { //write data
              bos.write(buffer, 0, byteRead);
            }
            //finalize writing into output stream
            bos.flush();
            //close used streams
            bos.close();
            content.close();
          //[End] : writing stream process
        }
        else
        { //error occurred passed input stream is invalid
          throw new IOException("An invalid or null input stream has been passed");
        }
      }
      else
      { //an error has occurred during folder creation, so run a IOException
        throw new IOException("Error during folder(s) creation");
      }
    }
    catch ( FileNotFoundException fnfe )
    { //throw occurred exception
      throw fnfe;     
    }
    catch ( IOException ioe )
    { //throw occurred exception
      throw ioe;
    }
    finally
    {
      // check for still opened output stream
      if (bos != null)
      {
        try
        {
          // finalize content write operation
          bos.flush();
          // close the stream
          bos.close();
        }
        catch ( Exception e )
        { //throw occurred exception
          throw e;
        }
      }
    }
  }

  /**
    * Tries to extract passed ZIP file into specified path
    * 
    * @param path
    *   The path where to extract the file content
    * @param file
    *   The ZIP file to extract
    * @throws Exception
    *   When something goes in the wrong way
    * @author Fabrizio Parlani
  */
  public static void writeZipFileEntries(String      path,
                                         InputStream file)
  throws Exception
  {
    //Declare used objects
    ZipInputStream       zipFile = null;  //The ZIP file container
    ZipEntry             entry   = null;  //The single ZIP entry object
    BufferedOutputStream outFile = null;  //The extracted output file to write 
    //declare read buffer size (4Kb)
    final int            BUFFER  = 4096;

    try
    {
      //check for folder(s) to create from passed path
      if ((checkDirs(path, false) == EDirsCheck.DIRS_EXISTING) || 
          (checkDirs(path, true)  == EDirsCheck.DIRS_CREATION_SUCCESS))
      {
        //load passed stream into ZIP stream
        zipFile = new ZipInputStream(file);
  
        //get current ZIP entry and cycle across all contained ZIP entries
        while ((entry = zipFile.getNextEntry()) != null)
        {
          //declare variable used to read bytes
          int count;
          byte data[] = new byte[BUFFER];
          
          //declare object used to write the file(s) to the file system
          FileOutputStream fos = new FileOutputStream(new StringBuilder(path).append(File.separator).append(entry.getName()).toString());
  
          //initialize the destination file
          outFile = new BufferedOutputStream(fos, BUFFER);
          //cycle to write current ZIP entry file to the file system
          while ((count = zipFile.read(data, 0, BUFFER)) != -1)
          { //Write read buffer of bytes from current cycled ZIP entry 
            outFile.write(data, 0, count);
          }
  
          //finalize writing process
          outFile.flush();
          //close writing channel
          outFile.close();
          //destroy used object
          outFile = null;
        }
  
        //close used ZIP file
        zipFile.close();
      }
      else
      { //an error has occurred during folder creation, so run a IOException
        throw new IOException("Error during folder(s) creation for ZIP file extraction");
      }
    }
    catch ( FileNotFoundException fnfe )
    { //throw occurred exception
      throw fnfe;     
    }
    catch ( IOException ioe )
    { //throw occurred exception
      throw ioe;
    }
    finally
    {
      // check for still opened output stream
      if (outFile != null) 
      {
        try
        {
          // finalize content write operation
          outFile.flush();
          // close the stream
          outFile.close();
        }
        catch ( Exception e )
        { 
          //throw occurred exception
          throw e;
        }
      }
    }
  }

  /**
    * Tries to delete provided file name 
    * 
    * @param file
    *   The file object to delete
    * @return 
    *   <i>true</i> if file has been successfully deleted, otherwise <i>false</i>
    * @throws Exception
    *   When something goes in the wrong way
    * @throws SecurityException
    *   When a control over provided file fails
    * @author Fabrizio Parlani
  */
  public static boolean deleteFile(File file)
  throws Exception,
         SecurityException
  {
    try
    {
      //check for file existence
      if(file.exists())
      { //check if passed file name is really a file 
        if (file.isFile())
        { //check for a permission to delete
          if (file.canWrite())
          {
            try
            { //try to delete passed file
              return file.delete();
            }
            catch ( SecurityException se )
            { //throw occurred exception
              throw se;
            }
          }
          else
          { //file is read only and cannot be deleted
            throw new SecurityException("File [" + file.getName() + "] cannot be deleted");
          }
        }
        else
        { //passed file is not a File
          throw new SecurityException("Provided file [" + file.getName() + "] is not a File object");
        }
      }
      else
      { //passed file doesn't exist
        throw new SecurityException("Provided file [" + file.getName() + "] does not exist");
      }
    }
    catch ( Exception ex )
    { //throws occurred exception
      throw ex;
    }
  }

  /**
    * Tries to delete provided file name 
    * 
    * @param fileName
    *   The file name to delete
    * @return 
    *   <i>true</i> if file has been successfully deleted, otherwise <i>false</i>
    * @throws Exception
    *   When something goes in the wrong way
    * @throws SecurityException
    *   When a control over provided file fails
    * @author Fabrizio Parlani
  */
  public static boolean deleteFile(String fileName)
  throws Exception,
         SecurityException
  {
    try
    {
      //check for a not empty passed filename
      if (isNotEmpty(fileName))
      {
        //declare and assign passed and checked not empty file name
        File f = new File(checkNullString(fileName, true));
        //call method that deletes file object
        return deleteFile(f);
      }
      else
      { //invalid file name provided
        throw new Exception("An invalid file name has been provided");
      }
    }
    catch ( Exception ex )
    { //throws occurred exception
      throw ex;
    }
  }

  /**
    * Tries to delete files into provided path with specified file name
    * filter. If filter is empty or invalid, every file into specified 
    * directory will be deleted
    * 
    * @param path
    *   The directory object where to delete files
    * @param filenameFilter
    *   The file name filter. When not specified method assumes that all files
    *   into directory must be deleted  
    * @return
    *   <i>true</i> if all files have been successfully deleted, otherwise <i>false</i>
    * @throws Exception
    *   When something goes in the wrong way
    * @throws SecurityException
    *   When a control over provided path fails
    * @author Fabrizio Parlani
  */
  public static boolean deleteFiles(File   path,
                                    String filenameFilter)
  throws Exception,
         SecurityException
  {
    try
    {
      //check for path existence
      if (path.exists())
      { //check if passed file name is really a directory 
        if (path.isDirectory())
        {
          //declare variable used to get provided path file(s) list probably filtered
          File[] files;

          //check for a provided filter over file names
          if (isNotEmpty(filenameFilter))
          { //save passed filter into a final variable to use into created on-the-fly inner class 
            final String nameFilter = filenameFilter;
            //create a file name filter with provided one
            FilenameFilter filter = new FilenameFilter()
                                        { //on-the-fly implementation of interface method
                                          public boolean accept(File   path, 
                                                                String name)
                                          { //check if file name contains provided filter
                                            return checkNullString(name).contains(nameFilter);
                                          }
                                        };
            //get filtered file(s) list
            files = path.listFiles(filter);
          }
          else
          { //get provided path entire file(s) list
            files = path.listFiles();
          }

          //check for an existing file(s) list
          if (containsItems(files))
          {
            //cycle over retrieved files to delete
            for (File f : files)
            { //try to delete current cycled file
              deleteFile(f);
            }
            //here operation has been successfully completed
            return true;
          }
          else
          { //there are no file(s) to delete, so we return a succeeded operation
            return true;
          }
        }
        else
        { //passed path is not a directory
          throw new SecurityException("Provided path [" + path.getName() + "] is not a directory");
        }
      }
      else
      { //passed path doesn't exist
        throw new SecurityException("Provided path [" + path.getName() + "] does not exist");
      }
    }
    catch ( Exception ex )
    { //throws occurred exception
      throw ex;
    }
  }

  /**
    * Tries to delete files into provided path with specified file name
    * filter. If filter is empty or invalid, every file into specified 
    * directory will be deleted
    * 
    * @param path
    *   The directory name where to delete files
    * @param filenameFilter
    *   The file name filter. When not specified method assumes that all files
    *   into directory must be deleted  
    * @return
    *   <i>true</i> if all files have been successfully deleted, otherwise <i>false</i>
    * @throws Exception
    *   When something goes in the wrong way
    * @throws SecurityException
    *   When a control over provided path fails
    * @author Fabrizio Parlani
  */
  public static boolean deleteFiles(String path,
                                    String filenameFilter)
  throws Exception,
         SecurityException
  {
    try
    {
      //check for a not empty path provided
      if (isNotEmpty(path))
      {
        //declare and assign passed and checked not empty directory path
        File dir = new File(checkNullString(path, true));
        //call method that deletes files into provided directory with specified filter
        return deleteFiles(dir,
                           filenameFilter);
      }
      else
      { //invalid path provided
        throw new Exception("An invalid path has been provided");
      }
    }
    catch ( Exception ex )
    { //throws occurred exception
      throw ex;
    }
  }
}
