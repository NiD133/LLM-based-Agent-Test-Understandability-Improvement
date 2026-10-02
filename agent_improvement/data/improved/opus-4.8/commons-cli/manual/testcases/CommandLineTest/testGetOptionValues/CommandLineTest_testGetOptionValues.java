package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests the various {@link CommandLine#getOptionValues} overloads (by {@code char}, short name,
 * long name, {@link Option} and {@link OptionGroup}).
 * <p>
 * The fixtures use a single option group containing two multi-valued options:
 * </p>
 * <ul>
 *   <li>{@code -T} / {@code --tee} — a <em>deprecated</em> option,</li>
 *   <li>{@code -U} / {@code --you} — a regular option.</li>
 * </ul>
 * <p>
 * Looking up a deprecated option must notify the parser's deprecated-option handler exactly once
 * per lookup; looking up a non-deprecated option (or a missing option/group) must not notify it.
 * </p>
 */
public class CommandLineTest_testGetOptionValues {

    /**
     * Supplies the test cases for {@link #testGetOptionValues}.
     * <p>
     * Each {@link Arguments} row carries the following positional values:
     * </p>
     * <ol>
     *   <li>{@code args}        — the command-line arguments to parse,</li>
     *   <li>{@code opt}         — the option to look up directly,</li>
     *   <li>{@code optionGroup} — the group that {@code opt} belongs to,</li>
     *   <li>{@code optDep}      — whether looking up {@code opt} should flag a deprecation,</li>
     *   <li>{@code optValue}    — the values expected when looking up {@code opt},</li>
     *   <li>{@code grpDep}      — whether looking up {@code optionGroup} should flag a deprecation,</li>
     *   <li>{@code grpValue}    — the values expected when looking up {@code optionGroup},</li>
     *   <li>{@code grpOpt}      — the option the group is expected to resolve to.</li>
     * </ol>
     */
    private static Stream<Arguments> createOptionValuesParameters() throws ParseException {
        final Option optTee = Option.builder().option("T").longOpt("tee").numberOfArgs(2).deprecated().optionalArg(true).get();
        final Option optYou = Option.builder("U").longOpt("you").numberOfArgs(2).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optTee).addOption(optYou);
        final String[] foobar = { "foo", "bar" };

        final List<Arguments> cases = new ArrayList<>();
        //                  args                                   opt     group        optDep optValue grpDep grpValue grpOpt
        // --- The deprecated option "T" is the one supplied on the command line ---
        cases.add(Arguments.of(new String[] { "-T" },              optTee, optionGroup, true,  null,    true,  null,    optTee));
        cases.add(Arguments.of(new String[] { "-T", "foo", "bar" }, optTee, optionGroup, true,  foobar,  true,  foobar,  optTee));
        cases.add(Arguments.of(new String[] { "--tee" },           optTee, optionGroup, true,  null,    true,  null,    optTee));
        cases.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, optTee, optionGroup, true, foobar, true, foobar,  optTee));
        cases.add(Arguments.of(new String[] { "-U" },              optTee, optionGroup, false, null,    false, null,    optYou));
        cases.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optTee, optionGroup, false, null,    false, foobar,  optYou));
        cases.add(Arguments.of(new String[] { "--you" },           optTee, optionGroup, false, null,    false, null,    optYou));
        cases.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optTee, optionGroup, false, null, false, foobar,  optYou));
        // --- The regular option "U" is the one supplied on the command line ---
        cases.add(Arguments.of(new String[] { "-T" },              optYou, optionGroup, false, null,    true,  null,    optTee));
        cases.add(Arguments.of(new String[] { "-T", "foo", "bar" }, optYou, optionGroup, false, null,    true,  foobar,  optTee));
        cases.add(Arguments.of(new String[] { "--tee" },           optYou, optionGroup, false, null,    true,  null,    optTee));
        cases.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, optYou, optionGroup, false, null, true, foobar,  optTee));
        cases.add(Arguments.of(new String[] { "-U" },              optYou, optionGroup, false, null,    false, null,    optYou));
        cases.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optYou, optionGroup, false, foobar,  false, foobar,  optYou));
        cases.add(Arguments.of(new String[] { "--you" },           optYou, optionGroup, false, null,    false, null,    optYou));
        cases.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optYou, optionGroup, false, foobar, false, foobar, optYou));
        return cases.stream();
    }

    /**
     * Returns the single-character short name of an option (e.g. {@code 'T'} for {@code -T}).
     */
    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Verifies that the deprecation handler was invoked exactly once (for {@code opt}) or not at all,
     * then resets the handler for the next assertion.
     *
     * @param expectDeprecation {@code true} if a deprecation should have been logged.
     * @param deprecationLog    the list the deprecation handler appends to.
     * @param opt               the option expected to have been logged; may be {@code null} when no deprecation is expected.
     */
    private void checkHandler(final boolean expectDeprecation, final List<Option> deprecationLog, final Option opt) {
        if (expectDeprecation) {
            assertEquals(1, deprecationLog.size());
            assertEquals(opt, deprecationLog.get(0));
        } else {
            assertEquals(0, deprecationLog.size());
        }
        deprecationLog.clear();
    }

    /**
     * Checks every {@link CommandLine#getOptionValues} overload for a parsed command line, and confirms
     * that deprecated options are reported to the deprecation handler exactly once per lookup.
     *
     * @param args        the argument strings to parse.
     * @param opt         the option to look up directly.
     * @param optionGroup the option group to look up.
     * @param optDep      {@code true} if {@code opt} is deprecated.
     * @param optValue    the values expected from looking up {@code opt}.
     * @param grpDep      {@code true} if the group's selected option is deprecated.
     * @param grpValue    the values expected from looking up the group.
     * @param grpOpt      the option the group is expected to resolve to.
     * @throws ParseException on parse error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValuesParameters")
    void testGetOptionValues(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final String[] optValue, final boolean grpDep, final String[] grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> deprecationLog = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(deprecationLog::add).get().parse(options, args);
        // A group that shares no options with the parsed command line.
        final OptionGroup otherGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // Each overload that resolves to the same option must return the same values...
        // ...looked up by char.
        assertArrayEquals(optValue, commandLine.getOptionValues(asChar(opt)));
        checkHandler(optDep, deprecationLog, opt);
        // ...looked up by short name.
        assertArrayEquals(optValue, commandLine.getOptionValues(opt.getOpt()));
        checkHandler(optDep, deprecationLog, opt);
        // ...looked up by long name.
        assertArrayEquals(optValue, commandLine.getOptionValues(opt.getLongOpt()));
        checkHandler(optDep, deprecationLog, opt);
        // ...looked up by Option instance.
        assertArrayEquals(optValue, commandLine.getOptionValues(opt));
        checkHandler(optDep, deprecationLog, opt);

        // Looking up the whole group returns the selected option's values.
        assertArrayEquals(grpValue, commandLine.getOptionValues(optionGroup));
        checkHandler(grpDep, deprecationLog, grpOpt);

        // Unknown names and unrelated/null groups return null and never trigger a deprecation.
        assertNull(commandLine.getOptionValues("Nope"));
        checkHandler(false, deprecationLog, opt);
        assertNull(commandLine.getOptionValues(otherGroup));
        checkHandler(false, deprecationLog, grpOpt);
        assertNull(commandLine.getOptionValues(nullGroup));
        checkHandler(false, deprecationLog, grpOpt);
    }
}
