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

public class CommandLineTest_testGetOptionValue {

    private static Stream<Arguments> createOptionValueParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        // T set
        lst.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        lst.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "foo" }, optT, optionGroup, false, null, false, "foo", optU));
        lst.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "foo" }, optT, optionGroup, false, null, false, "foo", optU));
        // U set
        lst.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "foo" }, optU, optionGroup, false, null, true, "foo", optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo" }, optU, optionGroup, false, null, true, "foo", optT));
        lst.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));
        lst.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));
        return lst.stream();
    }

    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Verifies that the deprecation handler was called exactly once (if expected) or not at all,
     * then resets the handler list for the next assertion.
     *
     * @param expectDeprecated {@code true} if the handler should have been invoked once.
     * @param handler          the list that deprecation events are recorded to.
     * @param expectedOption   the option expected to have triggered the deprecation.
     */
    void checkHandler(final boolean expectDeprecated, final List<Option> handler, final Option expectedOption) {
        if (expectDeprecated) {
            assertEquals(1, handler.size());
            assertEquals(expectedOption, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    /**
     * Verifies that {@code getOptionValue} returns the correct value for all overloads (char,
     * short name, long name, Option, OptionGroup), and that deprecated options trigger the
     * deprecation handler exactly once per lookup, never more.
     *
     * @param args        the argument strings to parse.
     * @param opt         the option to check for values with.
     * @param optionGroup the option group to check for values with.
     * @param optDep      {@code true} if opt is deprecated.
     * @param optValue    the value expected when querying by opt.
     * @param grpDep      {@code true} if the selected group option is deprecated.
     * @param grpValue    the value expected when querying by optionGroup.
     * @param grpOpt      the option that is expected to be selected in the group.
     * @throws ParseException on parse error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testGetOptionValue(
            final String[] args,
            final Option opt,
            final OptionGroup optionGroup,
            final boolean optDep,
            final String optValue,
            final boolean grpDep,
            final String grpValue,
            final Option grpOpt) throws ParseException {

        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);

        final Supplier<String> thinger = () -> "thing";
        // Pre-compute the expected result when a default of "thing" is supplied.
        final String optValueOrDefault = optValue != null ? optValue : "thing";
        final String grpValueOrDefault = grpValue != null ? grpValue : "thing";

        final OptionGroup otherGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // Query by option character
        assertEquals(optValue, commandLine.getOptionValue(asChar(opt)));
        checkHandler(optDep, handler, opt);
        assertEquals(optValueOrDefault, commandLine.getOptionValue(asChar(opt), "thing"));
        checkHandler(optDep, handler, opt);
        assertEquals(optValueOrDefault, commandLine.getOptionValue(asChar(opt), thinger));
        checkHandler(optDep, handler, opt);

        // Query by short option name
        assertEquals(optValue, commandLine.getOptionValue(opt.getOpt()));
        checkHandler(optDep, handler, opt);
        assertEquals(optValueOrDefault, commandLine.getOptionValue(opt.getOpt(), "thing"));
        checkHandler(optDep, handler, opt);
        assertEquals(optValueOrDefault, commandLine.getOptionValue(opt.getOpt(), thinger));
        checkHandler(optDep, handler, opt);

        // Query by long option name
        assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);
        assertEquals(optValueOrDefault, commandLine.getOptionValue(opt.getLongOpt(), "thing"));
        checkHandler(optDep, handler, opt);
        assertEquals(optValueOrDefault, commandLine.getOptionValue(opt.getLongOpt(), thinger));
        checkHandler(optDep, handler, opt);

        // Query by Option object
        assertEquals(optValue, commandLine.getOptionValue(opt));
        checkHandler(optDep, handler, opt);
        assertEquals(optValueOrDefault, commandLine.getOptionValue(opt, "thing"));
        checkHandler(optDep, handler, opt);
        assertEquals(optValueOrDefault, commandLine.getOptionValue(opt, thinger));
        checkHandler(optDep, handler, opt);

        // Query by OptionGroup (the group that contains the parsed option)
        assertEquals(grpValue, commandLine.getOptionValue(optionGroup));
        checkHandler(grpDep, handler, grpOpt);
        assertEquals(grpValueOrDefault, commandLine.getOptionValue(optionGroup, "thing"));
        checkHandler(grpDep, handler, grpOpt);
        assertEquals(grpValueOrDefault, commandLine.getOptionValue(optionGroup, thinger));
        checkHandler(grpDep, handler, grpOpt);

        // Query by an unrelated OptionGroup — should always return null / default
        assertNull(commandLine.getOptionValue(otherGroup));
        checkHandler(false, handler, grpOpt);
        assertEquals("thing", commandLine.getOptionValue(otherGroup, "thing"));
        checkHandler(false, handler, grpOpt);
        assertEquals("thing", commandLine.getOptionValue(otherGroup, thinger));
        checkHandler(false, handler, grpOpt);

        // Query by a null OptionGroup — should always return null / default
        assertNull(commandLine.getOptionValue(nullGroup));
        checkHandler(false, handler, grpOpt);
        assertEquals("thing", commandLine.getOptionValue(nullGroup, "thing"));
        checkHandler(false, handler, grpOpt);
        assertEquals("thing", commandLine.getOptionValue(nullGroup, thinger));
        checkHandler(false, handler, grpOpt);

        // Query by an option name that does not exist — should always return null / default
        assertNull(commandLine.getOptionValue("Nope"));
        checkHandler(false, handler, opt);
        assertEquals("thing", commandLine.getOptionValue("Nope", "thing"));
        checkHandler(false, handler, opt);
        assertEquals("thing", commandLine.getOptionValue("Nope", thinger));
        checkHandler(false, handler, opt);
    }
}
