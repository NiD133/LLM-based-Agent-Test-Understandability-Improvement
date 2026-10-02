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

public class DefaultParserTest_testParseIgnoreHappyPath extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testParseIgnoreHappyPath() throws ParseException {
        final Option a = Option.builder().option("a").longOpt("first-letter").get();
        final Option b = Option.builder().option("b").longOpt("second-letter").get();
        final Option c = Option.builder().option("c").longOpt("third-letter").get();
        final Option d = Option.builder().option("d").longOpt("fourth-letter").get();
        final Options baseOptions = new Options();
        baseOptions.addOption(a);
        baseOptions.addOption(b);
        final Options specificOptions = new Options();
        specificOptions.addOption(a);
        specificOptions.addOption(b);
        specificOptions.addOption(c);
        specificOptions.addOption(d);
        final String[] args = { "-a", "-b", "-c", "-d", "arg1", "arg2" };
        final DefaultParser parser = new DefaultParser();
        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.IGNORE, args);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(2, baseCommandLine.getArgs().length);
        assertTrue(baseCommandLine.hasOption("a"));
        assertTrue(baseCommandLine.hasOption("b"));
        assertFalse(baseCommandLine.hasOption("c"));
        assertFalse(baseCommandLine.hasOption("d"));
        assertFalse(baseCommandLine.getArgList().contains("-a"));
        assertFalse(baseCommandLine.getArgList().contains("-b"));
        assertFalse(baseCommandLine.getArgList().contains("-c"));
        assertFalse(baseCommandLine.getArgList().contains("-d"));
        assertTrue(baseCommandLine.getArgList().contains("arg1"));
        assertTrue(baseCommandLine.getArgList().contains("arg2"));
        final CommandLine specificCommandLine = parser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args);
        assertEquals(4, specificCommandLine.getOptions().length);
        assertEquals(2, specificCommandLine.getArgs().length);
        assertTrue(specificCommandLine.hasOption("a"));
        assertTrue(specificCommandLine.hasOption("b"));
        assertTrue(specificCommandLine.hasOption("c"));
        assertTrue(specificCommandLine.hasOption("d"));
        assertFalse(specificCommandLine.getArgList().contains("-a"));
        assertFalse(specificCommandLine.getArgList().contains("-b"));
        assertFalse(specificCommandLine.getArgList().contains("-c"));
        assertFalse(specificCommandLine.getArgList().contains("-d"));
        assertTrue(specificCommandLine.getArgList().contains("arg1"));
        assertTrue(specificCommandLine.getArgList().contains("arg2"));
    }
}
