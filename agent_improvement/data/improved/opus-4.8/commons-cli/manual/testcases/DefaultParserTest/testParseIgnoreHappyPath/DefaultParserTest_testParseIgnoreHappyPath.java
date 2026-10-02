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

    @Test
    void testParseIgnoreHappyPath() throws ParseException {
        // Four options that share the same command line arguments below.
        final Option a = Option.builder().option("a").longOpt("first-letter").get();
        final Option b = Option.builder().option("b").longOpt("second-letter").get();
        final Option c = Option.builder().option("c").longOpt("third-letter").get();
        final Option d = Option.builder().option("d").longOpt("fourth-letter").get();

        // The same arguments are parsed twice below, against two different option sets.
        final String[] args = { "-a", "-b", "-c", "-d", "arg1", "arg2" };
        final DefaultParser parser = new DefaultParser();

        // Case 1: only -a and -b are registered, so -c and -d are unrecognized.
        // With NonOptionAction.IGNORE the unrecognized "-c"/"-d" tokens are dropped
        // (neither parsed as options nor kept as arguments), while the plain
        // "arg1"/"arg2" tokens remain as command line arguments.
        final Options registeredAandB = new Options();
        registeredAandB.addOption(a);
        registeredAandB.addOption(b);

        final CommandLine ignoringCommandLine =
                parser.parse(registeredAandB, null, DefaultParser.NonOptionAction.IGNORE, args);

        assertEquals(2, ignoringCommandLine.getOptions().length);
        assertEquals(2, ignoringCommandLine.getArgs().length);
        // Registered options were parsed.
        assertTrue(ignoringCommandLine.hasOption("a"));
        assertTrue(ignoringCommandLine.hasOption("b"));
        // Unregistered options were ignored, not parsed.
        assertFalse(ignoringCommandLine.hasOption("c"));
        assertFalse(ignoringCommandLine.hasOption("d"));
        // No option-like token leaked into the argument list.
        assertFalse(ignoringCommandLine.getArgList().contains("-a"));
        assertFalse(ignoringCommandLine.getArgList().contains("-b"));
        assertFalse(ignoringCommandLine.getArgList().contains("-c"));
        assertFalse(ignoringCommandLine.getArgList().contains("-d"));
        // Plain tokens survived as arguments.
        assertTrue(ignoringCommandLine.getArgList().contains("arg1"));
        assertTrue(ignoringCommandLine.getArgList().contains("arg2"));

        // Case 2: all four options are registered, so every "-x" token is parsed.
        // NonOptionAction.THROW would reject any unrecognized option, but here there
        // are none; the plain "arg1"/"arg2" tokens are still kept as arguments.
        final Options registeredAll = new Options();
        registeredAll.addOption(a);
        registeredAll.addOption(b);
        registeredAll.addOption(c);
        registeredAll.addOption(d);

        final CommandLine fullCommandLine =
                parser.parse(registeredAll, null, DefaultParser.NonOptionAction.THROW, args);

        assertEquals(4, fullCommandLine.getOptions().length);
        assertEquals(2, fullCommandLine.getArgs().length);
        // All four registered options were parsed.
        assertTrue(fullCommandLine.hasOption("a"));
        assertTrue(fullCommandLine.hasOption("b"));
        assertTrue(fullCommandLine.hasOption("c"));
        assertTrue(fullCommandLine.hasOption("d"));
        // No option-like token leaked into the argument list.
        assertFalse(fullCommandLine.getArgList().contains("-a"));
        assertFalse(fullCommandLine.getArgList().contains("-b"));
        assertFalse(fullCommandLine.getArgList().contains("-c"));
        assertFalse(fullCommandLine.getArgList().contains("-d"));
        // Plain tokens survived as arguments.
        assertTrue(fullCommandLine.getArgList().contains("arg1"));
        assertTrue(fullCommandLine.getArgList().contains("arg2"));
    }
}
