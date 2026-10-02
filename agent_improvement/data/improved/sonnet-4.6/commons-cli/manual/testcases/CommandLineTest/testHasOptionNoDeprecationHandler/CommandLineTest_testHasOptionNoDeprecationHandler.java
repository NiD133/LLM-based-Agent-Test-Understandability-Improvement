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
 * Tests {@link CommandLine#hasOption} with the default deprecation handler
 * (which prints to {@link System#out}).
 *
 * <p>Option {@code -T / --tee} is marked deprecated; option {@code -U / --you} is not.
 * Each test case verifies that:
 * <ul>
 *   <li>querying a deprecated option triggers exactly one line written to stdout, and</li>
 *   <li>querying a non-deprecated option (or a non-existent flag) writes nothing.</li>
 * </ul>
 */
public class CommandLineTest_testHasOptionNoDeprecationHandler {

    /**
     * Provides test arguments for {@link #testHasOptionNoDeprecationHandler}.
     *
     * <p>Each row describes one parse scenario:
     * <ol>
     *   <li>{@code args}      – command-line tokens to parse</li>
     *   <li>{@code opt}       – the {@link Option} whose {@code hasOption} result is being checked</li>
     *   <li>{@code optionGroup} – the group containing both options</li>
     *   <li>{@code optIsDeprecated} – whether querying {@code opt} should trigger the deprecation handler</li>
     *   <li>{@code optPresent}  – expected result of {@code hasOption(opt)}</li>
     *   <li>{@code grpIsDeprecated} – whether querying the group should trigger the deprecation handler</li>
     *   <li>{@code grpPresent}  – expected result of {@code hasOption(optionGroup)}</li>
     *   <li>{@code selectedGroupOpt} – the option actually selected in the group</li>
     * </ol>
     */
    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();

        // -T / --tee  is deprecated; -U / --you  is not
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        // --- Checking opt=T when T is on the command line ---
        // Querying optT: deprecated=true, present=true; querying group: deprecated=true, present=true
        lst.add(Arguments.of(new String[] { "-T" },           optT, optionGroup, true,  true,  true,  true,  optT));
        lst.add(Arguments.of(new String[] { "-T", "foo" },    optT, optionGroup, true,  true,  true,  true,  optT));
        lst.add(Arguments.of(new String[] { "--tee" },        optT, optionGroup, true,  true,  true,  true,  optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true,  true,  true,  true,  optT));

        // --- Checking opt=T when U is on the command line ---
        // Querying optT: deprecated=false (T not present), present=false; querying group: deprecated=false (U not deprecated), present=true
        lst.add(Arguments.of(new String[] { "-U" },                optT, optionGroup, false, false, false, true,  optU));
        lst.add(Arguments.of(new String[] { "-U", "foo", "bar" },  optT, optionGroup, false, false, false, true,  optU));
        lst.add(Arguments.of(new String[] { "--you" },             optT, optionGroup, false, false, false, true,  optU));
        lst.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optT, optionGroup, false, false, false, true, optU));

        // --- Checking opt=U when T is on the command line ---
        // Querying optU: not deprecated, not present; querying group: deprecated (selected T is deprecated), present=true
        lst.add(Arguments.of(new String[] { "-T" },                optU, optionGroup, false, false, true,  true,  optT));
        lst.add(Arguments.of(new String[] { "-T", "foo", "bar" },  optU, optionGroup, false, false, true,  true,  optT));
        lst.add(Arguments.of(new String[] { "--tee" },             optU, optionGroup, false, false, true,  true,  optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, optU, optionGroup, false, false, true, true,  optT));

        // --- Checking opt=U when U is on the command line ---
        // Querying optU: not deprecated, present; querying group: not deprecated (U selected), present=true
        lst.add(Arguments.of(new String[] { "-U" },                optU, optionGroup, false, true,  false, true,  optU));
        lst.add(Arguments.of(new String[] { "-U", "foo", "bar" },  optU, optionGroup, false, true,  false, true,  optU));
        lst.add(Arguments.of(new String[] { "--you" },             optU, optionGroup, false, true,  false, true,  optU));
        lst.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optU, optionGroup, false, true, false, true,  optU));

        return lst.stream();
    }

    /** Returns the short-option character of the given option. */
    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Asserts that the captured stdout contains the expected deprecation warning
     * and then resets the buffer.
     *
     * @param expectDeprecationWarning {@code true} if a deprecation line should have been written
     * @param capturedOutput           the buffer that captured {@link System#out}
     */
    private void assertWritten(final boolean expectDeprecationWarning, final ByteArrayOutputStream capturedOutput) {
        System.out.flush();
        if (expectDeprecationWarning) {
            assertEquals("Option 'T''tee': Deprecated", capturedOutput.toString().trim());
        } else {
            assertEquals("", capturedOutput.toString());
        }
        capturedOutput.reset();
    }

    /**
     * Verifies every {@link CommandLine#hasOption} overload when the default deprecation
     * handler (stdout) is in effect.
     *
     * @param args             command-line tokens to parse
     * @param opt              the option whose presence is being queried
     * @param optionGroup      the group containing both options
     * @param optIsDeprecated  {@code true} when querying {@code opt} should trigger the handler
     * @param optPresent       expected return value of {@code hasOption(opt)}
     * @param grpIsDeprecated  {@code true} when querying the group should trigger the handler
     * @param grpPresent       expected return value of {@code hasOption(optionGroup)}
     * @param selectedGroupOpt the option that the group reports as selected (unused here, kept for parity)
     * @throws ParseException  on parsing error
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOptionNoDeprecationHandler(
            final String[] args,
            final Option opt,
            final OptionGroup optionGroup,
            final boolean optIsDeprecated,
            final boolean optPresent,
            final boolean grpIsDeprecated,
            final boolean grpPresent,
            final Option selectedGroupOpt) throws ParseException {

        final Options options = new Options().addOptionGroup(optionGroup);
        final CommandLine commandLine = DefaultParser.builder().get().parse(options, args);

        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(capturedOutput));

            // char overload
            assertEquals(optPresent, commandLine.hasOption(asChar(opt)));
            assertWritten(optIsDeprecated, capturedOutput);

            // short-name String overload
            assertEquals(optPresent, commandLine.hasOption(opt.getOpt()));
            assertWritten(optIsDeprecated, capturedOutput);

            // long-name String overload
            assertEquals(optPresent, commandLine.hasOption(opt.getLongOpt()));
            assertWritten(optIsDeprecated, capturedOutput);

            // Option-object overload
            assertEquals(optPresent, commandLine.hasOption(opt));
            assertWritten(optIsDeprecated, capturedOutput);

            // OptionGroup overload
            assertEquals(grpPresent, commandLine.hasOption(optionGroup));
            assertWritten(grpIsDeprecated, capturedOutput);

            // Non-existent option – must never trigger the handler and must return false
            assertFalse(commandLine.hasOption("Nope"));
            assertWritten(false, capturedOutput);

        } finally {
            System.setOut(originalOut);
        }
    }
}
