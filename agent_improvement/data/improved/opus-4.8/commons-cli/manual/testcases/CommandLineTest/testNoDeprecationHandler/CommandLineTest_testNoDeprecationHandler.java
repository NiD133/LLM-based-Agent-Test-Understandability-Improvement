package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies the various {@link CommandLine#getOptionValue} overloads, including the side effect that querying a
 * deprecated option through the default deprecation handler prints a notice to {@link System#out}.
 */
public class CommandLineTest_testNoDeprecationHandler {

    /**
     * Supplies the test cases for {@link #testNoDeprecationHandler}.
     * <p>
     * Two options share an {@link OptionGroup}: the deprecated {@code -T/--tee} and the regular {@code -U/--you}.
     * Each row supplies, in order:
     * </p>
     * <ol>
     * <li>{@code args}        - the command-line arguments to parse</li>
     * <li>{@code opt}         - the option to look up directly</li>
     * <li>{@code optionGroup} - the group containing both options</li>
     * <li>{@code optDeprecated} - whether looking up {@code opt} should print a deprecation notice</li>
     * <li>{@code optValue}    - the expected value of {@code opt}</li>
     * <li>{@code grpDeprecated} - whether looking up the group should print a deprecation notice</li>
     * <li>{@code grpValue}    - the expected value of the group's selected option</li>
     * <li>{@code grpOpt}      - the group's selected option</li>
     * </ol>
     */
    private static Stream<Arguments> createOptionValueParameters() throws ParseException {
        final List<Arguments> testCases = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        // Cases where the deprecated option T is the one being looked up.
        testCases.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, null, true, null, optT));
        testCases.add(Arguments.of(new String[] { "-T", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        testCases.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, null, true, null, optT));
        testCases.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        testCases.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, null, false, null, optU));
        testCases.add(Arguments.of(new String[] { "-U", "foo" }, optT, optionGroup, false, null, false, "foo", optU));
        testCases.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, null, false, null, optU));
        testCases.add(Arguments.of(new String[] { "--you", "foo" }, optT, optionGroup, false, null, false, "foo", optU));
        // Cases where the non-deprecated option U is the one being looked up.
        testCases.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, null, true, null, optT));
        testCases.add(Arguments.of(new String[] { "-T", "foo" }, optU, optionGroup, false, null, true, "foo", optT));
        testCases.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, null, true, null, optT));
        testCases.add(Arguments.of(new String[] { "--tee", "foo" }, optU, optionGroup, false, null, true, "foo", optT));
        testCases.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, null, false, null, optU));
        testCases.add(Arguments.of(new String[] { "-U", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));
        testCases.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, null, false, null, optU));
        testCases.add(Arguments.of(new String[] { "--you", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));
        return testCases.stream();
    }

    /**
     * Returns the short option name as a single character, for exercising the {@code char}-based overloads.
     */
    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Asserts what the default deprecation handler wrote to the captured output, then clears the buffer.
     *
     * @param expectDeprecationNotice {@code true} if the preceding lookup should have printed a deprecation notice.
     * @param captured                the buffer capturing {@link System#out}.
     */
    private void assertWritten(final boolean expectDeprecationNotice, final ByteArrayOutputStream captured) {
        System.out.flush();
        if (expectDeprecationNotice) {
            assertEquals("Option 'T''tee': Deprecated", captured.toString().trim());
        } else {
            assertEquals("", captured.toString());
        }
        captured.reset();
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testNoDeprecationHandler(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDeprecated,
            final String optValue, final boolean grpDeprecated, final String grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final CommandLine commandLine = DefaultParser.builder().get().parse(options, args);
        // A supplier that always yields "thing", used to exercise the Supplier-based default-value overloads.
        final Supplier<String> thingSupplier = () -> "thing";
        final Supplier<String> nullSupplier = null;
        // The value expected from a defaulting lookup: the option's value, or "thing" when the option is unset.
        final String optValueOrDefault = optValue == null ? "thing" : optValue;
        final String grpValueOrDefault = grpValue == null ? "thing" : grpValue;

        final ByteArrayOutputStream captured = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(captured));
            final OptionGroup otherGroup = new OptionGroup()
                    .addOption(Option.builder("o").longOpt("other").hasArg().get())
                    .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
            final OptionGroup nullGroup = null;

            // Look up by single-character option name.
            assertEquals(optValue, commandLine.getOptionValue(asChar(opt)));
            assertWritten(optDeprecated, captured);
            assertEquals(optValueOrDefault, commandLine.getOptionValue(asChar(opt), "thing"));
            assertWritten(optDeprecated, captured);
            assertEquals(optValueOrDefault, commandLine.getOptionValue(asChar(opt), thingSupplier));
            assertWritten(optDeprecated, captured);
            assertEquals(optValue, commandLine.getOptionValue(asChar(opt), nullSupplier));
            assertWritten(optDeprecated, captured);

            // Look up by short option name.
            assertEquals(optValue, commandLine.getOptionValue(opt.getOpt()));
            assertWritten(optDeprecated, captured);
            assertEquals(optValueOrDefault, commandLine.getOptionValue(opt.getOpt(), "thing"));
            assertWritten(optDeprecated, captured);
            assertEquals(optValueOrDefault, commandLine.getOptionValue(opt.getOpt(), thingSupplier));
            assertWritten(optDeprecated, captured);
            assertEquals(optValue, commandLine.getOptionValue(opt.getOpt(), nullSupplier));
            assertWritten(optDeprecated, captured);

            // Look up by long option name.
            assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt()));
            assertWritten(optDeprecated, captured);
            assertEquals(optValueOrDefault, commandLine.getOptionValue(opt.getLongOpt(), "thing"));
            assertWritten(optDeprecated, captured);
            assertEquals(optValueOrDefault, commandLine.getOptionValue(opt.getLongOpt(), thingSupplier));
            assertWritten(optDeprecated, captured);
            assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt(), nullSupplier));
            assertWritten(optDeprecated, captured);

            // Look up by Option instance.
            assertEquals(optValue, commandLine.getOptionValue(opt));
            assertWritten(optDeprecated, captured);
            assertEquals(optValueOrDefault, commandLine.getOptionValue(opt, "thing"));
            assertWritten(optDeprecated, captured);
            assertEquals(optValueOrDefault, commandLine.getOptionValue(opt, thingSupplier));
            assertWritten(optDeprecated, captured);
            assertEquals(optValue, commandLine.getOptionValue(opt, nullSupplier));
            assertWritten(optDeprecated, captured);

            // Look up by OptionGroup.
            assertEquals(grpValue, commandLine.getOptionValue(optionGroup));
            assertWritten(grpDeprecated, captured);
            assertEquals(grpValueOrDefault, commandLine.getOptionValue(optionGroup, "thing"));
            assertWritten(grpDeprecated, captured);
            assertEquals(grpValueOrDefault, commandLine.getOptionValue(optionGroup, thingSupplier));
            assertWritten(grpDeprecated, captured);
            assertEquals(grpValue, commandLine.getOptionValue(optionGroup, nullSupplier));
            assertWritten(grpDeprecated, captured);

            // A group that is present in neither the parsed options nor deprecated: always yields the default.
            assertNull(commandLine.getOptionValue(otherGroup));
            assertWritten(false, captured);
            assertEquals("thing", commandLine.getOptionValue(otherGroup, "thing"));
            assertWritten(false, captured);
            assertEquals("thing", commandLine.getOptionValue(otherGroup, thingSupplier));
            assertWritten(false, captured);
            assertNull(commandLine.getOptionValue(otherGroup, nullSupplier));
            assertWritten(false, captured);

            // A null group: always yields the default and never prints.
            assertNull(commandLine.getOptionValue(nullGroup));
            assertWritten(false, captured);
            assertEquals("thing", commandLine.getOptionValue(nullGroup, "thing"));
            assertWritten(false, captured);
            assertEquals("thing", commandLine.getOptionValue(nullGroup, thingSupplier));
            assertWritten(false, captured);
            assertNull(commandLine.getOptionValue(nullGroup, nullSupplier));
            assertWritten(false, captured);

            // An unknown option name: always yields the default and never prints.
            assertNull(commandLine.getOptionValue("Nope"));
            assertWritten(false, captured);
            assertEquals("thing", commandLine.getOptionValue("Nope", "thing"));
            assertWritten(false, captured);
            assertEquals("thing", commandLine.getOptionValue("Nope", thingSupplier));
            assertWritten(false, captured);
            assertNull(commandLine.getOptionValue("Nope", nullSupplier));
            assertWritten(false, captured);
        } finally {
            System.setOut(originalOut);
        }
    }
}
