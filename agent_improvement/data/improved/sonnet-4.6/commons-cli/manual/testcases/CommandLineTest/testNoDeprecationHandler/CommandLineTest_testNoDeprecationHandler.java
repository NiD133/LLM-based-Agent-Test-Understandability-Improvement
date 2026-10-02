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

public class CommandLineTest_testNoDeprecationHandler {

    private static Stream<Arguments> createOptionValueParameters() throws ParseException {
        final List<Arguments> cases = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        // Query option = optT (deprecated); command line selects -T or -U
        cases.add(Arguments.of(new String[] { "-T" },           optT, optionGroup, true,  null,  true,  null,  optT));
        cases.add(Arguments.of(new String[] { "-T", "foo" },    optT, optionGroup, true,  "foo", true,  "foo", optT));
        cases.add(Arguments.of(new String[] { "--tee" },        optT, optionGroup, true,  null,  true,  null,  optT));
        cases.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true,  "foo", true,  "foo", optT));
        cases.add(Arguments.of(new String[] { "-U" },           optT, optionGroup, false, null,  false, null,  optU));
        cases.add(Arguments.of(new String[] { "-U", "foo" },    optT, optionGroup, false, null,  false, "foo", optU));
        cases.add(Arguments.of(new String[] { "--you" },        optT, optionGroup, false, null,  false, null,  optU));
        cases.add(Arguments.of(new String[] { "--you", "foo" }, optT, optionGroup, false, null,  false, "foo", optU));
        // Query option = optU (non-deprecated); command line selects -T or -U
        cases.add(Arguments.of(new String[] { "-T" },           optU, optionGroup, false, null,  true,  null,  optT));
        cases.add(Arguments.of(new String[] { "-T", "foo" },    optU, optionGroup, false, null,  true,  "foo", optT));
        cases.add(Arguments.of(new String[] { "--tee" },        optU, optionGroup, false, null,  true,  null,  optT));
        cases.add(Arguments.of(new String[] { "--tee", "foo" }, optU, optionGroup, false, null,  true,  "foo", optT));
        cases.add(Arguments.of(new String[] { "-U" },           optU, optionGroup, false, null,  false, null,  optU));
        cases.add(Arguments.of(new String[] { "-U", "foo" },    optU, optionGroup, false, "foo", false, "foo", optU));
        cases.add(Arguments.of(new String[] { "--you" },        optU, optionGroup, false, null,  false, null,  optU));
        cases.add(Arguments.of(new String[] { "--you", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));
        return cases.stream();
    }

    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Asserts that the default deprecation handler did (or did not) write a message to the
     * captured output stream, then resets the stream for the next assertion.
     *
     * @param expectDeprecation true if a deprecation line should have been logged.
     * @param capturedOutput    stream that is currently capturing {@link System#out}.
     */
    private void assertDeprecationLogged(final boolean expectDeprecation, final ByteArrayOutputStream capturedOutput) {
        System.out.flush();
        if (expectDeprecation) {
            assertEquals("Option 'T''tee': Deprecated", capturedOutput.toString().trim());
        } else {
            assertEquals("", capturedOutput.toString());
        }
        capturedOutput.reset();
    }

    /**
     * Verifies that every {@link CommandLine#getOptionValue} overload correctly returns the
     * expected value and that the default deprecation handler (which prints to {@link System#out})
     * fires exactly once per call on a deprecated option and not at all on non-deprecated options.
     *
     * <p>Parameters (from {@code createOptionValueParameters}):
     * <ul>
     *   <li>{@code args}        – command-line tokens to parse
     *   <li>{@code opt}         – the option whose value is queried directly
     *   <li>{@code optionGroup} – group containing both -T (deprecated) and -U
     *   <li>{@code optDep}      – whether a deprecation log is expected when querying {@code opt}
     *   <li>{@code optValue}    – expected value when querying {@code opt}
     *   <li>{@code grpDep}      – whether a deprecation log is expected when querying the group
     *   <li>{@code grpValue}    – expected value when querying the group
     *   <li>{@code grpOpt}      – which option the group resolves to (informational)
     * </ul>
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testNoDeprecationHandler(
            final String[] args,
            final Option opt,
            final OptionGroup optionGroup,
            final boolean optDep,
            final String optValue,
            final boolean grpDep,
            final String grpValue,
            final Option grpOpt) throws ParseException {

        final Options options = new Options().addOptionGroup(optionGroup);
        final CommandLine commandLine = DefaultParser.builder().get().parse(options, args);

        final Supplier<String> defaultValueSupplier = () -> "thing";
        final Supplier<String> nullSupplier = null;

        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(capturedOutput));

            final OptionGroup otherGroup = new OptionGroup()
                    .addOption(Option.builder("o").longOpt("other").hasArg().get())
                    .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
            final OptionGroup nullGroup = null;

            // --- Query by char ---
            assertEquals(optValue, commandLine.getOptionValue(asChar(opt)));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(asChar(opt), "thing"));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(asChar(opt), defaultValueSupplier));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue, commandLine.getOptionValue(asChar(opt), nullSupplier));
            assertDeprecationLogged(optDep, capturedOutput);

            // --- Query by short option name ---
            assertEquals(optValue, commandLine.getOptionValue(opt.getOpt()));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getOpt(), "thing"));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getOpt(), defaultValueSupplier));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue, commandLine.getOptionValue(opt.getOpt(), nullSupplier));
            assertDeprecationLogged(optDep, capturedOutput);

            // --- Query by long option name ---
            assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt()));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getLongOpt(), "thing"));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt.getLongOpt(), defaultValueSupplier));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue, commandLine.getOptionValue(opt.getLongOpt(), nullSupplier));
            assertDeprecationLogged(optDep, capturedOutput);

            // --- Query by Option object ---
            assertEquals(optValue, commandLine.getOptionValue(opt));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt, "thing"));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue == null ? "thing" : optValue, commandLine.getOptionValue(opt, defaultValueSupplier));
            assertDeprecationLogged(optDep, capturedOutput);
            assertEquals(optValue, commandLine.getOptionValue(opt, nullSupplier));
            assertDeprecationLogged(optDep, capturedOutput);

            // --- Query by the OptionGroup that contains both -T and -U ---
            assertEquals(grpValue, commandLine.getOptionValue(optionGroup));
            assertDeprecationLogged(grpDep, capturedOutput);
            assertEquals(grpValue == null ? "thing" : grpValue, commandLine.getOptionValue(optionGroup, "thing"));
            assertDeprecationLogged(grpDep, capturedOutput);
            assertEquals(grpValue == null ? "thing" : grpValue, commandLine.getOptionValue(optionGroup, defaultValueSupplier));
            assertDeprecationLogged(grpDep, capturedOutput);
            assertEquals(grpValue, commandLine.getOptionValue(optionGroup, nullSupplier));
            assertDeprecationLogged(grpDep, capturedOutput);

            // --- Query by a group that was not used on the command line (no deprecation expected) ---
            assertNull(commandLine.getOptionValue(otherGroup));
            assertDeprecationLogged(false, capturedOutput);
            assertEquals("thing", commandLine.getOptionValue(otherGroup, "thing"));
            assertDeprecationLogged(false, capturedOutput);
            assertEquals("thing", commandLine.getOptionValue(otherGroup, defaultValueSupplier));
            assertDeprecationLogged(false, capturedOutput);
            assertNull(commandLine.getOptionValue(otherGroup, nullSupplier));
            assertDeprecationLogged(false, capturedOutput);

            // --- Query by null group (no deprecation expected) ---
            assertNull(commandLine.getOptionValue(nullGroup));
            assertDeprecationLogged(false, capturedOutput);
            assertEquals("thing", commandLine.getOptionValue(nullGroup, "thing"));
            assertDeprecationLogged(false, capturedOutput);
            assertEquals("thing", commandLine.getOptionValue(nullGroup, defaultValueSupplier));
            assertDeprecationLogged(false, capturedOutput);
            assertNull(commandLine.getOptionValue(nullGroup, nullSupplier));
            assertDeprecationLogged(false, capturedOutput);

            // --- Query by an unrecognised option name (no deprecation expected) ---
            assertNull(commandLine.getOptionValue("Nope"));
            assertDeprecationLogged(false, capturedOutput);
            assertEquals("thing", commandLine.getOptionValue("Nope", "thing"));
            assertDeprecationLogged(false, capturedOutput);
            assertEquals("thing", commandLine.getOptionValue("Nope", defaultValueSupplier));
            assertDeprecationLogged(false, capturedOutput);
            assertNull(commandLine.getOptionValue("Nope", nullSupplier));
            assertDeprecationLogged(false, capturedOutput);
        } finally {
            System.setOut(originalOut);
        }
    }
}
