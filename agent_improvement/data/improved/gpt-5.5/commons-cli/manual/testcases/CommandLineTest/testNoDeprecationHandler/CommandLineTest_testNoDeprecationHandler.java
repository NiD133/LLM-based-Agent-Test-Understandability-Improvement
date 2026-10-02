package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CommandLineTest_testNoDeprecationHandler {

    private static final String DEFAULT_VALUE = "thing";

    private static Stream<Arguments> createOptionValueParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);

        // Deprecated T is the option being queried.
        lst.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        lst.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "foo" }, optT, optionGroup, false, null, false, "foo", optU));
        lst.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "foo" }, optT, optionGroup, false, null, false, "foo", optU));

        // Non-deprecated U is the option being queried.
        lst.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "foo" }, optU, optionGroup, false, null, true, "foo", optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo" }, optU, optionGroup, false, null, true, "foo", optT));
        lst.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));
        lst.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "foo" }, optU, optionGroup, false, "foo", false, "foo", optU));
        return lst.stream();
    }

    private char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    private void assertWritten(final boolean deprecatedOptionExpected, final ByteArrayOutputStream baos) {
        System.out.flush();
        if (deprecatedOptionExpected) {
            assertEquals("Option 'T''tee': Deprecated", baos.toString().trim());
        } else {
            assertEquals("", baos.toString());
        }
        baos.reset();
    }

    private void assertCharLookup(final CommandLine commandLine, final Option opt, final String expectedValue,
            final boolean deprecatedOptionExpected, final Supplier<String> defaultSupplier, final Supplier<String> nullSupplier,
            final ByteArrayOutputStream baos) {
        assertEquals(expectedValue, commandLine.getOptionValue(asChar(opt)));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(valueOrDefault(expectedValue), commandLine.getOptionValue(asChar(opt), DEFAULT_VALUE));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(valueOrDefault(expectedValue), commandLine.getOptionValue(asChar(opt), defaultSupplier));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(expectedValue, commandLine.getOptionValue(asChar(opt), nullSupplier));
        assertWritten(deprecatedOptionExpected, baos);
    }

    private void assertMissingGroupLookup(final CommandLine commandLine, final OptionGroup optionGroup,
            final Supplier<String> defaultSupplier, final Supplier<String> nullSupplier, final ByteArrayOutputStream baos) {
        assertNull(commandLine.getOptionValue(optionGroup));
        assertWritten(false, baos);
        assertEquals(DEFAULT_VALUE, commandLine.getOptionValue(optionGroup, DEFAULT_VALUE));
        assertWritten(false, baos);
        assertEquals(DEFAULT_VALUE, commandLine.getOptionValue(optionGroup, defaultSupplier));
        assertWritten(false, baos);
        assertNull(commandLine.getOptionValue(optionGroup, nullSupplier));
        assertWritten(false, baos);
    }

    private void assertOptionLookup(final CommandLine commandLine, final Option opt, final String expectedValue,
            final boolean deprecatedOptionExpected, final Supplier<String> defaultSupplier, final Supplier<String> nullSupplier,
            final ByteArrayOutputStream baos) {
        assertEquals(expectedValue, commandLine.getOptionValue(opt));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(valueOrDefault(expectedValue), commandLine.getOptionValue(opt, DEFAULT_VALUE));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(valueOrDefault(expectedValue), commandLine.getOptionValue(opt, defaultSupplier));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(expectedValue, commandLine.getOptionValue(opt, nullSupplier));
        assertWritten(deprecatedOptionExpected, baos);
    }

    private void assertOptionNameLookup(final CommandLine commandLine, final String optionName, final String expectedValue,
            final boolean deprecatedOptionExpected, final Supplier<String> defaultSupplier, final Supplier<String> nullSupplier,
            final ByteArrayOutputStream baos) {
        assertEquals(expectedValue, commandLine.getOptionValue(optionName));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(valueOrDefault(expectedValue), commandLine.getOptionValue(optionName, DEFAULT_VALUE));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(valueOrDefault(expectedValue), commandLine.getOptionValue(optionName, defaultSupplier));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(expectedValue, commandLine.getOptionValue(optionName, nullSupplier));
        assertWritten(deprecatedOptionExpected, baos);
    }

    private void assertSelectedGroupLookup(final CommandLine commandLine, final OptionGroup optionGroup, final String expectedValue,
            final boolean deprecatedOptionExpected, final Supplier<String> defaultSupplier, final Supplier<String> nullSupplier,
            final ByteArrayOutputStream baos) {
        assertEquals(expectedValue, commandLine.getOptionValue(optionGroup));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(valueOrDefault(expectedValue), commandLine.getOptionValue(optionGroup, DEFAULT_VALUE));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(valueOrDefault(expectedValue), commandLine.getOptionValue(optionGroup, defaultSupplier));
        assertWritten(deprecatedOptionExpected, baos);
        assertEquals(expectedValue, commandLine.getOptionValue(optionGroup, nullSupplier));
        assertWritten(deprecatedOptionExpected, baos);
    }

    private String valueOrDefault(final String value) {
        return value == null ? DEFAULT_VALUE : value;
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValueParameters")
    void testNoDeprecationHandler(final String[] args, final Option opt, final OptionGroup optionGroup,
            final boolean optDep, final String optValue, final boolean grpDep, final String grpValue, final Option grpOpt)
            throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final CommandLine commandLine = DefaultParser.builder().get().parse(options, args);
        final Supplier<String> thinger = () -> DEFAULT_VALUE;
        final Supplier<String> nullSupplier = null;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream ps = System.out;

        try {
            System.setOut(new PrintStream(baos));
            final OptionGroup otherGroup = new OptionGroup()
                    .addOption(Option.builder("o").longOpt("other").hasArg().get())
                    .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
            final OptionGroup nullGroup = null;

            assertCharLookup(commandLine, opt, optValue, optDep, thinger, nullSupplier, baos);
            assertOptionNameLookup(commandLine, opt.getOpt(), optValue, optDep, thinger, nullSupplier, baos);
            assertOptionNameLookup(commandLine, opt.getLongOpt(), optValue, optDep, thinger, nullSupplier, baos);
            assertOptionLookup(commandLine, opt, optValue, optDep, thinger, nullSupplier, baos);
            assertSelectedGroupLookup(commandLine, optionGroup, grpValue, grpDep, thinger, nullSupplier, baos);
            assertMissingGroupLookup(commandLine, otherGroup, thinger, nullSupplier, baos);
            assertMissingGroupLookup(commandLine, nullGroup, thinger, nullSupplier, baos);
            assertOptionNameLookup(commandLine, "Nope", null, false, thinger, nullSupplier, baos);
        } finally {
            System.setOut(ps);
        }
    }
}
