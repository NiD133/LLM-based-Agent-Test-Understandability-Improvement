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

    /** Unquoted form of the value used in quote-handling parameterized tests. */
    private static final String BARE_VALUE = "quoted string";

    /** Quoted form of the value used in quote-handling parameterized tests. */
    private static final String QUOTED_VALUE = "\"quoted string\"";

    /**
     * Convenience factory for a flag option with a single-character short name and a long name.
     * Used to reduce boilerplate in tests that build option sets.
     */
    private static Option buildLetterOption(final String opt, final String longOpt) {
        return Option.builder().option(opt).longOpt(longOpt).get();
    }

    /**
     * Provides parameterized arguments for {@link #testParameterized}, covering quote-stripping
     * behavior across four syntactic forms (long-space, long-equals, short-space, short-concat)
     * under three parser configurations (default, strip=false, strip=true).
     *
     * <p>Each {@link Arguments} tuple contains, in order:</p>
     * <ol>
     *   <li>test case name (used as the parameterized test display name)</li>
     *   <li>configured {@link DefaultParser} instance</li>
     *   <li>command-line tokens to parse</li>
     *   <li>expected option value after parsing</li>
     *   <li>option name whose value is checked</li>
     *   <li>assertion failure message</li>
     * </ol>
     */
    static class ExternalArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    // --- Default parser (stripLeadingAndTrailingQuotes not explicitly set) ---
                    // Space-separated value: parser strips outer quotes
                    Arguments.of(
                            "Long option quote handling DEFAULT behavior",
                            DefaultParser.builder().get(),
                            new String[]{"--bfile", QUOTED_VALUE},
                            BARE_VALUE,
                            "b",
                            "Confirm --bfile=\"arg\" strips quotes"
                    ),
                    // Equals-separated value: parser preserves outer quotes
                    Arguments.of(
                            "Long option with equals quote handling DEFAULT behavior",
                            DefaultParser.builder().get(),
                            new String[]{"--bfile=" + BARE_VALUE},
                            QUOTED_VALUE,
                            "b",
                            "Confirm --bfile=\"arg\" keeps quotes"
                    ),
                    // Space-separated short option: parser strips outer quotes
                    Arguments.of(
                            "Short option quote handling DEFAULT behavior",
                            DefaultParser.builder().get(),
                            new String[]{"-b", QUOTED_VALUE},
                            BARE_VALUE,
                            "b",
                            "Confirm -b\"arg\" strips quotes"
                    ),
                    // Concatenated short option: parser preserves outer quotes
                    Arguments.of(
                            "Short option concatenated quote handling DEFAULT behavior",
                            DefaultParser.builder().get(),
                            new String[]{"-b" + BARE_VALUE},
                            QUOTED_VALUE,
                            "b",
                            "Confirm -b\"arg\" keeps quotes"
                    ),

                    // --- Parser with stripLeadingAndTrailingQuotes=false (never strip) ---
                    Arguments.of(
                            "Long option quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[]{"--bfile", QUOTED_VALUE},
                            QUOTED_VALUE,
                            "b",
                            "Confirm --bfile \"arg\" keeps quotes"
                    ),
                    Arguments.of(
                            "Long option with equals quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[]{"--bfile=" + BARE_VALUE},
                            QUOTED_VALUE,
                            "b",
                            "Confirm --bfile=\"arg\" keeps quotes"
                    ),
                    Arguments.of(
                            "Short option quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[]{"-b", QUOTED_VALUE},
                            QUOTED_VALUE,
                            "b",
                            "Confirm -b\"arg\" keeps quotes"
                    ),
                    Arguments.of(
                            "Short option concatenated quote handling WITHOUT strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).get(),
                            new String[]{"-b" + BARE_VALUE},
                            QUOTED_VALUE,
                            "b",
                            "Confirm -b\"arg\" keeps quotes"
                    ),

                    // --- Parser with stripLeadingAndTrailingQuotes=true (always strip) ---
                    Arguments.of(
                            "Long option quote handling WITH strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[]{"--bfile", QUOTED_VALUE},
                            BARE_VALUE,
                            "b",
                            "Confirm --bfile \"arg\" strips quotes"
                    ),
                    Arguments.of(
                            "Long option With Equals Quote Handling WITH Strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[]{"--bfile=" + BARE_VALUE},
                            BARE_VALUE,
                            "b",
                            "Confirm --bfile=\"arg\" strips quotes"
                    ),
                    Arguments.of(
                            "Short option quote handling WITH strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[]{"-b", QUOTED_VALUE},
                            BARE_VALUE,
                            "b",
                            "Confirm -b \"arg\" strips quotes"
                    ),
                    Arguments.of(
                            "Short option concatenated quote handling WITH strip",
                            DefaultParser.builder().setStripLeadingAndTrailingQuotes(true).get(),
                            new String[]{"-b" + BARE_VALUE},
                            BARE_VALUE,
                            "b",
                            "Confirm -b\"arg\" strips quotes"
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

    /** Verifies that both {@code build()} and {@code get()} on the fluent Builder produce a DefaultParser. */
    @Test
    void testBuilder() {
        // @formatter:off
        final Builder builder = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(false)
                .setAllowPartialMatching(false)
                .setDeprecatedHandler(null);
        // @formatter:on
        parser = builder.build();
        assertEquals(DefaultParser.class, parser.getClass(), "build() should return a DefaultParser instance");
        parser = builder.get();
        assertEquals(DefaultParser.class, parser.getClass(), "get() should return a DefaultParser instance");
    }

    /**
     * Verifies that a custom deprecatedHandler is invoked for each deprecated option encountered
     * during parsing, and is NOT invoked for non-deprecated options.
     */
    @Test
    void testDeprecated() throws ParseException {
        final Set<Option> deprecatedOptionsEncountered = new HashSet<>();
        parser = DefaultParser.builder().setDeprecatedHandler(deprecatedOptionsEncountered::add).build();

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

        // All three options should have been parsed successfully
        assertTrue(cl.hasOption(opt1.getOpt()), "opt1 (deprecated) should be present on the command line");
        assertTrue(cl.hasOption(opt2.getOpt()), "opt2 (deprecated) should be present on the command line");
        assertTrue(cl.hasOption(opt3.getOpt()), "opt3 (non-deprecated) should be present on the command line");

        // The handler should have been notified about deprecated options only
        assertTrue(deprecatedOptionsEncountered.contains(opt1), "handler should have been called for deprecated opt1");
        assertTrue(deprecatedOptionsEncountered.contains(opt2), "handler should have been called for deprecated opt2");
        assertFalse(deprecatedOptionsEncountered.contains(opt3), "handler should NOT have been called for non-deprecated opt3");
    }

    /**
     * Verifies the legacy {@code stopAtNonOption} boolean API:
     * <ul>
     *   <li>{@code stopAtNonOption=true} — parsing stops at the first unrecognized token;
     *       remaining tokens (including the rogue option) are collected as plain arguments.</li>
     *   <li>{@code stopAtNonOption=false} — an unrecognized option causes {@link UnrecognizedOptionException}.</li>
     * </ul>
     */
    @Test
    void testLegacyStopAtNonOption() throws ParseException {
        final Option a = buildLetterOption("a", "first-letter");
        final Option b = buildLetterOption("b", "second-letter");
        final Option c = buildLetterOption("c", "third-letter");

        final Options options = new Options();
        options.addOption(a);
        options.addOption(b);
        options.addOption(c);

        // -d is an unrecognized ("rogue") option that is not in the Options set
        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"};

        final DefaultParser parser = new DefaultParser();

        // stopAtNonOption=true: parsing halts at -d; -d, arg1, arg2 become plain args
        final CommandLine commandLine = parser.parse(options, args, null, true);
        assertEquals(3, commandLine.getOptions().length, "Only recognized options a, b, c should be parsed");
        assertEquals(3, commandLine.getArgs().length, "Remaining tokens (-d, arg1, arg2) should be collected as args");
        assertTrue(commandLine.getArgList().contains("-d"), "-d should be in args list when stopAtNonOption=true");
        assertTrue(commandLine.getArgList().contains("arg1"), "arg1 should be in args list");
        assertTrue(commandLine.getArgList().contains("arg2"), "arg2 should be in args list");

        // stopAtNonOption=false: unrecognized -d triggers an exception
        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(options, args, null, false));
        assertTrue(e.getMessage().contains("-d"), "Exception message should identify the unrecognized option");
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

    /**
     * Verifies {@link DefaultParser.NonOptionAction#IGNORE} happy path: unrecognized options are
     * silently dropped (not added to args), while positional arguments are collected normally.
     *
     * <p>Also verifies that {@link DefaultParser.NonOptionAction#THROW} on a fully specified
     * option set parses all recognized options and collects positional args.</p>
     */
    @Test
    void testParseIgnoreHappyPath() throws ParseException {
        final Option a = buildLetterOption("a", "first-letter");
        final Option b = buildLetterOption("b", "second-letter");
        final Option c = buildLetterOption("c", "third-letter");
        final Option d = buildLetterOption("d", "fourth-letter");

        // baseOptions knows only -a and -b; -c and -d are unrecognized
        final Options baseOptions = new Options();
        baseOptions.addOption(a);
        baseOptions.addOption(b);

        // specificOptions knows all four options
        final Options specificOptions = new Options();
        specificOptions.addOption(a);
        specificOptions.addOption(b);
        specificOptions.addOption(c);
        specificOptions.addOption(d);

        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"};

        final DefaultParser parser = new DefaultParser();

        // IGNORE: unrecognized -c and -d are silently dropped; only known options and plain args remain
        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.IGNORE, args);
        assertEquals(2, baseCommandLine.getOptions().length, "Only -a and -b are recognized in baseOptions");
        assertEquals(2, baseCommandLine.getArgs().length, "arg1 and arg2 are the only positional args");
        assertTrue(baseCommandLine.hasOption("a"), "-a should be present");
        assertTrue(baseCommandLine.hasOption("b"), "-b should be present");
        assertFalse(baseCommandLine.hasOption("c"), "-c is unrecognized and should be absent");
        assertFalse(baseCommandLine.hasOption("d"), "-d is unrecognized and should be absent");
        // Unrecognized option tokens are dropped entirely, not added to the arg list
        assertFalse(baseCommandLine.getArgList().contains("-a"), "-a is a recognized option, not an arg");
        assertFalse(baseCommandLine.getArgList().contains("-b"), "-b is a recognized option, not an arg");
        assertFalse(baseCommandLine.getArgList().contains("-c"), "-c should be ignored, not added to args");
        assertFalse(baseCommandLine.getArgList().contains("-d"), "-d should be ignored, not added to args");
        assertTrue(baseCommandLine.getArgList().contains("arg1"), "arg1 should be a positional arg");
        assertTrue(baseCommandLine.getArgList().contains("arg2"), "arg2 should be a positional arg");

        // THROW on a complete option set: all four options parsed; only positional args collected
        final CommandLine specificCommandLine = parser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args);
        assertEquals(4, specificCommandLine.getOptions().length, "All four options should be recognized");
        assertEquals(2, specificCommandLine.getArgs().length, "arg1 and arg2 are the only positional args");
        assertTrue(specificCommandLine.hasOption("a"), "-a should be present");
        assertTrue(specificCommandLine.hasOption("b"), "-b should be present");
        assertTrue(specificCommandLine.hasOption("c"), "-c should be present");
        assertTrue(specificCommandLine.hasOption("d"), "-d should be present");
        assertFalse(specificCommandLine.getArgList().contains("-a"), "-a is a recognized option, not an arg");
        assertFalse(specificCommandLine.getArgList().contains("-b"), "-b is a recognized option, not an arg");
        assertFalse(specificCommandLine.getArgList().contains("-c"), "-c is a recognized option, not an arg");
        assertFalse(specificCommandLine.getArgList().contains("-d"), "-d is a recognized option, not an arg");
        assertTrue(specificCommandLine.getArgList().contains("arg1"), "arg1 should be a positional arg");
        assertTrue(specificCommandLine.getArgList().contains("arg2"), "arg2 should be a positional arg");
    }

    /**
     * Verifies that {@link DefaultParser.NonOptionAction#IGNORE} silently tolerates unrecognized
     * options while {@link DefaultParser.NonOptionAction#THROW} raises {@link UnrecognizedOptionException}
     * for an option set that does not include a token present in the args.
     */
    @Test
    void testParseIgnoreNonHappyPath() throws ParseException {
        final Option a = buildLetterOption("a", "first-letter");
        final Option b = buildLetterOption("b", "second-letter");
        final Option c = buildLetterOption("c", "third-letter");

        final Options baseOptions = new Options();
        baseOptions.addOption(a);
        baseOptions.addOption(b);

        // specificOptions knows a, b, c — but not -d which appears in args
        final Options specificOptions = new Options();
        specificOptions.addOption(a);
        specificOptions.addOption(b);
        specificOptions.addOption(c);

        // -d is a rogue option: unrecognized by both option sets
        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"};

        final DefaultParser parser = new DefaultParser();

        // IGNORE: even though -d is unrecognized, parsing succeeds; -d is dropped silently
        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.IGNORE, args);
        assertEquals(2, baseCommandLine.getOptions().length, "Only -a and -b should be recognized");
        assertEquals(2, baseCommandLine.getArgs().length, "arg1 and arg2 are the only positional args");

        // THROW on specificOptions: -d is still unrecognized → exception
        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args));
        assertTrue(e.getMessage().contains("-d"), "Exception message should identify the unrecognized option -d");
    }

    /** Verifies that passing {@code null} as the Options argument throws {@link NullPointerException}. */
    @Test
    void testParseNullOption() throws ParseException {
        assertThrows(NullPointerException.class,
                () -> new DefaultParser().parse(null, null, DefaultParser.NonOptionAction.IGNORE, "-a"),
                "parse() with null Options should throw NullPointerException");
    }

    /**
     * Verifies {@link DefaultParser.NonOptionAction#SKIP} happy path: parsing continues past
     * unrecognized options, but those tokens are added to the arg list (unlike IGNORE which drops them).
     *
     * <p>Also verifies that {@link DefaultParser.NonOptionAction#THROW} on a complete option set
     * recognizes all options and collects only positional args.</p>
     */
    @Test
    void testParseSkipHappyPath() throws ParseException {
        final Option a = buildLetterOption("a", "first-letter");
        final Option b = buildLetterOption("b", "second-letter");
        final Option c = buildLetterOption("c", "third-letter");
        final Option d = buildLetterOption("d", "fourth-letter");

        // baseOptions knows only -a and -b; -c and -d are unrecognized
        final Options baseOptions = new Options();
        baseOptions.addOption(a);
        baseOptions.addOption(b);

        // specificOptions knows all four options
        final Options specificOptions = new Options();
        specificOptions.addOption(a);
        specificOptions.addOption(b);
        specificOptions.addOption(c);
        specificOptions.addOption(d);

        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"};

        final DefaultParser parser = new DefaultParser();

        // SKIP: unrecognized -c and -d are kept in the arg list (unlike IGNORE which drops them)
        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.SKIP, args);
        assertEquals(2, baseCommandLine.getOptions().length, "Only -a and -b are recognized in baseOptions");
        assertEquals(4, baseCommandLine.getArgs().length, "-c, -d, arg1, arg2 all end up as args with SKIP");
        assertTrue(baseCommandLine.hasOption("a"), "-a should be present");
        assertTrue(baseCommandLine.hasOption("b"), "-b should be present");
        assertFalse(baseCommandLine.hasOption("c"), "-c is unrecognized and should be absent");
        assertFalse(baseCommandLine.hasOption("d"), "-d is unrecognized and should be absent");
        assertFalse(baseCommandLine.getArgList().contains("-a"), "-a is a recognized option, not an arg");
        assertFalse(baseCommandLine.getArgList().contains("-b"), "-b is a recognized option, not an arg");
        // Key difference from IGNORE: skipped option tokens ARE kept in the arg list
        assertTrue(baseCommandLine.getArgList().contains("-c"), "-c should be added to args with SKIP");
        assertTrue(baseCommandLine.getArgList().contains("-d"), "-d should be added to args with SKIP");
        assertTrue(baseCommandLine.getArgList().contains("arg1"), "arg1 should be a positional arg");
        assertTrue(baseCommandLine.getArgList().contains("arg2"), "arg2 should be a positional arg");

        // THROW on a complete option set: all four options parsed; only positional args collected
        final CommandLine specificCommandLine = parser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args);
        assertEquals(4, specificCommandLine.getOptions().length, "All four options should be recognized");
        assertEquals(2, specificCommandLine.getArgs().length, "arg1 and arg2 are the only positional args");
        assertTrue(specificCommandLine.hasOption("a"), "-a should be present");
        assertTrue(specificCommandLine.hasOption("b"), "-b should be present");
        assertTrue(specificCommandLine.hasOption("c"), "-c should be present");
        assertTrue(specificCommandLine.hasOption("d"), "-d should be present");
        assertFalse(specificCommandLine.getArgList().contains("-a"), "-a is a recognized option, not an arg");
        assertFalse(specificCommandLine.getArgList().contains("-b"), "-b is a recognized option, not an arg");
        assertFalse(specificCommandLine.getArgList().contains("-c"), "-c is a recognized option, not an arg");
        assertFalse(specificCommandLine.getArgList().contains("-d"), "-d is a recognized option, not an arg");
        assertTrue(specificCommandLine.getArgList().contains("arg1"), "arg1 should be a positional arg");
        assertTrue(specificCommandLine.getArgList().contains("arg2"), "arg2 should be a positional arg");
    }

    /**
     * Verifies that {@link DefaultParser.NonOptionAction#SKIP} adds unrecognized option tokens to
     * args and continues, while {@link DefaultParser.NonOptionAction#THROW} raises
     * {@link UnrecognizedOptionException} for a truly unrecognized option.
     */
    @Test
    void testParseSkipNonHappyPath() throws ParseException {
        final Option a = buildLetterOption("a", "first-letter");
        final Option b = buildLetterOption("b", "second-letter");
        final Option c = buildLetterOption("c", "third-letter");

        final Options baseOptions = new Options();
        baseOptions.addOption(a);
        baseOptions.addOption(b);

        // specificOptions knows a, b, c — but not -d which appears in args
        final Options specificOptions = new Options();
        specificOptions.addOption(a);
        specificOptions.addOption(b);
        specificOptions.addOption(c);

        // -d is a rogue option: unrecognized by both option sets
        final String[] args = {"-a", "-b", "-c", "-d", "arg1", "arg2"};

        final DefaultParser parser = new DefaultParser();

        // SKIP: -d is unrecognized in baseOptions; it is added to the arg list and parsing continues
        final CommandLine baseCommandLine = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.SKIP, args);
        assertEquals(2, baseCommandLine.getOptions().length, "Only -a and -b should be recognized");
        assertEquals(4, baseCommandLine.getArgs().length, "-c, -d, arg1, arg2 should all be in args with SKIP");

        // THROW on specificOptions: -d is still unrecognized → exception
        final UnrecognizedOptionException e = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args));
        assertTrue(e.getMessage().contains("-d"), "Exception message should identify the unrecognized option -d");
    }

    @Override
    @Test
    @Disabled("Test case handled in the parameterized tests as \"DEFAULT behavior\"")
    void testShortOptionConcatenatedQuoteHandling() throws Exception {
    }
}
