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

import java.util.ArrayList;
import java.util.List;
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

    private static final String SHORT_OPTION = "o";
    private static final String LONG_OPTION = "opt";
    private static final String DESCRIPTION = "The description";

    public static Stream<Arguments> deprecatedAttributesData() {
        final List<Arguments> cases = new ArrayList<>();

        final DeprecatedAttributes.Builder deprecatedAttributes = DeprecatedAttributes.builder();
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated]"));

        deprecatedAttributes.setSince("now");
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated since now]"));

        deprecatedAttributes.setForRemoval(true);
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated for removal since now]"));

        deprecatedAttributes.setSince(null);
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated for removal]"));

        deprecatedAttributes.setForRemoval(false);
        deprecatedAttributes.setDescription("Use something else");
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated. Use something else]"));

        deprecatedAttributes.setForRemoval(true);
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated for removal. Use something else]"));

        deprecatedAttributes.setForRemoval(false);
        deprecatedAttributes.setSince("then");
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated since then. Use something else]"));

        deprecatedAttributes.setForRemoval(true);
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated for removal since then. Use something else]"));

        return cases.stream();
    }

    private Option optionWithShortLongAndArgument() {
        return Option.builder().option(SHORT_OPTION).longOpt(LONG_OPTION).hasArg().get();
    }

    private Option describedOption() {
        return Option.builder().option(SHORT_OPTION).longOpt("one").hasArg().desc(DESCRIPTION).get();
    }

    private void assertEquivalent(final OptionFormatter formatter, final OptionFormatter copiedFormatter) {
        assertEquals(formatter.toSyntaxOption(), copiedFormatter.toSyntaxOption());
        assertEquals(formatter.toSyntaxOption(true), copiedFormatter.toSyntaxOption(true));
        assertEquals(formatter.toSyntaxOption(false), copiedFormatter.toSyntaxOption(false));
        assertEquals(formatter.getOpt(), copiedFormatter.getOpt());
        assertEquals(formatter.getLongOpt(), copiedFormatter.getLongOpt());
        assertEquals(formatter.getBothOpt(), copiedFormatter.getBothOpt());
        assertEquals(formatter.getDescription(), copiedFormatter.getDescription());
        assertEquals(formatter.getArgName(), copiedFormatter.getArgName());
        assertEquals(formatter.toOptional("foo"), copiedFormatter.toOptional("foo"));
    }

    @Test
    void testAsOptional() {
        final Option option = optionWithShortLongAndArgument();

        OptionFormatter formatter = OptionFormatter.from(option);
        assertEquals("[what]", formatter.toOptional("what"));
        assertEquals("", formatter.toOptional(""), "empty string should return empty string");
        assertEquals("", formatter.toOptional(null), "null should return empty string");

        formatter = OptionFormatter.builder().setOptionalDelimiters("-> ", " <-").build(option);
        assertEquals("-> what <-", formatter.toOptional("what"));
    }

    @Test
    void testAsSyntaxOption() {
        Option option = optionWithShortLongAndArgument();
        OptionFormatter formatter = OptionFormatter.from(option);
        assertEquals("[-o <arg>]", formatter.toSyntaxOption(), "optional arg failed");

        option = Option.builder().option(SHORT_OPTION).longOpt(LONG_OPTION).hasArg().argName("other").get();
        formatter = OptionFormatter.from(option);
        assertEquals("[-o <other>]", formatter.toSyntaxOption(), "optional 'other' arg failed");

        option = Option.builder().option(SHORT_OPTION).longOpt(LONG_OPTION).hasArg().required().argName("other").get();
        formatter = OptionFormatter.from(option);
        assertEquals("-o <other>", formatter.toSyntaxOption(), "required 'other' arg failed");

        option = Option.builder().option(SHORT_OPTION).longOpt(LONG_OPTION).required().argName("other").get();
        formatter = OptionFormatter.from(option);
        assertEquals("-o", formatter.toSyntaxOption(), "required no arg failed");

        option = Option.builder().option(SHORT_OPTION).argName("other").get();
        formatter = OptionFormatter.from(option);
        assertEquals("[-o]", formatter.toSyntaxOption(), "optional no arg arg failed");

        option = Option.builder().longOpt(LONG_OPTION).hasArg().argName("other").get();
        formatter = OptionFormatter.from(option);
        assertEquals("[--opt <other>]", formatter.toSyntaxOption(), "optional longOpt 'other' arg failed");

        option = Option.builder().longOpt(LONG_OPTION).required().hasArg().argName("other").get();
        formatter = OptionFormatter.from(option);
        assertEquals("--opt <other>", formatter.toSyntaxOption(), "required longOpt 'other' arg failed");

        option = Option.builder().option("ot").longOpt(LONG_OPTION).hasArg().get();
        formatter = OptionFormatter.from(option);
        assertEquals("[-ot <arg>]", formatter.toSyntaxOption(), "optional multi char opt arg failed");
    }

    @Test
    void testCli343Part1() {
        assertThrows(IllegalStateException.class, () -> Option.builder().required(false).build());
        assertThrows(IllegalStateException.class, () -> Option.builder().required(false).get());
    }

    @Test
    void testCli343Part2() {
        assertThrows(IllegalStateException.class, () -> Option.builder().desc("description").build());
        assertThrows(IllegalStateException.class, () -> Option.builder().desc("description").get());
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("deprecatedAttributesData")
    void testComplexDeprecationFormat(final DeprecatedAttributes deprecatedAttributes, final String expected) {
        final Option.Builder builder = Option.builder(SHORT_OPTION).deprecated(deprecatedAttributes);
        final Option.Builder builderWithDescription = Option.builder(SHORT_OPTION).desc(DESCRIPTION).deprecated(deprecatedAttributes);

        assertEquals(expected, OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(builder.get()));
        assertEquals(expected + " " + DESCRIPTION, OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(builderWithDescription.get()));
    }

    @Test
    void testCopyConstructor() {
        final Function<Option, String> deprecatedFormat = option -> "Ooo Deprecated";
        final BiFunction<OptionFormatter, Boolean, String> syntaxFormat = (formatter, required) -> "Yep, it worked";
        // @formatter:off
        final OptionFormatter.Builder builder = OptionFormatter.builder()
                .setLongOptPrefix("l")
                .setOptPrefix("s")
                .setArgumentNameDelimiters("{", "}")
                .setDefaultArgName("Some Argument")
                .setOptSeparator(" and ")
                .setOptionalDelimiters("?>", "<?")
                .setSyntaxFormatFunction(syntaxFormat)
                .setDeprecatedFormatFunction(deprecatedFormat);
        // @formatter:on

        Option option = Option.builder(SHORT_OPTION).longOpt(LONG_OPTION).get();

        OptionFormatter formatter = builder.build(option);
        OptionFormatter.Builder copiedBuilder = new OptionFormatter.Builder(formatter);
        assertEquivalent(formatter, copiedBuilder.build(option));

        option = Option.builder(SHORT_OPTION).longOpt(LONG_OPTION).deprecated().required().get();
        formatter = builder.build(option);
        copiedBuilder = new OptionFormatter.Builder(formatter);
        assertEquivalent(formatter, copiedBuilder.build(option));
    }

    @Test
    void testDefaultSyntaxFormat() {
        Option option = optionWithShortLongAndArgument();
        OptionFormatter formatter = OptionFormatter.from(option);
        assertEquals("[-o <arg>]", formatter.toSyntaxOption());
        assertEquals("-o <arg>", formatter.toSyntaxOption(true));

        option = Option.builder().option(SHORT_OPTION).longOpt(LONG_OPTION).hasArg().required().get();
        formatter = OptionFormatter.from(option);
        assertEquals("-o <arg>", formatter.toSyntaxOption());
        assertEquals("[-o <arg>]", formatter.toSyntaxOption(false));
    }

    @Test
    void testGetBothOpt() {
        Option option = optionWithShortLongAndArgument();
        OptionFormatter formatter = OptionFormatter.from(option);
        assertEquals("-o, --opt", formatter.getBothOpt());

        option = Option.builder().longOpt(LONG_OPTION).hasArg().get();
        formatter = OptionFormatter.from(option);
        assertEquals("--opt", formatter.getBothOpt());

        option = Option.builder().option(SHORT_OPTION).hasArg().get();
        formatter = OptionFormatter.from(option);
        assertEquals("-o", formatter.getBothOpt());
    }

    @Test
    void testGetDescription() {
        final Option normalOption = describedOption();
        final Option deprecatedOption = Option.builder().option(SHORT_OPTION).longOpt("one").hasArg().desc(DESCRIPTION).deprecated().get();
        final DeprecatedAttributes deprecatedAttributes = DeprecatedAttributes.builder()
                .setForRemoval(true)
                .setSince("now")
                .setDescription("Use something else")
                .get();
        final Option deprecatedOptionWithAttributes = Option.builder()
                .option(SHORT_OPTION)
                .longOpt("one")
                .hasArg()
                .desc(DESCRIPTION)
                .deprecated(deprecatedAttributes)
                .get();

        assertEquals(DESCRIPTION, OptionFormatter.from(normalOption).getDescription(), "normal option failure");
        assertEquals(DESCRIPTION, OptionFormatter.from(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals(DESCRIPTION, OptionFormatter.from(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");

        OptionFormatter.Builder builder = OptionFormatter.builder().setDeprecatedFormatFunction(OptionFormatter.SIMPLE_DEPRECATED_FORMAT);

        assertEquals(DESCRIPTION, builder.build(normalOption).getDescription(), "normal option failure");
        assertEquals("[Deprecated] " + DESCRIPTION, builder.build(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals("[Deprecated] " + DESCRIPTION, builder.build(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");

        builder = OptionFormatter.builder().setDeprecatedFormatFunction(OptionFormatter.COMPLEX_DEPRECATED_FORMAT);

        assertEquals(DESCRIPTION, builder.build(normalOption).getDescription(), "normal option failure");
        assertEquals("[Deprecated] " + DESCRIPTION, builder.build(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals("[Deprecated for removal since now. Use something else] " + DESCRIPTION,
                builder.build(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");
    }

    @Test
    void testSetArgumentNameDelimiters() {
        final Option option = optionWithShortLongAndArgument();
        OptionFormatter.Builder builder = OptionFormatter.builder().setArgumentNameDelimiters("with argument named ", ".");
        assertEquals("with argument named arg.", builder.build(option).getArgName());

        builder = OptionFormatter.builder().setArgumentNameDelimiters(null, "");
        assertEquals("arg", builder.build(option).getArgName());

        builder = OptionFormatter.builder().setArgumentNameDelimiters("", null);
        assertEquals("arg", builder.build(option).getArgName());
    }

    @Test
    void testSetDefaultArgName() {
        final Option option = optionWithShortLongAndArgument();
        OptionFormatter.Builder builder = OptionFormatter.builder().setDefaultArgName("foo");
        assertEquals("<foo>", builder.build(option).getArgName());

        builder = OptionFormatter.builder().setDefaultArgName("");
        assertEquals("<arg>", builder.build(option).getArgName());

        builder = OptionFormatter.builder().setDefaultArgName(null);
        assertEquals("<arg>", builder.build(option).getArgName());
    }

    @Test
    void testSetLongOptPrefix() {
        final Option option = optionWithShortLongAndArgument();
        OptionFormatter.Builder builder = OptionFormatter.builder().setLongOptPrefix("fo");
        assertEquals("foopt", builder.build(option).getLongOpt());

        builder = OptionFormatter.builder().setLongOptPrefix("");
        assertEquals("opt", builder.build(option).getLongOpt());

        builder = OptionFormatter.builder().setLongOptPrefix(null);
        assertEquals("opt", builder.build(option).getLongOpt());
    }

    @Test
    void testSetOptArgumentSeparator() {
        final Option option = optionWithShortLongAndArgument();
        OptionFormatter.Builder builder = OptionFormatter.builder().setOptArgSeparator(" with argument named ");
        assertEquals("[-o with argument named <arg>]", builder.build(option).toSyntaxOption());

        builder = OptionFormatter.builder().setOptArgSeparator(null);
        assertEquals("[-o<arg>]", builder.build(option).toSyntaxOption());

        builder = OptionFormatter.builder().setOptArgSeparator("=");
        assertEquals("[-o=<arg>]", builder.build(option).toSyntaxOption());
    }

    @Test
    void testSetOptSeparator() {
        final Option option = optionWithShortLongAndArgument();
        OptionFormatter.Builder builder = OptionFormatter.builder().setOptSeparator(" and ");
        assertEquals("-o and --opt", builder.build(option).getBothOpt());

        builder = OptionFormatter.builder().setOptSeparator("");
        assertEquals("-o--opt", builder.build(option).getBothOpt(), "Empty string should return default");

        builder = OptionFormatter.builder().setOptSeparator(null);
        assertEquals("-o--opt", builder.build(option).getBothOpt(), "null string should return default");
    }

    @Test
    void testSetSyntaxFormatFunction() {
        final BiFunction<OptionFormatter, Boolean, String> syntaxFormat = (formatter, required) -> "Yep, it worked";
        final Option option = optionWithShortLongAndArgument();

        OptionFormatter.Builder builder = OptionFormatter.builder().setSyntaxFormatFunction(syntaxFormat);
        assertEquals("Yep, it worked", builder.build(option).toSyntaxOption());

        builder = OptionFormatter.builder().setSyntaxFormatFunction(null);
        assertEquals("[-o <arg>]", builder.build(option).toSyntaxOption());
    }
}
