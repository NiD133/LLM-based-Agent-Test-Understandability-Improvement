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

public class CommandLineTest_testGetOptionValue {

    private static final String DEFAULT_VALUE = "thing";

    private static Stream<Arguments> createOptionValueParameters() throws ParseException {
        final List<Arguments> cases = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        cases.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, null, true, null, optT));
        cases.add(Arguments.of(new String[] { "-T", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        cases.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, null, true, null, optT));
        cases.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        cases.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, null, false, null, optU));
        cases.add(Arguments.of(new String[] { "-U", "foo" }, optT, optionGroup, false, null, false, "foo", optU));
        cases.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, null, false, null, optU));
        cases.add(Arguments.of(new String[] { "--you", "foo" }, optT, optionGroup, false, null, false, "foo", optU));

        cases.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, null, true, null, optT));
        cases.add(Arguments.of(new String[] { "-T", "foo" }, optU, optionGroup, false, null, true, "foo", optT));
        cases.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, null, true, null, optT));
        cases.add(Arguments.of(new String[] { "--tee", "foo" }, optU, optionGroup, false, null, true, "foo", optT));
        cases.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, null, false, null, optU));
        cases.add(Arguments.of(new String[] { "-U", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));
        cases.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, null, false, null, optU));
        cases.add(Arguments.of(new String[] { "--you", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));

        return cases.stream();
    }

    private static OptionGroup createUnselectedGroup() {
        return new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
    }

    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    private void checkHandler(final boolean expectedDeprecation, final List<Option> handler, final Option expectedOption) {
        if (expectedDeprecation) {
            assertEquals(1, handler.size());
            assertEquals(expectedOption, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    private String defaulted(final String value) {
        return value == null ? DEFAULT_VALUE : value;
    }

    private void assertCharOptionValue(final CommandLine commandLine, final List<Option> handler, final Option option,
            final boolean expectedDeprecation, final String expectedValue, final Supplier<String> defaultSupplier) {
        assertEquals(expectedValue, commandLine.getOptionValue(asChar(option)));
        checkHandler(expectedDeprecation, handler, option);
        assertEquals(defaulted(expectedValue), commandLine.getOptionValue(asChar(option), DEFAULT_VALUE));
        checkHandler(expectedDeprecation, handler, option);
        assertEquals(defaulted(expectedValue), commandLine.getOptionValue(asChar(option), defaultSupplier));
        checkHandler(expectedDeprecation, handler, option);
    }

    private void assertNamedOptionValue(final CommandLine commandLine, final List<Option> handler, final String optionName,
            final boolean expectedDeprecation, final String expectedValue, final Option expectedOption,
            final Supplier<String> defaultSupplier) {
        assertEquals(expectedValue, commandLine.getOptionValue(optionName));
        checkHandler(expectedDeprecation, handler, expectedOption);
        assertEquals(defaulted(expectedValue), commandLine.getOptionValue(optionName, DEFAULT_VALUE));
        checkHandler(expectedDeprecation, handler, expectedOption);
        assertEquals(defaulted(expectedValue), commandLine.getOptionValue(optionName, defaultSupplier));
        checkHandler(expectedDeprecation, handler, expectedOption);
    }

    private void assertOptionValue(final CommandLine commandLine, final List<Option> handler, final Option option,
            final boolean expectedDeprecation, final String expectedValue, final Supplier<String> defaultSupplier) {
        assertEquals(expectedValue, commandLine.getOptionValue(option));
        checkHandler(expectedDeprecation, handler, option);
        assertEquals(defaulted(expectedValue), commandLine.getOptionValue(option, DEFAULT_VALUE));
        checkHandler(expectedDeprecation, handler, option);
        assertEquals(defaulted(expectedValue), commandLine.getOptionValue(option, defaultSupplier));
        checkHandler(expectedDeprecation, handler, option);
    }

    private void assertOptionGroupValue(final CommandLine commandLine, final List<Option> handler,
            final OptionGroup optionGroup, final boolean expectedDeprecation, final String expectedValue,
            final Option expectedOption, final Supplier<String> defaultSupplier) {
        assertEquals(expectedValue, commandLine.getOptionValue(optionGroup));
        checkHandler(expectedDeprecation, handler, expectedOption);
        assertEquals(defaulted(expectedValue), commandLine.getOptionValue(optionGroup, DEFAULT_VALUE));
        checkHandler(expectedDeprecation, handler, expectedOption);
        assertEquals(defaulted(expectedValue), commandLine.getOptionValue(optionGroup, defaultSupplier));
        checkHandler(expectedDeprecation, handler, expectedOption);
    }

    private void assertMissingGroupValue(final CommandLine commandLine, final List<Option> handler,
            final OptionGroup optionGroup, final Option expectedOption, final Supplier<String> defaultSupplier) {
        assertNull(commandLine.getOptionValue(optionGroup));
        checkHandler(false, handler, expectedOption);
        assertEquals(DEFAULT_VALUE, commandLine.getOptionValue(optionGroup, DEFAULT_VALUE));
        checkHandler(false, handler, expectedOption);
        assertEquals(DEFAULT_VALUE, commandLine.getOptionValue(optionGroup, defaultSupplier));
        checkHandler(false, handler, expectedOption);
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testGetOptionValue(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep,
            final String optValue, final boolean grpDep, final String grpValue, final Option grpOpt)
            throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options,
                args);
        final Supplier<String> thinger = () -> DEFAULT_VALUE;
        final OptionGroup otherGroup = createUnselectedGroup();
        final OptionGroup nullGroup = null;

        assertCharOptionValue(commandLine, handler, opt, optDep, optValue, thinger);
        assertNamedOptionValue(commandLine, handler, opt.getOpt(), optDep, optValue, opt, thinger);
        assertNamedOptionValue(commandLine, handler, opt.getLongOpt(), optDep, optValue, opt, thinger);
        assertOptionValue(commandLine, handler, opt, optDep, optValue, thinger);
        assertOptionGroupValue(commandLine, handler, optionGroup, grpDep, grpValue, grpOpt, thinger);
        assertMissingGroupValue(commandLine, handler, otherGroup, grpOpt, thinger);
        assertMissingGroupValue(commandLine, handler, nullGroup, grpOpt, thinger);
        assertNamedOptionValue(commandLine, handler, "Nope", false, null, opt, thinger);
    }
}
