package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CommandLineTest_testGetOptionValues {

    private static Stream<Arguments> createHasOptionParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final String[] foobar = { "foo", "bar" };
        // T set
        lst.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, true, true, true, optT));
        lst.add(Arguments.of(new String[] { "-T", "foo" }, optT, optionGroup, true, true, true, true, optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, true, true, true, optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true, true, true, true, optT));
        lst.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, false, false, true, optU));
        lst.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optT, optionGroup, false, false, false, true, optU));
        lst.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, false, false, true, optU));
        lst.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optT, optionGroup, false, false, false, true, optU));
        // U set
        lst.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, false, true, true, optT));
        lst.add(Arguments.of(new String[] { "-T", "foo", "bar" }, optU, optionGroup, false, false, true, true, optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, false, true, true, optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, optU, optionGroup, false, false, true, true, optT));
        lst.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, true, false, true, optU));
        lst.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optU, optionGroup, false, true, false, true, optU));
        lst.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, true, false, true, optU));
        lst.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optU, optionGroup, false, true, false, true, optU));
        return lst.stream();
    }

    private static Stream<Arguments> createOptionValueParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        // T set
        lst.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo" }, optT, optionGroup, true, "foo", true, "foo", optT));
        lst.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "foo" }, optT, optionGroup, false, null, false, "foo", optU));
        lst.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "foo" }, optT, optionGroup, false, null, false, "foo", optU));
        // U set
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

    private static Stream<Arguments> createOptionValuesParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").numberOfArgs(2).deprecated().optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").numberOfArgs(2).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final String[] foobar = { "foo", "bar" };
        // T set
        lst.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "foo", "bar" }, optT, optionGroup, true, foobar, true, foobar, optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, optT, optionGroup, true, foobar, true, foobar, optT));
        lst.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optT, optionGroup, false, null, false, foobar, optU));
        lst.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optT, optionGroup, false, null, false, foobar, optU));
        // U set
        lst.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "foo", "bar" }, optU, optionGroup, false, null, true, foobar, optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "foo", "bar" }, optU, optionGroup, false, null, true, foobar, optT));
        lst.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "foo", "bar" }, optU, optionGroup, false, foobar, false, foobar, optU));
        lst.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "foo", "bar" }, optU, optionGroup, false, foobar, false, foobar, optU));
        return lst.stream();
    }

    private static Stream<Arguments> createParsedOptionValueParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer expected = Integer.valueOf(1);
        // T set
        lst.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "1" }, optT, optionGroup, true, expected, true, expected, optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "1" }, optT, optionGroup, true, expected, true, expected, optT));
        lst.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "1" }, optT, optionGroup, false, null, false, expected, optU));
        lst.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "1" }, optT, optionGroup, false, null, false, expected, optU));
        // U set
        lst.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "1" }, optU, optionGroup, false, null, true, expected, optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "1" }, optU, optionGroup, false, null, true, expected, optT));
        lst.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "1" }, optU, optionGroup, false, expected, false, expected, optU));
        lst.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "1" }, optU, optionGroup, false, expected, false, expected, optU));
        return lst.stream();
    }

    private static Stream<Arguments> createParsedOptionValuesParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).hasArgs().get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).hasArgs().get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer[] expected = { 1, 2 };
        // T set
        lst.add(Arguments.of(new String[] { "-T" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "1", "2" }, optT, optionGroup, true, expected, true, expected, optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optT, optionGroup, true, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "1", "2" }, optT, optionGroup, true, expected, true, expected, optT));
        lst.add(Arguments.of(new String[] { "-U" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "1", "2" }, optT, optionGroup, false, null, false, expected, optU));
        lst.add(Arguments.of(new String[] { "--you" }, optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "1", "2" }, optT, optionGroup, false, null, false, expected, optU));
        // U set
        lst.add(Arguments.of(new String[] { "-T" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "-T", "1", "2" }, optU, optionGroup, false, null, true, expected, optT));
        lst.add(Arguments.of(new String[] { "--tee" }, optU, optionGroup, false, null, true, null, optT));
        lst.add(Arguments.of(new String[] { "--tee", "1", "2" }, optU, optionGroup, false, null, true, expected, optT));
        lst.add(Arguments.of(new String[] { "-U" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "-U", "1", "2" }, optU, optionGroup, false, expected, false, expected, optU));
        lst.add(Arguments.of(new String[] { "--you" }, optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[] { "--you", "1", "2" }, optU, optionGroup, false, expected, false, expected, optU));
        return lst.stream();
    }

    char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    private void assertWritten(final boolean optDep, final ByteArrayOutputStream baos) {
        System.out.flush();
        if (optDep) {
            assertEquals("Option 'T''tee': Deprecated", baos.toString().trim());
        } else {
            assertEquals("", baos.toString());
        }
        baos.reset();
    }

    /**
     * verifies that the deprecation handler has been called only once or not at all.
     * @param optDep {@code true} if the dependency should have been logged.
     * @param handler The list that the deprecation is logged to.
     * @param opt The option that triggered the logging. May be (@code null} if {@code optDep} is {@code false}.
     */
    void checkHandler(final boolean optDep, final List<Option> handler, final Option opt) {
        if (optDep) {
            assertEquals(1, handler.size());
            assertEquals(opt, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    /**
     * Test for get option values with and without default values.  Verifies that deprecated options only report as
     * deprecated once.
     * @param args the argument strings to parse.
     * @param opt the option to check for values with.
     * @param optionGroup the option group to check for values with.
     * @param optDep {@code true} if the opt is deprecated.
     * @param optValue  The value expected from opt.
     * @param grpDep {@code true} if the group is deprecated.
     * @param grpValue the value expected from the group.
     * @param grpOpt the option that is expected to be processed by the group.
     * @throws ParseException on parse error.
     */
    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createOptionValuesParameters")
    void testGetOptionValues(final String[] args, final Option opt, final OptionGroup optionGroup, final boolean optDep, final String[] optValue, final boolean grpDep, final String[] grpValue, final Option grpOpt) throws ParseException {
        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);
        final OptionGroup otherGroup = new OptionGroup().addOption(Option.builder("o").longOpt("other").hasArg().get()).addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;
        // test char option arg
        assertArrayEquals(optValue, commandLine.getOptionValues(asChar(opt)));
        checkHandler(optDep, handler, opt);
        // test short option arg
        assertArrayEquals(optValue, commandLine.getOptionValues(opt.getOpt()));
        checkHandler(optDep, handler, opt);
        // test long option arg
        assertArrayEquals(optValue, commandLine.getOptionValues(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);
        // test Option arg
        assertArrayEquals(optValue, commandLine.getOptionValues(opt));
        checkHandler(optDep, handler, opt);
        // test OptionGroup arg
        assertArrayEquals(grpValue, commandLine.getOptionValues(optionGroup));
        checkHandler(grpDep, handler, grpOpt);
        // test not an option
        assertNull(commandLine.getOptionValues("Nope"));
        checkHandler(false, handler, opt);
        // test other group arg
        assertNull(commandLine.getOptionValues(otherGroup));
        checkHandler(false, handler, grpOpt);
        // test null group arg
        assertNull(commandLine.getOptionValues(nullGroup));
        checkHandler(false, handler, grpOpt);
    }
}
