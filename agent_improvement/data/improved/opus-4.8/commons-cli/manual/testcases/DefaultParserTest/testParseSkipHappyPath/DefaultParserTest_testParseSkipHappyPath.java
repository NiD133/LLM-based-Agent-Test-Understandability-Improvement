package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link DefaultParser#parse(Options, java.util.Properties, DefaultParser.NonOptionAction, String...)}
 * treats command-line tokens that are not registered as options, for the two "happy path" actions:
 *
 * <ul>
 *   <li>{@link DefaultParser.NonOptionAction#SKIP}  - unknown option-like tokens are kept as plain arguments.</li>
 *   <li>{@link DefaultParser.NonOptionAction#THROW} - all tokens are known, so parsing succeeds without throwing.</li>
 * </ul>
 */
public class DefaultParserTest_testParseSkipHappyPath extends AbstractParserTestCase {

    /** Same command line used in both scenarios: four short options followed by two positional arguments. */
    private static final String[] COMMAND_LINE = {"-a", "-b", "-c", "-d", "arg1", "arg2"};

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testParseSkipHappyPath() throws ParseException {
        final Option a = Option.builder().option("a").longOpt("first-letter").get();
        final Option b = Option.builder().option("b").longOpt("second-letter").get();
        final Option c = Option.builder().option("c").longOpt("third-letter").get();
        final Option d = Option.builder().option("d").longOpt("fourth-letter").get();

        // Knows only "a" and "b"; "-c" and "-d" are therefore unknown.
        final Options optionsKnowingOnlyAandB = new Options();
        optionsKnowingOnlyAandB.addOption(a);
        optionsKnowingOnlyAandB.addOption(b);

        // Knows all four options.
        final Options optionsKnowingAll = new Options();
        optionsKnowingAll.addOption(a);
        optionsKnowingAll.addOption(b);
        optionsKnowingAll.addOption(c);
        optionsKnowingAll.addOption(d);

        final DefaultParser parser = new DefaultParser();

        // Scenario 1 - SKIP: unknown "-c" and "-d" are kept as arguments alongside "arg1" and "arg2".
        final CommandLine skipResult =
                parser.parse(optionsKnowingOnlyAandB, null, DefaultParser.NonOptionAction.SKIP, COMMAND_LINE);

        assertEquals(2, skipResult.getOptions().length, "Only -a and -b should be recognized as options");
        assertEquals(4, skipResult.getArgs().length, "-c, -d, arg1 and arg2 should be collected as arguments");
        assertTrue(skipResult.hasOption("a"));
        assertTrue(skipResult.hasOption("b"));
        assertFalse(skipResult.hasOption("c"));
        assertFalse(skipResult.hasOption("d"));
        // The known options are consumed, so their tokens are not left in the argument list...
        assertFalse(skipResult.getArgList().contains("-a"));
        assertFalse(skipResult.getArgList().contains("-b"));
        // ...while the unknown option-like tokens and the positional arguments are.
        assertTrue(skipResult.getArgList().contains("-c"));
        assertTrue(skipResult.getArgList().contains("-d"));
        assertTrue(skipResult.getArgList().contains("arg1"));
        assertTrue(skipResult.getArgList().contains("arg2"));

        // Scenario 2 - THROW: every option is known, so nothing is "unknown" and parsing succeeds.
        final CommandLine throwResult =
                parser.parse(optionsKnowingAll, null, DefaultParser.NonOptionAction.THROW, COMMAND_LINE);

        assertEquals(4, throwResult.getOptions().length, "All four options should be recognized");
        assertEquals(2, throwResult.getArgs().length, "Only arg1 and arg2 remain as arguments");
        assertTrue(throwResult.hasOption("a"));
        assertTrue(throwResult.hasOption("b"));
        assertTrue(throwResult.hasOption("c"));
        assertTrue(throwResult.hasOption("d"));
        // All option tokens are consumed, leaving only the positional arguments.
        assertFalse(throwResult.getArgList().contains("-a"));
        assertFalse(throwResult.getArgList().contains("-b"));
        assertFalse(throwResult.getArgList().contains("-c"));
        assertFalse(throwResult.getArgList().contains("-d"));
        assertTrue(throwResult.getArgList().contains("arg1"));
        assertTrue(throwResult.getArgList().contains("arg2"));
    }
}
