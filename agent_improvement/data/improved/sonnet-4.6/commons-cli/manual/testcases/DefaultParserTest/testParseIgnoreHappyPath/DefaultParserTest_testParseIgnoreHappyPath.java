package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testParseIgnoreHappyPath extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    /**
     * Verifies the IGNORE non-option action: unrecognized option flags are silently
     * dropped (neither stored as parsed options nor added to the arg list), while
     * positional arguments are preserved. Also confirms that when all options are
     * registered, THROW mode parses every flag successfully.
     */
    @Test
    void testParseIgnoreHappyPath() throws ParseException {
        // Define four CLI options, each with a short and long form
        final Option optionA = Option.builder().option("a").longOpt("first-letter").get();
        final Option optionB = Option.builder().option("b").longOpt("second-letter").get();
        final Option optionC = Option.builder().option("c").longOpt("third-letter").get();
        final Option optionD = Option.builder().option("d").longOpt("fourth-letter").get();

        // baseOptions recognises only -a and -b; -c and -d are unknown to it
        final Options baseOptions = new Options();
        baseOptions.addOption(optionA);
        baseOptions.addOption(optionB);

        // specificOptions recognises all four options
        final Options specificOptions = new Options();
        specificOptions.addOption(optionA);
        specificOptions.addOption(optionB);
        specificOptions.addOption(optionC);
        specificOptions.addOption(optionD);

        // Input: four option flags followed by two positional arguments
        final String[] args = { "-a", "-b", "-c", "-d", "arg1", "arg2" };

        final DefaultParser defaultParser = new DefaultParser();

        // --- Scenario 1: IGNORE action with baseOptions ---
        // Unknown flags (-c, -d) are silently dropped; positional args are preserved.
        final CommandLine baseCommandLine = defaultParser.parse(baseOptions, null, DefaultParser.NonOptionAction.IGNORE, args);

        assertEquals(2, baseCommandLine.getOptions().length,
                "Only the two recognised options (-a, -b) should be stored");
        assertEquals(2, baseCommandLine.getArgs().length,
                "Only the two positional arguments (arg1, arg2) should appear in args");
        assertTrue(baseCommandLine.hasOption("a"), "Recognised option -a must be present");
        assertTrue(baseCommandLine.hasOption("b"), "Recognised option -b must be present");
        assertFalse(baseCommandLine.hasOption("c"), "Unknown option -c must be absent (ignored)");
        assertFalse(baseCommandLine.hasOption("d"), "Unknown option -d must be absent (ignored)");
        // Recognised options must not be duplicated in the positional arg list
        assertFalse(baseCommandLine.getArgList().contains("-a"), "-a must not leak into the arg list");
        assertFalse(baseCommandLine.getArgList().contains("-b"), "-b must not leak into the arg list");
        // Ignored flags must not be added to the arg list either
        assertFalse(baseCommandLine.getArgList().contains("-c"), "Ignored -c must not appear in the arg list");
        assertFalse(baseCommandLine.getArgList().contains("-d"), "Ignored -d must not appear in the arg list");
        // Positional arguments must be retained
        assertTrue(baseCommandLine.getArgList().contains("arg1"), "Positional arg 'arg1' must be in the arg list");
        assertTrue(baseCommandLine.getArgList().contains("arg2"), "Positional arg 'arg2' must be in the arg list");

        // --- Scenario 2: THROW action with specificOptions ---
        // All four flags are registered, so all are parsed; positional args are preserved.
        final CommandLine specificCommandLine = defaultParser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args);

        assertEquals(4, specificCommandLine.getOptions().length,
                "All four recognised options (-a, -b, -c, -d) should be stored");
        assertEquals(2, specificCommandLine.getArgs().length,
                "Only the two positional arguments (arg1, arg2) should appear in args");
        assertTrue(specificCommandLine.hasOption("a"), "Recognised option -a must be present");
        assertTrue(specificCommandLine.hasOption("b"), "Recognised option -b must be present");
        assertTrue(specificCommandLine.hasOption("c"), "Recognised option -c must be present");
        assertTrue(specificCommandLine.hasOption("d"), "Recognised option -d must be present");
        // Parsed options must not appear in the positional arg list
        assertFalse(specificCommandLine.getArgList().contains("-a"), "-a must not appear in the arg list");
        assertFalse(specificCommandLine.getArgList().contains("-b"), "-b must not appear in the arg list");
        assertFalse(specificCommandLine.getArgList().contains("-c"), "-c must not appear in the arg list");
        assertFalse(specificCommandLine.getArgList().contains("-d"), "-d must not appear in the arg list");
        // Positional arguments must be retained
        assertTrue(specificCommandLine.getArgList().contains("arg1"), "Positional arg 'arg1' must be in the arg list");
        assertTrue(specificCommandLine.getArgList().contains("arg2"), "Positional arg 'arg2' must be in the arg list");
    }
}
