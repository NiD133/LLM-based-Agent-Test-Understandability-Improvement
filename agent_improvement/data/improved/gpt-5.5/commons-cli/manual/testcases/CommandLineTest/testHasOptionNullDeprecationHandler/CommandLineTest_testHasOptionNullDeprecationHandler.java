package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CommandLineTest_testHasOptionNullDeprecationHandler {

    private static final boolean DEPRECATED_OPTION_QUERIED = true;
    private static final boolean NON_DEPRECATED_OPTION_QUERIED = false;
    private static final boolean HAS_OPTION = true;
    private static final boolean MISSING_OPTION = false;
    private static final boolean DEPRECATED_GROUP_SELECTION = true;

    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final Option deprecatedTee = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option regularYou = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(deprecatedTee).addOption(regularYou);

        return Stream.of(
                // Querying the deprecated -T/--tee option when it was selected.
                hasOptionCase(new String[] { "-T" }, deprecatedTee, optionGroup, DEPRECATED_OPTION_QUERIED, HAS_OPTION, DEPRECATED_GROUP_SELECTION,
                        HAS_OPTION, deprecatedTee),
                hasOptionCase(new String[] { "-T", "foo" }, deprecatedTee, optionGroup, DEPRECATED_OPTION_QUERIED, HAS_OPTION,
                        DEPRECATED_GROUP_SELECTION, HAS_OPTION, deprecatedTee),
                hasOptionCase(new String[] { "--tee" }, deprecatedTee, optionGroup, DEPRECATED_OPTION_QUERIED, HAS_OPTION,
                        DEPRECATED_GROUP_SELECTION, HAS_OPTION, deprecatedTee),
                hasOptionCase(new String[] { "--tee", "foo" }, deprecatedTee, optionGroup, DEPRECATED_OPTION_QUERIED, HAS_OPTION,
                        DEPRECATED_GROUP_SELECTION, HAS_OPTION, deprecatedTee),

                // Querying the deprecated -T/--tee option when the regular -U/--you option was selected.
                hasOptionCase(new String[] { "-U" }, deprecatedTee, optionGroup, NON_DEPRECATED_OPTION_QUERIED, MISSING_OPTION,
                        NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION, regularYou),
                hasOptionCase(new String[] { "-U", "foo", "bar" }, deprecatedTee, optionGroup, NON_DEPRECATED_OPTION_QUERIED, MISSING_OPTION,
                        NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION, regularYou),
                hasOptionCase(new String[] { "--you" }, deprecatedTee, optionGroup, NON_DEPRECATED_OPTION_QUERIED, MISSING_OPTION,
                        NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION, regularYou),
                hasOptionCase(new String[] { "--you", "foo", "bar" }, deprecatedTee, optionGroup, NON_DEPRECATED_OPTION_QUERIED, MISSING_OPTION,
                        NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION, regularYou),

                // Querying the regular -U/--you option when the deprecated -T/--tee option was selected.
                hasOptionCase(new String[] { "-T" }, regularYou, optionGroup, NON_DEPRECATED_OPTION_QUERIED, MISSING_OPTION,
                        DEPRECATED_GROUP_SELECTION, HAS_OPTION, deprecatedTee),
                hasOptionCase(new String[] { "-T", "foo", "bar" }, regularYou, optionGroup, NON_DEPRECATED_OPTION_QUERIED, MISSING_OPTION,
                        DEPRECATED_GROUP_SELECTION, HAS_OPTION, deprecatedTee),
                hasOptionCase(new String[] { "--tee" }, regularYou, optionGroup, NON_DEPRECATED_OPTION_QUERIED, MISSING_OPTION,
                        DEPRECATED_GROUP_SELECTION, HAS_OPTION, deprecatedTee),
                hasOptionCase(new String[] { "--tee", "foo", "bar" }, regularYou, optionGroup, NON_DEPRECATED_OPTION_QUERIED, MISSING_OPTION,
                        DEPRECATED_GROUP_SELECTION, HAS_OPTION, deprecatedTee),

                // Querying the regular -U/--you option when it was selected.
                hasOptionCase(new String[] { "-U" }, regularYou, optionGroup, NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION,
                        NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION, regularYou),
                hasOptionCase(new String[] { "-U", "foo", "bar" }, regularYou, optionGroup, NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION,
                        NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION, regularYou),
                hasOptionCase(new String[] { "--you" }, regularYou, optionGroup, NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION,
                        NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION, regularYou),
                hasOptionCase(new String[] { "--you", "foo", "bar" }, regularYou, optionGroup, NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION,
                        NON_DEPRECATED_OPTION_QUERIED, HAS_OPTION, regularYou));
    }

    private static Arguments hasOptionCase(final String[] args, final Option optionToQuery, final OptionGroup optionGroup,
            final boolean optionDeprecationExpected, final boolean optionPresent, final boolean groupDeprecationExpected, final boolean groupPresent,
            final Option selectedGroupOption) {
        return Arguments.of(args, optionToQuery, optionGroup, optionDeprecationExpected, optionPresent, groupDeprecationExpected, groupPresent,
                selectedGroupOption);
    }

    private static char asChar(final Option option) {
        return option.getOpt().charAt(0);
    }

    private static void assertWritten(final boolean optionIsDeprecated, final ByteArrayOutputStream output) {
        System.out.flush();
        if (optionIsDeprecated) {
            assertEquals("Option 'T''tee': Deprecated", output.toString().trim());
        } else {
            assertEquals("", output.toString());
        }
        output.reset();
    }

    /**
     * Tests the hasOption calls.
     *
     * @param args the argument strings to parse.
     * @param optionToQuery the option to check.
     * @param optionGroup the option group to check.
     * @param optionDeprecationExpected {@code true} if querying the option should write a deprecation warning.
     * @param optionPresent {@code true} if the option is present.
     * @param groupDeprecationExpected {@code true} if querying the group should write a deprecation warning.
     * @param groupPresent {@code true} if the group is present.
     * @param selectedGroupOption the option expected to be selected by the group.
     * @throws ParseException on parsing error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOptionNullDeprecationHandler(final String[] args, final Option optionToQuery, final OptionGroup optionGroup,
            final boolean optionDeprecationExpected, final boolean optionPresent, final boolean groupDeprecationExpected, final boolean groupPresent,
            final Option selectedGroupOption) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(null).get().parse(options, args);
        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(output));

            assertEquals(optionPresent, commandLine.hasOption(asChar(optionToQuery)));
            assertWritten(false, output);

            assertEquals(optionPresent, commandLine.hasOption(optionToQuery.getOpt()));
            assertWritten(false, output);

            assertEquals(optionPresent, commandLine.hasOption(optionToQuery.getLongOpt()));
            assertWritten(false, output);

            assertEquals(optionPresent, commandLine.hasOption(optionToQuery));
            assertWritten(false, output);

            assertEquals(groupPresent, commandLine.hasOption(optionGroup));
            assertWritten(false, output);

            assertFalse(commandLine.hasOption("Nope"));
            assertWritten(false, output);
        } finally {
            System.setOut(originalOut);
        }
    }
}
