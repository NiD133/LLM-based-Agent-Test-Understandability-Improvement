package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest extends PosixParser_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void testParseWithUnrecognizedShortOptionAndStopAtNonOption() throws Throwable {
      PosixParser parser = new PosixParser();
      Options options = new Options();
      // Array has "-D" at index 0 and null at index 1; -D is not a registered option
      String[] args = new String[2];
      args[0] = "-D";
      CommandLine result = parser.parse(options, args, true);
      assertNotNull(result);
  }

  @Test(timeout = 4000)
  public void testParseWithLongOptionMatchingToken() throws Throwable {
      // Array of 13 elements; index 2 holds the token "-bdKQ" which matches the registered long option
      String[] args = new String[13];
      args[2] = "-bdKQ";
      Options options = new Options();
      Option longOpt = new Option("bdKQ", false, "bdKQ");
      options.addOption(longOpt);
      PosixParser parser = new PosixParser();
      CommandLine result = parser.parse(options, args);
      assertNotNull(result);
  }

  @Test(timeout = 4000)
  public void testFlattenOptionWithArgumentTwiceYieldsTwoTokens() throws Throwable {
      PosixParser parser = new PosixParser();
      Options options = new Options();
      // Register "Z" as an option that takes an argument
      Option optionZ = new Option("Z", true, "");
      Options optionsWithZ = options.addOption(optionZ);
      // "-Z&=" bursts into ["-Z", "&="] since Z takes an argument
      String[] args = new String[6];
      args[4] = "-Z&=";
      String[] firstFlatten = parser.flatten(optionsWithZ, args, true);
      // Flattening the already-flattened result should still yield 2 tokens
      String[] secondFlatten = parser.flatten(optionsWithZ, firstFlatten, true);
      assertEquals(2, secondFlatten.length);
  }

  @Test(timeout = 4000)
  public void testParseAmbiguousLongOptionThrowsException() throws Throwable {
      // "---" is ambiguous: it could match long options "---" or "--"
      String[] args = new String[38];
      args[17] = "---";
      Options options = new Options();
      Option optTripleDash = new Option(args[27], "---", false, args[1]);
      Options optionsWithTripleDash = options.addOption(optTripleDash);
      options.addRequiredOption("nz6YwG7", "--", false, "---");
      PosixParser parser = new PosixParser();
      try {
        parser.parse(optionsWithTripleDash, args);
        fail("Expecting exception: Exception");
      } catch (Exception e) {
         //
         // Ambiguous option: '---'  (could be: '---', '--')
         //
         verifyException("org.apache.commons.cli.PosixParser", e);
      }
  }

  @Test(timeout = 4000)
  public void testParseWithNullShortOptionThrowsNullPointerException() throws Throwable {
      // args[27] is null (uninitialized), so the Option short name is null -> NullPointerException
      String[] args = new String[38];
      args[17] = "---";
      Options options = new Options();
      Option optWithNullShortName = new Option(args[27], "---", false, args[1]);
      Options optionsWithOpt = options.addOption(optWithNullShortName);
      PosixParser parser = new PosixParser();
      // Undeclared exception!
      try {
        parser.parse(optionsWithOpt, args);
        fail("Expecting exception: NullPointerException");
      } catch (NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.apache.commons.cli.PosixParser", e);
      }
  }

  @Test(timeout = 4000)
  public void testParseUnrecognizedLongOptionWithEqualsThrowsException() throws Throwable {
      // "--=" has the long-option prefix but an empty name, so it is unrecognized
      String[] args = new String[2];
      args[1] = "--=";
      Options options = new Options();
      PosixParser parser = new PosixParser();
      try {
        parser.parse(options, args);
        fail("Expecting exception: Exception");
      } catch (Exception e) {
         //
         // Unrecognized option: --=
         //
         verifyException("org.apache.commons.cli.Parser", e);
      }
  }

  @Test(timeout = 4000)
  public void testFlattenNonOptionTokenWithStopAtNonOptionProducesDoubleDashSentinel() throws Throwable {
      PosixParser parser = new PosixParser();
      Options options = new Options();
      // "i}=ILQ<" has no leading dash; with stopAtNonOption=true it becomes ["--", "i}=ILQ<", ...nulls]
      String[] args = new String[6];
      args[0] = "i}=ILQ<";
      String[] firstFlatten = parser.flatten(options, args, true);
      // With stopAtNonOption=false the "--" sentinel and value are kept, nulls are dropped
      String[] secondFlatten = parser.flatten(options, firstFlatten, false);
      assertEquals(2, secondFlatten.length);
      assertEquals(7, firstFlatten.length);
  }

  @Test(timeout = 4000)
  public void testParseWithStandaloneDashSucceeds() throws Throwable {
      // A standalone "-" is a valid POSIX argument (often means stdin); it should parse without error
      String[] args = new String[65];
      args[4] = "-";
      Options options = new Options();
      PosixParser parser = new PosixParser();
      CommandLine result = parser.parse(options, args);
      assertNotNull(result);
  }

  @Test(timeout = 4000)
  public void testFlattenBurstsKnownOptionFollowedByUnknownChar() throws Throwable {
      PosixParser parser = new PosixParser();
      Options options = new Options();
      // Register "Z" as a flag option (no argument)
      Option optionZ = new Option("Z", "Z");
      Options optionsWithZ = options.addOption(optionZ);
      // "-ZA": Z is known (no-arg), A is unknown; with stopAtNonOption=true adds "--" sentinel
      String[] args = new String[7];
      args[5] = "-ZA";
      String[] flattened = parser.flatten(optionsWithZ, args, true);
      // Expected tokens: ["-Z", "--", "A", null] = 4 elements
      assertEquals(4, flattened.length);
  }

  @Test(timeout = 4000)
  public void testFlattenUnknownBurstTokenWithoutStopReturnsWholeToken() throws Throwable {
      PosixParser parser = new PosixParser();
      Options options = new Options();
      // No options registered; "-Z&=" cannot be burst, so the whole token is kept as-is
      String[] args = new String[6];
      args[4] = "-Z&=";
      String[] flattened = parser.flatten(options, args, false);
      assertEquals(1, flattened.length);
  }

  @Test(timeout = 4000)
  public void testBurstTokenWithEmptyStringDoesNothing() throws Throwable {
      PosixParser parser = new PosixParser();
      // An empty token has no characters to burst; the method should return without error
      parser.burstToken("", false);
  }

  @Test(timeout = 4000)
  public void testBurstTokenAfterParseUsesLatestOptions() throws Throwable {
      PosixParser parser = new PosixParser();
      Options options = new Options();
      // Parse with all-null args to initialise the parser's internal options reference
      String[] emptyArgs = new String[6];
      Properties properties = new Properties();
      parser.parse(options, emptyArgs, properties, true);
      // Add option "Z" (takes arg) to the same Options instance used during parse
      Option optionZ = new Option("Z", true, "");
      options.addOption(optionZ);
      // burstToken("wZ", true): 'w' is skipped (index 0), 'Z' is a known option -> no exception
      parser.burstToken("wZ", true);
  }
}
