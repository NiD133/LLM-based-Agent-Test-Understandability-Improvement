/*
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

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

class DefaultParserTest extends AbstractParserTestCase {

    private static final String[] PARSE_ACTION_ARGS = {"-a", "-b", "-c", "-d", "arg1", "arg2"};

    static class ExternalArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    quoteCase("Long option quote handling DEFAULT behavior", DefaultParser.builder().get(),
                            new String[] {"--bfile", "\"quoted string\""}, "quoted string", "b",
                            "Confirm --bfile=\"arg\" strips quotes"),
                    quoteCase("Long option with equals quote handling DEFAULT behavior", DefaultParser.builder().get(),
                            new String[] {"--bfile=\"quoted string\""}, "\"quoted string\"", "b",
                            "Confirm --bfile=\"arg\" keeps quotes"),
                    quoteCase("Short option quote handling DEFAULT behavior", DefaultParser.builder().get(),
                            new String[] {"-b", "\"quoted string\""}, "quoted string", "b",
                            "Confirm -b\"arg\" strips quotes"),
                    quoteCase("Short option concatenated quote handling DEFAULT behavior", DefaultParser.builder().get(),
                            new String[] {"-b\"quoted string\""}, "\"quoted string\"", "b",
                            "Confirm -b\"arg\" keeps quotes"),
                    quoteCase("Long option quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[] {"--bfile", "\"quoted string\""}, "\"quoted string\"", "b",
                            "Confirm --bfile \"arg\" keeps quotes"),
                    quoteCase("Long option with equals quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[] {"--bfile=\"quoted string\""}, "\"quoted string\"", "b",
                            "Confirm --bfile=\"arg\" keeps quotes"),
                    quoteCase("Short option quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[] {"-b", "\"quoted string\""}, "\"quoted string\"", "b",
                            "Confirm -b\"arg\" keeps quotes"),
                    quoteCase("Short option concatenated quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[] {"-b\"quoted string\""}, "\"quoted string\"", "b",
                            "Confirm -b\"arg\" keeps quotes"),
                    quoteCase("Long option quote handling WITH strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[] {"--bfile", "\"quoted string\""}, "quoted string", "b",
                            "Confirm --bfile \"arg\" strips quotes"),
                    quoteCase("Long option With Equals Quote Handling WITH Strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[] {"--bfile=\"quoted string\""}, "quoted string", "b",
                            "Confirm --bfile=\"arg\" strips quotes"),
                    quoteCase("Short option quote handling WITH strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[] {"-b", "\"quoted string\""}, "quoted string", "b",
                            "Confirm -b \"arg\" strips quotes"),
                    quoteCase("Short option concatenated quote handling WITH strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[] {"-b\"quoted string\""}, "quoted string", "b",
                            "Confirm -b\"arg\" strips quotes"));
        }

        private static Arguments quoteCase(final String name, final CommandLineParser parser, final String[] args,
                final String expected, final String option, final String message) {
            return Arguments.of(name, parser, args, expected, option, message);
        }
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testBuilder() {
        final Builder builder = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(false)
                .setAllowPartialMatching(false)
                .setDeprecatedHandler(null);

        parser = builder.build();
        assertEquals(DefaultParser.class, parser.getClass());
        parser = builder.get();
        assertEquals(DefaultParser.class, parser.getClass());
    }

    @Test
    void testDeprecated() throws ParseException {
        final Set<Option> handler = new HashSet<>();
        parser = DefaultParser.builder().setDeprecatedHandler(handler::add).build();

        final Option opt1 = Option.builder().option("d1").deprecated().get();
        final Option opt2 = Option.builder().option("d2").deprecated(DeprecatedAttributes.builder()
                .setForRemoval(true)
                .setSince("1.0")
                .setDescription("Do this instead.").get()).get();
        final Option opt3 = Option.builder().option("a").get();

        final CommandLine cl = parser.parse(new Options()
                .addOption(opt1)
                .addOption(opt2)
                .addOption(opt3),
                new String[] {"-d1", "-d2", "-a"});

        assertTrue(cl.hasOption(opt1.getOpt()));
        assertTrue(cl.hasOption(opt2.getOpt()));
        assertTrue(cl.hasOption(opt3.getOpt()));
        assertTrue(handler.contains(opt1));
        assertTrue(handler.contains(opt2));
        assertFalse(handler.contains(opt3));
    }

    @Test
    void testLegacyStopAtNonOption() throws ParseException {
        final Options options = optionsWithFirstSecondAndThirdLetters();
        final DefaultParser parser = new DefaultParser();

        final CommandLine commandLine = parser.parse(options, PARSE_ACTION_ARGS, null, true);
        assertEquals(3, commandLine.getOptions().length);
        assertEquals(3, commandLine.getArgs().length);
        assertArgumentsPresent(commandLine, "-d", "arg1", "arg2");

        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(options, PARSE_ACTION_ARGS, null, false));
        assertTrue(e.getMessage().contains("-d"));
    }

    @Override
    @Test
    @Disabled("Test case handled in the parameterized tests as \"DEFAULT behavior\"")
    void testLongOptionWithEqualsQuoteHandling() throws Exception {
    }

    @ParameterizedTest(name = "{index}. {0}")
    @ArgumentsSource(ExternalArgumentsProvider.class)
    void testParameterized(final String testName, final CommandLineParser parser, final String[] args, final String expected,
            final String option, final String message) throws Exception {
        final CommandLine cl = parser.parse(options, args);

        assertEquals(expected, cl.getOptionValue(option), message);
    }

    @Test
    void testParseIgnoreHappyPath() throws ParseException {
        final ParseActionOptions parseOptions = parseActionOptions(true);
        final DefaultParser parser = new DefaultParser();

        final CommandLine baseCommandLine = parser.parse(parseOptions.baseOptions, null, DefaultParser.NonOptionAction.IGNORE, PARSE_ACTION_ARGS);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(2, baseCommandLine.getArgs().length);
        assertParsedOptions(baseCommandLine, "a", "b");
        assertSkippedOptions(baseCommandLine, "c", "d");
        assertArgumentsAbsent(baseCommandLine, "-a", "-b", "-c", "-d");
        assertArgumentsPresent(baseCommandLine, "arg1", "arg2");

        final CommandLine specificCommandLine =
                parser.parse(parseOptions.specificOptions, null, DefaultParser.NonOptionAction.THROW, PARSE_ACTION_ARGS);
        assertEquals(4, specificCommandLine.getOptions().length);
        assertEquals(2, specificCommandLine.getArgs().length);
        assertParsedOptions(specificCommandLine, "a", "b", "c", "d");
        assertArgumentsAbsent(specificCommandLine, "-a", "-b", "-c", "-d");
        assertArgumentsPresent(specificCommandLine, "arg1", "arg2");
    }

    @Test
    void testParseIgnoreNonHappyPath() throws ParseException {
        final ParseActionOptions parseOptions = parseActionOptions(false);
        final DefaultParser parser = new DefaultParser();

        final CommandLine baseCommandLine = parser.parse(parseOptions.baseOptions, null, DefaultParser.NonOptionAction.IGNORE, PARSE_ACTION_ARGS);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(2, baseCommandLine.getArgs().length);

        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(parseOptions.specificOptions, null, DefaultParser.NonOptionAction.THROW, PARSE_ACTION_ARGS));
        assertTrue(e.getMessage().contains("-d"));
    }

    @Test
    void testParseNullOption() throws ParseException {
        assertThrows(NullPointerException.class, () -> new DefaultParser().parse(null, null, DefaultParser.NonOptionAction.IGNORE, "-a"));
    }

    @Test
    void testParseSkipHappyPath() throws ParseException {
        final ParseActionOptions parseOptions = parseActionOptions(true);
        final DefaultParser parser = new DefaultParser();

        final CommandLine baseCommandLine = parser.parse(parseOptions.baseOptions, null, DefaultParser.NonOptionAction.SKIP, PARSE_ACTION_ARGS);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(4, baseCommandLine.getArgs().length);
        assertParsedOptions(baseCommandLine, "a", "b");
        assertSkippedOptions(baseCommandLine, "c", "d");
        assertArgumentsAbsent(baseCommandLine, "-a", "-b");
        assertArgumentsPresent(baseCommandLine, "-c", "-d", "arg1", "arg2");

        final CommandLine specificCommandLine =
                parser.parse(parseOptions.specificOptions, null, DefaultParser.NonOptionAction.THROW, PARSE_ACTION_ARGS);
        assertEquals(4, specificCommandLine.getOptions().length);
        assertEquals(2, specificCommandLine.getArgs().length);
        assertParsedOptions(specificCommandLine, "a", "b", "c", "d");
        assertArgumentsAbsent(specificCommandLine, "-a", "-b", "-c", "-d");
        assertArgumentsPresent(specificCommandLine, "arg1", "arg2");
    }

    @Test
    void testParseSkipNonHappyPath() throws ParseException {
        final ParseActionOptions parseOptions = parseActionOptions(false);
        final DefaultParser parser = new DefaultParser();

        final CommandLine baseCommandLine = parser.parse(parseOptions.baseOptions, null, DefaultParser.NonOptionAction.SKIP, PARSE_ACTION_ARGS);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(4, baseCommandLine.getArgs().length);

        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(parseOptions.specificOptions, null, DefaultParser.NonOptionAction.THROW, PARSE_ACTION_ARGS));
        assertTrue(e.getMessage().contains("-d"));
    }

    @Override
    @Test
    @Disabled("Test case handled in the parameterized tests as \"DEFAULT behavior\"")
    void testShortOptionConcatenatedQuoteHandling() throws Exception {
    }

    private static void assertArgumentsAbsent(final CommandLine commandLine, final String... arguments) {
        for (final String argument : arguments) {
            assertFalse(commandLine.getArgList().contains(argument));
        }
    }

    private static void assertArgumentsPresent(final CommandLine commandLine, final String... arguments) {
        for (final String argument : arguments) {
            assertTrue(commandLine.getArgList().contains(argument));
        }
    }

    private static void assertParsedOptions(final CommandLine commandLine, final String... optionNames) {
        for (final String optionName : optionNames) {
            assertTrue(commandLine.hasOption(optionName));
        }
    }

    private static void assertSkippedOptions(final CommandLine commandLine, final String... optionNames) {
        for (final String optionName : optionNames) {
            assertFalse(commandLine.hasOption(optionName));
        }
    }

    private static Option firstLetterOption() {
        return Option.builder().option("a").longOpt("first-letter").get();
    }

    private static Option secondLetterOption() {
        return Option.builder().option("b").longOpt("second-letter").get();
    }

    private static Option thirdLetterOption() {
        return Option.builder().option("c").longOpt("third-letter").get();
    }

    private static Option fourthLetterOption() {
        return Option.builder().option("d").longOpt("fourth-letter").get();
    }

    private static Options optionsWithFirstSecondAndThirdLetters() {
        final Options options = new Options();
        options.addOption(firstLetterOption());
        options.addOption(secondLetterOption());
        options.addOption(thirdLetterOption());
        return options;
    }

    private static ParseActionOptions parseActionOptions(final boolean includeFourthSpecificOption) {
        final Option a = firstLetterOption();
        final Option b = secondLetterOption();
        final Option c = thirdLetterOption();

        final Options baseOptions = new Options();
        baseOptions.addOption(a);
        baseOptions.addOption(b);

        final Options specificOptions = new Options();
        specificOptions.addOption(a);
        specificOptions.addOption(b);
        specificOptions.addOption(c);
        if (includeFourthSpecificOption) {
            specificOptions.addOption(fourthLetterOption());
        }

        return new ParseActionOptions(baseOptions, specificOptions);
    }

    private static final class ParseActionOptions {

        private final Options baseOptions;
        private final Options specificOptions;

        private ParseActionOptions(final Options baseOptions, final Options specificOptions) {
            this.baseOptions = baseOptions;
            this.specificOptions = specificOptions;
        }
    }
}
