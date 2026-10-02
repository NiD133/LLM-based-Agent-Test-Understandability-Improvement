package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CommandLineTest_testHasOption {

    /**
     * Supplies test cases for {@link #testHasOption}.
     * <p>
     * Each {@link Arguments} tuple has the following positional parameters:
     * <ol>
     *   <li>{@code args}        – command-line arguments to parse</li>
     *   <li>{@code opt}         – the specific Option to query via hasOption</li>
     *   <li>{@code optionGroup} – the OptionGroup that contains both options T and U</li>
     *   <li>{@code optDep}      – whether querying {@code opt} should fire the deprecated handler</li>
     *   <li>{@code has}         – expected result of {@code commandLine.hasOption(opt)}</li>
     *   <li>{@code grpDep}      – whether querying the group should fire the deprecated handler</li>
     *   <li>{@code hasGrp}      – expected result of {@code commandLine.hasOption(optionGroup)}</li>
     *   <li>{@code grpOpt}      – the Option the group reports as selected</li>
     * </ol>
     */
    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final List<Arguments> testCases = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        // Querying optT when -T / --tee was supplied: optT IS present and IS deprecated
        testCases.add(Arguments.of(new String[] { "-T" },           optT, optionGroup, true,  true,  true,  true, optT));
        testCases.add(Arguments.of(new String[] { "-T", "foo" },    optT, optionGroup, true,  true,  true,  true, optT));
        testCases.add(Arguments.of(new String[] { "--tee" },        optT, optionGroup, true,  true,  true,  true, optT));
        testCases.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true,  true,  true,  true, optT));

        // Querying optT when -U / --you was supplied: optT is NOT present
        testCases.add(Arguments.of(new String[] { "-U" },                  optT, optionGroup, false, false, false, true, optU));
        testCases.add(Arguments.of(new String[] { "-U", "foo", "bar" },    optT, optionGroup, false, false, false, true, optU));
        testCases.add(Arguments.of(new String[] { "--you" },               optT, optionGroup, false, false, false, true, optU));
        testCases.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optT, optionGroup, false, false, false, true, optU));

        // Querying optU when -T / --tee was supplied: optU is NOT present; group selection triggers deprecated handler for optT
        testCases.add(Arguments.of(new String[] { "-T" },                  optU, optionGroup, false, false, true, true, optT));
        testCases.add(Arguments.of(new String[] { "-T", "foo", "bar" },    optU, optionGroup, false, false, true, true, optT));
        testCases.add(Arguments.of(new String[] { "--tee" },               optU, optionGroup, false, false, true, true, optT));
        testCases.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, optU, optionGroup, false, false, true, true, optT));

        // Querying optU when -U / --you was supplied: optU IS present and is NOT deprecated
        testCases.add(Arguments.of(new String[] { "-U" },                  optU, optionGroup, false, true, false, true, optU));
        testCases.add(Arguments.of(new String[] { "-U", "foo", "bar" },    optU, optionGroup, false, true, false, true, optU));
        testCases.add(Arguments.of(new String[] { "--you" },               optU, optionGroup, false, true, false, true, optU));
        testCases.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optU, optionGroup, false, true, false, true, optU));

        return testCases.stream();
    }

    /** Returns the single-character short name of {@code opt} as a {@code char}. */
    private static char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Verifies that the deprecation handler was invoked exactly once with {@code expectedOption},
     * or was not invoked at all, then clears the handler list.
     *
     * @param expectDeprecated {@code true} if the handler should have been called once.
     * @param handler          the list populated by the deprecation handler.
     * @param expectedOption   the option expected in the handler; only relevant when {@code expectDeprecated} is {@code true}.
     */
    private void checkHandler(final boolean expectDeprecated, final List<Option> handler, final Option expectedOption) {
        if (expectDeprecated) {
            assertEquals(1, handler.size());
            assertEquals(expectedOption, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    /**
     * Tests every {@link CommandLine#hasOption} overload: {@code char}, short {@code String},
     * long {@code String}, {@link Option}, {@link OptionGroup}, unrelated group, null group,
     * and an unrecognised option name.
     *
     * @param args        the command-line arguments to parse.
     * @param opt         the Option to query.
     * @param optionGroup the OptionGroup containing both options.
     * @param optDep      {@code true} if querying {@code opt} should trigger the deprecated handler.
     * @param has         expected result of {@code commandLine.hasOption(opt)}.
     * @param grpDep      {@code true} if querying the group should trigger the deprecated handler.
     * @param hasGrp      expected result of {@code commandLine.hasOption(optionGroup)}.
     * @param grpOpt      the Option expected to be selected within the group.
     * @throws ParseException on parse error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOption(final String[] args, final Option opt, final OptionGroup optionGroup,
            final boolean optDep, final boolean has,
            final boolean grpDep, final boolean hasGrp,
            final Option grpOpt) throws ParseException {

        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);

        final OptionGroup otherGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // hasOption(char) — single-character short name
        assertEquals(has, commandLine.hasOption(asChar(opt)));
        checkHandler(optDep, handler, opt);

        // hasOption(String) — short name string
        assertEquals(has, commandLine.hasOption(opt.getOpt()));
        checkHandler(optDep, handler, opt);

        // hasOption(String) — long name string
        assertEquals(has, commandLine.hasOption(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);

        // hasOption(Option)
        assertEquals(has, commandLine.hasOption(opt));
        checkHandler(optDep, handler, opt);

        // hasOption(OptionGroup) — the group that owns the parsed option
        assertEquals(hasGrp, commandLine.hasOption(optionGroup));
        checkHandler(grpDep, handler, grpOpt);

        // hasOption(OptionGroup) — an unrelated group: always false, no deprecation
        assertFalse(commandLine.hasOption(otherGroup));
        checkHandler(false, handler, grpOpt);

        // hasOption(OptionGroup) — null group: always false, no deprecation
        assertFalse(commandLine.hasOption(nullGroup));
        checkHandler(false, handler, grpOpt);

        // hasOption(String) — unrecognised option name: always false
        assertFalse(commandLine.hasOption("Nope"));
        checkHandler(false, handler, opt);
    }
}
