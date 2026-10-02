package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testLegacyStopAtNonOption extends AbstractParserTestCase {

    private static final String ROGUE_OPTION = "-d";
    private static final String FIRST_ARGUMENT = "arg1";
    private static final String SECOND_ARGUMENT = "arg2";
    private static final String[] ARGUMENTS_WITH_ROGUE_OPTION = { "-a", "-b", "-c", ROGUE_OPTION, FIRST_ARGUMENT, SECOND_ARGUMENT };

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testLegacyStopAtNonOption() throws ParseException {
        final Option a = Option.builder().option("a").longOpt("first-letter").get();
        final Option b = Option.builder().option("b").longOpt("second-letter").get();
        final Option c = Option.builder().option("c").longOpt("third-letter").get();

        final Options options = new Options();
        options.addOption(a);
        options.addOption(b);
        options.addOption(c);

        final DefaultParser parser = new DefaultParser();

        final CommandLine commandLine = parser.parse(options, ARGUMENTS_WITH_ROGUE_OPTION, null, true);
        assertEquals(3, commandLine.getOptions().length);
        assertEquals(3, commandLine.getArgs().length);
        assertTrue(commandLine.getArgList().contains(ROGUE_OPTION));
        assertTrue(commandLine.getArgList().contains(FIRST_ARGUMENT));
        assertTrue(commandLine.getArgList().contains(SECOND_ARGUMENT));

        final UnrecognizedOptionException exception = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(options, ARGUMENTS_WITH_ROGUE_OPTION, null, false));
        assertTrue(exception.getMessage().contains(ROGUE_OPTION));
    }
}
