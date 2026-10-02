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

/**
 * Tests the various {@code getParsedOptionValues(...)} overloads of {@link CommandLine}, which convert an option's raw
 * string arguments into a typed array (here {@code Integer[]}).
 * <p>
 * Each overload is exercised four ways of naming the same option (single char, short name, long name, the
 * {@link Option} object itself) plus the {@link OptionGroup} overloads. Every successful lookup of a <em>deprecated</em>
 * option is expected to notify the deprecation handler exactly once; {@link #checkHandler} verifies that.
 * </p>
 */
public class CommandLineTest_testGetParsedOptionValues {

    /**
     * Supplies the test cases for {@link #testGetParsedOptionValues}.
     * <p>
     * Two options share one group, so selecting one de-selects the other:
     * </p>
     * <ul>
     * <li>{@code T} / {@code tee} — deprecated, multi-valued, type {@link Integer}</li>
     * <li>{@code U} / {@code you} — multi-valued, type {@link Integer}</li>
     * </ul>
     * <p>
     * Each {@link Arguments} row has the following columns:
     * </p>
     * <ol>
     * <li>{@code args} — the command line to parse</li>
     * <li>{@code option} — the option whose values are queried directly</li>
     * <li>{@code optionGroup} — the group containing both options</li>
     * <li>{@code expectOptionDeprecationLogged} — whether querying {@code option} should log a deprecation</li>
     * <li>{@code expectedOptionValues} — values expected from the direct {@code option} query (null when not set)</li>
     * <li>{@code expectGroupDeprecationLogged} — whether querying the group should log a deprecation</li>
     * <li>{@code expectedGroupValues} — values expected from the group query (null when nothing selected)</li>
     * <li>{@code selectedGroupOption} — the option the group currently resolves to</li>
     * </ol>
     */
    private static Stream<Arguments> createParsedOptionValuesParameters() throws ParseException {
        final Option optTee = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).hasArgs().get();
        final Option optYou = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).hasArgs().get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optTee).addOption(optYou);
        final Integer[] oneTwo = { 1, 2 };

        final List<Arguments> parameters = new ArrayList<>();
        // Cases where the "tee" option (or its long form) is the one supplied on the command line.
        parameters.add(Arguments.of(new String[] { "-T" }, optTee, optionGroup, true, null, true, null, optTee));
        parameters.add(Arguments.of(new String[] { "-T", "1", "2" }, optTee, optionGroup, true, oneTwo, true, oneTwo, optTee));
        parameters.add(Arguments.of(new String[] { "--tee" }, optTee, optionGroup, true, null, true, null, optTee));
        parameters.add(Arguments.of(new String[] { "--tee", "1", "2" }, optTee, optionGroup, true, oneTwo, true, oneTwo, optTee));
        parameters.add(Arguments.of(new String[] { "-U" }, optTee, optionGroup, false, null, false, null, optYou));
        parameters.add(Arguments.of(new String[] { "-U", "1", "2" }, optTee, optionGroup, false, null, false, oneTwo, optYou));
        parameters.add(Arguments.of(new String[] { "--you" }, optTee, optionGroup, false, null, false, null, optYou));
        parameters.add(Arguments.of(new String[] { "--you", "1", "2" }, optTee, optionGroup, false, null, false, oneTwo, optYou));
        // Cases where the "you" option (or its long form) is the one supplied on the command line.
        parameters.add(Arguments.of(new String[] { "-T" }, optYou, optionGroup, false, null, true, null, optTee));
        parameters.add(Arguments.of(new String[] { "-T", "1", "2" }, optYou, optionGroup, false, null, true, oneTwo, optTee));
        parameters.add(Arguments.of(new String[] { "--tee" }, optYou, optionGroup, false, null, true, null, optTee));
        parameters.add(Arguments.of(new String[] { "--tee", "1", "2" }, optYou, optionGroup, false, null, true, oneTwo, optTee));
        parameters.add(Arguments.of(new String[] { "-U" }, optYou, optionGroup, false, null, false, null, optYou));
        parameters.add(Arguments.of(new String[] { "-U", "1", "2" }, optYou, optionGroup, false, oneTwo, false, oneTwo, optYou));
        parameters.add(Arguments.of(new String[] { "--you" }, optYou, optionGroup, false, null, false, null, optYou));
        parameters.add(Arguments.of(new String[] { "--you", "1", "2" }, optYou, optionGroup, false, oneTwo, false, oneTwo, optYou));
        return parameters.stream();
    }

    /** Returns the first character of the option's short name, i.e. the {@code char} key it is stored under. */
    private char asChar(final Option option) {
        return option.getOpt().charAt(0);
    }

    /**
     * Verifies that the deprecation handler was invoked exactly once (for {@code option}) or not at all, then clears it
     * ready for the next assertion.
     *
     * @param expectDeprecationLogged {@code true} if a deprecation should have been recorded.
     * @param handler                 the list the deprecation handler appends to.
     * @param option                  the option expected to have triggered the logging; may be {@code null} when none is
     *                                 expected.
     */
    private void checkHandler(final boolean expectDeprecationLogged, final List<Option> handler, final Option option) {
        if (expectDeprecationLogged) {
            assertEquals(1, handler.size());
            assertEquals(option, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValuesParameters")
    void testGetParsedOptionValues(final String[] args, final Option option, final OptionGroup optionGroup,
            final boolean expectOptionDeprecationLogged, final Integer[] expectedOptionValues,
            final boolean expectGroupDeprecationLogged, final Integer[] expectedGroupValues,
            final Option selectedGroupOption) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);

        // Default returned by the overloads that take a fallback, expressed both as a value and as a supplier.
        final Integer[] defaultValues = { 2, 3 };
        final Supplier<Integer[]> defaultValuesSupplier = () -> new Integer[] { 2, 3 };
        // When the queried option is set, the default is ignored; otherwise the default is returned.
        final Integer[] optionValuesOrDefault = expectedOptionValues == null ? defaultValues : expectedOptionValues;
        final Integer[] groupValuesOrDefault = expectedGroupValues == null ? defaultValues : expectedGroupValues;

        // A group with no selected option, plus a null group reference and an unknown option name, all of which
        // should resolve to no values (and therefore to the supplied default, where one is given).
        final OptionGroup unselectedGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // Look up by single char.
        assertArrayEquals(expectedOptionValues, commandLine.getParsedOptionValues(asChar(option)));
        checkHandler(expectOptionDeprecationLogged, handler, option);
        assertArrayEquals(optionValuesOrDefault, commandLine.getParsedOptionValues(asChar(option), defaultValues));
        checkHandler(expectOptionDeprecationLogged, handler, option);
        assertArrayEquals(optionValuesOrDefault, commandLine.getParsedOptionValues(asChar(option), defaultValuesSupplier));
        checkHandler(expectOptionDeprecationLogged, handler, option);

        // Look up by short name.
        assertArrayEquals(expectedOptionValues, commandLine.getParsedOptionValues(option.getOpt()));
        checkHandler(expectOptionDeprecationLogged, handler, option);
        assertArrayEquals(optionValuesOrDefault, commandLine.getParsedOptionValues(option.getOpt(), defaultValues));
        checkHandler(expectOptionDeprecationLogged, handler, option);
        assertArrayEquals(optionValuesOrDefault, commandLine.getParsedOptionValues(option.getOpt(), defaultValuesSupplier));
        checkHandler(expectOptionDeprecationLogged, handler, option);

        // Look up by long name.
        assertArrayEquals(expectedOptionValues, commandLine.getParsedOptionValues(option.getLongOpt()));
        checkHandler(expectOptionDeprecationLogged, handler, option);
        assertArrayEquals(optionValuesOrDefault, commandLine.getParsedOptionValues(option.getLongOpt(), defaultValues));
        checkHandler(expectOptionDeprecationLogged, handler, option);
        assertArrayEquals(optionValuesOrDefault, commandLine.getParsedOptionValues(option.getLongOpt(), defaultValuesSupplier));
        checkHandler(expectOptionDeprecationLogged, handler, option);

        // Look up by the Option object.
        assertArrayEquals(expectedOptionValues, commandLine.getParsedOptionValues(option));
        checkHandler(expectOptionDeprecationLogged, handler, option);
        assertArrayEquals(optionValuesOrDefault, commandLine.getParsedOptionValues(option, defaultValues));
        checkHandler(expectOptionDeprecationLogged, handler, option);
        assertArrayEquals(optionValuesOrDefault, commandLine.getParsedOptionValues(option, defaultValuesSupplier));
        checkHandler(expectOptionDeprecationLogged, handler, option);

        // Look up via the OptionGroup, which resolves to its selected option.
        assertArrayEquals(expectedGroupValues, commandLine.getParsedOptionValues(optionGroup));
        checkHandler(expectGroupDeprecationLogged, handler, selectedGroupOption);
        assertArrayEquals(groupValuesOrDefault, commandLine.getParsedOptionValues(optionGroup, defaultValues));
        checkHandler(expectGroupDeprecationLogged, handler, selectedGroupOption);
        assertArrayEquals(groupValuesOrDefault, commandLine.getParsedOptionValues(optionGroup, defaultValuesSupplier));
        checkHandler(expectGroupDeprecationLogged, handler, selectedGroupOption);

        // A group whose options were never on the command line: no values, no deprecation, default honored.
        assertNull(commandLine.getParsedOptionValues(unselectedGroup));
        checkHandler(false, handler, selectedGroupOption);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues(unselectedGroup, defaultValues));
        checkHandler(false, handler, selectedGroupOption);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues(unselectedGroup, defaultValuesSupplier));
        checkHandler(false, handler, selectedGroupOption);

        // A null group reference behaves the same way.
        assertNull(commandLine.getParsedOptionValues(nullGroup));
        checkHandler(false, handler, selectedGroupOption);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues(nullGroup, defaultValues));
        checkHandler(false, handler, selectedGroupOption);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues(nullGroup, defaultValuesSupplier));
        checkHandler(false, handler, selectedGroupOption);

        // An unknown option name behaves the same way.
        assertNull(commandLine.getParsedOptionValues("Nope"));
        checkHandler(false, handler, option);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues("Nope", defaultValues));
        checkHandler(false, handler, option);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues("Nope", defaultValuesSupplier));
        checkHandler(false, handler, option);
    }
}
