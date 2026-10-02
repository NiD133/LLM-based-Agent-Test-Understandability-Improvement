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
 * Tests the various {@link CommandLine#getOptionValue} overloads (by char, short name, long name,
 * {@link Option}, and {@link OptionGroup}), including the variants that accept a default value or a
 * default-value {@link Supplier}.
 * <p>
 * The test also verifies that querying a deprecated option triggers the deprecation handler exactly
 * once per access, and never for non-deprecated options or unknown names.
 * </p>
 */
public class CommandLineTest_testGetOptionValue {

    /** Fallback text returned by the default-value overloads when an option has no value. */
    private static final String DEFAULT_TEXT = "thing";

    /**
     * Supplies the test cases for {@link #testGetOptionValue}.
     * <p>
     * Two optional-argument options are grouped together: {@code T}/{@code tee} (deprecated) and
     * {@code U}/{@code you}. Each case records the parsed arguments, the option to query, the group,
     * and the expected value / deprecation flag for both the individual option and the group.
     * </p>
     */
    private static Stream<Arguments> createOptionValueParameters() throws ParseException {
        final List<Arguments> cases = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        // T set
        cases.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, null, true, null, optT));
        cases.add(Arguments.of(new String[] { "-T", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        cases.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, null, true, null, optT));
        cases.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        cases.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, null, false, null, optU));
        cases.add(Arguments.of(new String[] { "-U", "foo" }, optT, optionGroup, false, null, false, "foo", optU));
        cases.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, null, false, null, optU));
        cases.add(Arguments.of(new String[] { "--you", "foo" }, optT, optionGroup, false, null, false, "foo", optU));
        // U set
        cases.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, null, true, null, optT));
        cases.add(Arguments.of(new String[] { "-T", "foo" }, optU, optionGroup, false, null, true, "foo", optT));
        cases.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, null, true, null, optT));
        cases.add(Arguments.of(new String[] { "--tee", "foo" }, optU, optionGroup, false, null, true, "foo", optT));
        cases.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, null, false, null, optU));
        cases.add(Arguments.of(new String[] { "-U", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));
        cases.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, null, false, null, optU));
        cases.add(Arguments.of(new String[] { "--you", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));
        return cases.stream();
    }

    /** Returns the option's short name as a single character, for the {@code char} overloads. */
    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Verifies that the deprecation handler was invoked exactly once (with {@code opt}) when
     * {@code expectDeprecated} is {@code true}, or not at all otherwise, then clears the handler log.
     *
     * @param expectDeprecated {@code true} if the last access should have logged a deprecation.
     * @param handler          the list the deprecation handler appends to.
     * @param opt              the option expected to have been logged (ignored when not deprecated).
     */
    private void checkHandler(final boolean expectDeprecated, final List<Option> handler, final Option opt) {
        if (expectDeprecated) {
            assertEquals(1, handler.size());
            assertEquals(opt, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    /**
     * Tests every {@code getOptionValue} overload, with and without default values, and checks that
     * deprecated options report their deprecation exactly once per access.
     *
     * @param args        the argument strings to parse.
     * @param opt         the option to query for values.
     * @param optionGroup the option group to query for values.
     * @param optDep      {@code true} if querying {@code opt} should report a deprecation.
     * @param optValue    the value expected from {@code opt} (or {@code null} if none).
     * @param grpDep      {@code true} if querying the group should report a deprecation.
     * @param grpValue    the value expected from the group (or {@code null} if none).
     * @param grpOpt      the option the group is expected to resolve to.
     * @throws ParseException on parse error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testGetOptionValue(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final String optValue, final boolean grpDep, final String grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);

        // A supplier whose value matches DEFAULT_TEXT, used to exercise the Supplier-based overloads.
        final Supplier<String> defaultSupplier = () -> DEFAULT_TEXT;
        // Expected results from the default-value overloads: the real value if present, else the fallback.
        final String optWithDefault = optValue == null ? DEFAULT_TEXT : optValue;
        final String grpWithDefault = grpValue == null ? DEFAULT_TEXT : grpValue;

        // A group whose options were never parsed, and a null group, both yielding no value.
        final OptionGroup unselectedGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // Query by char name.
        assertEquals(optValue, commandLine.getOptionValue(asChar(opt)));
        checkHandler(optDep, handler, opt);
        assertEquals(optWithDefault, commandLine.getOptionValue(asChar(opt), DEFAULT_TEXT));
        checkHandler(optDep, handler, opt);
        assertEquals(optWithDefault, commandLine.getOptionValue(asChar(opt), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by short name.
        assertEquals(optValue, commandLine.getOptionValue(opt.getOpt()));
        checkHandler(optDep, handler, opt);
        assertEquals(optWithDefault, commandLine.getOptionValue(opt.getOpt(), DEFAULT_TEXT));
        checkHandler(optDep, handler, opt);
        assertEquals(optWithDefault, commandLine.getOptionValue(opt.getOpt(), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by long name.
        assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);
        assertEquals(optWithDefault, commandLine.getOptionValue(opt.getLongOpt(), DEFAULT_TEXT));
        checkHandler(optDep, handler, opt);
        assertEquals(optWithDefault, commandLine.getOptionValue(opt.getLongOpt(), defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by Option instance.
        assertEquals(optValue, commandLine.getOptionValue(opt));
        checkHandler(optDep, handler, opt);
        assertEquals(optWithDefault, commandLine.getOptionValue(opt, DEFAULT_TEXT));
        checkHandler(optDep, handler, opt);
        assertEquals(optWithDefault, commandLine.getOptionValue(opt, defaultSupplier));
        checkHandler(optDep, handler, opt);

        // Query by OptionGroup.
        assertEquals(grpValue, commandLine.getOptionValue(optionGroup));
        checkHandler(grpDep, handler, grpOpt);
        assertEquals(grpWithDefault, commandLine.getOptionValue(optionGroup, DEFAULT_TEXT));
        checkHandler(grpDep, handler, grpOpt);
        assertEquals(grpWithDefault, commandLine.getOptionValue(optionGroup, defaultSupplier));
        checkHandler(grpDep, handler, grpOpt);

        // A group with no selected option yields the default (or null) and never logs a deprecation.
        assertNull(commandLine.getOptionValue(unselectedGroup));
        checkHandler(false, handler, grpOpt);
        assertEquals(DEFAULT_TEXT, commandLine.getOptionValue(unselectedGroup, DEFAULT_TEXT));
        checkHandler(false, handler, grpOpt);
        assertEquals(DEFAULT_TEXT, commandLine.getOptionValue(unselectedGroup, defaultSupplier));
        checkHandler(false, handler, grpOpt);

        // A null group behaves like an unselected group.
        assertNull(commandLine.getOptionValue(nullGroup));
        checkHandler(false, handler, grpOpt);
        assertEquals(DEFAULT_TEXT, commandLine.getOptionValue(nullGroup, DEFAULT_TEXT));
        checkHandler(false, handler, grpOpt);
        assertEquals(DEFAULT_TEXT, commandLine.getOptionValue(nullGroup, defaultSupplier));
        checkHandler(false, handler, grpOpt);

        // An unknown name yields the default (or null) and never logs a deprecation.
        assertNull(commandLine.getOptionValue("Nope"));
        checkHandler(false, handler, opt);
        assertEquals(DEFAULT_TEXT, commandLine.getOptionValue("Nope", DEFAULT_TEXT));
        checkHandler(false, handler, opt);
        assertEquals(DEFAULT_TEXT, commandLine.getOptionValue("Nope", defaultSupplier));
        checkHandler(false, handler, opt);
    }
}
