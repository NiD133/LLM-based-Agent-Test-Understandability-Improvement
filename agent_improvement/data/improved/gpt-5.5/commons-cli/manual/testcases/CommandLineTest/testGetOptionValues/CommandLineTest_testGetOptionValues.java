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

    private static final String MISSING_OPTION = "Nope";

    private static Stream<Arguments> createOptionValuesParameters() throws ParseException {
        final List<Arguments> cases = new ArrayList<>();
        final Option deprecatedTee = Option.builder().option("T").longOpt("tee").numberOfArgs(2).deprecated().optionalArg(true).get();
        final Option you = Option.builder("U").longOpt("you").numberOfArgs(2).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(deprecatedTee).addOption(you);
        final String[] fooBar = { "foo", "bar" };

        cases.add(Arguments.of(new String[] { "-T" }, deprecatedTee, optionGroup, true, null, true, null, deprecatedTee));
        cases.add(Arguments.of(new String[] { "-T", "foo", "bar" }, deprecatedTee, optionGroup, true, fooBar, true, fooBar, deprecatedTee));
        cases.add(Arguments.of(new String[] { "--tee" }, deprecatedTee, optionGroup, true, null, true, null, deprecatedTee));
        cases.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, deprecatedTee, optionGroup, true, fooBar, true, fooBar, deprecatedTee));
        cases.add(Arguments.of(new String[] { "-U" }, deprecatedTee, optionGroup, false, null, false, null, you));
        cases.add(Arguments.of(new String[] { "-U", "foo", "bar" }, deprecatedTee, optionGroup, false, null, false, fooBar, you));
        cases.add(Arguments.of(new String[] { "--you" }, deprecatedTee, optionGroup, false, null, false, null, you));
        cases.add(Arguments.of(new String[] { "--you", "foo", "bar" }, deprecatedTee, optionGroup, false, null, false, fooBar, you));

        cases.add(Arguments.of(new String[] { "-T" }, you, optionGroup, false, null, true, null, deprecatedTee));
        cases.add(Arguments.of(new String[] { "-T", "foo", "bar" }, you, optionGroup, false, null, true, fooBar, deprecatedTee));
        cases.add(Arguments.of(new String[] { "--tee" }, you, optionGroup, false, null, true, null, deprecatedTee));
        cases.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, you, optionGroup, false, null, true, fooBar, deprecatedTee));
        cases.add(Arguments.of(new String[] { "-U" }, you, optionGroup, false, null, false, null, you));
        cases.add(Arguments.of(new String[] { "-U", "foo", "bar" }, you, optionGroup, false, fooBar, false, fooBar, you));
        cases.add(Arguments.of(new String[] { "--you" }, you, optionGroup, false, null, false, null, you));
        cases.add(Arguments.of(new String[] { "--you", "foo", "bar" }, you, optionGroup, false, fooBar, false, fooBar, you));

        return cases.stream();
    }

    private static char optionCharacter(final Option option) {
        return option.getOpt().charAt(0);
    }

    private static OptionGroup createUnselectedOptionGroup() {
        return new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
    }

    private static void verifyDeprecationHandler(final boolean expectedDeprecated, final List<Option> handledOptions, final Option expectedOption) {
        if (expectedDeprecated) {
            assertEquals(1, handledOptions.size());
            assertEquals(expectedOption, handledOptions.get(0));
        } else {
            assertEquals(0, handledOptions.size());
        }
        handledOptions.clear();
    }

    private static void assertValuesAndDeprecation(final String[] expectedValues, final String[] actualValues, final boolean expectedDeprecated,
            final List<Option> handledOptions, final Option expectedDeprecatedOption) {
        assertArrayEquals(expectedValues, actualValues);
        verifyDeprecationHandler(expectedDeprecated, handledOptions, expectedDeprecatedOption);
    }

    private static void assertNoValuesAndNoDeprecation(final String[] actualValues, final List<Option> handledOptions, final Option optionToCheck) {
        assertNull(actualValues);
        verifyDeprecationHandler(false, handledOptions, optionToCheck);
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValuesParameters")
    void testGetOptionValues(final String[] args, final Option option, final OptionGroup optionGroup, final boolean optionDeprecated,
            final String[] optionValues, final boolean groupDeprecated, final String[] groupValues, final Option selectedGroupOption) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handledDeprecatedOptions = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handledDeprecatedOptions::add).get().parse(options, args);
        final OptionGroup otherGroup = createUnselectedOptionGroup();
        final OptionGroup nullGroup = null;

        assertValuesAndDeprecation(optionValues, commandLine.getOptionValues(optionCharacter(option)), optionDeprecated, handledDeprecatedOptions, option);
        assertValuesAndDeprecation(optionValues, commandLine.getOptionValues(option.getOpt()), optionDeprecated, handledDeprecatedOptions, option);
        assertValuesAndDeprecation(optionValues, commandLine.getOptionValues(option.getLongOpt()), optionDeprecated, handledDeprecatedOptions, option);
        assertValuesAndDeprecation(optionValues, commandLine.getOptionValues(option), optionDeprecated, handledDeprecatedOptions, option);
        assertValuesAndDeprecation(groupValues, commandLine.getOptionValues(optionGroup), groupDeprecated, handledDeprecatedOptions, selectedGroupOption);

        assertNoValuesAndNoDeprecation(commandLine.getOptionValues(MISSING_OPTION), handledDeprecatedOptions, option);
        assertNoValuesAndNoDeprecation(commandLine.getOptionValues(otherGroup), handledDeprecatedOptions, selectedGroupOption);
        assertNoValuesAndNoDeprecation(commandLine.getOptionValues(nullGroup), handledDeprecatedOptions, selectedGroupOption);
    }
}
