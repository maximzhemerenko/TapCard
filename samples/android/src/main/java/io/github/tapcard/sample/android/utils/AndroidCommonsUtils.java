package io.github.tapcard.sample.android.utils;

import java.io.Closeable;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

/**
 * Some methods from Apache Commons library
 */

public class AndroidCommonsUtils {

  /**
   * <p>Eight-bit Unicode Transformation Format.</p>
   *
   * <p>Every implementation of the Java platform is required to support this character encoding.</p>
   */
  public static final String UTF_8 = "UTF-8";

  /**
   * <p>Checks if a CharSequence is empty ("") or null.</p>
   *
   * <pre>
   * StringUtils.isEmpty(null)      = true
   * StringUtils.isEmpty("")        = true
   * StringUtils.isEmpty(" ")       = false
   * StringUtils.isEmpty("bob")     = false
   * StringUtils.isEmpty("  bob  ") = false
   * </pre>
   *
   * <p>NOTE: This method changed in Lang version 2.0.
   * It no longer trims the CharSequence.
   * That functionality is available in isBlank().</p>
   *
   * @param cs  the CharSequence to check, may be null
   * @return {@code true} if the CharSequence is empty or null
   * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
   */
  public static boolean isEmpty(CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

  /**
   * <p>Deletes all whitespaces from a String as defined by
   * {@link Character#isWhitespace(char)}.</p>
   *
   * <pre>
   * StringUtils.deleteWhitespace(null)         = null
   * StringUtils.deleteWhitespace("")           = ""
   * StringUtils.deleteWhitespace("abc")        = "abc"
   * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
   * </pre>
   *
   * @param str  the String to delete whitespace from, may be null
   * @return the String without whitespaces, {@code null} if null String input
   */
  public static String deleteWhitespace(String str) {
    if (isEmpty(str)) {
      return str;
    }
    int sz = str.length();
    char[] chs = new char[sz];
    int count = 0;
    for (int i = 0; i < sz; i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        chs[count++] = str.charAt(i);
      }
    }
    if (count == sz) {
      return str;
    }
    return new String(chs, 0, count);
  }

  public static void closeQuietly(Closeable closeable) {
    try {
      if(closeable != null) {
        closeable.close();
      }
    } catch (IOException ignored) {
    }
  }

  /**
   * <p>Checks if a CharSequence is not empty ("") and not null.</p>
   *
   * <pre>
   * StringUtils.isNotEmpty(null)      = false
   * StringUtils.isNotEmpty("")        = false
   * StringUtils.isNotEmpty(" ")       = true
   * StringUtils.isNotEmpty("bob")     = true
   * StringUtils.isNotEmpty("  bob  ") = true
   * </pre>
   *
   * @param cs  the CharSequence to check, may be null
   * @return {@code true} if the CharSequence is not empty and not null
   * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
   */
  public static boolean isNotEmpty(CharSequence cs) {
    return !StringUtils.isEmpty(cs);
  }

  /**
   * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
   *
   * <pre>
   * StringUtils.isBlank(null)      = true
   * StringUtils.isBlank("")        = true
   * StringUtils.isBlank(" ")       = true
   * StringUtils.isBlank("bob")     = false
   * StringUtils.isBlank("  bob  ") = false
   * </pre>
   *
   * @param cs  the CharSequence to check, may be null
   * @return {@code true} if the CharSequence is null, empty or whitespace
   * @since 2.0
   * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
   */
  public static boolean isBlank(CharSequence cs) {
    int strLen;
    if (cs == null || (strLen = cs.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (Character.isWhitespace(cs.charAt(i)) == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
   *
   * <pre>
   * StringUtils.isNotBlank(null)      = false
   * StringUtils.isNotBlank("")        = false
   * StringUtils.isNotBlank(" ")       = false
   * StringUtils.isNotBlank("bob")     = true
   * StringUtils.isNotBlank("  bob  ") = true
   * </pre>
   *
   * @param cs  the CharSequence to check, may be null
   * @return {@code true} if the CharSequence is
   *  not empty and not null and not whitespace
   * @since 2.0
   * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
   */
  public static boolean isNotBlank(CharSequence cs) {
    return !StringUtils.isBlank(cs);
  }

}
