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

public class CommandLineTest_testHasOptionNoDeprecationHandler {

    private static final String SHORT_T = "T";
    private static final String LONG_T = "tee";
    private static final String SHORT_U = "U";
    private static final String LONG_U = "you";

    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final List<Arguments> cases = new ArrayList<>();
        final Option deprecatedT = Option.builder().option(SHORT_T).longOpt(LONG_T).deprecated().optionalArg(true).get();
        final Option regularU = Option.builder(SHORT_U).longOpt(LONG_U).optionalArg(true).get();
        final OptionGroup group = new OptionGroup().addOption(deprecatedT).addOption(regularU);

        addCasesCheckingDeprecatedOption(cases, deprecatedT, regularU, group);
        addCasesCheckingRegularOption(cases, deprecatedT, regularU, group);
        return cases.stream();
    }

    private static void addCasesCheckingDeprecatedOption(final List<Arguments> cases, final Option deprecatedT, final Option regularU, final OptionGroup group) {
        addCase(cases, args("-T"), deprecatedT, group, true, true, true, true, deprecatedT);
        addCase(cases, args("-T", "foo"), deprecatedT, group, true, true, true, true, deprecatedT);
        addCase(cases, args("--tee"), deprecatedT, group, true, true, true, true, deprecatedT);
        addCase(cases, args("--tee", "foo"), deprecatedT, group, true, true, true, true, deprecatedT);

        addCase(cases, args("-U"), deprecatedT, group, false, false, false, true, regularU);
        addCase(cases, args("-U", "foo", "bar"), deprecatedT, group, false, false, false, true, regularU);
        addCase(cases, args("--you"), deprecatedT, group, false, false, false, true, regularU);
        addCase(cases, args("--you", "foo", "bar"), deprecatedT, group, false, false, false, true, regularU);
    }

    private static void addCasesCheckingRegularOption(final List<Arguments> cases, final Option deprecatedT, final Option regularU, final OptionGroup group) {
        addCase(cases, args("-T"), regularU, group, false, false, true, true, deprecatedT);
        addCase(cases, args("-T", "foo", "bar"), regularU, group, false, false, true, true, deprecatedT);
        addCase(cases, args("--tee"), regularU, group, false, false, true, true, deprecatedT);
        addCase(cases, args("--tee", "foo", "bar"), regularU, group, false, false, true, true, deprecatedT);

        addCase(cases, args("-U"), regularU, group, false, true, false, true, regularU);
        addCase(cases, args("-U", "foo", "bar"), regularU, group, false, true, false, true, regularU);
        addCase(cases, args("--you"), regularU, group, false, true, false, true, regularU);
        addCase(cases, args("--you", "foo", "bar"), regularU, group, false, true, false, true, regularU);
    }

    private static void addCase(final List<Arguments> cases, final String[] args, final Option optionToQuery, final OptionGroup group,
            final boolean optionIsDeprecated, final boolean optionIsPresent, final boolean groupOptionIsDeprecated, final boolean groupIsPresent,
            final Option expectedGroupOption) {
        cases.add(Arguments.of(args, optionToQuery, group, optionIsDeprecated, optionIsPresent, groupOptionIsDeprecated, groupIsPresent, expectedGroupOption));
    }

    private static String[] args(final String... args) {
        return args;
    }

    private char firstCharacterOfShortOption(final Option option) {
        return option.getOpt().charAt(0);
    }

    private void assertDeprecationOutput(final boolean shouldWriteDeprecation, final ByteArrayOutputStream output) {
        System.out.flush();
        if (shouldWriteDeprecation) {
            assertEquals("Option 'T''tee': Deprecated", output.toString().trim());
        } else {
            assertEquals("", output.toString());
        }
        output.reset();
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOptionNoDeprecationHandler(final String[] args, final Option optionToQuery, final OptionGroup group, final boolean optionIsDeprecated,
            final boolean optionIsPresent, final boolean groupOptionIsDeprecated, final boolean groupIsPresent, final Option expectedGroupOption)
            throws ParseException {
        final Options options = new Options().addOptionGroup(group);
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final CommandLine commandLine = DefaultParser.builder().get().parse(options, args);
        final PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(output));

            assertEquals(optionIsPresent, commandLine.hasOption(firstCharacterOfShortOption(optionToQuery)));
            assertDeprecationOutput(optionIsDeprecated, output);

            assertEquals(optionIsPresent, commandLine.hasOption(optionToQuery.getOpt()));
            assertDeprecationOutput(optionIsDeprecated, output);

            assertEquals(optionIsPresent, commandLine.hasOption(optionToQuery.getLongOpt()));
            assertDeprecationOutput(optionIsDeprecated, output);

            assertEquals(optionIsPresent, commandLine.hasOption(optionToQuery));
            assertDeprecationOutput(optionIsDeprecated, output);

            assertEquals(groupIsPresent, commandLine.hasOption(group));
            assertDeprecationOutput(groupOptionIsDeprecated, output);

            assertFalse(commandLine.hasOption("Nope"));
            assertDeprecationOutput(false, output);
        } finally {
            System.setOut(originalOut);
        }
    }
}
