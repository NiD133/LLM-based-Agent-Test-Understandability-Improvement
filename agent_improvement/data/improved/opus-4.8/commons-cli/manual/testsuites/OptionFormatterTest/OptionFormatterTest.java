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
package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.cli.DeprecatedAttributes;
import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link OptionFormatter}.
 */
class OptionFormatterTest {

    /**
     * Provides {@link DeprecatedAttributes} combinations together with the message that
     * {@link OptionFormatter#COMPLEX_DEPRECATED_FORMAT} is expected to produce for them.
     * <p>
     * Each case builds its attributes independently so the inputs for a given expected
     * message can be read in isolation.
     * </p>
     */
    public static Stream<Arguments> deprecatedAttributesData() {
        return Stream.of(
                Arguments.of(
                        DeprecatedAttributes.builder().get(),
                        "[Deprecated]"),
                Arguments.of(
                        DeprecatedAttributes.builder().setSince("now").get(),
                        "[Deprecated since now]"),
                Arguments.of(
                        DeprecatedAttributes.builder().setSince("now").setForRemoval(true).get(),
                        "[Deprecated for removal since now]"),
                Arguments.of(
                        DeprecatedAttributes.builder().setForRemoval(true).get(),
                        "[Deprecated for removal]"),
                Arguments.of(
                        DeprecatedAttributes.builder().setDescription("Use something else").get(),
                        "[Deprecated. Use something else]"),
                Arguments.of(
                        DeprecatedAttributes.builder().setForRemoval(true).setDescription("Use something else").get(),
                        "[Deprecated for removal. Use something else]"),
                Arguments.of(
                        DeprecatedAttributes.builder().setSince("then").setDescription("Use something else").get(),
                        "[Deprecated since then. Use something else]"),
                Arguments.of(
                        DeprecatedAttributes.builder().setSince("then").setForRemoval(true).setDescription("Use something else").get(),
                        "[Deprecated for removal since then. Use something else]"));
    }

    /**
     * Asserts that two formatters render every Option attribute identically. Used to verify
     * that a formatter copied via the {@link OptionFormatter.Builder} copy constructor behaves
     * exactly like the original.
     */
    private void assertEquivalent(final OptionFormatter formatter, final OptionFormatter formatter2) {
        assertEquals(formatter.toSyntaxOption(), formatter2.toSyntaxOption());
        assertEquals(formatter.toSyntaxOption(true), formatter2.toSyntaxOption(true));
        assertEquals(formatter.toSyntaxOption(false), formatter2.toSyntaxOption(false));
        assertEquals(formatter.getOpt(), formatter2.getOpt());
        assertEquals(formatter.getLongOpt(), formatter2.getLongOpt());
        assertEquals(formatter.getBothOpt(), formatter2.getBothOpt());
        assertEquals(formatter.getDescription(), formatter2.getDescription());
        assertEquals(formatter.getArgName(), formatter2.getArgName());
        assertEquals(formatter.toOptional("foo"), formatter2.toOptional("foo"));
    }

    @Test
    void testAsOptional() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Default delimiters wrap the text in square brackets.
        final OptionFormatter defaultFormatter = OptionFormatter.from(option);
        assertEquals("[what]", defaultFormatter.toOptional("what"));
        assertEquals("", defaultFormatter.toOptional(""), "empty string should return empty string");
        assertEquals("", defaultFormatter.toOptional(null), "null should return empty string");

