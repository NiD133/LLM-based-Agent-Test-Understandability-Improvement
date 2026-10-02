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

    /*
     * The parameterized tests below all share the same column layout. Each row produced by the
     * data-provider methods is an 8-tuple consumed positionally by the test method:
     *
     *   [0] args         - the command-line arguments to parse.
     *   [1] opt          - the option to query directly (the "T" or "U" option).
     *   [2] optionGroup  - the group containing both "T" and "U".
     *   [3] optDep       - true if querying 'opt' should trigger the deprecation handler.
     *   [4] optExpected  - the value/flag expected when querying 'opt' directly.
     *   [5] grpDep       - true if querying 'optionGroup' should trigger the deprecation handler.
     *   [6] grpExpected  - the value/flag expected when querying 'optionGroup'.
     *   [7] grpOpt       - the option the group is expected to resolve/select.
     *
     * Only the "T" option is declared deprecated, so 'optDep'/'grpDep' are true exactly when the
     * relevant query resolves to "T".
     */

    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final List<Arguments> arguments = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        // Query the deprecated "T" option while "T" is the option actually present on the command line.
        arguments.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, true, true, true, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo"}, optT, optionGroup, true, true, true, true, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, true, true, true, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo"}, optT, optionGroup, true, true, true, true, optT));

        // Query "T" while "U" is present: "T" itself is absent, but the group still resolves to the present "U".
        arguments.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, false, false, true, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optT, optionGroup, false, false, false, true, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, false, false, true, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo", "bar"}, optT, optionGroup, false, false, false, true, optU));

        // Query "U" while "T" is present: the group resolves to the present "T", which is deprecated.
        arguments.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, false, true, true, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo", "bar"}, optU, optionGroup, false, false, true, true, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, false, true, true, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo", "bar"}, optU, optionGroup, false, false, true, true, optT));

        // Query "U" while "U" is present.
        arguments.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, true, false, true, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optU, optionGroup, false, true, false, true, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, true, false, true, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo", "bar"},  optU, optionGroup, false, true, false, true, optU));

        return arguments.stream();
    }

    private static Stream<Arguments> createOptionValueParameters() throws ParseException {
        final List<Arguments> arguments = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        // "T" present on the command line.
        arguments.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo"}, optT, optionGroup, true, "foo", true, "foo", optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo"}, optT, optionGroup, true, "foo", true, "foo", optT));

        // "U" present: querying "T" finds nothing, but the group resolves to "U".
        arguments.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo"}, optT, optionGroup, false, null, false, "foo", optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo"}, optT, optionGroup, false, null, false, "foo", optU));

        // "T" present: querying "U" finds nothing, but the group resolves to the deprecated "T".
        arguments.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo"}, optU, optionGroup, false, null, true, "foo", optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo"}, optU, optionGroup, false, null, true, "foo", optT));

        // "U" present on the command line.
        arguments.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo"}, optU, optionGroup, false, "foo", false, "foo", optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo"},  optU, optionGroup, false, "foo", false, "foo", optU));

        return arguments.stream();
    }

    private static Stream<Arguments> createOptionValuesParameters() throws ParseException {
        final List<Arguments> arguments = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").numberOfArgs(2).deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").numberOfArgs(2).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        final String[] foobar = { "foo", "bar" };

        // "T" present on the command line.
        arguments.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo", "bar"}, optT, optionGroup, true, foobar, true, foobar, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo", "bar"}, optT, optionGroup, true, foobar, true, foobar, optT));

        // "U" present: querying "T" finds nothing, but the group resolves to "U".
        arguments.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optT, optionGroup, false, null, false, foobar, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo", "bar"}, optT, optionGroup, false, null, false, foobar, optU));

        // "T" present: querying "U" finds nothing, but the group resolves to the deprecated "T".
        arguments.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "foo", "bar"}, optU, optionGroup, false, null, true, foobar, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "foo", "bar"}, optU, optionGroup, false, null, true, foobar, optT));

        // "U" present on the command line.
        arguments.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optU, optionGroup, false, foobar, false, foobar, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "foo", "bar"},  optU, optionGroup, false, foobar, false, foobar, optU));

        return arguments.stream();
    }

    private static Stream<Arguments> createParsedOptionValueParameters() throws ParseException {
        final List<Arguments> arguments = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer expected = Integer.valueOf(1);

        // "T" present on the command line.
        arguments.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "1"}, optT, optionGroup, true, expected, true, expected, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "1"}, optT, optionGroup, true, expected, true, expected, optT));

        // "U" present: querying "T" finds nothing, but the group resolves to "U".
        arguments.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "1"}, optT, optionGroup, false, null, false, expected, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "1"}, optT, optionGroup, false, null, false, expected, optU));

        // "T" present: querying "U" finds nothing, but the group resolves to the deprecated "T".
        arguments.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "1"}, optU, optionGroup, false, null, true, expected, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "1"}, optU, optionGroup, false, null, true, expected, optT));

        // "U" present on the command line.
        arguments.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "1"}, optU, optionGroup, false, expected, false, expected, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "1"},  optU, optionGroup, false, expected, false, expected, optU));

        return arguments.stream();
    }

    private static Stream<Arguments> createParsedOptionValuesParameters() throws ParseException {
        final List<Arguments> arguments = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).hasArgs().get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).hasArgs().get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer[] expected = {1, 2};

        // "T" present on the command line.
        arguments.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "1", "2"}, optT, optionGroup, true, expected, true, expected, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "1", "2"}, optT, optionGroup, true, expected, true, expected, optT));

        // "U" present: querying "T" finds nothing, but the group resolves to "U".
        arguments.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "1", "2"}, optT, optionGroup, false, null, false, expected, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "1", "2"}, optT, optionGroup, false, null, false, expected, optU));

        // "T" present: querying "U" finds nothing, but the group resolves to the deprecated "T".
        arguments.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"-T", "1", "2"}, optU, optionGroup, false, null, true, expected, optT));
        arguments.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        arguments.add(Arguments.of(new String[] {"--tee", "1", "2"}, optU, optionGroup, false, null, true, expected, optT));

        // "U" present on the command line.
        arguments.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"-U", "1", "2"}, optU, optionGroup, false, expected, false, expected, optU));
        arguments.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        arguments.add(Arguments.of(new String[] {"--you", "1", "2"},  optU, optionGroup, false, expected, false, expected, optU));

        return arguments.stream();
    }

    /**
     * Gets the single-character short name of an option.
     *
     * @param opt the option.
     * @return the first character of the option's short name.
     */
    char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Asserts what the default deprecation handler should have printed to {@code System.out}, then resets the buffer.
     *
     * @param optDep {@code true} if a deprecation message is expected.
     * @param baos   the buffer capturing {@code System.out}.
     */
    private void assertWritten(final boolean optDep, final ByteArrayOutputStream baos) {
        System.out.flush();
        if (optDep) {
            assertEquals("Option 'T''tee': Deprecated", baos.toString().trim());
        } else {
            assertEquals("", baos.toString());
        }
        baos.reset();
    }

    /**
     * verifies that the deprecation handler has been called only once or not at all.
     * @param optDep {@code true} if the dependency should have been logged.
     * @param handler The list that the deprecation is logged to.
     * @param opt The option that triggered the logging. May be (@code null} if {@code optDep} is {@code false}.
     */
    void checkHandler(final boolean optDep, final List<Option> handler, final Option opt) {
        if (optDep) {
            assertEquals(1, handler.size());
            assertEquals(opt, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    @Test
    void testBadGetParsedOptionValue() throws Exception {

        final Options options = new Options();
        options.addOption(Option.builder("i").hasArg().type(Number.class).get());
        options.addOption(Option.builder("c").hasArg().converter(s -> Count.valueOf(s.toUpperCase())).get());

        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, new String[] {"-i", "foo", "-c", "bar"});

        // "foo" cannot be parsed as a Number, and "bar" is not a valid Count enum constant.
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

        // null args are silently ignored, so none are recorded.
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

        // null options are silently ignored, so none are recorded.
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
        // param3 was given without a value, so it defaults to "true".
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
        // param3 was given without a value, so it defaults to "true".
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

        // The null option is ignored; only a, b and c are recorded.
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

        // The null option is ignored; only a, b and c are recorded.
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
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);
        final Supplier<String> defaultSupplier = () -> "thing";
        // A group whose options are never present on the command line.
        final OptionGroup absentGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // The default value ("thing") is expected only when the real value is null.
        final String optDefaulted = optValue == null ? "thing" : optValue;
        final String grpDefaulted = grpValue == null ? "thing" : grpValue;

        // Query by char short name.
        assertEquals(optValue, commandLine.getOptionValue(asChar(opt)));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getOptionValue(asChar(opt), "thing"));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getOptionValue(asChar(opt), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by String short name.
        assertEquals(optValue, commandLine.getOptionValue(opt.getOpt()));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getOptionValue(opt.getOpt(), "thing"));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getOptionValue(opt.getOpt(), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by long name.
        assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getOptionValue(opt.getLongOpt(), "thing"));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getOptionValue(opt.getLongOpt(), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by Option instance.
        assertEquals(optValue, commandLine.getOptionValue(opt));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getOptionValue(opt, "thing"));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getOptionValue(opt, defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by OptionGroup.
        assertEquals(grpValue, commandLine.getOptionValue(optionGroup));
        checkHandler(grpDep, handler, grpOpt);

        assertEquals(grpDefaulted, commandLine.getOptionValue(optionGroup, "thing"));
        checkHandler(grpDep, handler, grpOpt);

        assertEquals(grpDefaulted, commandLine.getOptionValue(optionGroup, defaultSupplier));
        checkHandler(grpDep, handler, grpOpt);

        // A group whose options are absent yields null (or the default).
        assertNull(commandLine.getOptionValue(absentGroup));
        checkHandler(false, handler, grpOpt);

        assertEquals("thing", commandLine.getOptionValue(absentGroup, "thing"));
        checkHandler(false, handler, grpOpt);

        assertEquals("thing", commandLine.getOptionValue(absentGroup, defaultSupplier));
        checkHandler(false, handler, grpOpt);

        // A null group yields null (or the default).
        assertNull(commandLine.getOptionValue(nullGroup));
        checkHandler(false, handler, grpOpt);

        assertEquals("thing", commandLine.getOptionValue(nullGroup, "thing"));
        checkHandler(false, handler, grpOpt);

        assertEquals("thing", commandLine.getOptionValue(nullGroup, defaultSupplier));
        checkHandler(false, handler, grpOpt);

        // An unknown option name yields null (or the default).
        assertNull(commandLine.getOptionValue("Nope"));
        checkHandler(false, handler, opt);

        assertEquals("thing", commandLine.getOptionValue("Nope", "thing"));
        checkHandler(false, handler, opt);

        assertEquals("thing", commandLine.getOptionValue("Nope", defaultSupplier));
        checkHandler(false, handler, opt);
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
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);
        // A group whose options are never present on the command line.
        final OptionGroup absentGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // Query by char short name.
        assertArrayEquals(optValue, commandLine.getOptionValues(asChar(opt)));
        checkHandler(optDep, handler, opt);

        // Query by String short name.
        assertArrayEquals(optValue, commandLine.getOptionValues(opt.getOpt()));
        checkHandler(optDep, handler, opt);

        // Query by long name.
        assertArrayEquals(optValue, commandLine.getOptionValues(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);

        // Query by Option instance.
        assertArrayEquals(optValue, commandLine.getOptionValues(opt));
        checkHandler(optDep, handler, opt);

        // Query by OptionGroup.
        assertArrayEquals(grpValue, commandLine.getOptionValues(optionGroup));
        checkHandler(grpDep, handler, grpOpt);

        // An unknown option name yields null.
        assertNull(commandLine.getOptionValues("Nope"));
        checkHandler(false, handler, opt);

        // A group whose options are absent yields null.
        assertNull(commandLine.getOptionValues(absentGroup));
        checkHandler(false, handler, grpOpt);

        // A null group yields null.
        assertNull(commandLine.getOptionValues(nullGroup));
        checkHandler(false, handler, grpOpt);
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValueParameters")
    void testGetParsedOptionValue(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                                         final Integer optValue, final boolean grpDep, final Integer grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);
        final Integer defaultInt = 2;
        final Supplier<Integer> defaultSupplier = () -> defaultInt;
        // A group whose options are never present on the command line.
        final OptionGroup absentGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // The default value is expected only when the real value is null.
        final Integer optDefaulted = optValue == null ? defaultInt : optValue;
        final Integer grpDefaulted = grpValue == null ? defaultInt : grpValue;

        // Query by char short name.
        assertEquals(optValue, commandLine.getParsedOptionValue(asChar(opt)));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getParsedOptionValue(asChar(opt), defaultInt));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getParsedOptionValue(asChar(opt), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by String short name.
        assertEquals(optValue, commandLine.getParsedOptionValue(opt.getOpt()));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getParsedOptionValue(opt.getOpt(), defaultInt));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getParsedOptionValue(opt.getOpt(), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by long name.
        assertEquals(optValue, commandLine.getParsedOptionValue(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getParsedOptionValue(opt.getLongOpt(), defaultInt));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getParsedOptionValue(opt.getLongOpt(), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by Option instance.
        assertEquals(optValue, commandLine.getParsedOptionValue(opt));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getParsedOptionValue(opt, defaultInt));
        checkHandler(optDep, handler, opt);

        assertEquals(optDefaulted, commandLine.getParsedOptionValue(opt, defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by OptionGroup.
        assertEquals(grpValue, commandLine.getParsedOptionValue(optionGroup));
        checkHandler(grpDep, handler, grpOpt);

        assertEquals(grpDefaulted, commandLine.getParsedOptionValue(optionGroup, defaultInt));
        checkHandler(grpDep, handler, grpOpt);

        assertEquals(grpDefaulted, commandLine.getParsedOptionValue(optionGroup, defaultSupplier));
        checkHandler(grpDep, handler, grpOpt);

        // A group whose options are absent yields null (or the default).
        assertNull(commandLine.getParsedOptionValue(absentGroup));
        checkHandler(false, handler, grpOpt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue(absentGroup, defaultInt));
        checkHandler(false, handler, grpOpt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue(absentGroup, defaultSupplier));
        checkHandler(false, handler, grpOpt);

        // A null group yields null (or the default).
        assertNull(commandLine.getParsedOptionValue(nullGroup));
        checkHandler(false, handler, grpOpt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue(nullGroup, defaultInt));
        checkHandler(false, handler, grpOpt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue(nullGroup, defaultSupplier));
        checkHandler(false, handler, grpOpt);

        // An unknown option name yields null (or the default).
        assertNull(commandLine.getParsedOptionValue("Nope"));
        checkHandler(false, handler, opt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue("Nope", defaultInt));
        checkHandler(false, handler, opt);

        assertEquals(defaultInt, commandLine.getParsedOptionValue("Nope", defaultSupplier));
        checkHandler(false, handler, opt);
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValuesParameters")
    void testGetParsedOptionValues(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                                         final Integer[] optValue, final boolean grpDep, final Integer[] grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);
        final Integer[] defaultInts = {2, 3};
        final Supplier<Integer[]> defaultSupplier = () -> new Integer[]{2, 3};
        // A group whose options are never present on the command line.
        final OptionGroup absentGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // The default value is expected only when the real value is null.
        final Integer[] optDefaulted = optValue == null ? defaultInts : optValue;
        final Integer[] grpDefaulted = grpValue == null ? defaultInts : grpValue;

        // Query by char short name.
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(asChar(opt)));
        checkHandler(optDep, handler, opt);

        assertArrayEquals(optDefaulted, commandLine.getParsedOptionValues(asChar(opt), defaultInts));
        checkHandler(optDep, handler, opt);

        assertArrayEquals(optDefaulted, commandLine.getParsedOptionValues(asChar(opt), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by String short name.
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(opt.getOpt()));
        checkHandler(optDep, handler, opt);

        assertArrayEquals(optDefaulted, commandLine.getParsedOptionValues(opt.getOpt(), defaultInts));
        checkHandler(optDep, handler, opt);

        assertArrayEquals(optDefaulted, commandLine.getParsedOptionValues(opt.getOpt(), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by long name.
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);

        assertArrayEquals(optDefaulted, commandLine.getParsedOptionValues(opt.getLongOpt(), defaultInts));
        checkHandler(optDep, handler, opt);

        assertArrayEquals(optDefaulted, commandLine.getParsedOptionValues(opt.getLongOpt(), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by Option instance.
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(opt));
        checkHandler(optDep, handler, opt);

        assertArrayEquals(optDefaulted, commandLine.getParsedOptionValues(opt, defaultInts));
        checkHandler(optDep, handler, opt);

        assertArrayEquals(optDefaulted, commandLine.getParsedOptionValues(opt, defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by OptionGroup.
        assertArrayEquals(grpValue, commandLine.getParsedOptionValues(optionGroup));
        checkHandler(grpDep, handler, grpOpt);

        assertArrayEquals(grpDefaulted, commandLine.getParsedOptionValues(optionGroup, defaultInts));
        checkHandler(grpDep, handler, grpOpt);

        assertArrayEquals(grpDefaulted, commandLine.getParsedOptionValues(optionGroup, defaultSupplier));
        checkHandler(grpDep, handler, grpOpt);

        // A group whose options are absent yields null (or the default).
        assertNull(commandLine.getParsedOptionValues(absentGroup));
        checkHandler(false, handler, grpOpt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues(absentGroup, defaultInts));
        checkHandler(false, handler, grpOpt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues(absentGroup, defaultSupplier));
        checkHandler(false, handler, grpOpt);

        // A null group yields null (or the default).
        assertNull(commandLine.getParsedOptionValues(nullGroup));
        checkHandler(false, handler, grpOpt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues(nullGroup, defaultInts));
        checkHandler(false, handler, grpOpt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues(nullGroup, defaultSupplier));
        checkHandler(false, handler, grpOpt);

        // An unknown option name yields null (or the default).
        assertNull(commandLine.getParsedOptionValues("Nope"));
        checkHandler(false, handler, opt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues("Nope", defaultInts));
        checkHandler(false, handler, opt);

        assertArrayEquals(defaultInts, commandLine.getParsedOptionValues("Nope", defaultSupplier));
        checkHandler(false, handler, opt);
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
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);
        // A group whose options are never present on the command line.
        final OptionGroup absentGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // Query by char short name.
        assertEquals(has, commandLine.hasOption(asChar(opt)));
        checkHandler(optDep, handler, opt);

        // Query by String short name.
        assertEquals(has, commandLine.hasOption(opt.getOpt()));
        checkHandler(optDep, handler, opt);

        // Query by long name.
        assertEquals(has, commandLine.hasOption(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);

        // Query by Option instance.
        assertEquals(has, commandLine.hasOption(opt));
        checkHandler(optDep, handler, opt);

        // Query by OptionGroup.
        assertEquals(hasGrp, commandLine.hasOption(optionGroup));
        checkHandler(grpDep, handler, grpOpt);

        // A group whose options are absent is never present.
        assertFalse(commandLine.hasOption(absentGroup));
        checkHandler(false, handler, grpOpt);

        // A null group is never present.
        assertFalse(commandLine.hasOption(nullGroup));
        checkHandler(false, handler, grpOpt);

        // An unknown option name is never present.
        assertFalse(commandLine.hasOption("Nope"));
        checkHandler(false, handler, opt);
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
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // No handler is configured, so the default handler prints deprecation messages to System.out.
        final CommandLine commandLine = DefaultParser.builder().get().parse(options, args);
        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(baos));

            // Query by char short name.
            assertEquals(has, commandLine.hasOption(asChar(opt)));
            assertWritten(optDep, baos);

            // Query by String short name.
            assertEquals(has, commandLine.hasOption(opt.getOpt()));
            assertWritten(optDep, baos);

            // Query by long name.
            assertEquals(has, commandLine.hasOption(opt.getLongOpt()));
            assertWritten(optDep, baos);

            // Query by Option instance.
            assertEquals(has, commandLine.hasOption(opt));
            assertWritten(optDep, baos);

            // Query by OptionGroup.
            assertEquals(hasGrp, commandLine.hasOption(optionGroup));
            assertWritten(grpDep, baos);

            // An unknown option name is never present and prints nothing.
            assertFalse(commandLine.hasOption("Nope"));
            assertWritten(false, baos);
        } finally {
            System.setOut(originalOut);
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
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // An explicit null handler suppresses all deprecation output, regardless of optDep/grpDep.
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(null).get().parse(options, args);
        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(baos));

            // Query by char short name.
            assertEquals(has, commandLine.hasOption(asChar(opt)));
            assertWritten(false, baos);

            // Query by String short name.
            assertEquals(has, commandLine.hasOption(opt.getOpt()));
            assertWritten(false, baos);

            // Query by long name.
            assertEquals(has, commandLine.hasOption(opt.getLongOpt()));
            assertWritten(false, baos);

            // Query by Option instance.
            assertEquals(has, commandLine.hasOption(opt));
            assertWritten(false, baos);

            // Query by OptionGroup.
            assertEquals(hasGrp, commandLine.hasOption(optionGroup));
            assertWritten(false, baos);

            // An unknown option name is never present.
            assertFalse(commandLine.hasOption("Nope"));
            assertWritten(false, baos);
        } finally {
            System.setOut(originalOut);
        }
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testNoDeprecationHandler(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
                                   final String optValue, final boolean grpDep, final String grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        // No handler is configured, so the default handler prints deprecation messages to System.out.
        final CommandLine commandLine = DefaultParser.builder().get().parse(options, args);
        final Supplier<String> defaultSupplier = () -> "thing";
        final Supplier<String> nullSupplier = null;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(baos));

            // A group whose options are never present on the command line.
            final OptionGroup absentGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                    .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
            final OptionGroup nullGroup = null;

            // The default value ("thing") is expected only when the real value is null.
            final String optDefaulted = optValue == null ? "thing" : optValue;
            final String grpDefaulted = grpValue == null ? "thing" : grpValue;

            // Query by char short name.
            assertEquals(optValue, commandLine.getOptionValue(asChar(opt)));
            assertWritten(optDep, baos);

            assertEquals(optDefaulted, commandLine.getOptionValue(asChar(opt), "thing"));
            assertWritten(optDep, baos);

            assertEquals(optDefaulted, commandLine.getOptionValue(asChar(opt), defaultSupplier));
            assertWritten(optDep, baos);

            // A null default supplier behaves like no default.
            assertEquals(optValue, commandLine.getOptionValue(asChar(opt), nullSupplier));
            assertWritten(optDep, baos);

            // Query by String short name.
            assertEquals(optValue, commandLine.getOptionValue(opt.getOpt()));
            assertWritten(optDep, baos);

            assertEquals(optDefaulted, commandLine.getOptionValue(opt.getOpt(), "thing"));
            assertWritten(optDep, baos);

            assertEquals(optDefaulted, commandLine.getOptionValue(opt.getOpt(), defaultSupplier));
            assertWritten(optDep, baos);

            assertEquals(optValue, commandLine.getOptionValue(opt.getOpt(), nullSupplier));
            assertWritten(optDep, baos);

            // Query by long name.
            assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt()));
            assertWritten(optDep, baos);

            assertEquals(optDefaulted, commandLine.getOptionValue(opt.getLongOpt(), "thing"));
            assertWritten(optDep, baos);

            assertEquals(optDefaulted, commandLine.getOptionValue(opt.getLongOpt(), defaultSupplier));
            assertWritten(optDep, baos);

            assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt(), nullSupplier));
            assertWritten(optDep, baos);

            // Query by Option instance.
            assertEquals(optValue, commandLine.getOptionValue(opt));
            assertWritten(optDep, baos);

            assertEquals(optDefaulted, commandLine.getOptionValue(opt, "thing"));
            assertWritten(optDep, baos);

            assertEquals(optDefaulted, commandLine.getOptionValue(opt, defaultSupplier));
            assertWritten(optDep, baos);

            assertEquals(optValue, commandLine.getOptionValue(opt, nullSupplier));
            assertWritten(optDep, baos);

            // Query by OptionGroup.
            assertEquals(grpValue, commandLine.getOptionValue(optionGroup));
            assertWritten(grpDep, baos);

            assertEquals(grpDefaulted, commandLine.getOptionValue(optionGroup, "thing"));
            assertWritten(grpDep, baos);

            assertEquals(grpDefaulted, commandLine.getOptionValue(optionGroup, defaultSupplier));
            assertWritten(grpDep, baos);

            assertEquals(grpValue, commandLine.getOptionValue(optionGroup, nullSupplier));
            assertWritten(grpDep, baos);

            // A group whose options are absent yields null (or the default).
            assertNull(commandLine.getOptionValue(absentGroup));
            assertWritten(false, baos);

            assertEquals("thing", commandLine.getOptionValue(absentGroup, "thing"));
            assertWritten(false, baos);

            assertEquals("thing", commandLine.getOptionValue(absentGroup, defaultSupplier));
            assertWritten(false, baos);

            assertNull(commandLine.getOptionValue(absentGroup, nullSupplier));
            assertWritten(false, baos);

            // A null group yields null (or the default).
            assertNull(commandLine.getOptionValue(nullGroup));
            assertWritten(false, baos);

            assertEquals("thing", commandLine.getOptionValue(nullGroup, "thing"));
            assertWritten(false, baos);

            assertEquals("thing", commandLine.getOptionValue(nullGroup, defaultSupplier));
            assertWritten(false, baos);

            assertNull(commandLine.getOptionValue(nullGroup, nullSupplier));
            assertWritten(false, baos);

            // An unknown option name yields null (or the default).
            assertNull(commandLine.getOptionValue("Nope"));
            assertWritten(false, baos);

            assertEquals("thing", commandLine.getOptionValue("Nope", "thing"));
            assertWritten(false, baos);

            assertEquals("thing", commandLine.getOptionValue("Nope", defaultSupplier));
            assertWritten(false, baos);

            assertNull(commandLine.getOptionValue("Nope", nullSupplier));
            assertWritten(false, baos);
        } finally {
            System.setOut(originalOut);
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
        // Null option / option-group arguments resolve to null rather than throwing.
        assertNull(cmd.getOptionValue((Option) null));
        assertNull(cmd.getParsedOptionValue((Option) null));
        assertNull(cmd.getOptionValue((OptionGroup) null));
        assertNull(cmd.getParsedOptionValue((OptionGroup) null));
    }
}
