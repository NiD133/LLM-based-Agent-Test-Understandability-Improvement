package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests DefaultParser's NonOptionAction.SKIP behaviour.
 *
 * With SKIP, unrecognised option tokens are added to the args list and parsing
 * continues (i.e. recognised options that appear after an unrecognised one are
 * still picked up).  This is in contrast to THROW (which raises an exception)
 * and STOP (which stops parsing at the first unknown token).
 */
public class DefaultParserTest_testParseSkipHappyPath extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    /**
     * Verifies SKIP mode: unrecognised options are silently moved to the args
     * list while recognised ones are captured normally, even when they are
     * interleaved with unknown flags.
     *
     * Scenario A – baseOptions (only -a / -b defined) parsed with SKIP:
     *   "-a -b -c -d arg1 arg2"
     *   → -a and -b become parsed options; -c, -d, arg1, arg2 go to args.
     *
     * Scenario B – specificOptions (all four defined) parsed with THROW:
     *   same input
     *   → all four flags become parsed options; only arg1, arg2 go to args.
     */
    @Test
    void testParseSkipHappyPath() throws ParseException {
        // --- Option definitions ---
        final Option optA = Option.builder().option("a").longOpt("first-letter").get();
        final Option optB = Option.builder().option("b").longOpt("second-letter").get();
        final Option optC = Option.builder().option("c").longOpt("third-letter").get();
        final Option optD = Option.builder().option("d").longOpt("fourth-letter").get();

        // baseOptions knows only -a and -b; -c and -d are unknown to it.
        final Options baseOptions = new Options();
        baseOptions.addOption(optA);
        baseOptions.addOption(optB);

        // specificOptions knows all four flags.
        final Options specificOptions = new Options();
        specificOptions.addOption(optA);
        specificOptions.addOption(optB);
        specificOptions.addOption(optC);
        specificOptions.addOption(optD);

        final String[] args = { "-a", "-b", "-c", "-d", "arg1", "arg2" };
        final DefaultParser skipParser = new DefaultParser();

        // --- Scenario A: SKIP with baseOptions ---
        // Only -a and -b are recognised; -c and -d are unknown and therefore
        // skipped into the remaining-args list rather than causing an error.
        final CommandLine baseResult = skipParser.parse(baseOptions, null, DefaultParser.NonOptionAction.SKIP, args);

        // Two recognised options (-a, -b); four items in the args list (-c, -d, arg1, arg2).
        assertEquals(2, baseResult.getOptions().length, "Only the two recognised options should be parsed");
        assertEquals(4, baseResult.getArgs().length,   "The two unknown flags plus the two plain args should land in args");

        // Recognised flags appear as options, not in the args list.
        assertTrue(baseResult.hasOption("a"),                   "-a must be a recognised option");
        assertTrue(baseResult.hasOption("b"),                   "-b must be a recognised option");
        assertFalse(baseResult.getArgList().contains("-a"),     "-a must NOT appear in the args list");
        assertFalse(baseResult.getArgList().contains("-b"),     "-b must NOT appear in the args list");

        // Unknown flags are skipped into the args list, not parsed as options.
        assertFalse(baseResult.hasOption("c"),                  "-c must NOT be a recognised option");
        assertFalse(baseResult.hasOption("d"),                  "-d must NOT be a recognised option");
        assertTrue(baseResult.getArgList().contains("-c"),      "-c must appear in the args list after being skipped");
        assertTrue(baseResult.getArgList().contains("-d"),      "-d must appear in the args list after being skipped");

        // Plain positional arguments are always in the args list.
        assertTrue(baseResult.getArgList().contains("arg1"),    "arg1 must appear in the args list");
        assertTrue(baseResult.getArgList().contains("arg2"),    "arg2 must appear in the args list");

        // --- Scenario B: THROW with specificOptions ---
        // All four flags are recognised this time; THROW would raise an exception
        // on any unrecognised token, but there are none here.
        final CommandLine specificResult = skipParser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args);

        // All four flags become options; only the two plain positional args remain.
        assertEquals(4, specificResult.getOptions().length, "All four flags should be recognised options");
        assertEquals(2, specificResult.getArgs().length,    "Only arg1 and arg2 should remain in the args list");

        // All four flags are captured as options, none leaked into args.
        assertTrue(specificResult.hasOption("a"),               "-a must be a recognised option");
        assertTrue(specificResult.hasOption("b"),               "-b must be a recognised option");
        assertTrue(specificResult.hasOption("c"),               "-c must be a recognised option");
        assertTrue(specificResult.hasOption("d"),               "-d must be a recognised option");
        assertFalse(specificResult.getArgList().contains("-a"), "-a must NOT appear in the args list");
        assertFalse(specificResult.getArgList().contains("-b"), "-b must NOT appear in the args list");
        assertFalse(specificResult.getArgList().contains("-c"), "-c must NOT appear in the args list");
        assertFalse(specificResult.getArgList().contains("-d"), "-d must NOT appear in the args list");

        // Plain positional arguments are always in the args list.
        assertTrue(specificResult.getArgList().contains("arg1"), "arg1 must appear in the args list");
        assertTrue(specificResult.getArgList().contains("arg2"), "arg2 must appear in the args list");
    }
}
