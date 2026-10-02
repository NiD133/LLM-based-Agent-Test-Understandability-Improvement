package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CommandLineTest_testHasOption {

    private static final String OPTION_T = "T";
    private static final String OPTION_T_LONG = "tee";
    private static final String OPTION_U = "U";
    private static final String OPTION_U_LONG = "you";

    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final List<Arguments> parameters = new ArrayList<>();
        final Option deprecatedT = deprecatedT();
        final Option optionU = optionU();
        final OptionGroup optionGroup = new OptionGroup().addOption(deprecatedT).addOption(optionU);

        addCasesWhereTIsSelected(parameters, deprecatedT, optionU, optionGroup);
        addCasesWhereUIsSelected(parameters, deprecatedT, optionU, optionGroup);
        return parameters.stream();
    }

    private static Option deprecatedT() {
        return Option.builder().option(OPTION_T).longOpt(OPTION_T_LONG).deprecated().optionalArg(true).get();
    }

    private static Option optionU() {
        return Option.builder(OPTION_U).longOpt(OPTION_U_LONG).optionalArg(true).get();
    }

    private static void addCasesWhereTIsSelected(final List<Arguments> parameters, final Option deprecatedT, final Option optionU, final OptionGroup optionGroup) {
        parameters.add(Arguments.of(new String[] { "-T" }, deprecatedT, optionGroup, true, true, true, true, deprecatedT));
        parameters.add(Arguments.of(new String[] { "-T", "foo" }, deprecatedT, optionGroup, true, true, true, true, deprecatedT));
        parameters.add(Arguments.of(new String[] { "--tee" }, deprecatedT, optionGroup, true, true, true, true, deprecatedT));
        parameters.add(Arguments.of(new String[] { "--tee", "foo" }, deprecatedT, optionGroup, true, true, true, true, deprecatedT));
        parameters.add(Arguments.of(new String[] { "-U" }, deprecatedT, optionGroup, false, false, false, true, optionU));
        parameters.add(Arguments.of(new String[] { "-U", "foo", "bar" }, deprecatedT, optionGroup, false, false, false, true, optionU));
        parameters.add(Arguments.of(new String[] { "--you" }, deprecatedT, optionGroup, false, false, false, true, optionU));
        parameters.add(Arguments.of(new String[] { "--you", "foo", "bar" }, deprecatedT, optionGroup, false, false, false, true, optionU));
    }

    private static void addCasesWhereUIsSelected(final List<Arguments> parameters, final Option deprecatedT, final Option optionU, final OptionGroup optionGroup) {
        parameters.add(Arguments.of(new String[] { "-T" }, optionU, optionGroup, false, false, true, true, deprecatedT));
        parameters.add(Arguments.of(new String[] { "-T", "foo", "bar" }, optionU, optionGroup, false, false, true, true, deprecatedT));
        parameters.add(Arguments.of(new String[] { "--tee" }, optionU, optionGroup, false, false, true, true, deprecatedT));
        parameters.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, optionU, optionGroup, false, false, true, true, deprecatedT));
        parameters.add(Arguments.of(new String[] { "-U" }, optionU, optionGroup, false, true, false, true, optionU));
        parameters.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optionU, optionGroup, false, true, false, true, optionU));
        parameters.add(Arguments.of(new String[] { "--you" }, optionU, optionGroup, false, true, false, true, optionU));
        parameters.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optionU, optionGroup, false, true, false, true, optionU));
    }

    private char asChar(final Option option) {
        return option.getOpt().charAt(0);
    }

    private void checkHandler(final boolean expectedDeprecatedOption, final List<Option> handler, final Option expectedOption) {
        if (expectedDeprecatedOption) {
            assertEquals(1, handler.size());
            assertEquals(expectedOption, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    private void assertHasOption(final CommandLine commandLine, final Object optionReference, final boolean expected, final boolean expectedDeprecatedOption,
            final List<Option> handler, final Option expectedOption) {
        if (optionReference instanceof Character) {
            assertEquals(expected, commandLine.hasOption(((Character) optionReference).charValue()));
        } else if (optionReference instanceof Option) {
            assertEquals(expected, commandLine.hasOption((Option) optionReference));
        } else {
            assertEquals(expected, commandLine.hasOption((String) optionReference));
        }
        checkHandler(expectedDeprecatedOption, handler, expectedOption);
    }

    private void assertHasOptionGroup(final CommandLine commandLine, final OptionGroup optionGroup, final boolean expected,
            final boolean expectedDeprecatedOption, final List<Option> handler, final Option expectedOption) {
        assertEquals(expected, commandLine.hasOption(optionGroup));
        checkHandler(expectedDeprecatedOption, handler, expectedOption);
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createHasOptionParameters")
    void testHasOption(final String[] args, final Option option, final OptionGroup optionGroup, final boolean optionIsDeprecated, final boolean hasOption,
            final boolean groupOptionIsDeprecated, final boolean hasOptionGroup, final Option groupOption) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);
        final OptionGroup otherGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        assertHasOption(commandLine, Character.valueOf(asChar(option)), hasOption, optionIsDeprecated, handler, option);
        assertHasOption(commandLine, option.getOpt(), hasOption, optionIsDeprecated, handler, option);
        assertHasOption(commandLine, option.getLongOpt(), hasOption, optionIsDeprecated, handler, option);
        assertHasOption(commandLine, option, hasOption, optionIsDeprecated, handler, option);
        assertHasOptionGroup(commandLine, optionGroup, hasOptionGroup, groupOptionIsDeprecated, handler, groupOption);
        assertFalse(commandLine.hasOption(otherGroup));
        checkHandler(false, handler, groupOption);
        assertFalse(commandLine.hasOption(nullGroup));
        checkHandler(false, handler, groupOption);
        assertFalse(commandLine.hasOption("Nope"));
        checkHandler(false, handler, option);
    }
}
