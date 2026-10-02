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
 * Verifies {@link CommandLine#hasOption} when the parser is configured with a
 * {@code null} deprecated-option handler.
 * <p>
 * The key behavior under test: with a {@code null} handler, querying a deprecated
 * option must never print a deprecation notice to {@code System.out}. Each case
 * therefore asserts both the expected {@code hasOption} result and that nothing
 * was written to the captured output stream.
 * </p>
 */
public class CommandLineTest_testHasOptionNullDeprecationHandler {

    /**
     * Supplies the parsing scenarios for {@link #hasOptionWithNullDeprecationHandler}.
     * <p>
     * The option group contains a deprecated option {@code -T/--tee} and a regular
     * option {@code -U/--you}; selecting one deselects the other. Each case provides:
     * </p>
     * <ol>
     * <li>the command-line arguments to parse,</li>
     * <li>the option to query for presence,</li>
     * <li>the option group to query for presence,</li>
     * <li>whether that option is expected to be present,</li>
     * <li>whether the group is expected to be present.</li>
     * </ol>
     */
    private static Stream<Arguments> hasOptionTestCases() throws ParseException {
        final Option deprecatedTee = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option regularYou = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup group = new OptionGroup().addOption(deprecatedTee).addOption(regularYou);

        final List<Arguments> cases = new ArrayList<>();
        // The deprecated "-T/--tee" option is selected by the parsed arguments.
        cases.add(Arguments.of(new String[] { "-T" }, deprecatedTee, group, true, true));
        cases.add(Arguments.of(new String[] { "-T", "foo" }, deprecatedTee, group, true, true));
        cases.add(Arguments.of(new String[] { "--tee" }, deprecatedTee, group, true, true));
        cases.add(Arguments.of(new String[] { "--tee", "foo" }, deprecatedTee, group, true, true));
        cases.add(Arguments.of(new String[] { "-U" }, deprecatedTee, group, false, true));
        cases.add(Arguments.of(new String[] { "-U", "foo", "bar" }, deprecatedTee, group, false, true));
        cases.add(Arguments.of(new String[] { "--you" }, deprecatedTee, group, false, true));
        cases.add(Arguments.of(new String[] { "--you", "foo", "bar" }, deprecatedTee, group, false, true));
        // The regular "-U/--you" option is queried against the same parsed arguments.
        cases.add(Arguments.of(new String[] { "-T" }, regularYou, group, false, true));
        cases.add(Arguments.of(new String[] { "-T", "foo", "bar" }, regularYou, group, false, true));
        cases.add(Arguments.of(new String[] { "--tee" }, regularYou, group, false, true));
        cases.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, regularYou, group, false, true));
        cases.add(Arguments.of(new String[] { "-U" }, regularYou, group, true, true));
        cases.add(Arguments.of(new String[] { "-U", "foo", "bar" }, regularYou, group, true, true));
        cases.add(Arguments.of(new String[] { "--you" }, regularYou, group, true, true));
        cases.add(Arguments.of(new String[] { "--you", "foo", "bar" }, regularYou, group, true, true));
        return cases.stream();
    }

    /** Returns the single-character short name of an option (e.g. 'T' for "-T"). */
    private char shortNameChar(final Option option) {
        return option.getOpt().charAt(0);
    }

    /**
     * Asserts that nothing was printed to the captured output, then clears it so the
     * next query starts from a clean slate. With a {@code null} deprecation handler,
     * every {@code hasOption} call is expected to stay silent.
     */
    private void assertNothingWritten(final ByteArrayOutputStream capturedOut) {
        System.out.flush();
        assertEquals("", capturedOut.toString());
        capturedOut.reset();
    }

    /**
     * Queries the parsed command line for an option through every {@code hasOption}
     * overload and confirms each returns the expected presence flag without emitting
     * a deprecation notice.
     *
     * @param args            the arguments to parse.
     * @param option          the option to query (may be the deprecated one).
     * @param group           the option group to query.
     * @param optionIsPresent expected result of querying {@code option}.
     * @param groupIsPresent  expected result of querying {@code group}.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("hasOptionTestCases")
    void hasOptionWithNullDeprecationHandler(final String[] args, final Option option, final OptionGroup group,
            final boolean optionIsPresent, final boolean groupIsPresent) throws ParseException {
        final Options options = new Options().addOptionGroup(group);
        final ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(null).get().parse(options, args);

        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(capturedOut));

            // Query by char short name, e.g. hasOption('T').
            assertEquals(optionIsPresent, commandLine.hasOption(shortNameChar(option)));
            assertNothingWritten(capturedOut);

            // Query by String short name, e.g. hasOption("T").
            assertEquals(optionIsPresent, commandLine.hasOption(option.getOpt()));
            assertNothingWritten(capturedOut);

            // Query by long name, e.g. hasOption("tee").
            assertEquals(optionIsPresent, commandLine.hasOption(option.getLongOpt()));
            assertNothingWritten(capturedOut);

            // Query by Option instance.
            assertEquals(optionIsPresent, commandLine.hasOption(option));
            assertNothingWritten(capturedOut);

            // Query by OptionGroup instance.
            assertEquals(groupIsPresent, commandLine.hasOption(group));
            assertNothingWritten(capturedOut);

            // Query an unknown option name: always absent.
            assertFalse(commandLine.hasOption("Nope"));
            assertNothingWritten(capturedOut);
        } finally {
            System.setOut(originalOut);
        }
    }
}
