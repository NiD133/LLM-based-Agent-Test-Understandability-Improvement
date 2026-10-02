package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests the various {@link CommandLine#hasOption} overloads (by char, short name, long name,
 * {@link Option} and {@link OptionGroup}) and verifies that querying a deprecated option triggers
 * the deprecation handler exactly once.
 */
public class CommandLineTest_testHasOption {

    /**
     * Supplies the scenarios for {@link #testHasOption}.
     * <p>
     * Each scenario uses a group of two mutually exclusive options:
     * </p>
     * <ul>
     *   <li>{@code optT} ("-T" / "--tee"): deprecated, optional argument.</li>
     *   <li>{@code optU} ("-U" / "--you"): not deprecated, optional argument.</li>
     * </ul>
     * <p>
     * The {@link Arguments} columns are, in order:
     * </p>
     * <ol>
     *   <li>{@code args}        - the command-line arguments to parse.</li>
     *   <li>{@code opt}         - the option queried directly (by char, short, long and Option).</li>
     *   <li>{@code optionGroup} - the option group queried.</li>
     *   <li>{@code optDep}      - expected: querying {@code opt} fires the deprecation handler.</li>
     *   <li>{@code has}         - expected: {@code opt} is present on the command line.</li>
     *   <li>{@code grpDep}      - expected: querying {@code optionGroup} fires the deprecation handler.</li>
     *   <li>{@code hasGrp}      - expected: {@code optionGroup} is present on the command line.</li>
     *   <li>{@code grpOpt}      - the option the group resolves to (used to verify the handler).</li>
     * </ol>
     */
    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        final List<Arguments> scenarios = new ArrayList<>();
        //                    args                               opt   optionGroup  optDep  has    grpDep hasGrp grpOpt
        // Querying optT (the deprecated option) while "-T"/"--tee" is the selected option.
        scenarios.add(Arguments.of(new String[] { "-T" },               optT, optionGroup, true,  true,  true,  true,  optT));
        scenarios.add(Arguments.of(new String[] { "-T", "foo" },        optT, optionGroup, true,  true,  true,  true,  optT));
        scenarios.add(Arguments.of(new String[] { "--tee" },            optT, optionGroup, true,  true,  true,  true,  optT));
        scenarios.add(Arguments.of(new String[] { "--tee", "foo" },     optT, optionGroup, true,  true,  true,  true,  optT));
        // Querying optT while "-U"/"--you" is the selected option: optT absent, group present (resolves to optU).
        scenarios.add(Arguments.of(new String[] { "-U" },               optT, optionGroup, false, false, false, true,  optU));
        scenarios.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optT, optionGroup, false, false, false, true,  optU));
        scenarios.add(Arguments.of(new String[] { "--you" },            optT, optionGroup, false, false, false, true,  optU));
        scenarios.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optT, optionGroup, false, false, false, true, optU));
        // Querying optU (the non-deprecated option) while "-T"/"--tee" is selected: optU absent, group present (resolves to optT, deprecated).
        scenarios.add(Arguments.of(new String[] { "-T" },               optU, optionGroup, false, false, true,  true,  optT));
        scenarios.add(Arguments.of(new String[] { "-T", "foo", "bar" }, optU, optionGroup, false, false, true,  true,  optT));
        scenarios.add(Arguments.of(new String[] { "--tee" },            optU, optionGroup, false, false, true,  true,  optT));
        scenarios.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, optU, optionGroup, false, false, true, true, optT));
        // Querying optU while "-U"/"--you" is selected: optU present, group present (resolves to optU, not deprecated).
        scenarios.add(Arguments.of(new String[] { "-U" },               optU, optionGroup, false, true,  false, true,  optU));
        scenarios.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optU, optionGroup, false, true,  false, true,  optU));
        scenarios.add(Arguments.of(new String[] { "--you" },            optU, optionGroup, false, true,  false, true,  optU));
        scenarios.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optU, optionGroup, false, true, false, true, optU));
        return scenarios.stream();
    }

    /** Returns the first character of the option's short name, for the {@code hasOption(char)} overload. */
    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Verifies that the deprecation handler was invoked exactly once (for {@code opt}) or not at all,
     * then clears the handler's log for the next assertion.
     *
     * @param expectDeprecation {@code true} if the deprecation handler should have logged {@code opt}.
     * @param handler           the list the deprecation handler appends to.
     * @param opt               the option expected to have been logged (ignored when not expected).
     */
    private void checkHandler(final boolean expectDeprecation, final List<Option> handler, final Option opt) {
        if (expectDeprecation) {
            assertEquals(1, handler.size());
            assertEquals(opt, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    /**
     * Tests the {@link CommandLine#hasOption} overloads against a parsed command line.
     *
     * @param args        the argument strings to parse.
     * @param opt         the option to check for presence.
     * @param optionGroup the option group to check for presence.
     * @param optDep      {@code true} if querying {@code opt} should fire the deprecation handler.
     * @param has         {@code true} if {@code opt} is present.
     * @param grpDep      {@code true} if querying the group should fire the deprecation handler.
     * @param hasGrp      {@code true} if the group is present.
     * @param grpOpt      the option the group is expected to resolve to.
     * @throws ParseException on parsing error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOption(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final boolean has, final boolean grpDep, final boolean hasGrp, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);
        // A group whose options never appear in args, used to confirm hasOption returns false for it.
        final OptionGroup otherGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // hasOption(char)
        assertEquals(has, commandLine.hasOption(asChar(opt)));
        checkHandler(optDep, handler, opt);
        // hasOption(String) with the short name
        assertEquals(has, commandLine.hasOption(opt.getOpt()));
        checkHandler(optDep, handler, opt);
        // hasOption(String) with the long name
        assertEquals(has, commandLine.hasOption(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);
        // hasOption(Option)
        assertEquals(has, commandLine.hasOption(opt));
        checkHandler(optDep, handler, opt);
        // hasOption(OptionGroup)
        assertEquals(hasGrp, commandLine.hasOption(optionGroup));
        checkHandler(grpDep, handler, grpOpt);
        // hasOption(OptionGroup) for a group not present on the command line
        assertFalse(commandLine.hasOption(otherGroup));
        checkHandler(false, handler, grpOpt);
        // hasOption(OptionGroup) with a null group
        assertFalse(commandLine.hasOption(nullGroup));
        checkHandler(false, handler, grpOpt);
        // hasOption(String) for an unknown option name
        assertFalse(commandLine.hasOption("Nope"));
        checkHandler(false, handler, opt);
    }
}