        // Custom delimiters wrap the text with the configured prefix and suffix.
        final OptionFormatter customFormatter = OptionFormatter.builder().setOptionalDelimiters("-> ", " <-").build(option);
        assertEquals("-> what <-", customFormatter.toOptional("what"));
    }

    @Test
    void testAsSyntaxOption() {
        assertSyntaxOption(Option.builder().option("o").longOpt("opt").hasArg().get(),
                "[-o <arg>]", "optional arg failed");

        assertSyntaxOption(Option.builder().option("o").longOpt("opt").hasArg().argName("other").get(),
                "[-o <other>]", "optional 'other' arg failed");

        assertSyntaxOption(Option.builder().option("o").longOpt("opt").hasArg().required().argName("other").get(),
                "-o <other>", "required 'other' arg failed");

        assertSyntaxOption(Option.builder().option("o").required().argName("other").get(),
                "-o", "required no arg failed");

        assertSyntaxOption(Option.builder().option("o").argName("other").get(),
                "[-o]", "optional no arg arg failed");

        assertSyntaxOption(Option.builder().longOpt("opt").hasArg().argName("other").get(),
                "[--opt <other>]", "optional longOpt 'other' arg failed");

        assertSyntaxOption(Option.builder().longOpt("opt").required().hasArg().argName("other").get(),
                "--opt <other>", "required longOpt 'other' arg failed");

        assertSyntaxOption(Option.builder().option("ot").longOpt("opt").hasArg().get(),
                "[-ot <arg>]", "optional multi char opt arg failed");
    }

    /** Asserts the default syntax rendering of an option. */
    private void assertSyntaxOption(final Option option, final String expected, final String message) {
        assertEquals(expected, OptionFormatter.from(option).toSyntaxOption(), message);
    }

    @Test
    void testCli343Part1() {
        // An Option requires at least an opt or longOpt: building one with only required() fails.
        assertThrows(IllegalStateException.class, () -> Option.builder().required(false).build());
        assertThrows(IllegalStateException.class, () -> Option.builder().required(false).get());
    }

    @Test
    void testCli343Part2() {
        // An Option requires at least an opt or longOpt: building one with only a description fails.
        assertThrows(IllegalStateException.class, () -> Option.builder().desc("description").build());
        assertThrows(IllegalStateException.class, () -> Option.builder().desc("description").get());
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("deprecatedAttributesData")
    void testComplexDeprecationFormat(final DeprecatedAttributes da, final String expected) {
        final Option optionWithoutDesc = Option.builder("o").deprecated(da).get();
        final Option optionWithDesc = Option.builder("o").desc("The description").deprecated(da).get();

        assertEquals(expected, OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(optionWithoutDesc));
        // When the option has a description it is appended after the deprecation message.
        assertEquals(expected + " The description", OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(optionWithDesc));
    }

    @Test
    void testCopyConstructor() {
        final Function<Option, String> depFunc = o -> "Ooo Deprecated";
        final BiFunction<OptionFormatter, Boolean, String> fmtFunc = (o, b) -> "Yep, it worked";
        // @formatter:off
        final OptionFormatter.Builder builder = OptionFormatter.builder()
                .setLongOptPrefix("l")
                .setOptPrefix("s")
                .setArgumentNameDelimiters("{", "}")
                .setDefaultArgName("Some Argument")
                .setOptSeparator(" and ")
                .setOptionalDelimiters("?>", "<?")
                .setSyntaxFormatFunction(fmtFunc)
                .setDeprecatedFormatFunction(depFunc);
        // @formatter:on

        // A formatter copied through the copy constructor must behave like the original.
        Option option = Option.builder("o").longOpt("opt").get();
        OptionFormatter formatter = builder.build(option);
        OptionFormatter copiedFormatter = new OptionFormatter.Builder(formatter).build(option);
        assertEquivalent(formatter, copiedFormatter);

        // Repeat with a deprecated, required option to exercise the deprecated/required paths.
        option = Option.builder("o").longOpt("opt").deprecated().required().get();
        formatter = builder.build(option);
        copiedFormatter = new OptionFormatter.Builder(formatter).build(option);
        assertEquivalent(formatter, copiedFormatter);
    }

    @Test
    void testDefaultSyntaxFormat() {
        // Optional option: brackets are added unless required rendering is explicitly requested.
        Option option = Option.builder().option("o").longOpt("opt").hasArg().get();
        OptionFormatter formatter = OptionFormatter.from(option);
        assertEquals("[-o <arg>]", formatter.toSyntaxOption());
        assertEquals("-o <arg>", formatter.toSyntaxOption(true));

        // Required option: brackets are omitted unless optional rendering is explicitly requested.
        option = Option.builder().option("o").longOpt("opt").hasArg().required().get();
        formatter = OptionFormatter.from(option);
        assertEquals("-o <arg>", formatter.toSyntaxOption());
        assertEquals("[-o <arg>]", formatter.toSyntaxOption(false));
    }

    @Test
    void testGetBothOpt() {
        // Both opt and longOpt present: joined by the default separator.
        Option option = Option.builder().option("o").longOpt("opt").hasArg().get();
        assertEquals("-o, --opt", OptionFormatter.from(option).getBothOpt());

        // Only longOpt present.
        option = Option.builder().longOpt("opt").hasArg().get();
        assertEquals("--opt", OptionFormatter.from(option).getBothOpt());

        // Only opt present.
        option = Option.builder().option("o").hasArg().get();
        assertEquals("-o", OptionFormatter.from(option).getBothOpt());
    }

    @Test
    void testGetDescription() {
        final Option normalOption = Option.builder().option("o").longOpt("one").hasArg().desc("The description").get();

        final Option deprecatedOption = Option.builder().option("o").longOpt("one").hasArg().desc("The description").deprecated().get();

        final Option deprecatedOptionWithAttributes = Option.builder().option("o").longOpt("one").hasArg().desc("The description")
                .deprecated(DeprecatedAttributes.builder().setForRemoval(true).setSince("now").setDescription("Use something else").get()).get();

        // Default formatter: the raw description is returned regardless of deprecation.
        assertEquals("The description", OptionFormatter.from(normalOption).getDescription(), "normal option failure");
        assertEquals("The description", OptionFormatter.from(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals("The description", OptionFormatter.from(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");

        // SIMPLE format: deprecated options are prefixed with "[Deprecated]" without further detail.
        OptionFormatter.Builder builder = OptionFormatter.builder().setDeprecatedFormatFunction(OptionFormatter.SIMPLE_DEPRECATED_FORMAT);
        assertEquals("The description", builder.build(normalOption).getDescription(), "normal option failure");
        assertEquals("[Deprecated] The description", builder.build(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals("[Deprecated] The description", builder.build(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");

        // COMPLEX format: deprecated options include all deprecation attributes.
        builder = OptionFormatter.builder().setDeprecatedFormatFunction(OptionFormatter.COMPLEX_DEPRECATED_FORMAT);
        assertEquals("The description", builder.build(normalOption).getDescription(), "normal option failure");
        assertEquals("[Deprecated] The description", builder.build(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals("[Deprecated for removal since now. Use something else] The description", builder.build(deprecatedOptionWithAttributes).getDescription(),
                "complex deprecated option failure");
    }

    @Test
    void testSetArgumentNameDelimiters() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Custom delimiters wrap the default argument name.
        OptionFormatter.Builder builder = OptionFormatter.builder().setArgumentNameDelimiters("with argument named ", ".");
        assertEquals("with argument named arg.", builder.build(option).getArgName());

        // A null begin delimiter falls back to an empty delimiter.
        builder = OptionFormatter.builder().setArgumentNameDelimiters(null, "");
        assertEquals("arg", builder.build(option).getArgName());

        // A null end delimiter falls back to an empty delimiter.
        builder = OptionFormatter.builder().setArgumentNameDelimiters("", null);
        assertEquals("arg", builder.build(option).getArgName());
    }

    @Test
    void testSetDefaultArgName() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // A custom default argument name is used.
        OptionFormatter.Builder builder = OptionFormatter.builder().setDefaultArgName("foo");
        assertEquals("<foo>", builder.build(option).getArgName());

        // Empty and null default argument names fall back to the built-in default "arg".
        builder = OptionFormatter.builder().setDefaultArgName("");
        assertEquals("<arg>", builder.build(option).getArgName());

        builder = OptionFormatter.builder().setDefaultArgName(null);
        assertEquals("<arg>", builder.build(option).getArgName());
    }

    @Test
    void testSetLongOptPrefix() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // A custom prefix is prepended to the long option.
        OptionFormatter.Builder builder = OptionFormatter.builder().setLongOptPrefix("fo");
        assertEquals("foopt", builder.build(option).getLongOpt());

        // Empty and null prefixes leave the long option unprefixed.
        builder = OptionFormatter.builder().setLongOptPrefix("");
        assertEquals("opt", builder.build(option).getLongOpt());

        builder = OptionFormatter.builder().setLongOptPrefix(null);
        assertEquals("opt", builder.build(option).getLongOpt());
    }

    @Test
    void testSetOptArgumentSeparator() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // A custom separator is placed between the option and its argument name.
        OptionFormatter.Builder builder = OptionFormatter.builder().setOptArgSeparator(" with argument named ");
        assertEquals("[-o with argument named <arg>]", builder.build(option).toSyntaxOption());

        // A null separator results in no separator.
        builder = OptionFormatter.builder().setOptArgSeparator(null);
        assertEquals("[-o<arg>]", builder.build(option).toSyntaxOption());

        // An "=" separator.
        builder = OptionFormatter.builder().setOptArgSeparator("=");
        assertEquals("[-o=<arg>]", builder.build(option).toSyntaxOption());
    }

    @Test
    void testSetOptSeparator() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // A custom separator is placed between the short and long options.
        OptionFormatter.Builder builder = OptionFormatter.builder().setOptSeparator(" and ");
        assertEquals("-o and --opt", builder.build(option).getBothOpt());

        // Empty and null separators leave the options directly concatenated.
        builder = OptionFormatter.builder().setOptSeparator("");
        assertEquals("-o--opt", builder.build(option).getBothOpt(), "Empty string should return default");

        builder = OptionFormatter.builder().setOptSeparator(null);
        assertEquals("-o--opt", builder.build(option).getBothOpt(), "null string should return default");
    }

    @Test
    void testSetSyntaxFormatFunction() {
        final BiFunction<OptionFormatter, Boolean, String> func = (o, b) -> "Yep, it worked";
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // A custom syntax function overrides the default rendering.
        OptionFormatter.Builder builder = OptionFormatter.builder().setSyntaxFormatFunction(func);
        assertEquals("Yep, it worked", builder.build(option).toSyntaxOption());

        // A null syntax function restores the default rendering.
        builder = OptionFormatter.builder().setSyntaxFormatFunction(null);
        assertEquals("[-o <arg>]", builder.build(option).toSyntaxOption());
    }
}
