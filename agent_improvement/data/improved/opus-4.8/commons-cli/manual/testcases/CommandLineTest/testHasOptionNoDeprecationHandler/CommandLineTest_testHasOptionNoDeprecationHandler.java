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

/**
 * Verifies the various {@code CommandLine.hasOption(...)} overloads when the command line is built with the default
 * deprecation handler (which prints a "Deprecated" line to {@link System#out} the first time a deprecated option is
 * queried).
 * <p>
 * Each test case parses an argument list against an {@link OptionGroup} containing two mutually exclusive options:
 * </p>
 * <ul>
 *   <li>{@code -T} / {@code --tee} &mdash; marked deprecated.</li>
 *   <li>{@code -U} / {@code --you} &mdash; not deprecated.</li>
 * </ul>
 */
public class CommandLineTest_testHasOptionNoDeprecationHandler {

    /**
     * Supplies the test cases for {@link #testHasOptionNoDeprecationHandler}.
     * <p>
     * Each row is {@code (args, opt, optionGroup, optDep, has, grpDep, hasGrp, grpOpt)} where:
     * </p>
     * <ul>
     *   <li>{@code args}        &mdash; the command-line arguments to parse.</li>
     *   <li>{@code opt}         &mdash; the option queried directly via the char/short/long/Option overloads.</li>
     *   <li>{@code optionGroup} &mdash; the option group queried via the OptionGroup overload.</li>
     *   <li>{@code optDep}      &mdash; whether querying {@code opt} should emit a deprecation message.</li>
     *   <li>{@code has}         &mdash; expected result of {@code hasOption(opt)}.</li>
     *   <li>{@code grpDep}      &mdash; whether querying {@code optionGroup} should emit a deprecation message.</li>
     *   <li>{@code hasGrp}      &mdash; expected result of {@code hasOption(optionGroup)}.</li>
     *   <li>{@code grpOpt}      &mdash; the option the group is expected to resolve to (documentation only).</li>
     * </ul>
     */
    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        final List<Arguments> cases = new ArrayList<>();
        // Deprecated option T is the one that was set on the command line.
        cases.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, true, true, true, optT));
        cases.add(Arguments.of(new String[] { "-T", "foo" }, optT, optionGroup, true, true, true, true, optT));
        cases.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, true, true, true, optT));
        cases.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true, true, true, true, optT));
        cases.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, false, false, true, optU));
        cases.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optT, optionGroup, false, false, false, true, optU));
        cases.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, false, false, true, optU));
        cases.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optT, optionGroup, false, false, false, true, optU));
        // Non-deprecated option U is the one that was set on the command line.
        cases.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, false, true, true, optT));
        cases.add(Arguments.of(new String[] { "-T", "foo", "bar" }, optU, optionGroup, false, false, true, true, optT));
        cases.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, false, true, true, optT));
        cases.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, optU, optionGroup, false, false, true, true, optT));
        cases.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, true, false, true, optU));
        cases.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optU, optionGroup, false, true, false, true, optU));
        cases.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, true, false, true, optU));
        cases.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optU, optionGroup, false, true, false, true, optU));
        return cases.stream();
    }

    /** Returns the single-character short name of an option (e.g. 'T' for {@code -T}). */
    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Asserts what the default deprecation handler wrote to {@code System.out} since the last check, then clears the
     * buffer ready for the next assertion.
     *
     * @param expectDeprecationMessage {@code true} if a deprecation line for option {@code T} was expected.
     * @param capturedOut              the buffer capturing {@code System.out}.
     */
    private void assertWritten(final boolean expectDeprecationMessage, final ByteArrayOutputStream capturedOut) {
        System.out.flush();
        if (expectDeprecationMessage) {
            assertEquals("Option 'T''tee': Deprecated", capturedOut.toString().trim());
        } else {
            assertEquals("", capturedOut.toString());
        }
        capturedOut.reset();
    }

    /**
     * Exercises every {@code hasOption} overload (char, short name, long name, {@link Option}, and {@link OptionGroup})
     * and checks both the boolean result and the deprecation message emitted as a side effect.
     *
     * @param args        the argument strings to parse.
     * @param opt         the option to query directly.
     * @param optionGroup the option group to query.
     * @param optDep      {@code true} if querying {@code opt} should log a deprecation message.
     * @param has         {@code true} if {@code opt} is expected to be present.
     * @param grpDep      {@code true} if querying the group should log a deprecation message.
     * @param hasGrp      {@code true} if the group is expected to be present.
     * @param grpOpt      the option the group is expected to resolve to (documentation only; unused here).
     * @throws ParseException on parsing error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOptionNoDeprecationHandler(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep, final boolean has, final boolean grpDep, final boolean hasGrp, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
        final CommandLine commandLine = DefaultParser.builder().get().parse(options, args);
        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(capturedOut));
            // Query by char short name, e.g. hasOption('T').
            assertEquals(has, commandLine.hasOption(asChar(opt)));
            assertWritten(optDep, capturedOut);
            // Query by String short name, e.g. hasOption("T").
            assertEquals(has, commandLine.hasOption(opt.getOpt()));
            assertWritten(optDep, capturedOut);
            // Query by String long name, e.g. hasOption("tee").
            assertEquals(has, commandLine.hasOption(opt.getLongOpt()));
            assertWritten(optDep, capturedOut);
            // Query by Option instance.
            assertEquals(has, commandLine.hasOption(opt));
            assertWritten(optDep, capturedOut);
            // Query by OptionGroup.
            assertEquals(hasGrp, commandLine.hasOption(optionGroup));
            assertWritten(grpDep, capturedOut);
            // An unknown option is never present and never deprecated.
            assertFalse(commandLine.hasOption("Nope"));
            assertWritten(false, capturedOut);
        } finally {
            System.setOut(originalOut);
        }
    }
}
