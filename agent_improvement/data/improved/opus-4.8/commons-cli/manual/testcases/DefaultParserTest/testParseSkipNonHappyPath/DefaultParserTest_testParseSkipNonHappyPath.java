package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testParseSkipNonHappyPath extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    /**
     * Verifies how {@link DefaultParser#parse(Options, java.util.Properties, DefaultParser.NonOptionAction, String...)}
     * treats an unrecognized ("rogue") option under the two opposite policies:
     * <ul>
     *   <li>{@link DefaultParser.NonOptionAction#SKIP}: unrecognized options are pushed into the
     *       argument list instead of failing.</li>
     *   <li>{@link DefaultParser.NonOptionAction#THROW}: the first unrecognized option aborts parsing
     *       with an {@link UnrecognizedOptionException}.</li>
     * </ul>
     */
    @Test
    void testParseSkipNonHappyPath() throws ParseException {
        final Option optionA = Option.builder().option("a").longOpt("first-letter").get();
        final Option optionB = Option.builder().option("b").longOpt("second-letter").get();
        final Option optionC = Option.builder().option("c").longOpt("third-letter").get();

        // Options that recognize only -a and -b; -c and -d are unknown here.
        final Options optionsAB = new Options();
        optionsAB.addOption(optionA);
        optionsAB.addOption(optionB);

        // Options that recognize -a, -b and -c; only -d remains unknown here.
        final Options optionsABC = new Options();
        optionsABC.addOption(optionA);
        optionsABC.addOption(optionB);
        optionsABC.addOption(optionC);

        // -d is the rogue (never-defined) option in both scenarios.
        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"};

        final DefaultParser parser = new DefaultParser();

        // SKIP policy: -a and -b are recognized; the unknown -c, -d plus arg1, arg2 become arguments.
        final CommandLine skippingCommandLine = parser.parse(optionsAB, null, DefaultParser.NonOptionAction.SKIP, args);
        assertEquals(2, skippingCommandLine.getOptions().length, "expected -a and -b to be recognized");
        assertEquals(4, skippingCommandLine.getArgs().length, "expected -c, -d, arg1 and arg2 as arguments");

        // THROW policy: -c is now recognized, but the still-unknown -d aborts parsing.
        final UnrecognizedOptionException exception = assertThrows(UnrecognizedOptionException.class,
            () -> parser.parse(optionsABC, null, DefaultParser.NonOptionAction.THROW, args));
        assertTrue(exception.getMessage().contains("-d"), "message should identify the rogue option -d");
    }
}
