package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies the legacy {@code stopAtNonOption} flag behaviour of {@link DefaultParser}.
 *
 * <p>When {@code stopAtNonOption = true} the parser halts at the first unrecognised
 * token and appends it together with all remaining tokens to the argument list rather
 * than throwing.  When {@code stopAtNonOption = false} an
 * {@link UnrecognizedOptionException} is thrown instead.
 */
public class DefaultParserTest_testLegacyStopAtNonOption extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testLegacyStopAtNonOption() throws ParseException {
        // --- Arrange ---
        // Define three recognised single-letter options with long-form aliases.
        final Option a = Option.builder().option("a").longOpt("first-letter").get();
        final Option b = Option.builder().option("b").longOpt("second-letter").get();
        final Option c = Option.builder().option("c").longOpt("third-letter").get();

        final Options options = new Options();
        options.addOption(a);
        options.addOption(b);
        options.addOption(c);

        // Input contains three recognised options followed by one rogue option (-d)
        // and two plain arguments.  -d is intentionally unknown to exercise the
        // stopAtNonOption boundary.
        final String[] args = { "-a", "-b", "-c", "-d", "arg1", "arg2" };

        final DefaultParser parser = new DefaultParser();

        // --- Act (stopAtNonOption = true) ---
        // Parsing must not throw: the parser should stop at -d and collect it
        // together with the remaining tokens as plain arguments.
        final CommandLine commandLine = parser.parse(options, args, null, true);

        // --- Assert (stopAtNonOption = true) ---
        // Exactly -a, -b, -c were recognised as options.
        assertEquals(3, commandLine.getOptions().length,
                "Three recognised options should have been parsed");
        // The rogue token (-d) and the two plain arguments are captured as args.
        assertEquals(3, commandLine.getArgs().length,
                "Rogue option and two plain args should be in the args list");
        assertTrue(commandLine.getArgList().contains("-d"),
                "Unrecognised -d must appear in the args list");
        assertTrue(commandLine.getArgList().contains("arg1"),
                "arg1 must appear in the args list");
        assertTrue(commandLine.getArgList().contains("arg2"),
                "arg2 must appear in the args list");

        // --- Act & Assert (stopAtNonOption = false) ---
        // When stop-at-non-option is disabled, the unrecognised -d must cause an
        // UnrecognizedOptionException whose message identifies the offending flag.
        final UnrecognizedOptionException e = assertThrows(
                UnrecognizedOptionException.class,
                () -> parser.parse(options, args, null, false),
                "An unrecognised option must throw UnrecognizedOptionException");
        assertTrue(e.getMessage().contains("-d"),
                "Exception message should name the offending option -d");
    }
}
