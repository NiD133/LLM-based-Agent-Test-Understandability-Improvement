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

    @FunctionalInterface
    private interface ThrowingSupplier<T> {
        T get() throws ParseException;
    }

    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = deprecatedOption("T", "tee").optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        addHasOptionCasesForSelectedT(lst, optT, optU, optionGroup);
        addHasOptionCasesForSelectedU(lst, optT, optU, optionGroup);
        return lst.stream();
    }

    private static Stream<Arguments> createOptionValueParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = deprecatedOption("T", "tee").optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        addOptionValueCasesForSelectedT(lst, optT, optU, optionGroup);
        addOptionValueCasesForSelectedU(lst, optT, optU, optionGroup);
        return lst.stream();
    }

    private static Stream<Arguments> createOptionValuesParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = deprecatedOption("T", "tee").numberOfArgs(2).optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").numberOfArgs(2).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final String[] foobar = { "foo", "bar" };

        addOptionValuesCasesForSelectedT(lst, optT, optU, optionGroup, foobar);
        addOptionValuesCasesForSelectedU(lst, optT, optU, optionGroup, foobar);
        return lst.stream();
    }

    private static Stream<Arguments> createParsedOptionValueParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = deprecatedOption("T", "tee").type(Integer.class).optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer expected = Integer.valueOf(1);

        addParsedOptionValueCasesForSelectedT(lst, optT, optU, optionGroup, expected);
        addParsedOptionValueCasesForSelectedU(lst, optT, optU, optionGroup, expected);
        return lst.stream();
    }

    private static Stream<Arguments> createParsedOptionValuesParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = deprecatedOption("T", "tee").type(Integer.class).optionalArg(true).hasArgs().get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).hasArgs().get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer[] expected = {1, 2};

        addParsedOptionValuesCasesForSelectedT(lst, optT, optU, optionGroup, expected);
        addParsedOptionValuesCasesForSelectedU(lst, optT, optU, optionGroup, expected);
        return lst.stream();
    }

    private static Option.Builder deprecatedOption(final String option, final String longOption) {
        return Option.builder().option(option).longOpt(longOption).deprecated();
    }

    private static void addHasOptionCasesForSelectedT(final List<Arguments> lst, final Option optT, final Option optU,
            final OptionGroup optionGroup) {
        lst.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, true, true, true, optT));
        lst.add(Arguments.of(new String[] {"-T", "foo"}, optT, optionGroup, true, true, true, true, optT));
        lst.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, true, true, true, optT));
        lst.add(Arguments.of(new String[] {"--tee", "foo"}, optT, optionGroup, true, true, true, true, optT));

        lst.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, false, true, true, optT));
        lst.add(Arguments.of(new String[] {"-T", "foo", "bar"}, optU, optionGroup, false, false, true, true, optT));
        lst.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, false, true, true, optT));
        lst.add(Arguments.of(new String[] {"--tee", "foo", "bar"}, optU, optionGroup, false, false, true, true, optT));
    }

    private static void addHasOptionCasesForSelectedU(final List<Arguments> lst, final Option optT, final Option optU,
            final OptionGroup optionGroup) {
        lst.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, false, false, true, optU));
        lst.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optT, optionGroup, false, false, false, true, optU));
        lst.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, false, false, true, optU));
        lst.add(Arguments.of(new String[] {"--you", "foo", "bar"}, optT, optionGroup, false, false, false, true, optU));

        lst.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, true, false, true, optU));
        lst.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optU, optionGroup, false, true, false, true, optU));
        lst.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, true, false, true, optU));
        lst.add(Arguments.of(new String[] {"--you", "foo", "bar"}, optU, optionGroup, false, true, false, true, optU));
    }

    private static void addOptionValueCasesForSelectedT(final List<Arguments> lst, final Option optT, final Option optU,
            final OptionGroup optionGroup) {
        lst.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"-T", "foo"}, optT, optionGroup, true, "foo", true, "foo", optT));
        lst.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"--tee", "foo"}, optT, optionGroup, true, "foo", true, "foo", optT));

        lst.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"-T", "foo"}, optU, optionGroup, false, null, true, "foo", optT));
        lst.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"--tee", "foo"}, optU, optionGroup, false, null, true, "foo", optT));
    }

    private static void addOptionValueCasesForSelectedU(final List<Arguments> lst, final Option optT, final Option optU,
            final OptionGroup optionGroup) {
        lst.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"-U", "foo"}, optT, optionGroup, false, null, false, "foo", optU));
        lst.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"--you", "foo"}, optT, optionGroup, false, null, false, "foo", optU));

        lst.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"-U", "foo"}, optU, optionGroup, false, "foo", false, "foo", optU));
        lst.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"--you", "foo"}, optU, optionGroup, false, "foo", false, "foo", optU));
    }

    private static void addOptionValuesCasesForSelectedT(final List<Arguments> lst, final Option optT, final Option optU,
            final OptionGroup optionGroup, final String[] foobar) {
        lst.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"-T", "foo", "bar"}, optT, optionGroup, true, foobar, true, foobar, optT));
        lst.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"--tee", "foo", "bar"}, optT, optionGroup, true, foobar, true, foobar, optT));

        lst.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"-T", "foo", "bar"}, optU, optionGroup, false, null, true, foobar, optT));
        lst.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"--tee", "foo", "bar"}, optU, optionGroup, false, null, true, foobar, optT));
    }

    private static void addOptionValuesCasesForSelectedU(final List<Arguments> lst, final Option optT, final Option optU,
            final OptionGroup optionGroup, final String[] foobar) {
        lst.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optT, optionGroup, false, null, false, foobar, optU));
        lst.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"--you", "foo", "bar"}, optT, optionGroup, false, null, false, foobar, optU));

        lst.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"-U", "foo", "bar"}, optU, optionGroup, false, foobar, false, foobar, optU));
        lst.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"--you", "foo", "bar"}, optU, optionGroup, false, foobar, false, foobar, optU));
    }

    private static void addParsedOptionValueCasesForSelectedT(final List<Arguments> lst, final Option optT, final Option optU,
            final OptionGroup optionGroup, final Integer expected) {
        lst.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"-T", "1"}, optT, optionGroup, true, expected, true, expected, optT));
        lst.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"--tee", "1"}, optT, optionGroup, true, expected, true, expected, optT));

        lst.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"-T", "1"}, optU, optionGroup, false, null, true, expected, optT));
        lst.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"--tee", "1"}, optU, optionGroup, false, null, true, expected, optT));
    }

    private static void addParsedOptionValueCasesForSelectedU(final List<Arguments> lst, final Option optT, final Option optU,
            final OptionGroup optionGroup, final Integer expected) {
        lst.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"-U", "1"}, optT, optionGroup, false, null, false, expected, optU));
        lst.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"--you", "1"}, optT, optionGroup, false, null, false, expected, optU));

        lst.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"-U", "1"}, optU, optionGroup, false, expected, false, expected, optU));
        lst.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"--you", "1"}, optU, optionGroup, false, expected, false, expected, optU));
    }

    private static void addParsedOptionValuesCasesForSelectedT(final List<Arguments> lst, final Option optT, final Option optU,
            final OptionGroup optionGroup, final Integer[] expected) {
        lst.add(Arguments.of(new String[] {"-T"}, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"-T", "1", "2"}, optT, optionGroup, true, expected, true, expected, optT));
        lst.add(Arguments.of(new String[] {"--tee"}, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"--tee", "1", "2"}, optT, optionGroup, true, expected, true, expected, optT));

        lst.add(Arguments.of(new String[] {"-T"}, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"-T", "1", "2"}, optU, optionGroup, false, null, true, expected, optT));
        lst.add(Arguments.of(new String[] {"--tee"}, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] {"--tee", "1", "2"}, optU, optionGroup, false, null, true, expected, optT));
    }

    private static void addParsedOptionValuesCasesForSelectedU(final List<Arguments> lst, final Option optT, final Option optU,
            final OptionGroup optionGroup, final Integer[] expected) {
        lst.add(Arguments.of(new String[] {"-U"}, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"-U", "1", "2"}, optT, optionGroup, false, null, false, expected, optU));
        lst.add(Arguments.of(new String[] {"--you"}, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"--you", "1", "2"}, optT, optionGroup, false, null, false, expected, optU));

        lst.add(Arguments.of(new String[] {"-U"}, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"-U", "1", "2"}, optU, optionGroup, false, expected, false, expected, optU));
        lst.add(Arguments.of(new String[] {"--you"}, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] {"--you", "1", "2"}, optU, optionGroup, false, expected, false, expected, optU));
    }

    char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    private CommandLine parseWithRecordingHandler(final OptionGroup optionGroup, final List<Option> handler, final String[] args)
            throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        return DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);
    }

    private CommandLine parseWithDefaultHandler(final OptionGroup optionGroup, final String[] args) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        return DefaultParser.builder().get().parse(options, args);
    }

    private CommandLine parseWithNullHandler(final OptionGroup optionGroup, final String[] args) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        return DefaultParser.builder().setDeprecatedHandler(null).get().parse(options, args);
    }

    private OptionGroup otherOptionGroup() {
        return new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
    }

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
     * Verifies that the deprecation handler has been called only once or not at all.
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

    private <T> void assertValueAndHandler(final T expected, final boolean deprecated, final List<Option> handler,
            final Option reportedOption, final ThrowingSupplier<T> actual) throws ParseException {
        assertEquals(expected, actual.get());
        checkHandler(deprecated, handler, reportedOption);
    }

    private <T> void assertArrayAndHandler(final T[] expected, final boolean deprecated, final List<Option> handler,
            final Option reportedOption, final ThrowingSupplier<T[]> actual) throws ParseException {
        assertArrayEquals(expected, actual.get());
        checkHandler(deprecated, handler, reportedOption);
    }

    private void assertNullAndHandler(final boolean deprecated, final List<Option> handler, final Option reportedOption,
            final ThrowingSupplier<?> actual) throws ParseException {
        assertNull(actual.get());
        checkHandler(deprecated, handler, reportedOption);
    }

    private <T> void assertValueAndOutput(final T expected, final boolean deprecated, final ByteArrayOutputStream baos,
            final ThrowingSupplier<T> actual) throws ParseException {
        assertEquals(expected, actual.get());
        assertWritten(deprecated, baos);
    }

    private void assertFalseAndOutput(final boolean deprecated, final ByteArrayOutputStream baos,
            final ThrowingSupplier<Boolean> actual) throws ParseException {
        assertFalse(actual.get());
        assertWritten(deprecated, baos);
    }

    private void assertNullAndOutput(final boolean deprecated, final ByteArrayOutputStream baos,
            final ThrowingSupplier<?> actual) throws ParseException {
        assertNull(actual.get());
        assertWritten(deprecated, baos);
    }

    private void assertBuilderContents(final CommandLine cmd) {
        assertEquals("foo", cmd.getArgs()[0]);
        assertEquals("bar", cmd.getArgList().get(1));
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }

    private void assertPropertyValues(final Properties props) {
        assertNotNull(props, "null properties");
        assertEquals(4, props.size(), "number of properties in " + props);
        assertEquals("value1", props.getProperty("param1"), "property 1");
        assertEquals("value2", props.getProperty("param2"), "property 2");
        assertEquals("true", props.getProperty("param3"), "property 3");
        assertEquals("value4", props.getProperty("param4"), "property 4");
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
        final CommandLine cmd = CommandLine.builder()
                .addArg("foo")
                .addArg("bar")
                .addOption(Option.builder("T").get())
                .build();

        assertBuilderContents(cmd);
    }

    @Test
    void testBuilderGet() {
        final CommandLine cmd = CommandLine.builder()
                .addArg("foo")
                .addArg("bar")
                .addOption(Option.builder("T").get())
                .get();

        assertBuilderContents(cmd);
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

        assertPropertyValues(cl.getOptionProperties("D"));
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

        assertPropertyValues(cl.getOptionProperties(optionD));
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

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testGetOptionValue(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final String optValue, final boolean grpDep, final String grpValue, final Option grpOpt) throws ParseException {
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = parseWithRecordingHandler(optionGroup, handler, args);
        final Supplier<String> thinger = () -> "thing";
        final OptionGroup otherGroup = otherOptionGroup();
        final OptionGroup nullGroup = null;

        assertStringOptionValueAccessors(commandLine, opt, optValue, optDep, handler, opt, thinger);
        assertStringOptionGroupValueAccessors(commandLine, optionGroup, grpValue, grpDep, handler, grpOpt, thinger);
        assertMissingStringOptionGroupAccessors(commandLine, otherGroup, handler, grpOpt, thinger);
        assertMissingStringOptionGroupAccessors(commandLine, nullGroup, handler, grpOpt, thinger);
        assertMissingStringOptionAccessors(commandLine, handler, opt, thinger);
    }

    private void assertStringOptionValueAccessors(final CommandLine commandLine, final Option opt, final String optValue,
            final boolean optDep, final List<Option> handler, final Option reportedOption, final Supplier<String> thinger)
            throws ParseException {
        final String expectedWithDefault = optValue == null ? "thing" : optValue;

        assertValueAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getOptionValue(asChar(opt)));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getOptionValue(asChar(opt), "thing"));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getOptionValue(asChar(opt), thinger));

        assertValueAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getOptionValue(opt.getOpt()));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getOptionValue(opt.getOpt(), "thing"));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getOptionValue(opt.getOpt(), thinger));

        assertValueAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getOptionValue(opt.getLongOpt()));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getOptionValue(opt.getLongOpt(), "thing"));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getOptionValue(opt.getLongOpt(), thinger));

        assertValueAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getOptionValue(opt));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getOptionValue(opt, "thing"));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getOptionValue(opt, thinger));
    }

    private void assertStringOptionGroupValueAccessors(final CommandLine commandLine, final OptionGroup optionGroup,
            final String grpValue, final boolean grpDep, final List<Option> handler, final Option grpOpt,
            final Supplier<String> thinger) throws ParseException {
        final String expectedWithDefault = grpValue == null ? "thing" : grpValue;

        assertValueAndHandler(grpValue, grpDep, handler, grpOpt, () -> commandLine.getOptionValue(optionGroup));
        assertValueAndHandler(expectedWithDefault, grpDep, handler, grpOpt, () -> commandLine.getOptionValue(optionGroup, "thing"));
        assertValueAndHandler(expectedWithDefault, grpDep, handler, grpOpt, () -> commandLine.getOptionValue(optionGroup, thinger));
    }

    private void assertMissingStringOptionGroupAccessors(final CommandLine commandLine, final OptionGroup optionGroup,
            final List<Option> handler, final Option grpOpt, final Supplier<String> thinger) throws ParseException {
        assertNullAndHandler(false, handler, grpOpt, () -> commandLine.getOptionValue(optionGroup));
        assertValueAndHandler("thing", false, handler, grpOpt, () -> commandLine.getOptionValue(optionGroup, "thing"));
        assertValueAndHandler("thing", false, handler, grpOpt, () -> commandLine.getOptionValue(optionGroup, thinger));
    }

    private void assertMissingStringOptionAccessors(final CommandLine commandLine, final List<Option> handler,
            final Option opt, final Supplier<String> thinger) throws ParseException {
        assertNullAndHandler(false, handler, opt, () -> commandLine.getOptionValue("Nope"));
        assertValueAndHandler("thing", false, handler, opt, () -> commandLine.getOptionValue("Nope", "thing"));
        assertValueAndHandler("thing", false, handler, opt, () -> commandLine.getOptionValue("Nope", thinger));
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValuesParameters")
    void testGetOptionValues(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final String[] optValue, final boolean grpDep, final String[] grpValue, final Option grpOpt) throws ParseException {
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = parseWithRecordingHandler(optionGroup, handler, args);
        final OptionGroup otherGroup = otherOptionGroup();
        final OptionGroup nullGroup = null;

        assertArrayAndHandler(optValue, optDep, handler, opt, () -> commandLine.getOptionValues(asChar(opt)));
        assertArrayAndHandler(optValue, optDep, handler, opt, () -> commandLine.getOptionValues(opt.getOpt()));
        assertArrayAndHandler(optValue, optDep, handler, opt, () -> commandLine.getOptionValues(opt.getLongOpt()));
        assertArrayAndHandler(optValue, optDep, handler, opt, () -> commandLine.getOptionValues(opt));
        assertArrayAndHandler(grpValue, grpDep, handler, grpOpt, () -> commandLine.getOptionValues(optionGroup));

        assertNullAndHandler(false, handler, opt, () -> commandLine.getOptionValues("Nope"));
        assertNullAndHandler(false, handler, grpOpt, () -> commandLine.getOptionValues(otherGroup));
        assertNullAndHandler(false, handler, grpOpt, () -> commandLine.getOptionValues(nullGroup));
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValueParameters")
    void testGetParsedOptionValue(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final Integer optValue, final boolean grpDep, final Integer grpValue, final Option grpOpt) throws ParseException {
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = parseWithRecordingHandler(optionGroup, handler, args);
        final Supplier<Integer> thinger = () -> 2;
        final OptionGroup otherGroup = otherOptionGroup();
        final OptionGroup nullGroup = null;
        final Integer thing = 2;

        assertParsedOptionValueAccessors(commandLine, opt, optValue, optDep, handler, opt, thing, thinger);
        assertParsedOptionGroupValueAccessors(commandLine, optionGroup, grpValue, grpDep, handler, grpOpt, thing, thinger);
        assertMissingParsedOptionGroupAccessors(commandLine, otherGroup, handler, grpOpt, thing, thinger);
        assertMissingParsedOptionGroupAccessors(commandLine, nullGroup, handler, grpOpt, thing, thinger);
        assertMissingParsedOptionAccessors(commandLine, handler, opt, thing, thinger);
    }

    private void assertParsedOptionValueAccessors(final CommandLine commandLine, final Option opt, final Integer optValue,
            final boolean optDep, final List<Option> handler, final Option reportedOption, final Integer thing,
            final Supplier<Integer> thinger) throws ParseException {
        final Integer expectedWithDefault = optValue == null ? thing : optValue;

        assertValueAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(asChar(opt)));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(asChar(opt), thing));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(asChar(opt), thinger));

        assertValueAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(opt.getOpt()));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(opt.getOpt(), thing));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(opt.getOpt(), thinger));

        assertValueAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(opt.getLongOpt()));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(opt.getLongOpt(), thing));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(opt.getLongOpt(), thinger));

        assertValueAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(opt));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(opt, thing));
        assertValueAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValue(opt, thinger));
    }

    private void assertParsedOptionGroupValueAccessors(final CommandLine commandLine, final OptionGroup optionGroup,
            final Integer grpValue, final boolean grpDep, final List<Option> handler, final Option grpOpt,
            final Integer thing, final Supplier<Integer> thinger) throws ParseException {
        final Integer expectedWithDefault = grpValue == null ? thing : grpValue;

        assertValueAndHandler(grpValue, grpDep, handler, grpOpt, () -> commandLine.getParsedOptionValue(optionGroup));
        assertValueAndHandler(expectedWithDefault, grpDep, handler, grpOpt, () -> commandLine.getParsedOptionValue(optionGroup, thing));
        assertValueAndHandler(expectedWithDefault, grpDep, handler, grpOpt, () -> commandLine.getParsedOptionValue(optionGroup, thinger));
    }

    private void assertMissingParsedOptionGroupAccessors(final CommandLine commandLine, final OptionGroup optionGroup,
            final List<Option> handler, final Option grpOpt, final Integer thing, final Supplier<Integer> thinger)
            throws ParseException {
        assertNullAndHandler(false, handler, grpOpt, () -> commandLine.getParsedOptionValue(optionGroup));
        assertValueAndHandler(thing, false, handler, grpOpt, () -> commandLine.getParsedOptionValue(optionGroup, thing));
        assertValueAndHandler(thing, false, handler, grpOpt, () -> commandLine.getParsedOptionValue(optionGroup, thinger));
    }

    private void assertMissingParsedOptionAccessors(final CommandLine commandLine, final List<Option> handler,
            final Option opt, final Integer thing, final Supplier<Integer> thinger) throws ParseException {
        assertNullAndHandler(false, handler, opt, () -> commandLine.getParsedOptionValue("Nope"));
        assertValueAndHandler(thing, false, handler, opt, () -> commandLine.getParsedOptionValue("Nope", thing));
        assertValueAndHandler(thing, false, handler, opt, () -> commandLine.getParsedOptionValue("Nope", thinger));
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValuesParameters")
    void testGetParsedOptionValues(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final Integer[] optValue, final boolean grpDep, final Integer[] grpValue, final Option grpOpt) throws ParseException {
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = parseWithRecordingHandler(optionGroup, handler, args);
        final Supplier<Integer[]> thinger = () -> new Integer[]{2, 3};
        final OptionGroup otherGroup = otherOptionGroup();
        final OptionGroup nullGroup = null;
        final Integer[] thing = {2, 3};

        assertParsedOptionValuesAccessors(commandLine, opt, optValue, optDep, handler, opt, thing, thinger);
        assertParsedOptionGroupValuesAccessors(commandLine, optionGroup, grpValue, grpDep, handler, grpOpt, thing, thinger);
        assertMissingParsedOptionGroupValuesAccessors(commandLine, otherGroup, handler, grpOpt, thing, thinger);
        assertMissingParsedOptionGroupValuesAccessors(commandLine, nullGroup, handler, grpOpt, thing, thinger);
        assertMissingParsedOptionValuesAccessors(commandLine, handler, opt, thing, thinger);
    }

    private void assertParsedOptionValuesAccessors(final CommandLine commandLine, final Option opt, final Integer[] optValue,
            final boolean optDep, final List<Option> handler, final Option reportedOption, final Integer[] thing,
            final Supplier<Integer[]> thinger) throws ParseException {
        final Integer[] expectedWithDefault = optValue == null ? thing : optValue;

        assertArrayAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(asChar(opt)));
        assertArrayAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(asChar(opt), thing));
        assertArrayAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(asChar(opt), thinger));

        assertArrayAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(opt.getOpt()));
        assertArrayAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(opt.getOpt(), thing));
        assertArrayAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(opt.getOpt(), thinger));

        assertArrayAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(opt.getLongOpt()));
        assertArrayAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(opt.getLongOpt(), thing));
        assertArrayAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(opt.getLongOpt(), thinger));

        assertArrayAndHandler(optValue, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(opt));
        assertArrayAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(opt, thing));
        assertArrayAndHandler(expectedWithDefault, optDep, handler, reportedOption, () -> commandLine.getParsedOptionValues(opt, thinger));
    }

    private void assertParsedOptionGroupValuesAccessors(final CommandLine commandLine, final OptionGroup optionGroup,
            final Integer[] grpValue, final boolean grpDep, final List<Option> handler, final Option grpOpt,
            final Integer[] thing, final Supplier<Integer[]> thinger) throws ParseException {
        final Integer[] expectedWithDefault = grpValue == null ? thing : grpValue;

        assertArrayAndHandler(grpValue, grpDep, handler, grpOpt, () -> commandLine.getParsedOptionValues(optionGroup));
        assertArrayAndHandler(expectedWithDefault, grpDep, handler, grpOpt, () -> commandLine.getParsedOptionValues(optionGroup, thing));
        assertArrayAndHandler(expectedWithDefault, grpDep, handler, grpOpt, () -> commandLine.getParsedOptionValues(optionGroup, thinger));
    }

    private void assertMissingParsedOptionGroupValuesAccessors(final CommandLine commandLine, final OptionGroup optionGroup,
            final List<Option> handler, final Option grpOpt, final Integer[] thing, final Supplier<Integer[]> thinger)
            throws ParseException {
        assertNullAndHandler(false, handler, grpOpt, () -> commandLine.getParsedOptionValues(optionGroup));
        assertArrayAndHandler(thing, false, handler, grpOpt, () -> commandLine.getParsedOptionValues(optionGroup, thing));
        assertArrayAndHandler(thing, false, handler, grpOpt, () -> commandLine.getParsedOptionValues(optionGroup, thinger));
    }

    private void assertMissingParsedOptionValuesAccessors(final CommandLine commandLine, final List<Option> handler,
            final Option opt, final Integer[] thing, final Supplier<Integer[]> thinger) throws ParseException {
        assertNullAndHandler(false, handler, opt, () -> commandLine.getParsedOptionValues("Nope"));
        assertArrayAndHandler(thing, false, handler, opt, () -> commandLine.getParsedOptionValues("Nope", thing));
        assertArrayAndHandler(thing, false, handler, opt, () -> commandLine.getParsedOptionValues("Nope", thinger));
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOption(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final boolean has, final boolean grpDep, final boolean hasGrp, final Option grpOpt) throws ParseException {
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = parseWithRecordingHandler(optionGroup, handler, args);
        final OptionGroup otherGroup = otherOptionGroup();
        final OptionGroup nullGroup = null;

        assertValueAndHandler(has, optDep, handler, opt, () -> commandLine.hasOption(asChar(opt)));
        assertValueAndHandler(has, optDep, handler, opt, () -> commandLine.hasOption(opt.getOpt()));
        assertValueAndHandler(has, optDep, handler, opt, () -> commandLine.hasOption(opt.getLongOpt()));
        assertValueAndHandler(has, optDep, handler, opt, () -> commandLine.hasOption(opt));
        assertValueAndHandler(hasGrp, grpDep, handler, grpOpt, () -> commandLine.hasOption(optionGroup));

        assertValueAndHandler(false, false, handler, grpOpt, () -> commandLine.hasOption(otherGroup));
        assertValueAndHandler(false, false, handler, grpOpt, () -> commandLine.hasOption(nullGroup));
        assertValueAndHandler(false, false, handler, opt, () -> commandLine.hasOption("Nope"));
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOptionNoDeprecationHandler(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final boolean has, final boolean grpDep, final boolean hasGrp, final Option grpOpt) throws ParseException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final CommandLine commandLine = parseWithDefaultHandler(optionGroup, args);
        final PrintStream ps = System.out;
        try {
            System.setOut(new PrintStream(baos));

            assertValueAndOutput(has, optDep, baos, () -> commandLine.hasOption(asChar(opt)));
            assertValueAndOutput(has, optDep, baos, () -> commandLine.hasOption(opt.getOpt()));
            assertValueAndOutput(has, optDep, baos, () -> commandLine.hasOption(opt.getLongOpt()));
            assertValueAndOutput(has, optDep, baos, () -> commandLine.hasOption(opt));
            assertValueAndOutput(hasGrp, grpDep, baos, () -> commandLine.hasOption(optionGroup));
            assertFalseAndOutput(false, baos, () -> commandLine.hasOption("Nope"));
        } finally {
            System.setOut(ps);
        }
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOptionNullDeprecationHandler(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final boolean has, final boolean grpDep, final boolean hasGrp, final Option grpOpt) throws ParseException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final CommandLine commandLine = parseWithNullHandler(optionGroup, args);
        final PrintStream ps = System.out;
        try {
            System.setOut(new PrintStream(baos));

            assertValueAndOutput(has, false, baos, () -> commandLine.hasOption(asChar(opt)));
            assertValueAndOutput(has, false, baos, () -> commandLine.hasOption(opt.getOpt()));
            assertValueAndOutput(has, false, baos, () -> commandLine.hasOption(opt.getLongOpt()));
            assertValueAndOutput(has, false, baos, () -> commandLine.hasOption(opt));
            assertValueAndOutput(hasGrp, false, baos, () -> commandLine.hasOption(optionGroup));
            assertFalseAndOutput(false, baos, () -> commandLine.hasOption("Nope"));
        } finally {
            System.setOut(ps);
        }
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testNoDeprecationHandler(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final String optValue, final boolean grpDep, final String grpValue, final Option grpOpt) throws ParseException {
        final CommandLine commandLine = parseWithDefaultHandler(optionGroup, args);
        final Supplier<String> thinger = () -> "thing";
        final Supplier<String> nullSupplier = null;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream ps = System.out;
        try {
            System.setOut(new PrintStream(baos));

            final OptionGroup otherGroup = otherOptionGroup();
            final OptionGroup nullGroup = null;

            assertStringOptionValueAccessorsWithOutput(commandLine, opt, optValue, optDep, baos, thinger, nullSupplier);
            assertStringOptionGroupValueAccessorsWithOutput(commandLine, optionGroup, grpValue, grpDep, baos, thinger, nullSupplier);
            assertMissingStringOptionGroupAccessorsWithOutput(commandLine, otherGroup, baos, thinger, nullSupplier);
            assertMissingStringOptionGroupAccessorsWithOutput(commandLine, nullGroup, baos, thinger, nullSupplier);
            assertMissingStringOptionAccessorsWithOutput(commandLine, baos, thinger, nullSupplier);
        } finally {
            System.setOut(ps);
        }
    }

    private void assertStringOptionValueAccessorsWithOutput(final CommandLine commandLine, final Option opt, final String optValue,
            final boolean optDep, final ByteArrayOutputStream baos, final Supplier<String> thinger,
            final Supplier<String> nullSupplier) throws ParseException {
        final String expectedWithDefault = optValue == null ? "thing" : optValue;

        assertValueAndOutput(optValue, optDep, baos, () -> commandLine.getOptionValue(asChar(opt)));
        assertValueAndOutput(expectedWithDefault, optDep, baos, () -> commandLine.getOptionValue(asChar(opt), "thing"));
        assertValueAndOutput(expectedWithDefault, optDep, baos, () -> commandLine.getOptionValue(asChar(opt), thinger));
        assertValueAndOutput(optValue, optDep, baos, () -> commandLine.getOptionValue(asChar(opt), nullSupplier));

        assertValueAndOutput(optValue, optDep, baos, () -> commandLine.getOptionValue(opt.getOpt()));
        assertValueAndOutput(expectedWithDefault, optDep, baos, () -> commandLine.getOptionValue(opt.getOpt(), "thing"));
        assertValueAndOutput(expectedWithDefault, optDep, baos, () -> commandLine.getOptionValue(opt.getOpt(), thinger));
        assertValueAndOutput(optValue, optDep, baos, () -> commandLine.getOptionValue(opt.getOpt(), nullSupplier));

        assertValueAndOutput(optValue, optDep, baos, () -> commandLine.getOptionValue(opt.getLongOpt()));
        assertValueAndOutput(expectedWithDefault, optDep, baos, () -> commandLine.getOptionValue(opt.getLongOpt(), "thing"));
        assertValueAndOutput(expectedWithDefault, optDep, baos, () -> commandLine.getOptionValue(opt.getLongOpt(), thinger));
        assertValueAndOutput(optValue, optDep, baos, () -> commandLine.getOptionValue(opt.getLongOpt(), nullSupplier));

        assertValueAndOutput(optValue, optDep, baos, () -> commandLine.getOptionValue(opt));
        assertValueAndOutput(expectedWithDefault, optDep, baos, () -> commandLine.getOptionValue(opt, "thing"));
        assertValueAndOutput(expectedWithDefault, optDep, baos, () -> commandLine.getOptionValue(opt, thinger));
        assertValueAndOutput(optValue, optDep, baos, () -> commandLine.getOptionValue(opt, nullSupplier));
    }

    private void assertStringOptionGroupValueAccessorsWithOutput(final CommandLine commandLine, final OptionGroup optionGroup,
            final String grpValue, final boolean grpDep, final ByteArrayOutputStream baos, final Supplier<String> thinger,
            final Supplier<String> nullSupplier) throws ParseException {
        final String expectedWithDefault = grpValue == null ? "thing" : grpValue;

        assertValueAndOutput(grpValue, grpDep, baos, () -> commandLine.getOptionValue(optionGroup));
        assertValueAndOutput(expectedWithDefault, grpDep, baos, () -> commandLine.getOptionValue(optionGroup, "thing"));
        assertValueAndOutput(expectedWithDefault, grpDep, baos, () -> commandLine.getOptionValue(optionGroup, thinger));
        assertValueAndOutput(grpValue, grpDep, baos, () -> commandLine.getOptionValue(optionGroup, nullSupplier));
    }

    private void assertMissingStringOptionGroupAccessorsWithOutput(final CommandLine commandLine, final OptionGroup optionGroup,
            final ByteArrayOutputStream baos, final Supplier<String> thinger, final Supplier<String> nullSupplier)
            throws ParseException {
        assertNullAndOutput(false, baos, () -> commandLine.getOptionValue(optionGroup));
        assertValueAndOutput("thing", false, baos, () -> commandLine.getOptionValue(optionGroup, "thing"));
        assertValueAndOutput("thing", false, baos, () -> commandLine.getOptionValue(optionGroup, thinger));
        assertNullAndOutput(false, baos, () -> commandLine.getOptionValue(optionGroup, nullSupplier));
    }

    private void assertMissingStringOptionAccessorsWithOutput(final CommandLine commandLine, final ByteArrayOutputStream baos,
            final Supplier<String> thinger, final Supplier<String> nullSupplier) throws ParseException {
        assertNullAndOutput(false, baos, () -> commandLine.getOptionValue("Nope"));
        assertValueAndOutput("thing", false, baos, () -> commandLine.getOptionValue("Nope", "thing"));
        assertValueAndOutput("thing", false, baos, () -> commandLine.getOptionValue("Nope", thinger));
        assertNullAndOutput(false, baos, () -> commandLine.getOptionValue("Nope", nullSupplier));
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
