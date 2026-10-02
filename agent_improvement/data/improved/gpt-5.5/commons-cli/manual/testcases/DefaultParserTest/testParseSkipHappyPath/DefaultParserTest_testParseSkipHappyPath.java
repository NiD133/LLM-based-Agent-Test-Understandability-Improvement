package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testParseSkipHappyPath extends AbstractParserTestCase {

    private static final String SHORT_OPTION_A = "a";
    private static final String SHORT_OPTION_B = "b";
    private static final String SHORT_OPTION_C = "c";
    private static final String SHORT_OPTION_D = "d";

    private static final String FIRST_ARGUMENT = "arg1";
    private static final String SECOND_ARGUMENT = "arg2";

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testParseSkipHappyPath() throws ParseException {
        final Option a = Option.builder().option(SHORT_OPTION_A).longOpt("first-letter").get();
        final Option b = Option.builder().option(SHORT_OPTION_B).longOpt("second-letter").get();
        final Option c = Option.builder().option(SHORT_OPTION_C).longOpt("third-letter").get();
        final Option d = Option.builder().option(SHORT_OPTION_D).longOpt("fourth-letter").get();

        final Options baseOptions = new Options();
        baseOptions.addOption(a);
        baseOptions.addOption(b);

        final Options specificOptions = new Options();
        specificOptions.addOption(a);
        specificOptions.addOption(b);
        specificOptions.addOption(c);
        specificOptions.addOption(d);

        final String[] args = { "-a", "-b", "-c", "-d", FIRST_ARGUMENT, SECOND_ARGUMENT };
        final DefaultParser parser = new DefaultParser();

        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.SKIP, args);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(4, baseCommandLine.getArgs().length);
        assertTrue(baseCommandLine.hasOption(SHORT_OPTION_A));
        assertTrue(baseCommandLine.hasOption(SHORT_OPTION_B));
        assertFalse(baseCommandLine.hasOption(SHORT_OPTION_C));
        assertFalse(baseCommandLine.hasOption(SHORT_OPTION_D));
        assertFalse(baseCommandLine.getArgList().contains("-a"));
        assertFalse(baseCommandLine.getArgList().contains("-b"));
        assertTrue(baseCommandLine.getArgList().contains("-c"));
        assertTrue(baseCommandLine.getArgList().contains("-d"));
        assertTrue(baseCommandLine.getArgList().contains(FIRST_ARGUMENT));
        assertTrue(baseCommandLine.getArgList().contains(SECOND_ARGUMENT));

        final CommandLine specificCommandLine = parser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args);
        assertEquals(4, specificCommandLine.getOptions().length);
        assertEquals(2, specificCommandLine.getArgs().length);
        assertTrue(specificCommandLine.hasOption(SHORT_OPTION_A));
        assertTrue(specificCommandLine.hasOption(SHORT_OPTION_B));
        assertTrue(specificCommandLine.hasOption(SHORT_OPTION_C));
        assertTrue(specificCommandLine.hasOption(SHORT_OPTION_D));
        assertFalse(specificCommandLine.getArgList().contains("-a"));
        assertFalse(specificCommandLine.getArgList().contains("-b"));
        assertFalse(specificCommandLine.getArgList().contains("-c"));
        assertFalse(specificCommandLine.getArgList().contains("-d"));
        assertTrue(specificCommandLine.getArgList().contains(FIRST_ARGUMENT));
        assertTrue(specificCommandLine.getArgList().contains(SECOND_ARGUMENT));
    }
}
