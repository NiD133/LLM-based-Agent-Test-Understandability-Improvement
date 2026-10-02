package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests DefaultParser behaviour when encountering unrecognized options.
 * Covers the IGNORE action (silently skip unknown flags) and the THROW action
 * (raise UnrecognizedOptionException for unknown flags).
 */
public class DefaultParserTest_testParseIgnoreNonHappyPath extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testParseIgnoreNonHappyPath() throws ParseException {
        // Define three individual options that may or may not be included in an option set.
        final Option optionA = Option.builder().option("a").longOpt("first-letter").get();
        final Option optionB = Option.builder().option("b").longOpt("second-letter").get();
        final Option optionC = Option.builder().option("c").longOpt("third-letter").get();

        // baseOptions recognises only -a and -b; -c and -d are both unrecognized here.
        final Options baseOptions = new Options();
        baseOptions.addOption(optionA);
        baseOptions.addOption(optionB);

        // specificOptions recognises -a, -b, and -c; only -d is unrecognized.
        final Options specificOptions = new Options();
        specificOptions.addOption(optionA);
        specificOptions.addOption(optionB);
        specificOptions.addOption(optionC);

        // Input contains two known flags (-a, -b), one conditionally known flag (-c),
        // one completely unknown flag (-d), and two plain positional arguments.
        final String[] args = { "-a", "-b", "-c", "-d", "arg1", "arg2" };

        final DefaultParser defaultParser = new DefaultParser();

        // --- Scenario 1: IGNORE unknown options ---
        // With IGNORE, unrecognized flags (-c, -d) are silently dropped.
        // Only -a and -b are recognised by baseOptions, so 2 options should be parsed.
        // The two plain arguments (arg1, arg2) are retained as leftover args.
        final CommandLine baseCommandLine = defaultParser.parse(
                baseOptions, null, DefaultParser.NonOptionAction.IGNORE, args);

        assertEquals(2, baseCommandLine.getOptions().length,
                "IGNORE mode: only the two recognized options (-a, -b) should be present");
        assertEquals(2, baseCommandLine.getArgs().length,
                "IGNORE mode: the two positional arguments (arg1, arg2) should be retained");

        // --- Scenario 2: THROW on unknown options ---
        // With THROW, encountering -d (unrecognized by specificOptions) must throw.
        // The exception message must identify the offending flag.
        final UnrecognizedOptionException exception = assertThrows(
                UnrecognizedOptionException.class,
                () -> defaultParser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args));

        assertTrue(exception.getMessage().contains("-d"),
                "THROW mode: exception message should name the unrecognized option (-d)");
    }
}
