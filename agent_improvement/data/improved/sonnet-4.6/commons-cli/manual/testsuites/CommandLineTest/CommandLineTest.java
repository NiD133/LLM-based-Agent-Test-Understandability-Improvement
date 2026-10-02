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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CommandLineTest {

    private enum Count { ONE, TWO, THREE }

    /**
     * Builds test arguments for {@code hasOption} parameterized tests.
     * <p>
     * Each row contains: (args, opt, optionGroup, optDep, has, grpDep, hasGrp, grpOpt)
     * where {@code optDep}/{@code grpDep} indicate whether the deprecation handler should fire
     * when querying via {@code opt}/{@code optionGroup} respectively.
     */
    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final List<Arguments> arguments = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        // Querying optT (the deprecated option): opt parameter = optT
        // -T/--tee in args: T is present — has=true, deprecation fires for both opt and group
        arguments.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, true, true, true, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo"}, optT, optionGroup, true, true, true, true, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, true, true, true, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo"}, optT, optionGroup, true, true, true, true, optT));

        // -U/--you in args: T is absent — has=false, no deprecation for optT; group selected U (non-deprecated)
        arguments.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, false, false, true, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optT, optionGroup, false, false, false, true, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, false, false, true, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo", "bar"}, optT, optionGroup, false, false, false, true, optU));

        // Querying optU (the non-deprecated option): opt parameter = optU
        // -T/--tee in args: U is absent — has=false, no deprecation for optU; group selected T (deprecated)
        arguments.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, false, true, true, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo", "bar"}, optU, optionGroup, false, false, true, true, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, false, true, true, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo", "bar"}, optU, optionGroup, false, false, true, true, optT));

        // -U/--you in args: U is present — has=true, no deprecation (U is not deprecated); group selected U
        arguments.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, true, false, true, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optU, optionGroup, false, true, false, true, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, true, false, true, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo", "bar"},  optU, optionGroup, false, true, false, true, optU));

        return arguments.stream();
    }

    /**
     * Builds test arguments for {@code getOptionValue} parameterized tests.
     * <p>
     * Each row contains: (args, opt, optionGroup, optDep, optValue, grpDep, grpValue, grpOpt)
     * where {@code optDep}/{@code grpDep} indicate whether the deprecation handler should fire
     * and {@code optValue}/{@code grpValue} are the expected string values.
     */
    private static Stream<Arguments> createOptionValueParameters() throws ParseException {
        final List<Arguments> arguments = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        // Querying optT (the deprecated option): opt parameter = optT
        // -T/--tee in args: T is present — deprecation fires, value comes from T
        arguments.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo"}, optT, optionGroup, true, "foo", true, "foo", optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo"}, optT, optionGroup, true, "foo", true, "foo", optT));

        // -U/--you in args: T is absent — no deprecation for optT, value from U belongs to group only
        arguments.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo"}, optT, optionGroup, false, null, false, "foo", optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo"}, optT, optionGroup, false, null, false, "foo", optU));

        // Querying optU (the non-deprecated option): opt parameter = optU
        // -T/--tee in args: U is absent — no deprecation for optU; group selected T (deprecated)
        arguments.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo"}, optU, optionGroup, false, null, true, "foo", optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo"}, optU, optionGroup, false, null, true, "foo", optT));

        // -U/--you in args: U is present — value comes from U, no deprecation (U is not deprecated)
        arguments.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo"}, optU, optionGroup, false, "foo", false, "foo", optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo"},  optU, optionGroup, false, "foo", false, "foo", optU));

        return arguments.stream();
    }

    /**
     * Builds test arguments for {@code getOptionValues} parameterized tests.
     * <p>
     * Each row contains: (args, opt, optionGroup, optDep, optValue, grpDep, grpValue, grpOpt)
     * where values are String arrays (options accept two arguments each).
     */
    private static Stream<Arguments> createOptionValuesParameters() throws ParseException {
        final List<Arguments> arguments = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").numberOfArgs(2).deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").numberOfArgs(2).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        final String[] foobar = { "foo", "bar" };

        // Querying optT (the deprecated option): opt parameter = optT
        // -T/--tee in args: T is present — deprecation fires, values come from T
        arguments.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo", "bar"}, optT, optionGroup, true, foobar, true, foobar, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo", "bar"}, optT, optionGroup, true, foobar, true, foobar, optT));

        // -U/--you in args: T is absent — no deprecation for optT, values from U belong to group only
        arguments.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optT, optionGroup, false, null, false, foobar, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo", "bar"}, optT, optionGroup, false, null, false, foobar, optU));

        // Querying optU (the non-deprecated option): opt parameter = optU
        // -T/--tee in args: U is absent — no deprecation for optU; group selected T (deprecated)
        arguments.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo", "bar"}, optU, optionGroup, false, null, true, foobar, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo", "bar"}, optU, optionGroup, false, null, true, foobar, optT));

        // -U/--you in args: U is present — values come from U, no deprecation (U is not deprecated)
        arguments.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optU, optionGroup, false, foobar, false, foobar, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo", "bar"},  optU, optionGroup, false, foobar, false, foobar, optU));

        return arguments.stream();
    }

    /**
     * Builds test arguments for {@code getParsedOptionValue} parameterized tests.
     * <p>
     * Each row contains: (args, opt, optionGroup, optDep, optValue, grpDep, grpValue, grpOpt)
     * where values are parsed {@code Integer} objects (options have type {@code Integer.class}).
     */
    private static Stream<Arguments> createParsedOptionValueParameters() throws ParseException {
        final List<Arguments> arguments = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer expected = Integer.valueOf(1);

        // Querying optT (the deprecated option): opt parameter = optT
        // -T/--tee in args: T is present — deprecation fires, parsed value comes from T
        arguments.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "1"}, optT, optionGroup, true, expected, true, expected, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "1"}, optT, optionGroup, true, expected, true, expected, optT));

        // -U/--you in args: T is absent — no deprecation for optT, parsed value from U belongs to group only
        arguments.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "1"}, optT, optionGroup, false, null, false, expected, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "1"}, optT, optionGroup, false, null, false, expected, optU));

        // Querying optU (the non-deprecated option): opt parameter = optU
        // -T/--tee in args: U is absent — no deprecation for optU; group selected T (deprecated)
        arguments.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "1"}, optU, optionGroup, false, null, true, expected, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "1"}, optU, optionGroup, false, null, true, expected, optT));

        // -U/--you in args: U is present — parsed value comes from U, no deprecation (U is not deprecated)
        arguments.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "1"}, optU, optionGroup, false, expected, false, expected, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "1"},  optU, optionGroup, false, expected, false, expected, optU));

        return arguments.stream();
    }

    /**
     * Builds test arguments for {@code getParsedOptionValues} parameterized tests.
     * <p>
     * Each row contains: (args, opt, optionGroup, optDep, optValue, grpDep, grpValue, grpOpt)
     * where values are parsed {@code Integer} arrays (options accept multiple args with type {@code Integer.class}).
     */
    private static Stream<Arguments> createParsedOptionValuesParameters() throws ParseException {
        final List<Arguments> arguments = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).hasArgs().get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).hasArgs().get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer[] expected = {1, 2};

        // Querying optT (the deprecated option): opt parameter = optT
        // -T/--tee in args: T is present — deprecation fires, parsed values come from T
        arguments.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "1", "2"}, optT, optionGroup, true, expected, true, expected, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "1", "2"}, optT, optionGroup, true, expected, true, expected, optT));

        // -U/--you in args: T is absent — no deprecation for optT, parsed values from U belong to group only
        arguments.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "1", "2"}, optT, optionGroup, false, null, false, expected, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "1", "2"}, optT, optionGroup, false, null, false, expected, optU));

        // Querying optU (the non-deprecated option): opt parameter = optU
        // -T/--tee in args: U is absent — no deprecation for optU; group selected T (deprecated)
        arguments.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "1", "2"}, optU, optionGroup, false, null, true, expected, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "1", "2"}, optU, optionGroup, false, null, true, expected, optT));

        // -U/--you in args: U is present — parsed values come from U, no deprecation (U is not deprecated)
        arguments.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "1", "2"}, optU, optionGroup, false, expected, false, expected, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "1", "2"},  optU, optionGroup, false, expected, false, expected, optU));

        return arguments.stream();
    }

    /** Returns the first character of the option's short name, for use with char-based API overloads. */
    char toOptionChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Asserts that the deprecation notice for option T was printed to stdout exactly once (or not at all),
     * then clears the captured output so the next assertion starts from a clean state.
     *
     * @param shouldHavePrinted {@code true} if the deprecation message is expected in the output.
     * @param capturedOutput    the byte stream capturing stdout.
     */
    private void assertDeprecationPrinted(final boolean shouldHavePrinted, final ByteArrayOutputStream capturedOutput) {
        System.out.flush();
        if (shouldHavePrinted) {
            assertEquals("Option 'T''tee': Deprecated", capturedOutput.toString().trim());
        } else {
            assertEquals("", capturedOutput.toString());
        }
        capturedOutput.reset();
    }

    /**
     * Asserts that the deprecation handler was triggered exactly once with the expected option (or not at all),
     * then clears the log so the next assertion starts from a clean state.
     *
     * @param shouldHaveFired {@code true} if the handler should have been called once.
     * @param deprecationLog  the list that collects options passed to the deprecation handler.
     * @param expectedOption  the option expected to have triggered the handler; ignored when {@code shouldHaveFired} is {@code false}.
     */
    void assertDeprecationHandlerInvoked(final boolean shouldHaveFired, final List<Option> deprecationLog, final Option expectedOption) {
        if (shouldHaveFired) {
            assertEquals(1, deprecationLog.size());
            assertEquals(expectedOption, deprecationLog.get(0));
        } else {
            assertEquals(0, deprecationLog.size());
        }
        deprecationLog.clear();
    }

    @Test
    void testBadGetParsedOptionValue() throws Exception {

        final Options options = new Options();
        options.addOption(Option.builder("i").hasArg().type(Number.class).get());
        options.addOption(Option.builder("c").hasArg().converter(s -> Count.valueOf(s.toUpperCase())).get());

        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, new String[] {"-i", "foo", "-c", "bar"});

        assertEquals(NumberFormatException.class, assertThrows(ParseException.class, () -> cmd.getParsedOptionValue("i")).getCause().getClass());
        assertEquals(IllegalArgumentException.class, assertThrows(ParseException.class, () -> cmd.getParsedOptionValue("c")).getCause().getClass());
    }

    @Test
    void testBuilderBuild() {
        // @formatter:off
        final CommandLine cmd = CommandLine.builder()
                .addArg("foo")
                .addArg("bar")
                .addOption(Option.builder("T").get())
                .build();
        // @formatter:on
        assertEquals("foo", cmd.getArgs()[0]);
        assertEquals("bar", cmd.getArgList().get(1));
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }

    @Test
    void testBuilderGet() {
        // @formatter:off
        final CommandLine cmd = CommandLine.builder()
                .addArg("foo")
                .addArg("bar")
                .addOption(Option.builder("T").get())
                .get();
        // @formatter:on
        assertEquals("foo", cmd.getArgs()[0]);
        assertEquals("bar", cmd.getArgList().get(1));
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }

    @Test
    void testBuilderNullArgs() {
        final CommandLine.Builder builder = CommandLine.builder();
        builder.addArg(null).addArg(null);
        builder.addOption(Option.builder("T").get());
        final CommandLine cmd = builder.build();

        assertEquals(0, cmd.getArgs().length);
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }

    @Test
    void testBuilderNullOption() {
        final CommandLine.Builder builder = CommandLine.builder();
        builder.addArg("foo").addArg("bar");
        builder.addOption(null);
        builder.addOption(null);
        builder.addOption(null);
        final CommandLine cmd = builder.build();

        assertEquals("foo", cmd.getArgs()[0]);
        assertEquals("bar", cmd.getArgList().get(1));
        assertEquals(0, cmd.getOptions().length);
    }

    @Test
    void testGetOptionProperties() throws Exception {
        final String[] args = {"-Dparam1=value1", "-Dparam2=value2", "-Dparam3", "-Dparam4=value4", "-D", "--property", "foo=bar"};

        final Options options = new Options();
        options.addOption(Option.builder("D").valueSeparator().optionalArg(true).numberOfArgs(2).get());
        options.addOption(Option.builder().valueSeparator().numberOfArgs(2).longOpt("property").get());

        final Parser parser = new GnuParser();
        final CommandLine cl = parser.parse(options, args);

        final Properties props = cl.getOptionProperties("D");
        assertNotNull(props, "null properties");
        assertEquals(4, props.size(), "number of properties in " + props);
        assertEquals("value1", props.getProperty("param1"), "property 1");
        assertEquals("value2", props.getProperty("param2"), "property 2");
        assertEquals("true", props.getProperty("param3"), "property 3");
        assertEquals("value4", props.getProperty("param4"), "property 4");

        assertEquals("bar", cl.getOptionProperties("property").getProperty("foo"), "property with long format");
    }

    @Test
    void testGetOptionPropertiesWithOption() throws Exception {
        final String[] args = {"-Dparam1=value1", "-Dparam2=value2", "-Dparam3", "-Dparam4=value4", "-D", "--property", "foo=bar"};

        final Options options = new Options();
        final Option optionD = Option.builder("D").valueSeparator().numberOfArgs(2).optionalArg(true).get();
        final Option optionProperty = Option.builder().valueSeparator().numberOfArgs(2).longOpt("property").get();
        options.addOption(optionD);
        options.addOption(optionProperty);

        final Parser parser = new GnuParser();
        final CommandLine cl = parser.parse(options, args);

        final Properties props = cl.getOptionProperties(optionD);
        assertNotNull(props, "null properties");
        assertEquals(4, props.size(), "number of properties in " + props);
        assertEquals("value1", props.getProperty("param1"), "property 1");
        assertEquals("value2", props.getProperty("param2"), "property 2");
        assertEquals("true", props.getProperty("param3"), "property 3");
        assertEquals("value4", props.getProperty("param4"), "property 4");

        assertEquals("bar", cl.getOptionProperties(optionProperty).getProperty("foo"), "property with long format");
    }

    @Test
    void testGetOptionsBuilder() {
        final CommandLine cmd = CommandLine.builder().build();
        assertNotNull(cmd.getOptions());
        assertEquals(0, cmd.getOptions().length);

        cmd.addOption(null);
        cmd.addOption(new Option("a", null));
        cmd.addOption(new Option("b", null));
        cmd.addOption(new Option("c", null));

        assertEquals(3, cmd.getOptions().length);
    }

    @Test
    void testGetOptionsCtor() {
        final CommandLine cmd = new CommandLine();
        assertNotNull(cmd.getOptions());
        assertEquals(0, cmd.getOptions().length);

        cmd.addOption(new Option("a", null));
        cmd.addOption(new Option("b", null));
        cmd.addOption(new Option("c", null));
        cmd.addOption(null);

        assertEquals(3, cmd.getOptions().length);
    }

    /**
     * Test for get option value with and without default values.  Verifies that deprecated options only report as
     * deprecated once.
     * @param args the argument strings to parse.
     * @param opt the option to check for values with.
     * @param optionGroup the option group to check for values with.
     * @param optDep {@code true} if the opt is deprecated.
     * @param optValue  The value expected from opt.
     * @param grpDep {@code true} if the group is deprecated.
     * @param grpValue the value expected from the group.
     * @param grpOpt the option that is expected to be processed by the group.
     * @throws ParseException on parse error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testGetOptionValue(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                                   final String optValue, final boolean grpDep, final String grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> deprecationLog = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(deprecationLog::add).get().parse(options, args);
        final Supplier<String> defaultSupplier = () -> "thing";
        final OptionGroup otherGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // test char option
        assertEquals(optValue, commandLine.getOptionValue(toOptionChar(opt)));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(toOptionChar(opt), "thing"));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(toOptionChar(opt), defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test short option arg
        assertEquals(optValue, commandLine.getOptionValue(opt.getOpt()));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getOpt(), "thing"));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getOpt(), defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test long option arg
        assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt()));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getLongOpt(), "thing"));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getLongOpt(), defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test Option arg
        assertEquals(optValue, commandLine.getOptionValue(opt));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt, "thing"));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt, defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test option group  arg
        assertEquals(grpValue, commandLine.getOptionValue(optionGroup));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        assertEquals(grpValue == null ? "thing" : grpValue, commandLine.getOptionValue(optionGroup, "thing"));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        assertEquals(grpValue == null ? "thing" : grpValue, commandLine.getOptionValue(optionGroup, defaultSupplier));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        // test other group arg — none of its options were parsed, so no value and no deprecation
        assertNull(commandLine.getOptionValue(otherGroup));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertEquals("thing", commandLine.getOptionValue(otherGroup, "thing"));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertEquals("thing", commandLine.getOptionValue(otherGroup, defaultSupplier));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        // test null Group arg — null group is always absent, returns null/default without deprecation
        assertNull(commandLine.getOptionValue(nullGroup));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertEquals("thing", commandLine.getOptionValue(nullGroup, "thing"));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertEquals("thing", commandLine.getOptionValue(nullGroup, defaultSupplier));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        // test not an option — unknown option name, returns null/default without deprecation
        assertNull(commandLine.getOptionValue("Nope"));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);

        assertEquals("thing", commandLine.getOptionValue("Nope", "thing"));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);

        assertEquals("thing", commandLine.getOptionValue("Nope", defaultSupplier));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);
    }

    /**
     * Test for get option values with and without default values.  Verifies that deprecated options only report as
     * deprecated once.
     * @param args the argument strings to parse.
     * @param opt the option to check for values with.
     * @param optionGroup the option group to check for values with.
     * @param optDep {@code true} if the opt is deprecated.
     * @param optValue  The value expected from opt.
     * @param grpDep {@code true} if the group is deprecated.
     * @param grpValue the value expected from the group.
     * @param grpOpt the option that is expected to be processed by the group.
     * @throws ParseException on parse error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValuesParameters")
    void testGetOptionValues(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                                    final String[] optValue, final boolean grpDep, final String[] grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> deprecationLog = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(deprecationLog::add).get().parse(options, args);
        final OptionGroup otherGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // test char option arg
        assertArrayEquals(optValue, commandLine.getOptionValues(toOptionChar(opt)));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test short option arg
        assertArrayEquals(optValue, commandLine.getOptionValues(opt.getOpt()));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test long option arg
        assertArrayEquals(optValue, commandLine.getOptionValues(opt.getLongOpt()));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test Option arg
        assertArrayEquals(optValue, commandLine.getOptionValues(opt));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test OptionGroup arg
        assertArrayEquals(grpValue, commandLine.getOptionValues(optionGroup));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        // test not an option
        assertNull(commandLine.getOptionValues("Nope"));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);

        // test other group arg — none of its options were parsed, so no value and no deprecation
        assertNull(commandLine.getOptionValues(otherGroup));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        // test null group arg — null group is always absent, returns null without deprecation
        assertNull(commandLine.getOptionValues(nullGroup));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValueParameters")
    void testGetParsedOptionValue(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                                         final Integer optValue, final boolean grpDep, final Integer grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> deprecationLog = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(deprecationLog::add).get().parse(options, args);
        final Supplier<Integer> defaultSupplier = () -> 2;
        final OptionGroup otherGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;
        final Integer defaultInt = 2;

        // test char option arg
        assertEquals(optValue, commandLine.getParsedOptionValue(toOptionChar(opt)));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? defaultInt : optValue, commandLine.getParsedOptionValue(toOptionChar(opt), defaultInt));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? defaultInt : optValue, commandLine.getParsedOptionValue(toOptionChar(opt), defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test short option arg
        assertEquals(optValue, commandLine.getParsedOptionValue(opt.getOpt()));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? defaultInt : optValue, commandLine.getParsedOptionValue(opt.getOpt(), defaultInt));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? defaultInt : optValue, commandLine.getParsedOptionValue(opt.getOpt(), defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test long option arg
        assertEquals(optValue, commandLine.getParsedOptionValue(opt.getLongOpt()));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? defaultInt : optValue, commandLine.getParsedOptionValue(opt.getLongOpt(), defaultInt));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? defaultInt : optValue, commandLine.getParsedOptionValue(opt.getLongOpt(), defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test Option arg
        assertEquals(optValue, commandLine.getParsedOptionValue(opt));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? defaultInt : optValue, commandLine.getParsedOptionValue(opt, defaultInt));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertEquals(optValue == null ? defaultInt : optValue, commandLine.getParsedOptionValue(opt, defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test OptionGroup arg
        assertEquals(grpValue, commandLine.getParsedOptionValue(optionGroup));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        assertEquals(grpValue == null ? defaultInt : grpValue, commandLine.getParsedOptionValue(optionGroup, defaultInt));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        assertEquals(grpValue == null ? defaultInt : grpValue, commandLine.getParsedOptionValue(optionGroup, defaultSupplier));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        // test other Group arg — none of its options were parsed, returns null/default without deprecation
        assertNull(commandLine.getParsedOptionValue(otherGroup));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue(otherGroup, defaultInt));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue(otherGroup, defaultSupplier));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        // test null Group arg — null group is always absent, returns null/default without deprecation
        assertNull(commandLine.getParsedOptionValue(nullGroup));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue(nullGroup, defaultInt));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue(nullGroup, defaultSupplier));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        // test not an option — unknown option name, returns null/default without deprecation
        assertNull(commandLine.getParsedOptionValue("Nope"));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue("Nope", defaultInt));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue("Nope", defaultSupplier));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValuesParameters")
    void testGetParsedOptionValues(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                                         final Integer[] optValue, final boolean grpDep, final Integer[] grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> deprecationLog = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(deprecationLog::add).get().parse(options, args);
        final Supplier<Integer[]> defaultSupplier = () -> new Integer[]{2, 3};
        final OptionGroup otherGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;
        final Integer[] defaultInts = {2, 3};

        // test char option arg
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(toOptionChar(opt)));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertArrayEquals(optValue == null ? defaultInts : optValue, commandLine.getParsedOptionValues(toOptionChar(opt), defaultInts));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertArrayEquals(optValue == null ? defaultInts : optValue, commandLine.getParsedOptionValues(toOptionChar(opt), defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test short option arg
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(opt.getOpt()));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertArrayEquals(optValue == null ? defaultInts : optValue, commandLine.getParsedOptionValues(opt.getOpt(), defaultInts));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertArrayEquals(optValue == null ? defaultInts : optValue, commandLine.getParsedOptionValues(opt.getOpt(), defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test long option arg
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(opt.getLongOpt()));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertArrayEquals(optValue == null ? defaultInts : optValue, commandLine.getParsedOptionValues(opt.getLongOpt(), defaultInts));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertArrayEquals(optValue == null ? defaultInts : optValue, commandLine.getParsedOptionValues(opt.getLongOpt(), defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test Option arg
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(opt));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertArrayEquals(optValue == null ? defaultInts : optValue, commandLine.getParsedOptionValues(opt, defaultInts));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        assertArrayEquals(optValue == null ? defaultInts : optValue, commandLine.getParsedOptionValues(opt, defaultSupplier));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test OptionGroup arg
        assertArrayEquals(grpValue, commandLine.getParsedOptionValues(optionGroup));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        assertArrayEquals(grpValue == null ? defaultInts : grpValue, commandLine.getParsedOptionValues(optionGroup, defaultInts));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        assertArrayEquals(grpValue == null ? defaultInts : grpValue, commandLine.getParsedOptionValues(optionGroup, defaultSupplier));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        // test other Group arg — none of its options were parsed, returns null/default without deprecation
        assertNull(commandLine.getParsedOptionValues(otherGroup));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues(otherGroup, defaultInts));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues(otherGroup, defaultSupplier));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        // test null Group arg — null group is always absent, returns null/default without deprecation
        assertNull(commandLine.getParsedOptionValues(nullGroup));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues(nullGroup, defaultInts));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues(nullGroup, defaultSupplier));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        // test not an option — unknown option name, returns null/default without deprecation
        assertNull(commandLine.getParsedOptionValues("Nope"));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues("Nope", defaultInts));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues("Nope", defaultSupplier));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);
    }

    /**
     * Tests the hasOption calls.
     * @param args the argument strings to parse.
     * @param opt the option to check for values with.
     * @param optionGroup the option group to check for values with.
     * @param optDep {@code true} if the opt is deprecated.
     * @param has {@code true} if the opt is present.
     * @param grpDep {@code true} if the group is deprecated.
     * @param hasGrp {@code true} if the group is present.
     * @param grpOpt the option that is expected to be processed by the group.
     * @throws ParseException on parsing error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOption(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                              final boolean has, final boolean grpDep, final boolean hasGrp, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> deprecationLog = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(deprecationLog::add).get().parse(options, args);
        final OptionGroup otherGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // test char option arg
        assertEquals(has, commandLine.hasOption(toOptionChar(opt)));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test short option arg
        assertEquals(has, commandLine.hasOption(opt.getOpt()));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test long option arg
        assertEquals(has, commandLine.hasOption(opt.getLongOpt()));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test Option arg
        assertEquals(has, commandLine.hasOption(opt));
        assertDeprecationHandlerInvoked(optDep, deprecationLog, opt);

        // test OptionGroup arg
        assertEquals(hasGrp, commandLine.hasOption(optionGroup));
        assertDeprecationHandlerInvoked(grpDep, deprecationLog, grpOpt);

        // test other group arg — none of its options were parsed, always returns false
        assertFalse(commandLine.hasOption(otherGroup));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        // test null group arg — null group is always absent, always returns false
        assertFalse(commandLine.hasOption(nullGroup));
        assertDeprecationHandlerInvoked(false, deprecationLog, grpOpt);

        // test not an option — unknown option name, always returns false
        assertFalse(commandLine.hasOption("Nope"));
        assertDeprecationHandlerInvoked(false, deprecationLog, opt);
    }

    /**
     * Tests the hasOption calls.
     * @param args the argument strings to parse.
     * @param opt the option to check for values with.
     * @param optionGroup the option group to check for values with.
     * @param optDep {@code true} if the opt is deprecated.
     * @param has {@code true} if the opt is present.
     * @param grpDep {@code true} if the group is deprecated.
     * @param hasGrp {@code true} if the group is present.
     * @param grpOpt the option that is expected to be processed by the group.
     * @throws ParseException on parsing error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOptionNoDeprecationHandler(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                              final boolean has, final boolean grpDep, final boolean hasGrp, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        final CommandLine commandLine = DefaultParser.builder().get().parse(options, args);
        final PrintStream savedOut = System.out;
        try {
            System.setOut(new PrintStream(capturedOutput));

            // test char option arg
            assertEquals(has, commandLine.hasOption(toOptionChar(opt)));
            assertDeprecationPrinted(optDep, capturedOutput);

            // test short option arg
            assertEquals(has, commandLine.hasOption(opt.getOpt()));
            assertDeprecationPrinted(optDep, capturedOutput);

            // test long option arg
            assertEquals(has, commandLine.hasOption(opt.getLongOpt()));
            assertDeprecationPrinted(optDep, capturedOutput);

            // test Option arg
            assertEquals(has, commandLine.hasOption(opt));
            assertDeprecationPrinted(optDep, capturedOutput);

            // test OptionGroup arg
            assertEquals(hasGrp, commandLine.hasOption(optionGroup));
            assertDeprecationPrinted(grpDep, capturedOutput);

            // test not an option
            assertFalse(commandLine.hasOption("Nope"));
            assertDeprecationPrinted(false, capturedOutput);
        } finally {
            System.setOut(savedOut);
        }
    }

    /**
     * Tests the hasOption calls.
     * @param args the argument strings to parse.
     * @param opt the option to check for values with.
     * @param optionGroup the option group to check for values with.
     * @param optDep {@code true} if the opt is deprecated.
     * @param has {@code true} if the opt is present.
     * @param grpDep {@code true} if the group is deprecated.
     * @param hasGrp {@code true} if the group is present.
     * @param grpOpt the option that is expected to be processed by the group.
     * @throws ParseException on parsing error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOptionNullDeprecationHandler(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                                                  final boolean has, final boolean grpDep, final boolean hasGrp, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(null).get().parse(options, args);
        final PrintStream savedOut = System.out;
        try {
            System.setOut(new PrintStream(capturedOutput));

            // test char option arg
            assertEquals(has, commandLine.hasOption(toOptionChar(opt)));
            assertDeprecationPrinted(false, capturedOutput);

            // test short option arg
            assertEquals(has, commandLine.hasOption(opt.getOpt()));
            assertDeprecationPrinted(false, capturedOutput);

            // test long option arg
            assertEquals(has, commandLine.hasOption(opt.getLongOpt()));
            assertDeprecationPrinted(false, capturedOutput);

            // test Option arg
            assertEquals(has, commandLine.hasOption(opt));
            assertDeprecationPrinted(false, capturedOutput);

            // test OptionGroup arg
            assertEquals(hasGrp, commandLine.hasOption(optionGroup));
            assertDeprecationPrinted(false, capturedOutput);

            // test not an option
            assertFalse(commandLine.hasOption("Nope"));
            assertDeprecationPrinted(false, capturedOutput);
        } finally {
            System.setOut(savedOut);
        }
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testNoDeprecationHandler(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                                   final String optValue, final boolean grpDep, final String grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final CommandLine commandLine = DefaultParser.builder().get().parse(options, args);
        final Supplier<String> defaultSupplier = () -> "thing";
        final Supplier<String> nullSupplier = null;
        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        final PrintStream savedOut = System.out;
        try {
            System.setOut(new PrintStream(capturedOutput));

            final OptionGroup otherGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                    .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
            final OptionGroup nullGroup = null;

            // test char option
            assertEquals(optValue, commandLine.getOptionValue(toOptionChar(opt)));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(toOptionChar(opt), "thing"));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(toOptionChar(opt), defaultSupplier));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue, commandLine.getOptionValue(toOptionChar(opt), nullSupplier));
            assertDeprecationPrinted(optDep, capturedOutput);

            // test short option arg
            assertEquals(optValue, commandLine.getOptionValue(opt.getOpt()));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getOpt(), "thing"));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getOpt(), defaultSupplier));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue, commandLine.getOptionValue(opt.getOpt(), nullSupplier));
            assertDeprecationPrinted(optDep, capturedOutput);

            // test long option arg
            assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt()));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getLongOpt(), "thing"));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getLongOpt(), defaultSupplier));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt(), nullSupplier));
            assertDeprecationPrinted(optDep, capturedOutput);

            // test Option arg
            assertEquals(optValue, commandLine.getOptionValue(opt));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt, "thing"));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt, defaultSupplier));
            assertDeprecationPrinted(optDep, capturedOutput);

            assertEquals(optValue, commandLine.getOptionValue(opt, nullSupplier));
            assertDeprecationPrinted(optDep, capturedOutput);

            // test optionGroup  arg
            assertEquals(grpValue, commandLine.getOptionValue(optionGroup));
            assertDeprecationPrinted(grpDep, capturedOutput);

            assertEquals(grpValue == null ? "thing" : grpValue, commandLine.getOptionValue(optionGroup, "thing"));
            assertDeprecationPrinted(grpDep, capturedOutput);

            assertEquals(grpValue == null ? "thing" : grpValue, commandLine.getOptionValue(optionGroup, defaultSupplier));
            assertDeprecationPrinted(grpDep, capturedOutput);

            assertEquals(grpValue, commandLine.getOptionValue(optionGroup, nullSupplier));
            assertDeprecationPrinted(grpDep, capturedOutput);

            // test other group arg — none of its options were parsed, returns null/default without deprecation
            assertNull(commandLine.getOptionValue(otherGroup));
            assertDeprecationPrinted(false, capturedOutput);

            assertEquals("thing", commandLine.getOptionValue(otherGroup, "thing"));
            assertDeprecationPrinted(false, capturedOutput);

            assertEquals("thing", commandLine.getOptionValue(otherGroup, defaultSupplier));
            assertDeprecationPrinted(false, capturedOutput);

            assertNull(commandLine.getOptionValue(otherGroup, nullSupplier));
            assertDeprecationPrinted(false, capturedOutput);

            // test null Group arg — null group is always absent, returns null/default without deprecation
            assertNull(commandLine.getOptionValue(nullGroup));
            assertDeprecationPrinted(false, capturedOutput);

            assertEquals("thing", commandLine.getOptionValue(nullGroup, "thing"));
            assertDeprecationPrinted(false, capturedOutput);

            assertEquals("thing", commandLine.getOptionValue(nullGroup, defaultSupplier));
            assertDeprecationPrinted(false, capturedOutput);

            assertNull(commandLine.getOptionValue(nullGroup, nullSupplier));
            assertDeprecationPrinted(false, capturedOutput);

            // test not an option — unknown option name, returns null/default without deprecation
            assertNull(commandLine.getOptionValue("Nope"));
            assertDeprecationPrinted(false, capturedOutput);

            assertEquals("thing", commandLine.getOptionValue("Nope", "thing"));
            assertDeprecationPrinted(false, capturedOutput);

            assertEquals("thing", commandLine.getOptionValue("Nope", defaultSupplier));
            assertDeprecationPrinted(false, capturedOutput);

            assertNull(commandLine.getOptionValue("Nope", nullSupplier));
            assertDeprecationPrinted(false, capturedOutput);
        } finally {
            System.setOut(savedOut);
        }
    }

    @Test
    void testNullOption() throws Exception {
        final Options options = new Options();
        final Option optI = Option.builder("i").hasArg().type(Number.class).get();
        final Option optF = Option.builder("f").hasArg().get();
        options.addOption(optI);
        options.addOption(optF);
        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, new String[] {"-i", "123", "-f", "foo"});
        assertNull(cmd.getOptionValue((Option) null));
        assertNull(cmd.getParsedOptionValue((Option) null));
        assertNull(cmd.getOptionValue((OptionGroup) null));
        assertNull(cmd.getParsedOptionValue((OptionGroup) null));
    }
}
