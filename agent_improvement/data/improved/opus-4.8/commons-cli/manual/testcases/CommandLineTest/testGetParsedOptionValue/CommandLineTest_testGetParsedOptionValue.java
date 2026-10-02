package org.apache.commons.cli;

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
 * Tests the {@code getParsedOptionValue(...)} overloads of {@link CommandLine}, which return a single
 * option value already converted to the option's declared type (here {@link Integer}).
 *
 * <p>Each test case is built around a two-option group ({@code -T/--tee} and {@code -U/--you}). One of the
 * two options is designated as the "selected" option for the case, and the parameters describe what every
 * overload is expected to return and whether the deprecation handler should fire.</p>
 */
public class CommandLineTest_testGetParsedOptionValue {

    /**
     * Supplies the test cases for {@link #testGetParsedOptionValue}.
     *
     * <p>Each {@link Arguments} row maps, in order, to the parameters of the test method:</p>
     * <ol>
     *   <li>{@code args} – the command-line arguments to parse.</li>
     *   <li>{@code selectedOption} – the option the per-option assertions target ({@code -T} or {@code -U}).</li>
     *   <li>{@code optionGroup} – the group containing both options.</li>
     *   <li>{@code optionIsDeprecated} – whether querying {@code selectedOption} should log a deprecation.</li>
     *   <li>{@code expectedOptionValue} – the value expected when querying {@code selectedOption} directly.</li>
     *   <li>{@code groupIsDeprecated} – whether querying the group should log a deprecation.</li>
     *   <li>{@code expectedGroupValue} – the value expected when querying the group.</li>
     *   <li>{@code expectedGroupOption} – the option the group resolves to (used to verify the deprecation handler).</li>
     * </ol>
     *
     * <p>Only {@code -T} ({@code --tee}) is marked deprecated, so the deprecation flags are {@code true}
     * only when a deprecated option is actually the one being queried.</p>
     */
    private static Stream<Arguments> createParsedOptionValueParameters() throws ParseException {
        final List<Arguments> cases = new ArrayList<>();
        final Option optionTee = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).get();
        final Option optionYou = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).get();
        final OptionGroup group = new OptionGroup().addOption(optionTee).addOption(optionYou);
        final Integer one = Integer.valueOf(1);

        // Cases where -T/--tee is the selected (deprecated) option.
        cases.add(Arguments.of(new String[] { "-T" }, optionTee, group, true, null, true, null, optionTee));
        cases.add(Arguments.of(new String[] { "-T", "1" }, optionTee, group, true, one, true, one, optionTee));
        cases.add(Arguments.of(new String[] { "--tee" }, optionTee, group, true, null, true, null, optionTee));
        cases.add(Arguments.of(new String[] { "--tee", "1" }, optionTee, group, true, one, true, one, optionTee));
        cases.add(Arguments.of(new String[] { "-U" }, optionTee, group, false, null, false, null, optionYou));
        cases.add(Arguments.of(new String[] { "-U", "1" }, optionTee, group, false, null, false, one, optionYou));
        cases.add(Arguments.of(new String[] { "--you" }, optionTee, group, false, null, false, null, optionYou));
        cases.add(Arguments.of(new String[] { "--you", "1" }, optionTee, group, false, null, false, one, optionYou));

        // Cases where -U/--you is the selected (non-deprecated) option.
        cases.add(Arguments.of(new String[] { "-T" }, optionYou, group, false, null, true, null, optionTee));
        cases.add(Arguments.of(new String[] { "-T", "1" }, optionYou, group, false, null, true, one, optionTee));
        cases.add(Arguments.of(new String[] { "--tee" }, optionYou, group, false, null, true, null, optionTee));
        cases.add(Arguments.of(new String[] { "--tee", "1" }, optionYou, group, false, null, true, one, optionTee));
        cases.add(Arguments.of(new String[] { "-U" }, optionYou, group, false, null, false, null, optionYou));
        cases.add(Arguments.of(new String[] { "-U", "1" }, optionYou, group, false, one, false, one, optionYou));
        cases.add(Arguments.of(new String[] { "--you" }, optionYou, group, false, null, false, null, optionYou));
        cases.add(Arguments.of(new String[] { "--you", "1" }, optionYou, group, false, one, false, one, optionYou));
        return cases.stream();
    }

    /** Returns the single-character short name of the given option. */
    private char asChar(final Option option) {
        return option.getOpt().charAt(0);
    }

    /**
     * Verifies that the deprecation handler was invoked exactly once (for {@code expectedOption}) when a
     * deprecation was expected, or not at all otherwise, then clears the handler's log for the next check.
     *
     * @param deprecationExpected {@code true} if a deprecation should have been logged.
     * @param handler             the list the deprecation handler appends to.
     * @param expectedOption      the option expected to have triggered the log; may be {@code null} when none is expected.
     */
    private void assertDeprecationLogged(final boolean deprecationExpected, final List<Option> handler, final Option expectedOption) {
        if (deprecationExpected) {
            assertEquals(1, handler.size());
            assertEquals(expectedOption, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValueParameters")
    void testGetParsedOptionValue(final String[] args, final Option selectedOption, final OptionGroup optionGroup,
            final boolean optionIsDeprecated, final Integer expectedOptionValue, final boolean groupIsDeprecated,
            final Integer expectedGroupValue, final Option expectedGroupOption) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);

        // A default value supplied either directly or via a Supplier; both must yield the same result.
        final Integer defaultValue = 2;
        final Supplier<Integer> defaultValueSupplier = () -> 2;

        // The value the default-providing overloads should return: the parsed value, or the default when unset.
        final Integer expectedOptionValueOrDefault = expectedOptionValue == null ? defaultValue : expectedOptionValue;
        final Integer expectedGroupValueOrDefault = expectedGroupValue == null ? defaultValue : expectedGroupValue;

        // A group whose options are never present on the command line.
        final OptionGroup unmatchedGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // Query by char option name.
        assertEquals(expectedOptionValue, commandLine.getParsedOptionValue(asChar(selectedOption)));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);
        assertEquals(expectedOptionValueOrDefault, commandLine.getParsedOptionValue(asChar(selectedOption), defaultValue));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);
        assertEquals(expectedOptionValueOrDefault, commandLine.getParsedOptionValue(asChar(selectedOption), defaultValueSupplier));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);

        // Query by short option name.
        assertEquals(expectedOptionValue, commandLine.getParsedOptionValue(selectedOption.getOpt()));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);
        assertEquals(expectedOptionValueOrDefault, commandLine.getParsedOptionValue(selectedOption.getOpt(), defaultValue));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);
        assertEquals(expectedOptionValueOrDefault, commandLine.getParsedOptionValue(selectedOption.getOpt(), defaultValueSupplier));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);

        // Query by long option name.
        assertEquals(expectedOptionValue, commandLine.getParsedOptionValue(selectedOption.getLongOpt()));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);
        assertEquals(expectedOptionValueOrDefault, commandLine.getParsedOptionValue(selectedOption.getLongOpt(), defaultValue));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);
        assertEquals(expectedOptionValueOrDefault, commandLine.getParsedOptionValue(selectedOption.getLongOpt(), defaultValueSupplier));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);

        // Query by Option instance.
        assertEquals(expectedOptionValue, commandLine.getParsedOptionValue(selectedOption));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);
        assertEquals(expectedOptionValueOrDefault, commandLine.getParsedOptionValue(selectedOption, defaultValue));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);
        assertEquals(expectedOptionValueOrDefault, commandLine.getParsedOptionValue(selectedOption, defaultValueSupplier));
        assertDeprecationLogged(optionIsDeprecated, handler, selectedOption);

        // Query by OptionGroup; resolves to whichever option in the group was selected.
        assertEquals(expectedGroupValue, commandLine.getParsedOptionValue(optionGroup));
        assertDeprecationLogged(groupIsDeprecated, handler, expectedGroupOption);
        assertEquals(expectedGroupValueOrDefault, commandLine.getParsedOptionValue(optionGroup, defaultValue));
        assertDeprecationLogged(groupIsDeprecated, handler, expectedGroupOption);
        assertEquals(expectedGroupValueOrDefault, commandLine.getParsedOptionValue(optionGroup, defaultValueSupplier));
        assertDeprecationLogged(groupIsDeprecated, handler, expectedGroupOption);

        // A group with no selected option returns null / the default, and never logs a deprecation.
        assertNull(commandLine.getParsedOptionValue(unmatchedGroup));
        assertDeprecationLogged(false, handler, expectedGroupOption);
        assertEquals(defaultValue, commandLine.getParsedOptionValue(unmatchedGroup, defaultValue));
        assertDeprecationLogged(false, handler, expectedGroupOption);
        assertEquals(defaultValue, commandLine.getParsedOptionValue(unmatchedGroup, defaultValueSupplier));
        assertDeprecationLogged(false, handler, expectedGroupOption);

        // A null group behaves the same as an unmatched group.
        assertNull(commandLine.getParsedOptionValue(nullGroup));
        assertDeprecationLogged(false, handler, expectedGroupOption);
        assertEquals(defaultValue, commandLine.getParsedOptionValue(nullGroup, defaultValue));
        assertDeprecationLogged(false, handler, expectedGroupOption);
        assertEquals(defaultValue, commandLine.getParsedOptionValue(nullGroup, defaultValueSupplier));
        assertDeprecationLogged(false, handler, expectedGroupOption);

        // An unknown option name returns null / the default, and never logs a deprecation.
        assertNull(commandLine.getParsedOptionValue("Nope"));
        assertDeprecationLogged(false, handler, selectedOption);
        assertEquals(defaultValue, commandLine.getParsedOptionValue("Nope", defaultValue));
        assertDeprecationLogged(false, handler, selectedOption);
        assertEquals(defaultValue, commandLine.getParsedOptionValue("Nope", defaultValueSupplier));
        assertDeprecationLogged(false, handler, selectedOption);
    }
}
