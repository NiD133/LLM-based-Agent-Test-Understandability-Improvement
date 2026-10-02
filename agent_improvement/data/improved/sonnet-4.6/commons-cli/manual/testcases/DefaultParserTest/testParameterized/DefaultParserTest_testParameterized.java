package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class DefaultParserTest_testParameterized extends AbstractParserTestCase {

    /**
     * Supplies test cases for {@link #testParameterized}.
     *
     * Each row is: (scenarioName, parser, args, expectedValue, optionKey, failureMessage).
     * All scenarios operate on the shared {@code options} object set up by
     * {@link AbstractParserTestCase#setUp()}: -a/--enable-a (no arg), -b/--bfile (requires arg), -c/--copt (no arg).
     */
    static class ExternalArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                // Short option: value passed as the next token
                Arguments.of(
                    "short option with space-separated value",
                    new DefaultParser(),
                    new String[]{"-b", "toast"},
                    "toast",
                    "b",
                    "Short option -b should capture the immediately following token as its value"
                ),
                // Long option: value passed as the next token
                Arguments.of(
                    "long option with space-separated value",
                    new DefaultParser(),
                    new String[]{"--bfile", "toast"},
                    "toast",
                    "b",
                    "Long option --bfile should capture the immediately following token as its value"
                ),
                // Long option: value embedded with '=' separator
                Arguments.of(
                    "long option with equals-sign value",
                    new DefaultParser(),
                    new String[]{"--bfile=toast"},
                    "toast",
                    "b",
                    "Long option --bfile=<value> should parse the part after '=' as the option value"
                ),
                // Short option: value concatenated directly to the option character
                Arguments.of(
                    "short option with concatenated value",
                    new DefaultParser(),
                    new String[]{"-btoast"},
                    "toast",
                    "b",
                    "Short option -b<value> should parse the remaining characters as the option value"
                ),
                // A negative number should be accepted as an option value, not treated as a new option
                Arguments.of(
                    "negative number accepted as option value",
                    new DefaultParser(),
                    new String[]{"-b", "-1"},
                    "-1",
                    "b",
                    "A negative numeric token following -b should be treated as the option value, not as a new option"
                ),
                // Balanced outer double quotes must be stripped from space-separated option values
                Arguments.of(
                    "balanced double quotes stripped from space-separated value",
                    new DefaultParser(),
                    new String[]{"-b", "\"quoted string\""},
                    "quoted string",
                    "b",
                    "Balanced outer double quotes around a space-separated value should be stripped by the default parser"
                )
            );
        }
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @ParameterizedTest(name = "{index}. {0}")
    @ArgumentsSource(ExternalArgumentsProvider.class)
    void testParameterized(
            final String testName,
            final CommandLineParser parser,
            final String[] args,
            final String expected,
            final String option,
            final String message) throws Exception {
        final CommandLine cl = parser.parse(options, args);
        assertEquals(expected, cl.getOptionValue(option), message);
    }
}
