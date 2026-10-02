package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testParseIgnoreHappyPath extends AbstractParserTestCase {

    private static final String[] COMMAND_LINE_ARGS = { "-a", "-b", "-c", "-d", "arg1", "arg2" };

    private Option firstLetter;
    private Option secondLetter;
    private Option thirdLetter;
    private Option fourthLetter;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
        firstLetter = Option.builder().option("a").longOpt("first-letter").get();
        secondLetter = Option.builder().option("b").longOpt("second-letter").get();
        thirdLetter = Option.builder().option("c").longOpt("third-letter").get();
        fourthLetter = Option.builder().option("d").longOpt("fourth-letter").get();
    }

    @Test
    void testParseIgnoreHappyPath() throws ParseException {
        final Options baseOptions = optionsWith(firstLetter, secondLetter);
        final Options specificOptions = optionsWith(firstLetter, secondLetter, thirdLetter, fourthLetter);
        final DefaultParser parser = new DefaultParser();

        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.IGNORE, COMMAND_LINE_ARGS);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(2, baseCommandLine.getArgs().length);
        assertTrue(baseCommandLine.hasOption("a"));
        assertTrue(baseCommandLine.hasOption("b"));
        assertFalse(baseCommandLine.hasOption("c"));
        assertFalse(baseCommandLine.hasOption("d"));
        assertOnlyPlainArgumentsRemain(baseCommandLine);

        final CommandLine specificCommandLine = parser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, COMMAND_LINE_ARGS);
        assertEquals(4, specificCommandLine.getOptions().length);
        assertEquals(2, specificCommandLine.getArgs().length);
        assertTrue(specificCommandLine.hasOption("a"));
        assertTrue(specificCommandLine.hasOption("b"));
        assertTrue(specificCommandLine.hasOption("c"));
        assertTrue(specificCommandLine.hasOption("d"));
        assertOnlyPlainArgumentsRemain(specificCommandLine);
    }

    private Options optionsWith(final Option... optionsToAdd) {
        final Options options = new Options();
        for (final Option option : optionsToAdd) {
            options.addOption(option);
        }
        return options;
    }

    private void assertOnlyPlainArgumentsRemain(final CommandLine commandLine) {
        assertFalse(commandLine.getArgList().contains("-a"));
        assertFalse(commandLine.getArgList().contains("-b"));
        assertFalse(commandLine.getArgList().contains("-c"));
        assertFalse(commandLine.getArgList().contains("-d"));
        assertTrue(commandLine.getArgList().contains("arg1"));
        assertTrue(commandLine.getArgList().contains("arg2"));
    }
}
