/*
 * Improved from EvoSuite-generated test (Mon Jun 15 22:39:31 GMT 2026).
 * Understandability improvements: descriptive method and variable names,
 * explanatory comments, and clearer test structure. Runtime behaviour unchanged.
 */

package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.regex.Pattern;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.helper.Regex;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Regex_ESTest extends Regex_ESTest_scaffolding {

  /** re2j library is present on the test classpath, so hasRe2j() must return true. */
  @Test(timeout = 4000)
  public void test_hasRe2j_returnsTrueWhenRe2jOnClasspath() throws Throwable {
      boolean re2jAvailable = Regex.hasRe2j();
      assertTrue(re2jAvailable);
  }

  /**
   * When re2j is disabled and the pattern contains an unclosed character class,
   * the JDK engine throws IllegalArgumentException wrapping PatternSyntaxException.
   */
  @Test(timeout = 4000)
  public void test_compile_throwsOnUnclosedCharacterClass_whenRe2jDisabled() throws Throwable {
      Regex.wantsRe2j(false);
      // Unclosed '[' at index 10 makes the JDK Pattern compiler reject the input.
      try {
        Regex.compile("w%-9C1X&IB[`L");
        fail("Expecting exception: IllegalArgumentException");
      } catch(IllegalArgumentException e) {
         //
         // Pattern syntax error: Unclosed character class near index 12
         // w%-9C1X&IB[`L
         //             ^
         //
      }
  }

  /**
   * When re2j is disabled, a syntactically valid (though unusual) pattern
   * compiles successfully and toString() returns the original pattern string.
   */
  @Test(timeout = 4000)
  public void test_compile_preservesPatternString_whenRe2jDisabled() throws Throwable {
      Regex.wantsRe2j(false);
      Regex compiledRegex = Regex.compile("&f}HZ;:/IF8@");
      assertEquals("&f}HZ;:/IF8@", compiledRegex.toString());
  }

  /**
   * fromPattern wraps an existing JDK Pattern; the resulting Regex produces
   * a non-null Matcher when given a matching input string.
   */
  @Test(timeout = 4000)
  public void test_fromPattern_matcherIsNonNull() throws Throwable {
      Pattern jdkPattern = Pattern.compile("=;r'm!");
      Regex regex = Regex.fromPattern(jdkPattern);
      Regex.Matcher matcher = regex.matcher("=;r'm!");
      assertNotNull(matcher);
  }

  /**
   * By default (re2j enabled and available), compile() uses the re2j engine,
   * so usingRe2j() returns true on the resulting Regex instance.
   */
  @Test(timeout = 4000)
  public void test_compile_usesRe2jEngineByDefault() throws Throwable {
      Regex compiledRegex = Regex.compile("VERTICAL_BAR");
      assertTrue(Regex.usingRe2j());
  }

  /**
   * fromPattern wraps a JDK Pattern without switching engines; toString()
   * must return the original pattern string unchanged.
   */
  @Test(timeout = 4000)
  public void test_fromPattern_toStringReturnsOriginalPatternString() throws Throwable {
      Pattern jdkPattern = Pattern.compile("VERTICAL_BAR");
      Regex regex = Regex.fromPattern(jdkPattern);
      String patternString = regex.toString();
      assertEquals("VERTICAL_BAR", patternString);
  }
}
