package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testLegacyStopAtNonOption extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testLegacyStopAtNonOption() throws ParseException {
        // Recognized options: -a, -b and -c.
        final Options options = new Options();
        options.addOption(Option.builder().option("a").longOpt("first-letter").get());
        options.addOption(Option.builder().option("b").longOpt("second-letter").get());
        options.addOption(Option.builder().option("c").longOpt("third-letter").get());

        // -d is an unrecognized ("rogue") option; arg1 and arg2 are plain arguments.
        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"};
        final DefaultParser parser = new DefaultParser();

        // With stopAtNonOption == true, parsing stops at the first unrecognized token (-d).
        // The three recognized options are consumed; -d and everything after it become arguments.
        final CommandLine commandLine = parser.parse(options, args, null, true);
        assertEquals(3, commandLine.getOptions().length);
        assertEquals(3, commandLine.getArgs().length);
        assertTrue(commandLine.getArgList().contains("-d"));
        assertTrue(commandLine.getArgList().contains("arg1"));
        assertTrue(commandLine.getArgList().contains("arg2"));

        // With stopAtNonOption == false, the unrecognized -d triggers an exception.
        final UnrecognizedOptionException e =
                assertThrows(UnrecognizedOptionException.class, () -> parser.parse(options, args, null, false));
        assertTrue(e.getMessage().contains("-d"));
    }
}
