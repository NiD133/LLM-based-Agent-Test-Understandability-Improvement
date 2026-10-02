package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CommandLineTest_testHasOptionNullDeprecationHandler {

    /**
     * Builds test cases covering every combination of:
     *   - which option was supplied on the command line (-T/--tee or -U/--you)
     *   - which option object is queried via hasOption() (optT or optU)
     *   - with or without an argument value
     *
     * Parameter meanings per row:
     *   args        – raw command-line tokens to parse
     *   opt         – the Option object whose hasOption() overloads are exercised
     *   optionGroup – the OptionGroup that contains both options
     *   optDep      – whether opt is the deprecated -T option (informational)
     *   has         – expected return value of hasOption(opt)
     *   grpDep      – whether the selected group option is deprecated (informational)
     *   hasGrp      – expected return value of hasOption(optionGroup)
     *   grpOpt      – the Option that was actually selected in the group
     */
    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        final List<Arguments> cases = new ArrayList<>();

        // Querying optT when -T / --tee was supplied (the deprecated option is present)
        cases.add(Arguments.of(new String[] { "-T" },           optT, optionGroup, true,  true,  true,  true, optT));
        cases.add(Arguments.of(new String[] { "-T", "foo" },    optT, optionGroup, true,  true,  true,  true, optT));
        cases.add(Arguments.of(new String[] { "--tee" },        optT, optionGroup, true,  true,  true,  true, optT));
        cases.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true,  true,  true,  true, optT));

        // Querying optT when -U / --you was supplied (optT is absent)
        cases.add(Arguments.of(new String[] { "-U" },                    optT, optionGroup, false, false, false, true, optU));
        cases.add(Arguments.of(new String[] { "-U", "foo", "bar" },      optT, optionGroup, false, false, false, true, optU));
        cases.add(Arguments.of(new String[] { "--you" },                 optT, optionGroup, false, false, false, true, optU));
        cases.add(Arguments.of(new String[] { "--you", "foo", "bar" },   optT, optionGroup, false, false, false, true, optU));

        // Querying optU when -T / --tee was supplied (optU is absent, but the selected group option is deprecated)
        cases.add(Arguments.of(new String[] { "-T" },                    optU, optionGroup, false, false, true, true, optT));
        cases.add(Arguments.of(new String[] { "-T", "foo", "bar" },      optU, optionGroup, false, false, true, true, optT));
        cases.add(Arguments.of(new String[] { "--tee" },                 optU, optionGroup, false, false, true, true, optT));
        cases.add(Arguments.of(new String[] { "--tee", "foo", "bar" },   optU, optionGroup, false, false, true, true, optT));

        // Querying optU when -U / --you was supplied (the non-deprecated option is present)
        cases.add(Arguments.of(new String[] { "-U" },                    optU, optionGroup, false, true,  false, true, optU));
        cases.add(Arguments.of(new String[] { "-U", "foo", "bar" },      optU, optionGroup, false, true,  false, true, optU));
        cases.add(Arguments.of(new String[] { "--you" },                 optU, optionGroup, false, true,  false, true, optU));
        cases.add(Arguments.of(new String[] { "--you", "foo", "bar" },   optU, optionGroup, false, true,  false, true, optU));

        return cases.stream();
    }

    /** Returns the single-character short name of the given option. */
    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Asserts that nothing was written to the captured output stream, then resets it.
     * With a null deprecation handler every hasOption() call must remain silent.
     */
    private void assertNoDeprecationOutput(final ByteArrayOutputStream capturedOut) {
        System.out.flush();
        assertEquals("", capturedOut.toString(),
                "No deprecation output expected when the deprecation handler is null");
        capturedOut.reset();
    }

    /**
     * Verifies that {@link CommandLine#hasOption} never triggers deprecation output
     * when the parser is built with {@code setDeprecatedHandler(null)}.
     *
     * <p>Even when the deprecated {@code -T / --tee} option is queried, the null
     * handler must suppress all output across every hasOption() overload:
     * char, short String, long String, Option object, and OptionGroup.</p>
     *
     * @param args        command-line tokens to parse
     * @param opt         the Option whose presence is being checked
     * @param optionGroup the group containing both options
     * @param optDep      whether {@code opt} is the deprecated option (informational)
     * @param has         expected return value of {@code hasOption(opt)}
     * @param grpDep      whether the selected group option is deprecated (informational)
     * @param hasGrp      expected return value of {@code hasOption(optionGroup)}
     * @param grpOpt      the Option that was actually selected in the group
     * @throws ParseException on parsing error
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOptionNullDeprecationHandler(
            final String[] args,
            final Option opt,
            final OptionGroup optionGroup,
            final boolean optDep,
            final boolean has,
            final boolean grpDep,
            final boolean hasGrp,
            final Option grpOpt) throws ParseException {

        final Options options = new Options().addOptionGroup(optionGroup);
        // A null deprecation handler means the CommandLine must never produce any output,
        // even when a deprecated option is accessed via hasOption().
        final CommandLine commandLine = DefaultParser.builder()
                .setDeprecatedHandler(null)
                .get()
                .parse(options, args);

        final ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(capturedOut));

            // char overload
            assertEquals(has, commandLine.hasOption(asChar(opt)));
            assertNoDeprecationOutput(capturedOut);

            // short name (String) overload
            assertEquals(has, commandLine.hasOption(opt.getOpt()));
            assertNoDeprecationOutput(capturedOut);

            // long name (String) overload
            assertEquals(has, commandLine.hasOption(opt.getLongOpt()));
            assertNoDeprecationOutput(capturedOut);

            // Option object overload
            assertEquals(has, commandLine.hasOption(opt));
            assertNoDeprecationOutput(capturedOut);

            // OptionGroup overload
            assertEquals(hasGrp, commandLine.hasOption(optionGroup));
            assertNoDeprecationOutput(capturedOut);

            // Non-existent option — always false, never any output
            assertFalse(commandLine.hasOption("Nope"));
            assertNoDeprecationOutput(capturedOut);
        } finally {
            System.setOut(originalOut);
        }
    }
}
