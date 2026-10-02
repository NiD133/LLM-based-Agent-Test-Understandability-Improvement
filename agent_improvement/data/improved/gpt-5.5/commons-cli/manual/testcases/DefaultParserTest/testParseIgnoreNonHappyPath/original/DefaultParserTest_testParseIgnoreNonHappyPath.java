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

public class DefaultParserTest_testParseIgnoreNonHappyPath extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testParseIgnoreNonHappyPath() throws ParseException {
        final Option a = Option.builder().option("a").longOpt("first-letter").get();
        final Option b = Option.builder().option("b").longOpt("second-letter").get();
        final Option c = Option.builder().option("c").longOpt("third-letter").get();
        final Options baseOptions = new Options();
        baseOptions.addOption(a);
        baseOptions.addOption(b);
        final Options specificOptions = new Options();
        specificOptions.addOption(a);
        specificOptions.addOption(b);
        specificOptions.addOption(c);
        // -d is rogue option
        final String[] args = { "-a", "-b", "-c", "-d", "arg1", "arg2" };
        final DefaultParser parser = new DefaultParser();
        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.IGNORE, args);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(2, baseCommandLine.getArgs().length);
        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class, () -> parser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args));
        assertTrue(e.getMessage().contains("-d"));
    }
}
