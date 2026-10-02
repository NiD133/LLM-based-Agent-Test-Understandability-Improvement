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

public class CommandLineTest_testGetOptionValues {

    /**
     * Provides test cases for {@link #testGetOptionValues}.
     * <p>
     * Option T is deprecated with an optional two-value argument ({@code --tee}).
     * Option U is non-deprecated with an optional two-value argument ({@code --you}).
     * They belong to a mutual-exclusion {@link OptionGroup}.
     * </p>
     * <p>
     * Each argument tuple corresponds to the {@code testGetOptionValues} parameters, in order:
     * <ol>
     *   <li>{@code args}      – command-line tokens to parse</li>
     *   <li>{@code opt}       – the {@link Option} queried via every {@code getOptionValues} overload</li>
     *   <li>{@code optionGroup} – the {@link OptionGroup} containing both T and U</li>
     *   <li>{@code optDep}    – {@code true} when calling {@code getOptionValues(opt)} should fire the deprecation handler</li>
     *   <li>{@code optValue}  – expected array from {@code getOptionValues(opt)}, or {@code null} if opt is not selected</li>
     *   <li>{@code grpDep}    – {@code true} when calling {@code getOptionValues(optionGroup)} should fire the deprecation handler</li>
     *   <li>{@code grpValue}  – expected array from {@code getOptionValues(optionGroup)}, or {@code null}</li>
     *   <li>{@code grpOpt}    – the option expected to be selected within the group</li>
     * </ol>
     * </p>
     */
    private static Stream<Arguments> createOptionValuesParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").numberOfArgs(2).deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").numberOfArgs(2).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final String[] foobar = { "foo", "bar" };

        // --- Querying optT when option T is active (deprecated) ---
        // getOptionValues(optT) fires the handler (optDep=true); group also resolves to deprecated T (grpDep=true)
        lst.add(Arguments.of(new String[] { "-T" },                   optT, optionGroup, true,  null,   true,  null,   optT));
        lst.add(Arguments.of(new String[] { "-T", "foo", "bar" },     optT, optionGroup, true,  foobar, true,  foobar, optT));
        lst.add(Arguments.of(new String[] { "--tee" },                optT, optionGroup, true,  null,   true,  null,   optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo", "bar" },  optT, optionGroup, true,  foobar, true,  foobar, optT));

        // --- Querying optT when option U is active ---
        // optT is not selected → no deprecation handler, values=null; group resolves to non-deprecated U (grpDep=false)
        lst.add(Arguments.of(new String[] { "-U" },                   optT, optionGroup, false, null,   false, null,   optU));
        lst.add(Arguments.of(new String[] { "-U", "foo", "bar" },     optT, optionGroup, false, null,   false, foobar, optU));
        lst.add(Arguments.of(new String[] { "--you" },                optT, optionGroup, false, null,   false, null,   optU));
        lst.add(Arguments.of(new String[] { "--you", "foo", "bar" },  optT, optionGroup, false, null,   false, foobar, optU));

        // --- Querying optU when option T is active ---
        // optU is not selected → values=null, no handler; group resolves to deprecated T (grpDep=true)
        lst.add(Arguments.of(new String[] { "-T" },                   optU, optionGroup, false, null,   true,  null,   optT));
        lst.add(Arguments.of(new String[] { "-T", "foo", "bar" },     optU, optionGroup, false, null,   true,  foobar, optT));
        lst.add(Arguments.of(new String[] { "--tee" },                optU, optionGroup, false, null,   true,  null,   optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo", "bar" },  optU, optionGroup, false, null,   true,  foobar, optT));

        // --- Querying optU when option U is active (non-deprecated) ---
        // getOptionValues(optU) does not fire the handler; group also resolves to non-deprecated U (grpDep=false)
        lst.add(Arguments.of(new String[] { "-U" },                   optU, optionGroup, false, null,   false, null,   optU));
        lst.add(Arguments.of(new String[] { "-U", "foo", "bar" },     optU, optionGroup, false, foobar, false, foobar, optU));
        lst.add(Arguments.of(new String[] { "--you" },                optU, optionGroup, false, null,   false, null,   optU));
        lst.add(Arguments.of(new String[] { "--you", "foo", "bar" },  optU, optionGroup, false, foobar, false, foobar, optU));

        return lst.stream();
    }

    /** Extracts the single-character option key from an {@link Option}. */
    char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Asserts that the deprecation handler was invoked exactly once (when expected) or not at all,
     * then clears the handler list for the next assertion.
     *
     * @param optDep  {@code true} if the handler should have been called exactly once.
     * @param handler the list that the handler appends to on each invocation.
     * @param opt     the option expected to have triggered the handler; ignored when {@code optDep} is {@code false}.
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

    /**
     * Verifies {@link CommandLine#getOptionValues} across all its overloads (char, short name,
     * long name, {@link Option} object, and {@link OptionGroup}), and confirms that the deprecation
     * handler fires exactly once per call when the queried option is deprecated — never more,
     * regardless of the number of values stored.
     *
     * @param args        command-line tokens to parse.
     * @param opt         the option queried via every {@code getOptionValues} overload.
     * @param optionGroup the option group containing both test options.
     * @param optDep      {@code true} when {@code getOptionValues(opt)} should trigger the deprecation handler.
     * @param optValue    expected array from {@code getOptionValues(opt)}, or {@code null} if opt is not active.
     * @param grpDep      {@code true} when {@code getOptionValues(optionGroup)} should trigger the deprecation handler.
     * @param grpValue    expected array from {@code getOptionValues(optionGroup)}, or {@code null}.
     * @param grpOpt      the option the group is expected to have selected.
     * @throws ParseException on parse error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValuesParameters")
    void testGetOptionValues(
            final String[] args,
            final Option opt,
            final OptionGroup optionGroup,
            final boolean optDep,
            final String[] optValue,
            final boolean grpDep,
            final String[] grpValue,
            final Option grpOpt) throws ParseException {

        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder()
                .setDeprecatedHandler(handler::add)
                .get()
                .parse(options, args);

        // A group whose options were not parsed, used to confirm null return for unselected groups
        final OptionGroup otherGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // Verify via char key
        assertArrayEquals(optValue, commandLine.getOptionValues(asChar(opt)));
        checkHandler(optDep, handler, opt);

        // Verify via short option name string
        assertArrayEquals(optValue, commandLine.getOptionValues(opt.getOpt()));
        checkHandler(optDep, handler, opt);

        // Verify via long option name string
        assertArrayEquals(optValue, commandLine.getOptionValues(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);

        // Verify via Option object
        assertArrayEquals(optValue, commandLine.getOptionValues(opt));
        checkHandler(optDep, handler, opt);

        // Verify via OptionGroup – resolves to whichever option was actually selected
        assertArrayEquals(grpValue, commandLine.getOptionValues(optionGroup));
        checkHandler(grpDep, handler, grpOpt);

        // An unrecognised option name must return null and must not fire the handler
        assertNull(commandLine.getOptionValues("Nope"));
        checkHandler(false, handler, opt);

        // A group whose options were not parsed must return null and must not fire the handler
        assertNull(commandLine.getOptionValues(otherGroup));
        checkHandler(false, handler, grpOpt);

        // A null group reference must return null and must not fire the handler
        assertNull(commandLine.getOptionValues(nullGroup));
        checkHandler(false, handler, grpOpt);
    }
}
