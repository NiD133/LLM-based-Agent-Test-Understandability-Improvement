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

    static class ExternalArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    Arguments.of("Long option quote handling DEFAULT behavior", DefaultParser.builder().get(),
                            new String[] {"--bfile", "\"quoted string\""}, "quoted string", "b",
                            "Confirm --bfile=\"arg\" strips quotes"),
                    Arguments.of("Long option with equals quote handling DEFAULT behavior",
                            DefaultParser.builder().get(), new String[] {"--bfile=\"quoted string\""},
                            "\"quoted string\"", "b", "Confirm --bfile=\"arg\" keeps quotes"),
                    Arguments.of("Short option quote handling DEFAULT behavior", DefaultParser.builder().get(),
                            new String[] {"-b", "\"quoted string\""}, "quoted string", "b",
                            "Confirm -b\"arg\" strips quotes"),
                    Arguments.of("Short option concatenated quote handling DEFAULT behavior",
                            DefaultParser.builder().get(), new String[] {"-b\"quoted string\""},
                            "\"quoted string\"", "b", "Confirm -b\"arg\" keeps quotes"),
                    Arguments.of("Long option quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[] {"--bfile", "\"quoted string\""}, "\"quoted string\"", "b",
                            "Confirm --bfile \"arg\" keeps quotes"),
                    Arguments.of("Long option with equals quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[] {"--bfile=\"quoted string\""}, "\"quoted string\"", "b",
                            "Confirm --bfile=\"arg\" keeps quotes"),
                    Arguments.of("Short option quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[] {"-b", "\"quoted string\""}, "\"quoted string\"", "b",
                            "Confirm -b\"arg\" keeps quotes"),
                    Arguments.of("Short option concatenated quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[] {"-b\"quoted string\""}, "\"quoted string\"", "b",
                            "Confirm -b\"arg\" keeps quotes"),
                    Arguments.of("Long option quote handling WITH strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[] {"--bfile", "\"quoted string\""}, "quoted string", "b",
                            "Confirm --bfile \"arg\" strips quotes"),
                    Arguments.of("Long option With Equals Quote Handling WITH Strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[] {"--bfile=\"quoted string\""}, "quoted string", "b",
                            "Confirm --bfile=\"arg\" strips quotes"),
                    Arguments.of("Short option quote handling WITH strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[] {"-b", "\"quoted string\""}, "quoted string", "b",
                            "Confirm -b \"arg\" strips quotes"),
                    Arguments.of("Short option concatenated quote handling WITH strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[] {"-b\"quoted string\""}, "quoted string", "b",
                            "Confirm -b\"arg\" strips quotes"));
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
    void parsesParameterizedArguments(final String testName, final CommandLineParser parser, final String[] args,
            final String expectedOptionValue, final String optionName, final String failureMessage) throws Exception {
        final CommandLine commandLine = parser.parse(options, args);

        assertEquals(expectedOptionValue, commandLine.getOptionValue(optionName), failureMessage);
    }
}
