package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testParseIgnoreNonHappyPath extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    /**
     * Verifies how {@link DefaultParser} reacts to an unrecognized ("rogue") option,
     * depending on the chosen {@link DefaultParser.NonOptionAction}:
     * <ul>
     *   <li>{@code IGNORE} - the rogue option is silently dropped and parsing succeeds.</li>
     *   <li>{@code THROW}  - the rogue option triggers an {@link UnrecognizedOptionException}.</li>
     * </ul>
     */
    @Test
    void testParseIgnoreNonHappyPath() throws ParseException {
        // Define three options identified by -a, -b and -c.
        final Option optionA = Option.builder().option("a").longOpt("first-letter").get();
        final Option optionB = Option.builder().option("b").longOpt("second-letter").get();
        final Option optionC = Option.builder().option("c").longOpt("third-letter").get();

        // optionsWithoutC recognizes only -a and -b, so both -c and -d are rogue here.
        final Options optionsWithoutC = new Options();
        optionsWithoutC.addOption(optionA);
        optionsWithoutC.addOption(optionB);

        // optionsWithC additionally recognizes -c, leaving only -d as a rogue option.
        final Options optionsWithC = new Options();
        optionsWithC.addOption(optionA);
        optionsWithC.addOption(optionB);
        optionsWithC.addOption(optionC);

        // -d is never declared, so it is always a rogue option.
        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"};

        final DefaultParser parser = new DefaultParser();

        // IGNORE: rogue options (-c, -d) are dropped; only -a and -b plus the two
        // positional arguments survive.
        final CommandLine ignoredCommandLine =
                parser.parse(optionsWithoutC, null, DefaultParser.NonOptionAction.IGNORE, args);
        assertEquals(2, ignoredCommandLine.getOptions().length);
        assertEquals(2, ignoredCommandLine.getArgs().length);

        // THROW: the still-unrecognized -d aborts parsing with an exception naming it.
        final UnrecognizedOptionException exception = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(optionsWithC, null, DefaultParser.NonOptionAction.THROW, args));
        assertTrue(exception.getMessage().contains("-d"));
    }
}
