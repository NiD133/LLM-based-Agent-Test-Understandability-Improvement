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

    /**
     * Supplies the quote-handling scenarios consumed by {@link #testParameterized}.
     * <p>
     * Each scenario parses a single option whose value is the double-quoted string
     * {@link #QUOTED} and asserts whether the parser stripped the surrounding quotes,
     * depending on the parser's {@code stripLeadingAndTrailingQuotes} setting.
     * </p>
     */
    static class ExternalArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    // ---- Default parser: quote stripping follows the historic behavior ----
                    // Quotes ARE stripped when the value is a separate token (space-separated).
                    quoteCase(
                            "Long option quote handling DEFAULT behavior",
                            defaultParser(),
                            new String[] {"--bfile", QUOTED},
                            UNQUOTED,
                            "Confirm --bfile=\"arg\" strips quotes"),
                    // Quotes are KEPT when the value is attached with '=' or concatenated.
                    quoteCase(
                            "Long option with equals quote handling DEFAULT behavior",
                            defaultParser(),
                            new String[] {"--bfile=" + QUOTED},
                            QUOTED,
                            "Confirm --bfile=\"arg\" keeps quotes"),
                    quoteCase(
                            "Short option quote handling DEFAULT behavior",
                            defaultParser(),
                            new String[] {"-b", QUOTED},
                            UNQUOTED,
                            "Confirm -b\"arg\" strips quotes"),
                    quoteCase(
                            "Short option concatenated quote handling DEFAULT behavior",
                            defaultParser(),
                            new String[] {"-b" + QUOTED},
                            QUOTED,
                            "Confirm -b\"arg\" keeps quotes"),

                    // ---- Stripping disabled: quotes are KEPT in every form ----
                    quoteCase(
                            "Long option quote handling WITHOUT strip",
                            keepQuotesParser(),
                            new String[] {"--bfile", QUOTED},
                            QUOTED,
                            "Confirm --bfile \"arg\" keeps quotes"),
                    quoteCase(
                            "Long option with equals quote handling WITHOUT strip",
                            keepQuotesParser(),
                            new String[] {"--bfile=" + QUOTED},
                            QUOTED,
                            "Confirm --bfile=\"arg\" keeps quotes"),
                    quoteCase(
                            "Short option quote handling WITHOUT strip",
                            keepQuotesParser(),
                            new String[] {"-b", QUOTED},
                            QUOTED,
                            "Confirm -b\"arg\" keeps quotes"),
                    quoteCase(
                            "Short option concatenated quote handling WITHOUT strip",
                            keepQuotesParser(),
                            new String[] {"-b" + QUOTED},
                            QUOTED,
                            "Confirm -b\"arg\" keeps quotes"),

                    // ---- Stripping enabled: quotes are STRIPPED in every form ----
                    quoteCase(
                            "Long option quote handling WITH strip",
                            stripQuotesParser(),
                            new String[] {"--bfile", QUOTED},
                            UNQUOTED,
                            "Confirm --bfile \"arg\" strips quotes"),
                    quoteCase(
                            "Long option With Equals Quote Handling WITH Strip",
                            stripQuotesParser(),
                            new String[] {"--bfile=" + QUOTED},
                            UNQUOTED,
                            "Confirm --bfile=\"arg\" strips quotes"),
                    quoteCase(
                            "Short option quote handling WITH strip",
                            stripQuotesParser(),
                            new String[] {"-b", QUOTED},
                            UNQUOTED,
                            "Confirm -b \"arg\" strips quotes"),
                    quoteCase(
                            "Short option concatenated quote handling WITH strip",
                            stripQuotesParser(),
                            new String[] {"-b" + QUOTED},
                            UNQUOTED,
                            "Confirm -b\"arg\" strips quotes"));
        }
    }

    /** A double-quoted argument value exactly as it appears on the command line. */
    private static final String QUOTED = "\"quoted string\"";

    /** {@link #QUOTED} after the balanced surrounding quotes have been stripped. */
    private static final String UNQUOTED = "quoted string";

    /** The short option ("b", a.k.a. "--bfile") whose value is inspected in the quote-handling cases. */
    private static final String OPTION_B = "b";

    /**
     * Command-line tokens shared by the IGNORE/SKIP/STOP tests: four single-letter options
     * followed by two positional arguments. Whether {@code -d} is "rogue" depends on the
     * {@link Options} a given test registers.
     */
    private static final String[] STANDARD_ARGS = {"-a", "-b", "-c", "-d", "arg1", "arg2"};

    /** Builds the default parser (historic quote handling). */
    private static DefaultParser defaultParser() {
        return DefaultParser.builder().get();
    }

    /** Builds a parser that always keeps leading/trailing quotes. */
    private static DefaultParser keepQuotesParser() {
        return DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get();
    }

    /** Builds an option identified by a short and a long name. */
    private static Option letterOption(final String opt, final String longOpt) {
        return Option.builder().option(opt).longOpt(longOpt).get();
    }

    /** Bundles a quote-handling scenario into JUnit {@link Arguments}, always checking {@link #OPTION_B}. */
    private static Arguments quoteCase(final String testName, final CommandLineParser parser, final String[] args, final String expected,
            final String message) {
        return Arguments.of(testName, parser, args, expected, OPTION_B, message);
    }

    /** Builds a parser that always strips balanced leading/trailing quotes. */
    private static DefaultParser stripQuotesParser() {
        return DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get();
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testBuilder() {
        // @formatter:off
        final Builder builder = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(false)
                .setAllowPartialMatching(false)
                .setDeprecatedHandler(null);
        // @formatter:on
        // build() and get() are equivalent factory methods; both yield a DefaultParser.
        parser = builder.build();
        assertEquals(DefaultParser.class, parser.getClass());
        parser = builder.get();
        assertEquals(DefaultParser.class, parser.getClass());
    }

    @Test
    void testDeprecated() throws ParseException {
        // Collect every deprecated option the parser encounters.
        final Set<Option> handler = new HashSet<>();
        parser = DefaultParser.builder().setDeprecatedHandler(handler::add).build();
        final Option opt1 = Option.builder().option("d1").deprecated().get();
        // @formatter:off
        final Option opt2 = Option.builder().option("d2").deprecated(DeprecatedAttributes.builder()
        .setForRemoval(true)
        .setSince("1.0")
        .setDescription("Do this instead.").get()).get();
        // @formatter:on
        final Option opt3 = Option.builder().option("a").get();
        // @formatter:off
        final CommandLine cl = parser.parse(new Options()
                .addOption(opt1)
                .addOption(opt2)
                .addOption(opt3),
                new String[] {"-d1", "-d2", "-a"});
        // @formatter:on
        // All three options were supplied on the command line.
        assertTrue(cl.hasOption(opt1.getOpt()));
        assertTrue(cl.hasOption(opt2.getOpt()));
        assertTrue(cl.hasOption(opt3.getOpt()));
        // Only the deprecated options (opt1, opt2) should have reached the handler.
        assertTrue(handler.contains(opt1));
        assertTrue(handler.contains(opt2));
        assertFalse(handler.contains(opt3));
    }

    @Test
    void testLegacyStopAtNonOption() throws ParseException {
        final Options options = new Options();
        options.addOption(letterOption("a", "first-letter"));
        options.addOption(letterOption("b", "second-letter"));
        options.addOption(letterOption("c", "third-letter"));

        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"}; // -d is rogue option

        final DefaultParser parser = new DefaultParser();

        // stopAtNonOption == true: parsing stops at the unknown -d, which plus the
        // trailing positional arguments end up in the argument list.
        final CommandLine commandLine = parser.parse(options, args, null, true);
        assertEquals(3, commandLine.getOptions().length);
        assertEquals(3, commandLine.getArgs().length);
        assertTrue(commandLine.getArgList().contains("-d"));
        assertTrue(commandLine.getArgList().contains("arg1"));
        assertTrue(commandLine.getArgList().contains("arg2"));

        // stopAtNonOption == false: the unknown -d triggers an exception instead.
        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class, () -> parser.parse(options, args, null, false));
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
        // baseOptions knows only a and b; specificOptions knows all four (a-d).
        final Options baseOptions = new Options();
        baseOptions.addOption(letterOption("a", "first-letter"));
        baseOptions.addOption(letterOption("b", "second-letter"));
        final Options specificOptions = new Options();
        specificOptions.addOption(letterOption("a", "first-letter"));
        specificOptions.addOption(letterOption("b", "second-letter"));
        specificOptions.addOption(letterOption("c", "third-letter"));
        specificOptions.addOption(letterOption("d", "fourth-letter"));

        final String[] args = STANDARD_ARGS;

        final DefaultParser parser = new DefaultParser();

        // IGNORE: unrecognized options (-c, -d) are silently dropped, not added to the args.
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

        // THROW: with all options recognized, parsing succeeds and only positionals remain.
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

    @Test
    void testParseIgnoreNonHappyPath() throws ParseException {
        // Neither option set knows -d, so it is a rogue option.
        final Options baseOptions = new Options();
        baseOptions.addOption(letterOption("a", "first-letter"));
        baseOptions.addOption(letterOption("b", "second-letter"));
        final Options specificOptions = new Options();
        specificOptions.addOption(letterOption("a", "first-letter"));
        specificOptions.addOption(letterOption("b", "second-letter"));
        specificOptions.addOption(letterOption("c", "third-letter"));

        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"}; // -d is rogue option

        final DefaultParser parser = new DefaultParser();

        // IGNORE: unknown options are dropped, leaving only the two positional args.
        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.IGNORE, args);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(2, baseCommandLine.getArgs().length);

        // THROW: the still-unknown -d makes parsing fail.
        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args));
        assertTrue(e.getMessage().contains("-d"));
    }

    @Test
    void testParseNullOption() throws ParseException {
        // Edge case: a null Options argument is rejected before any parsing happens.
        assertThrows(NullPointerException.class, () -> new DefaultParser().parse(null, null, DefaultParser.NonOptionAction.IGNORE, "-a"));
    }

    @Test
    void testParseSkipHappyPath() throws ParseException {
        // baseOptions knows only a and b; specificOptions knows all four (a-d).
        final Options baseOptions = new Options();
        baseOptions.addOption(letterOption("a", "first-letter"));
        baseOptions.addOption(letterOption("b", "second-letter"));
        final Options specificOptions = new Options();
        specificOptions.addOption(letterOption("a", "first-letter"));
        specificOptions.addOption(letterOption("b", "second-letter"));
        specificOptions.addOption(letterOption("c", "third-letter"));
        specificOptions.addOption(letterOption("d", "fourth-letter"));

        final String[] args = STANDARD_ARGS;

        final DefaultParser parser = new DefaultParser();

        // SKIP: unrecognized options (-c, -d) are kept verbatim in the argument list.
        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.SKIP, args);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(4, baseCommandLine.getArgs().length);
        assertTrue(baseCommandLine.hasOption("a"));
        assertTrue(baseCommandLine.hasOption("b"));
        assertFalse(baseCommandLine.hasOption("c"));
        assertFalse(baseCommandLine.hasOption("d"));
        assertFalse(baseCommandLine.getArgList().contains("-a"));
        assertFalse(baseCommandLine.getArgList().contains("-b"));
        assertTrue(baseCommandLine.getArgList().contains("-c"));
        assertTrue(baseCommandLine.getArgList().contains("-d"));
        assertTrue(baseCommandLine.getArgList().contains("arg1"));
        assertTrue(baseCommandLine.getArgList().contains("arg2"));

        // THROW: with all options recognized, parsing succeeds and only positionals remain.
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

    @Test
    void testParseSkipNonHappyPath() throws ParseException {
        // Neither option set knows -d, so it is a rogue option.
        final Options baseOptions = new Options();
        baseOptions.addOption(letterOption("a", "first-letter"));
        baseOptions.addOption(letterOption("b", "second-letter"));
        final Options specificOptions = new Options();
        specificOptions.addOption(letterOption("a", "first-letter"));
        specificOptions.addOption(letterOption("b", "second-letter"));
        specificOptions.addOption(letterOption("c", "third-letter"));

        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"}; // -d is rogue option

        final DefaultParser parser = new DefaultParser();

        // SKIP: the unknown options are retained, so all four non-option tokens become args.
        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.SKIP, args);
        assertEquals(2, baseCommandLine.getOptions().length);
        assertEquals(4, baseCommandLine.getArgs().length);

        // THROW: the still-unknown -d makes parsing fail.
        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args));
        assertTrue(e.getMessage().contains("-d"));
    }

    @Override
    @Test
    @Disabled("Test case handled in the parameterized tests as \"DEFAULT behavior\"")
    void testShortOptionConcatenatedQuoteHandling() throws Exception {
    }
}
