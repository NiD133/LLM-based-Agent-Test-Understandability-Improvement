package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CommandLineTest_testGetParsedOptionValue {

    private static final Integer DEFAULT_PARSED_VALUE = 2;
    private static final Supplier<Integer> DEFAULT_PARSED_VALUE_SUPPLIER = () -> DEFAULT_PARSED_VALUE;

    private static Stream<Arguments> createParsedOptionValueParameters() throws ParseException {
        final List<Arguments> cases = new ArrayList<>();
        final Option deprecatedTeeOption = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).get();
        final Option youOption = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(deprecatedTeeOption).addOption(youOption);
        final Integer parsedOne = Integer.valueOf(1);

        cases.add(Arguments.of(new String[] { "-T" }, deprecatedTeeOption, optionGroup, true, null, true, null, deprecatedTeeOption));
        cases.add(Arguments.of(new String[] { "-T", "1" }, deprecatedTeeOption, optionGroup, true, parsedOne, true, parsedOne, deprecatedTeeOption));
        cases.add(Arguments.of(new String[] { "--tee" }, deprecatedTeeOption, optionGroup, true, null, true, null, deprecatedTeeOption));
        cases.add(Arguments.of(new String[] { "--tee", "1" }, deprecatedTeeOption, optionGroup, true, parsedOne, true, parsedOne, deprecatedTeeOption));
        cases.add(Arguments.of(new String[] { "-U" }, deprecatedTeeOption, optionGroup, false, null, false, null, youOption));
        cases.add(Arguments.of(new String[] { "-U", "1" }, deprecatedTeeOption, optionGroup, false, null, false, parsedOne, youOption));
        cases.add(Arguments.of(new String[] { "--you" }, deprecatedTeeOption, optionGroup, false, null, false, null, youOption));
        cases.add(Arguments.of(new String[] { "--you", "1" }, deprecatedTeeOption, optionGroup, false, null, false, parsedOne, youOption));

        cases.add(Arguments.of(new String[] { "-T" }, youOption, optionGroup, false, null, true, null, deprecatedTeeOption));
        cases.add(Arguments.of(new String[] { "-T", "1" }, youOption, optionGroup, false, null, true, parsedOne, deprecatedTeeOption));
        cases.add(Arguments.of(new String[] { "--tee" }, youOption, optionGroup, false, null, true, null, deprecatedTeeOption));
        cases.add(Arguments.of(new String[] { "--tee", "1" }, youOption, optionGroup, false, null, true, parsedOne, deprecatedTeeOption));
        cases.add(Arguments.of(new String[] { "-U" }, youOption, optionGroup, false, null, false, null, youOption));
        cases.add(Arguments.of(new String[] { "-U", "1" }, youOption, optionGroup, false, parsedOne, false, parsedOne, youOption));
        cases.add(Arguments.of(new String[] { "--you" }, youOption, optionGroup, false, null, false, null, youOption));
        cases.add(Arguments.of(new String[] { "--you", "1" }, youOption, optionGroup, false, parsedOne, false, parsedOne, youOption));

        return cases.stream();
    }

    private static char shortOptionChar(final Option option) {
        return option.getOpt().charAt(0);
    }

    private void assertDeprecatedHandlerCalled(final boolean expectedCall, final List<Option> handler, final Option expectedOption) {
        if (expectedCall) {
            assertEquals(1, handler.size());
            assertEquals(expectedOption, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    private void assertParsedValueByOptionName(final CommandLine commandLine, final List<Option> handler, final Option option,
            final boolean expectDeprecatedHandler, final Integer expectedValue) throws ParseException {
        assertParsedValue(commandLine.getParsedOptionValue(shortOptionChar(option)), expectedValue, expectDeprecatedHandler, handler, option);
        assertParsedValue(commandLine.getParsedOptionValue(shortOptionChar(option), DEFAULT_PARSED_VALUE), defaulted(expectedValue),
                expectDeprecatedHandler, handler, option);
        assertParsedValue(commandLine.getParsedOptionValue(shortOptionChar(option), DEFAULT_PARSED_VALUE_SUPPLIER), defaulted(expectedValue),
                expectDeprecatedHandler, handler, option);

        assertParsedValue(commandLine.getParsedOptionValue(option.getOpt()), expectedValue, expectDeprecatedHandler, handler, option);
        assertParsedValue(commandLine.getParsedOptionValue(option.getOpt(), DEFAULT_PARSED_VALUE), defaulted(expectedValue),
                expectDeprecatedHandler, handler, option);
        assertParsedValue(commandLine.getParsedOptionValue(option.getOpt(), DEFAULT_PARSED_VALUE_SUPPLIER), defaulted(expectedValue),
                expectDeprecatedHandler, handler, option);

        assertParsedValue(commandLine.getParsedOptionValue(option.getLongOpt()), expectedValue, expectDeprecatedHandler, handler, option);
        assertParsedValue(commandLine.getParsedOptionValue(option.getLongOpt(), DEFAULT_PARSED_VALUE), defaulted(expectedValue),
                expectDeprecatedHandler, handler, option);
        assertParsedValue(commandLine.getParsedOptionValue(option.getLongOpt(), DEFAULT_PARSED_VALUE_SUPPLIER), defaulted(expectedValue),
                expectDeprecatedHandler, handler, option);

        assertParsedValue(commandLine.getParsedOptionValue(option), expectedValue, expectDeprecatedHandler, handler, option);
        assertParsedValue(commandLine.getParsedOptionValue(option, DEFAULT_PARSED_VALUE), defaulted(expectedValue),
                expectDeprecatedHandler, handler, option);
        assertParsedValue(commandLine.getParsedOptionValue(option, DEFAULT_PARSED_VALUE_SUPPLIER), defaulted(expectedValue),
                expectDeprecatedHandler, handler, option);
    }

    private void assertParsedValueByOptionGroup(final CommandLine commandLine, final List<Option> handler, final OptionGroup optionGroup,
            final boolean expectDeprecatedHandler, final Integer expectedValue, final Option selectedOption) throws ParseException {
        assertParsedValue(commandLine.getParsedOptionValue(optionGroup), expectedValue, expectDeprecatedHandler, handler, selectedOption);
        assertParsedValue(commandLine.getParsedOptionValue(optionGroup, DEFAULT_PARSED_VALUE), defaulted(expectedValue),
                expectDeprecatedHandler, handler, selectedOption);
        assertParsedValue(commandLine.getParsedOptionValue(optionGroup, DEFAULT_PARSED_VALUE_SUPPLIER), defaulted(expectedValue),
                expectDeprecatedHandler, handler, selectedOption);
    }

    private void assertParsedValueForUnselectedGroup(final CommandLine commandLine, final List<Option> handler, final Option selectedOption)
            throws ParseException {
        final OptionGroup otherGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        assertNull(commandLine.getParsedOptionValue(otherGroup));
        assertDeprecatedHandlerCalled(false, handler, selectedOption);
        assertEquals(DEFAULT_PARSED_VALUE, commandLine.getParsedOptionValue(otherGroup, DEFAULT_PARSED_VALUE));
        assertDeprecatedHandlerCalled(false, handler, selectedOption);
        assertEquals(DEFAULT_PARSED_VALUE, commandLine.getParsedOptionValue(otherGroup, DEFAULT_PARSED_VALUE_SUPPLIER));
        assertDeprecatedHandlerCalled(false, handler, selectedOption);

        assertNull(commandLine.getParsedOptionValue(nullGroup));
        assertDeprecatedHandlerCalled(false, handler, selectedOption);
        assertEquals(DEFAULT_PARSED_VALUE, commandLine.getParsedOptionValue(nullGroup, DEFAULT_PARSED_VALUE));
        assertDeprecatedHandlerCalled(false, handler, selectedOption);
        assertEquals(DEFAULT_PARSED_VALUE, commandLine.getParsedOptionValue(nullGroup, DEFAULT_PARSED_VALUE_SUPPLIER));
        assertDeprecatedHandlerCalled(false, handler, selectedOption);
    }

    private void assertParsedValueForUnknownOption(final CommandLine commandLine, final List<Option> handler, final Option option)
            throws ParseException {
        assertNull(commandLine.getParsedOptionValue("Nope"));
        assertDeprecatedHandlerCalled(false, handler, option);
        assertEquals(DEFAULT_PARSED_VALUE, commandLine.getParsedOptionValue("Nope", DEFAULT_PARSED_VALUE));
        assertDeprecatedHandlerCalled(false, handler, option);
        assertEquals(DEFAULT_PARSED_VALUE, commandLine.getParsedOptionValue("Nope", DEFAULT_PARSED_VALUE_SUPPLIER));
        assertDeprecatedHandlerCalled(false, handler, option);
    }

    private void assertParsedValue(final Integer actualValue, final Integer expectedValue, final boolean expectDeprecatedHandler,
            final List<Option> handler, final Option expectedDeprecatedOption) {
        assertEquals(expectedValue, actualValue);
        assertDeprecatedHandlerCalled(expectDeprecatedHandler, handler, expectedDeprecatedOption);
    }

    private Integer defaulted(final Integer expectedValue) {
        return expectedValue == null ? DEFAULT_PARSED_VALUE : expectedValue;
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValueParameters")
    void testGetParsedOptionValue(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final Integer optValue, final boolean grpDep, final Integer grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);

        assertParsedValueByOptionName(commandLine, handler, opt, optDep, optValue);
        assertParsedValueByOptionGroup(commandLine, handler, optionGroup, grpDep, grpValue, grpOpt);
        assertParsedValueForUnselectedGroup(commandLine, handler, grpOpt);
        assertParsedValueForUnknownOption(commandLine, handler, opt);
    }
}
