package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.commons.cli.DefaultParser.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class DefaultParserTest_testLegacyStopAtNonOption extends AbstractParserTestCase {

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
        // -d is rogue option
        final String[] args = { "-a", "-b", "-c", "-d", "arg1", "arg2" };
        final DefaultParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args, null, true);
        assertEquals(3, commandLine.getOptions().length);
        assertEquals(3, commandLine.getArgs().length);
        assertTrue(commandLine.getArgList().contains("-d"));
        assertTrue(commandLine.getArgList().contains("arg1"));
        assertTrue(commandLine.getArgList().contains("arg2"));
        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class, () -> parser.parse(options, args, null, false));
        assertTrue(e.getMessage().contains("-d"));
    }
}
