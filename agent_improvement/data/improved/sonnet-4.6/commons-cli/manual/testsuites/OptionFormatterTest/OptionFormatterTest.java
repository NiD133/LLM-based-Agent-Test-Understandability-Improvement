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
     * Provides test cases for each combination of {@link DeprecatedAttributes} fields,
     * paired with the expected formatted string. Each entry is independent so that the
     * relationship between attributes and expected output is immediately visible.
     */
    public static Stream<Arguments> deprecatedAttributesData() {
        return Stream.of(
            // No extra attributes → plain "[Deprecated]"
            Arguments.of(
                DeprecatedAttributes.builder().get(),
                "[Deprecated]"),
            // since only
            Arguments.of(
                DeprecatedAttributes.builder().setSince("now").get(),
                "[Deprecated since now]"),
            // since + forRemoval
            Arguments.of(
                DeprecatedAttributes.builder().setSince("now").setForRemoval(true).get(),
                "[Deprecated for removal since now]"),
            // forRemoval only
            Arguments.of(
                DeprecatedAttributes.builder().setForRemoval(true).get(),
                "[Deprecated for removal]"),
            // description only
            Arguments.of(
                DeprecatedAttributes.builder().setDescription("Use something else").get(),
                "[Deprecated. Use something else]"),
            // forRemoval + description
            Arguments.of(
                DeprecatedAttributes.builder().setForRemoval(true).setDescription("Use something else").get(),
                "[Deprecated for removal. Use something else]"),
            // since + description
            Arguments.of(
                DeprecatedAttributes.builder().setSince("then").setDescription("Use something else").get(),
                "[Deprecated since then. Use something else]"),
            // since + forRemoval + description (all fields set)
            Arguments.of(
                DeprecatedAttributes.builder().setSince("then").setForRemoval(true).setDescription("Use something else").get(),
                "[Deprecated for removal since then. Use something else]")
        );
    }

    /**
     * Asserts that two {@link OptionFormatter} instances produce identical output for every
     * public formatting method, confirming that {@code formatter2} is an equivalent copy of
     * {@code formatter}.
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

        final OptionFormatter defaultFormatter = OptionFormatter.from(option);
        assertEquals("[what]", defaultFormatter.toOptional("what"));
        assertEquals("", defaultFormatter.toOptional(""), "empty string should return empty string");
        assertEquals("", defaultFormatter.toOptional(null), "null should return empty string");

        final OptionFormatter customDelimiterFormatter =
            OptionFormatter.builder().setOptionalDelimiters("-> ", " <-").build(option);
        assertEquals("-> what <-", customDelimiterFormatter.toOptional("what"));
    }

    @Test
    void testAsSyntaxOption() {
        // Optional short+long opt with default arg name
        Option option = Option.builder().option("o").longOpt("opt").hasArg().get();
        assertEquals("[-o <arg>]", OptionFormatter.from(option).toSyntaxOption(), "optional arg failed");

        // Optional short+long opt with custom arg name
        option = Option.builder().option("o").longOpt("opt").hasArg().argName("other").get();
        assertEquals("[-o <other>]", OptionFormatter.from(option).toSyntaxOption(), "optional 'other' arg failed");

        // Required short+long opt with custom arg name
        option = Option.builder().option("o").longOpt("opt").hasArg().required().argName("other").get();
        assertEquals("-o <other>", OptionFormatter.from(option).toSyntaxOption(), "required 'other' arg failed");

        // Required short opt with no arg
        option = Option.builder().option("o").longOpt("opt").required().argName("other").get();
        assertEquals("-o", OptionFormatter.from(option).toSyntaxOption(), "required no arg failed");

        // Optional short opt with no arg (short opt only, no arg configured)
        option = Option.builder().option("o").argName("other").get();
        assertEquals("[-o]", OptionFormatter.from(option).toSyntaxOption(), "optional no arg failed");

        // Optional long opt only with custom arg name
        option = Option.builder().longOpt("opt").hasArg().argName("other").get();
        assertEquals("[--opt <other>]", OptionFormatter.from(option).toSyntaxOption(), "optional longOpt 'other' arg failed");

        // Required long opt only with custom arg name
        option = Option.builder().longOpt("opt").required().hasArg().argName("other").get();
        assertEquals("--opt <other>", OptionFormatter.from(option).toSyntaxOption(), "required longOpt 'other' arg failed");

        // Optional multi-character short opt with default arg name
        option = Option.builder().option("ot").longOpt("opt").hasArg().get();
        assertEquals("[-ot <arg>]", OptionFormatter.from(option).toSyntaxOption(), "optional multi char opt arg failed");
    }

    /**
     * CLI-343: {@link Option.Builder#build()} must throw when no option name or long option name
     * has been set — even when other builder attributes such as {@code required} are configured.
     */
    @Test
    void testOptionBuilderThrowsWithoutOptionName_requiredFlagOnly() {
        assertThrows(IllegalStateException.class, () -> Option.builder().required(false).build());
        assertThrows(IllegalStateException.class, () -> Option.builder().required(false).get());
    }

    /**
     * CLI-343: {@link Option.Builder#build()} must throw when no option name or long option name
     * has been set — even when other builder attributes such as {@code desc} are configured.
     */
    @Test
    void testOptionBuilderThrowsWithoutOptionName_descriptionOnly() {
        assertThrows(IllegalStateException.class, () -> Option.builder().desc("description").build());
        assertThrows(IllegalStateException.class, () -> Option.builder().desc("description").get());
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("deprecatedAttributesData")
    void testComplexDeprecationFormat(final DeprecatedAttributes da, final String expected) {
        final Option.Builder builder = Option.builder("o").deprecated(da);
        final Option.Builder builderWithDesc = Option.builder("o").desc("The description").deprecated(da);

        assertEquals(expected, OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(builder.get()));
        assertEquals(expected + " The description", OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(builderWithDesc.get()));
    }

    @Test
    void testCopyConstructor() {
        final Function<Option, String> depFunc = o -> "Ooo Deprecated";
        final BiFunction<OptionFormatter, Boolean, String> fmtFunc = (o, b) -> "Yep, it worked";
        // @formatter:off
        final OptionFormatter.Builder originalBuilder = OptionFormatter.builder()
                .setLongOptPrefix("l")
                .setOptPrefix("s")
                .setArgumentNameDelimiters("{", "}")
                .setDefaultArgName("Some Argument")
                .setOptSeparator(" and ")
                .setOptionalDelimiters("?>", "<?")
                .setSyntaxFormatFunction(fmtFunc)
                .setDeprecatedFormatFunction(depFunc);
        // @formatter:on

        // Verify copy of a non-deprecated, non-required option
        Option option = Option.builder("o").longOpt("opt").get();
        OptionFormatter formatter = originalBuilder.build(option);
        OptionFormatter.Builder copiedBuilder = new OptionFormatter.Builder(formatter);
        assertEquivalent(formatter, copiedBuilder.build(option));

        // Verify copy of a deprecated + required option
        option = Option.builder("o").longOpt("opt").deprecated().required().get();
        formatter = originalBuilder.build(option);
        copiedBuilder = new OptionFormatter.Builder(formatter);
        assertEquivalent(formatter, copiedBuilder.build(option));
    }

    @Test
    void testDefaultSyntaxFormat() {
        // Optional option: toSyntaxOption() wraps in brackets; passing true forces required format
        Option option = Option.builder().option("o").longOpt("opt").hasArg().get();
        OptionFormatter formatter = OptionFormatter.from(option);
        assertEquals("[-o <arg>]", formatter.toSyntaxOption(), "optional by default");
        assertEquals("-o <arg>", formatter.toSyntaxOption(true), "forced required");

        // Required option: toSyntaxOption() omits brackets; passing false forces optional format
        option = Option.builder().option("o").longOpt("opt").hasArg().required().get();
        formatter = OptionFormatter.from(option);
        assertEquals("-o <arg>", formatter.toSyntaxOption(), "required by default");
        assertEquals("[-o <arg>]", formatter.toSyntaxOption(false), "forced optional");
    }

    @Test
    void testGetBothOpt() {
        // Short and long opt present: both are shown with separator
        Option option = Option.builder().option("o").longOpt("opt").hasArg().get();
        assertEquals("-o, --opt", OptionFormatter.from(option).getBothOpt());

        // Long opt only
        option = Option.builder().longOpt("opt").hasArg().get();
        assertEquals("--opt", OptionFormatter.from(option).getBothOpt());

        // Short opt only
        option = Option.builder().option("o").hasArg().get();
        assertEquals("-o", OptionFormatter.from(option).getBothOpt());
    }

    @Test
    void testGetDescription() {
        final Option normalOption = Option.builder().option("o").longOpt("one").hasArg().desc("The description").get();
        final Option deprecatedOption = Option.builder().option("o").longOpt("one").hasArg().desc("The description").deprecated().get();
        final Option deprecatedOptionWithAttributes = Option.builder().option("o").longOpt("one").hasArg().desc("The description")
                .deprecated(DeprecatedAttributes.builder().setForRemoval(true).setSince("now").setDescription("Use something else").get()).get();

        // Default formatter suppresses deprecation notice — only the raw description is returned
        assertEquals("The description", OptionFormatter.from(normalOption).getDescription(), "normal option failure");
        assertEquals("The description", OptionFormatter.from(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals("The description", OptionFormatter.from(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");

        // SIMPLE_DEPRECATED_FORMAT prepends "[Deprecated]" for any deprecated option
        OptionFormatter.Builder simpleDeprecatedBuilder =
            OptionFormatter.builder().setDeprecatedFormatFunction(OptionFormatter.SIMPLE_DEPRECATED_FORMAT);
        assertEquals("The description", simpleDeprecatedBuilder.build(normalOption).getDescription(), "normal option failure");
        assertEquals("[Deprecated] The description", simpleDeprecatedBuilder.build(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals("[Deprecated] The description", simpleDeprecatedBuilder.build(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");

        // COMPLEX_DEPRECATED_FORMAT includes all deprecation attributes in the prefix
        OptionFormatter.Builder complexDeprecatedBuilder =
            OptionFormatter.builder().setDeprecatedFormatFunction(OptionFormatter.COMPLEX_DEPRECATED_FORMAT);
        assertEquals("The description", complexDeprecatedBuilder.build(normalOption).getDescription(), "normal option failure");
        assertEquals("[Deprecated] The description", complexDeprecatedBuilder.build(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals("[Deprecated for removal since now. Use something else] The description",
                complexDeprecatedBuilder.build(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");
    }

    @Test
    void testSetArgumentNameDelimiters() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Custom delimiters wrap the arg name
        OptionFormatter.Builder builder = OptionFormatter.builder().setArgumentNameDelimiters("with argument named ", ".");
        assertEquals("with argument named arg.", builder.build(option).getArgName());

        // Null begin delimiter is treated as empty string
        builder = OptionFormatter.builder().setArgumentNameDelimiters(null, "");
        assertEquals("arg", builder.build(option).getArgName());

        // Null end delimiter is treated as empty string
        builder = OptionFormatter.builder().setArgumentNameDelimiters("", null);
        assertEquals("arg", builder.build(option).getArgName());
    }

    @Test
    void testSetDefaultArgName() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Custom default arg name is used when option does not specify its own arg name
        OptionFormatter.Builder builder = OptionFormatter.builder().setDefaultArgName("foo");
        assertEquals("<foo>", builder.build(option).getArgName());

        // Empty default arg name falls back to the built-in default ("arg")
        builder = OptionFormatter.builder().setDefaultArgName("");
        assertEquals("<arg>", builder.build(option).getArgName());

        // Null default arg name falls back to the built-in default ("arg")
        builder = OptionFormatter.builder().setDefaultArgName(null);
        assertEquals("<arg>", builder.build(option).getArgName());
    }

    @Test
    void testSetLongOptPrefix() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Custom prefix is prepended to the long option name
        OptionFormatter.Builder builder = OptionFormatter.builder().setLongOptPrefix("fo");
        assertEquals("foopt", builder.build(option).getLongOpt());

        // Empty prefix results in no prefix before the long option name
        builder = OptionFormatter.builder().setLongOptPrefix("");
        assertEquals("opt", builder.build(option).getLongOpt());

        // Null prefix is treated as empty string — no prefix before the long option name
        builder = OptionFormatter.builder().setLongOptPrefix(null);
        assertEquals("opt", builder.build(option).getLongOpt());
    }

    @Test
    void testSetOptArgumentSeparator() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Custom separator is placed between the option name and the argument name
        OptionFormatter.Builder builder = OptionFormatter.builder().setOptArgSeparator(" with argument named ");
        assertEquals("[-o with argument named <arg>]", builder.build(option).toSyntaxOption());

        // Null separator is treated as empty string — option and arg name are concatenated directly
        builder = OptionFormatter.builder().setOptArgSeparator(null);
        assertEquals("[-o<arg>]", builder.build(option).toSyntaxOption());

        // Single-character separator (e.g. "=")
        builder = OptionFormatter.builder().setOptArgSeparator("=");
        assertEquals("[-o=<arg>]", builder.build(option).toSyntaxOption());
    }

    @Test
    void testSetOptSeparator() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Custom separator is placed between the short and long option names
        OptionFormatter.Builder builder = OptionFormatter.builder().setOptSeparator(" and ");
        assertEquals("-o and --opt", builder.build(option).getBothOpt());

        // Empty separator causes short and long opts to be concatenated with no separator
        builder = OptionFormatter.builder().setOptSeparator("");
        assertEquals("-o--opt", builder.build(option).getBothOpt(), "empty separator results in no separator between opts");

        // Null separator is treated as empty string — same result as empty separator
        builder = OptionFormatter.builder().setOptSeparator(null);
        assertEquals("-o--opt", builder.build(option).getBothOpt(), "null separator results in no separator between opts");
    }

    @Test
    void testSetSyntaxFormatFunction() {
        final BiFunction<OptionFormatter, Boolean, String> customFunc = (o, b) -> "Yep, it worked";
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Custom syntax function overrides the default formatting logic
        OptionFormatter.Builder builder = OptionFormatter.builder().setSyntaxFormatFunction(customFunc);
        assertEquals("Yep, it worked", builder.build(option).toSyntaxOption());

        // Null syntax function restores the default formatting logic
        builder = OptionFormatter.builder().setSyntaxFormatFunction(null);
        assertEquals("[-o <arg>]", builder.build(option).toSyntaxOption());
    }
}
