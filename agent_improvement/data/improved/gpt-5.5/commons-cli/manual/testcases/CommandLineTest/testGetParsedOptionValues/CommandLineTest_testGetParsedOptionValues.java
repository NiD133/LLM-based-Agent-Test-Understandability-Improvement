package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CommandLineTest_testGetParsedOptionValues {

    private static final Integer[] DEFAULT_PARSED_VALUES = { 2, 3 };

    private static Stream<Arguments> createParsedOptionValuesParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).hasArgs().get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).hasArgs().get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer[] expected = { 1, 2 };

        // T set
        lst.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "1", "2" }, optT, optionGroup, true, expected, true, expected, optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "1", "2" }, optT, optionGroup, true, expected, true, expected, optT));
        lst.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "1", "2" }, optT, optionGroup, false, null, false, expected, optU));
        lst.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "1", "2" }, optT, optionGroup, false, null, false, expected, optU));

        // U set
        lst.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "1", "2" }, optU, optionGroup, false, null, true, expected, optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "1", "2" }, optU, optionGroup, false, null, true, expected, optT));
        lst.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "1", "2" }, optU, optionGroup, false, expected, false, expected, optU));
        lst.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "1", "2" }, optU, optionGroup, false, expected, false, expected, optU));
        return lst.stream();
    }

    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Verifies that the deprecation handler has been called only once or not at all.
     *
     * @param optDep {@code true} if the deprecation should have been logged.
     * @param handler The list that the deprecation is logged to.
     * @param opt The option that triggered the logging. May be {@code null} if {@code optDep} is {@code false}.
     */
    private void checkHandler(final boolean optDep, final List<Option> handler, final Option opt) {
        if (optDep) {
            assertEquals(1, handler.size());
            assertEquals(opt, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    private Integer[] expectedOrDefault(final Integer[] expectedValues) {
        return expectedValues == null ? DEFAULT_PARSED_VALUES : expectedValues;
    }

    private void assertParsedValuesByChar(final CommandLine commandLine, final char optionChar, final Integer[] expectedValues, final boolean deprecated,
            final List<Option> handler, final Option deprecatedOption, final Supplier<Integer[]> defaultSupplier) throws ParseException {
        assertArrayEquals(expectedValues, commandLine.getParsedOptionValues(optionChar));
        checkHandler(deprecated, handler, deprecatedOption);
        assertArrayEquals(expectedOrDefault(expectedValues), commandLine.getParsedOptionValues(optionChar, DEFAULT_PARSED_VALUES));
        checkHandler(deprecated, handler, deprecatedOption);
        assertArrayEquals(expectedOrDefault(expectedValues), commandLine.getParsedOptionValues(optionChar, defaultSupplier));
        checkHandler(deprecated, handler, deprecatedOption);
    }

    private void assertParsedValuesByName(final CommandLine commandLine, final String optionName, final Integer[] expectedValues, final boolean deprecated,
            final List<Option> handler, final Option deprecatedOption, final Supplier<Integer[]> defaultSupplier) throws ParseException {
        assertArrayEquals(expectedValues, commandLine.getParsedOptionValues(optionName));
        checkHandler(deprecated, handler, deprecatedOption);
        assertArrayEquals(expectedOrDefault(expectedValues), commandLine.getParsedOptionValues(optionName, DEFAULT_PARSED_VALUES));
        checkHandler(deprecated, handler, deprecatedOption);
        assertArrayEquals(expectedOrDefault(expectedValues), commandLine.getParsedOptionValues(optionName, defaultSupplier));
        checkHandler(deprecated, handler, deprecatedOption);
    }

    private void assertParsedValuesByOption(final CommandLine commandLine, final Option option, final Integer[] expectedValues, final boolean deprecated,
            final List<Option> handler, final Option deprecatedOption, final Supplier<Integer[]> defaultSupplier) throws ParseException {
        assertArrayEquals(expectedValues, commandLine.getParsedOptionValues(option));
        checkHandler(deprecated, handler, deprecatedOption);
        assertArrayEquals(expectedOrDefault(expectedValues), commandLine.getParsedOptionValues(option, DEFAULT_PARSED_VALUES));
        checkHandler(deprecated, handler, deprecatedOption);
        assertArrayEquals(expectedOrDefault(expectedValues), commandLine.getParsedOptionValues(option, defaultSupplier));
        checkHandler(deprecated, handler, deprecatedOption);
    }

    private void assertParsedValuesByGroup(final CommandLine commandLine, final OptionGroup optionGroup, final Integer[] expectedValues, final boolean deprecated,
            final List<Option> handler, final Option deprecatedOption, final Supplier<Integer[]> defaultSupplier) throws ParseException {
        assertArrayEquals(expectedValues, commandLine.getParsedOptionValues(optionGroup));
        checkHandler(deprecated, handler, deprecatedOption);
        assertArrayEquals(expectedOrDefault(expectedValues), commandLine.getParsedOptionValues(optionGroup, DEFAULT_PARSED_VALUES));
        checkHandler(deprecated, handler, deprecatedOption);
        assertArrayEquals(expectedOrDefault(expectedValues), commandLine.getParsedOptionValues(optionGroup, defaultSupplier));
        checkHandler(deprecated, handler, deprecatedOption);
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValuesParameters")
    void testGetParsedOptionValues(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep, final Integer[] optValue,
            final boolean grpDep, final Integer[] grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);
        final Supplier<Integer[]> thinger = () -> new Integer[] { 2, 3 };
        final OptionGroup otherGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;
        final Integer[] thing = DEFAULT_PARSED_VALUES;

        assertParsedValuesByChar(commandLine, asChar(opt), optValue, optDep, handler, opt, thinger);
        assertParsedValuesByName(commandLine, opt.getOpt(), optValue, optDep, handler, opt, thinger);
        assertParsedValuesByName(commandLine, opt.getLongOpt(), optValue, optDep, handler, opt, thinger);
        assertParsedValuesByOption(commandLine, opt, optValue, optDep, handler, opt, thinger);
        assertParsedValuesByGroup(commandLine, optionGroup, grpValue, grpDep, handler, grpOpt, thinger);

        assertNull(commandLine.getParsedOptionValues(otherGroup));
        checkHandler(false, handler, grpOpt);
        assertArrayEquals(thing, commandLine.getParsedOptionValues(otherGroup, thing));
        checkHandler(false, handler, grpOpt);
        assertArrayEquals(thing, commandLine.getParsedOptionValues(otherGroup, thinger));
        checkHandler(false, handler, grpOpt);

        assertNull(commandLine.getParsedOptionValues(nullGroup));
        checkHandler(false, handler, grpOpt);
        assertArrayEquals(thing, commandLine.getParsedOptionValues(nullGroup, thing));
        checkHandler(false, handler, grpOpt);
        assertArrayEquals(thing, commandLine.getParsedOptionValues(nullGroup, thinger));
        checkHandler(false, handler, grpOpt);

        assertNull(commandLine.getParsedOptionValues("Nope"));
        checkHandler(false, handler, opt);
        assertArrayEquals(thing, commandLine.getParsedOptionValues("Nope", thing));
        checkHandler(false, handler, opt);
        assertArrayEquals(thing, commandLine.getParsedOptionValues("Nope", thinger));
        checkHandler(false, handler, opt);
    }
}
