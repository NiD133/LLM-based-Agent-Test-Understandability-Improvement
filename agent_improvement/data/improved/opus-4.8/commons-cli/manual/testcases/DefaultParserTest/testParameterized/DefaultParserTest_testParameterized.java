package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Verifies how {@link DefaultParser} handles the balanced double quotes that surround an option's
 * argument value, across the three quote-stripping policies and the four ways an argument can be
 * supplied (long with space, long with {@code =}, short with space, short concatenated).
 */
public class DefaultParserTest_testParameterized extends AbstractParserTestCase {

    /** The single option, {@code -b} / {@code --bfile}, whose value every case inspects. */
    private static final String CHECKED_OPTION = "b";

    /** An argument value that still carries its surrounding double quotes. */
    private static final String QUOTED = "\"quoted string\"";

    /** The same value once the surrounding double quotes have been stripped. */
    private static final String UNQUOTED = "quoted string";

    /** Parser that uses the historic default quote handling. */
    private static CommandLineParser defaultParser() {
        return DefaultParser.builder().get();
    }

    /** Parser that explicitly enables ({@code true}) or disables ({@code false}) quote stripping. */
    private static CommandLineParser parserWithStripping(final boolean stripQuotes) {
        return DefaultParser.builder().setStripLeadingAndTrailingQuotes(stripQuotes).get();
    }

    /**
     * Supplies the quote-handling cases. Each case carries, in order:
     * <ol>
     *   <li>a human-readable case name (shown in the test report),</li>
     *   <li>the parser under test,</li>
     *   <li>the command-line arguments to parse,</li>
     *   <li>the expected value of option {@code -b},</li>
     *   <li>the option to read back ({@code "b"}),</li>
     *   <li>the assertion message.</li>
     * </ol>
     */
    static class ExternalArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    // --- Default policy: quotes are stripped only when the value is a separate token ---
                    Arguments.of("Long option quote handling DEFAULT behavior",
                            defaultParser(), new String[] {"--bfile", QUOTED},
                            UNQUOTED, CHECKED_OPTION, "Confirm --bfile=\"arg\" strips quotes"),
                    Arguments.of("Long option with equals quote handling DEFAULT behavior",
                            defaultParser(), new String[] {"--bfile=" + QUOTED},
                            QUOTED, CHECKED_OPTION, "Confirm --bfile=\"arg\" keeps quotes"),
                    Arguments.of("Short option quote handling DEFAULT behavior",
                            defaultParser(), new String[] {"-b", QUOTED},
                            UNQUOTED, CHECKED_OPTION, "Confirm -b\"arg\" strips quotes"),
                    Arguments.of("Short option concatenated quote handling DEFAULT behavior",
                            defaultParser(), new String[] {"-b" + QUOTED},
                            QUOTED, CHECKED_OPTION, "Confirm -b\"arg\" keeps quotes"),

                    // --- Stripping disabled: quotes are always preserved ---
                    Arguments.of("Long option quote handling WITHOUT strip",
                            parserWithStripping(false), new String[] {"--bfile", QUOTED},
                            QUOTED, CHECKED_OPTION, "Confirm --bfile \"arg\" keeps quotes"),
                    Arguments.of("Long option with equals quote handling WITHOUT strip",
                            parserWithStripping(false), new String[] {"--bfile=" + QUOTED},
                            QUOTED, CHECKED_OPTION, "Confirm --bfile=\"arg\" keeps quotes"),
                    Arguments.of("Short option quote handling WITHOUT strip",
                            parserWithStripping(false), new String[] {"-b", QUOTED},
                            QUOTED, CHECKED_OPTION, "Confirm -b\"arg\" keeps quotes"),
                    Arguments.of("Short option concatenated quote handling WITHOUT strip",
                            parserWithStripping(false), new String[] {"-b" + QUOTED},
                            QUOTED, CHECKED_OPTION, "Confirm -b\"arg\" keeps quotes"),

                    // --- Stripping enabled: quotes are always removed ---
                    Arguments.of("Long option quote handling WITH strip",
                            parserWithStripping(true), new String[] {"--bfile", QUOTED},
                            UNQUOTED, CHECKED_OPTION, "Confirm --bfile \"arg\" strips quotes"),
                    Arguments.of("Long option With Equals Quote Handling WITH Strip",
                            parserWithStripping(true), new String[] {"--bfile=" + QUOTED},
                            UNQUOTED, CHECKED_OPTION, "Confirm --bfile=\"arg\" strips quotes"),
                    Arguments.of("Short option quote handling WITH strip",
                            parserWithStripping(true), new String[] {"-b", QUOTED},
                            UNQUOTED, CHECKED_OPTION, "Confirm -b \"arg\" strips quotes"),
                    Arguments.of("Short option concatenated quote handling WITH strip",
                            parserWithStripping(true), new String[] {"-b" + QUOTED},
                            UNQUOTED, CHECKED_OPTION, "Confirm -b\"arg\" strips quotes"));
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
    void testParameterized(final String testName, final CommandLineParser parser, final String[] args,
            final String expected, final String option, final String message) throws Exception {
        final CommandLine cl = parser.parse(options, args);
        assertEquals(expected, cl.getOptionValue(option), message);
    }
}
