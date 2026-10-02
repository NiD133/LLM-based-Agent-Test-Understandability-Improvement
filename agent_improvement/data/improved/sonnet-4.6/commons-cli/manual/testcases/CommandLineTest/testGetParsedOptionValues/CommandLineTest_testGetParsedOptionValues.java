package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CommandLineTest_testGetParsedOptionValues {

    /**
     * Provides test parameters for {@link #testGetParsedOptionValues}.
     *
     * <p>Parameter positions:
     * <ol>
     *   <li>{@code args}       – raw command-line tokens to parse</li>
     *   <li>{@code opt}        – the option being queried directly (either T or U)</li>
     *   <li>{@code optionGroup}– the option group containing both T and U</li>
     *   <li>{@code optDep}     – whether querying {@code opt} directly triggers the deprecated handler</li>
     *   <li>{@code optValue}   – expected parsed values when querying {@code opt} directly; null if not set</li>
     *   <li>{@code grpDep}     – whether querying via the group triggers the deprecated handler</li>
     *   <li>{@code grpValue}   – expected parsed values when querying via the group; null if not set</li>
     *   <li>{@code grpOpt}     – which option in the group was actually selected by the parser</li>
     * </ol>
     */
    private static Stream<Arguments> createParsedOptionValuesParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).hasArgs().get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).hasArgs().get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer[] expected = { 1, 2 };

        // T is the option selected by the parser
        lst.add(Arguments.of(new String[] { "-T" },           optT, optionGroup, true,  null,     true,  null,     optT));
        lst.add(Arguments.of(new String[] { "-T", "1", "2" }, optT, optionGroup, true,  expected, true,  expected, optT));
        lst.add(Arguments.of(new String[] { "--tee" },        optT, optionGroup, true,  null,     true,  null,     optT));
        lst.add(Arguments.of(new String[] { "--tee", "1", "2" }, optT, optionGroup, true, expected, true, expected, optT));
        lst.add(Arguments.of(new String[] { "-U" },           optT, optionGroup, false, null,     false, null,     optU));
        lst.add(Arguments.of(new String[] { "-U", "1", "2" }, optT, optionGroup, false, null,     false, expected, optU));
        lst.add(Arguments.of(new String[] { "--you" },        optT, optionGroup, false, null,     false, null,     optU));
        lst.add(Arguments.of(new String[] { "--you", "1", "2" }, optT, optionGroup, false, null,  false, expected, optU));

        // U is the option selected by the parser
        lst.add(Arguments.of(new String[] { "-T" },           optU, optionGroup, false, null,     true,  null,     optT));
        lst.add(Arguments.of(new String[] { "-T", "1", "2" }, optU, optionGroup, false, null,     true,  expected, optT));
        lst.add(Arguments.of(new String[] { "--tee" },        optU, optionGroup, false, null,     true,  null,     optT));
        lst.add(Arguments.of(new String[] { "--tee", "1", "2" }, optU, optionGroup, false, null,  true,  expected, optT));
        lst.add(Arguments.of(new String[] { "-U" },           optU, optionGroup, false, null,     false, null,     optU));
        lst.add(Arguments.of(new String[] { "-U", "1", "2" }, optU, optionGroup, false, expected, false, expected, optU));
        lst.add(Arguments.of(new String[] { "--you" },        optU, optionGroup, false, null,     false, null,     optU));
        lst.add(Arguments.of(new String[] { "--you", "1", "2" }, optU, optionGroup, false, expected, false, expected, optU));

        return lst.stream();
    }

    private static char asChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Verifies that the deprecation handler was invoked exactly once (when {@code expectDeprecated}
     * is {@code true}) or not at all (when {@code false}). Resets the handler list afterward.
     */
    private void checkHandler(final boolean expectDeprecated, final List<Option> handler, final Option opt) {
        if (expectDeprecated) {
            assertEquals(1, handler.size());
            assertEquals(opt, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValuesParameters")
    void testGetParsedOptionValues(
            final String[] args,
            final Option opt,
            final OptionGroup optionGroup,
            final boolean optDep,
            final Integer[] optValue,
            final boolean grpDep,
            final Integer[] grpValue,
            final Option grpOpt) throws ParseException {

        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder().setDeprecatedHandler(handler::add).get().parse(options, args);

        // Fallback values supplied when the queried option/group produced no result
        final Integer[] defaultValues = { 2, 3 };
        final Supplier<Integer[]> defaultValuesSupplier = () -> new Integer[] { 2, 3 };

        // An option group whose options were never passed on the command line
        final OptionGroup otherGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // --- Query by single-character option key ---
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(asChar(opt)));
        checkHandler(optDep, handler, opt);
        assertArrayEquals(optValue == null ? defaultValues : optValue, commandLine.getParsedOptionValues(asChar(opt), defaultValues));
        checkHandler(optDep, handler, opt);
        assertArrayEquals(optValue == null ? defaultValues : optValue, commandLine.getParsedOptionValues(asChar(opt), defaultValuesSupplier));
        checkHandler(optDep, handler, opt);

        // --- Query by short option name string ---
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(opt.getOpt()));
        checkHandler(optDep, handler, opt);
        assertArrayEquals(optValue == null ? defaultValues : optValue, commandLine.getParsedOptionValues(opt.getOpt(), defaultValues));
        checkHandler(optDep, handler, opt);
        assertArrayEquals(optValue == null ? defaultValues : optValue, commandLine.getParsedOptionValues(opt.getOpt(), defaultValuesSupplier));
        checkHandler(optDep, handler, opt);

        // --- Query by long option name string ---
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);
        assertArrayEquals(optValue == null ? defaultValues : optValue, commandLine.getParsedOptionValues(opt.getLongOpt(), defaultValues));
        checkHandler(optDep, handler, opt);
        assertArrayEquals(optValue == null ? defaultValues : optValue, commandLine.getParsedOptionValues(opt.getLongOpt(), defaultValuesSupplier));
        checkHandler(optDep, handler, opt);

        // --- Query by Option object ---
        assertArrayEquals(optValue, commandLine.getParsedOptionValues(opt));
        checkHandler(optDep, handler, opt);
        assertArrayEquals(optValue == null ? defaultValues : optValue, commandLine.getParsedOptionValues(opt, defaultValues));
        checkHandler(optDep, handler, opt);
        assertArrayEquals(optValue == null ? defaultValues : optValue, commandLine.getParsedOptionValues(opt, defaultValuesSupplier));
        checkHandler(optDep, handler, opt);

        // --- Query via the selected OptionGroup ---
        assertArrayEquals(grpValue, commandLine.getParsedOptionValues(optionGroup));
        checkHandler(grpDep, handler, grpOpt);
        assertArrayEquals(grpValue == null ? defaultValues : grpValue, commandLine.getParsedOptionValues(optionGroup, defaultValues));
        checkHandler(grpDep, handler, grpOpt);
        assertArrayEquals(grpValue == null ? defaultValues : grpValue, commandLine.getParsedOptionValues(optionGroup, defaultValuesSupplier));
        checkHandler(grpDep, handler, grpOpt);

        // --- Query via an OptionGroup whose options were not used (expects null / default) ---
        assertNull(commandLine.getParsedOptionValues(otherGroup));
        checkHandler(false, handler, grpOpt);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues(otherGroup, defaultValues));
        checkHandler(false, handler, grpOpt);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues(otherGroup, defaultValuesSupplier));
        checkHandler(false, handler, grpOpt);

        // --- Query via a null OptionGroup (expects null / default) ---
        assertNull(commandLine.getParsedOptionValues(nullGroup));
        checkHandler(false, handler, grpOpt);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues(nullGroup, defaultValues));
        checkHandler(false, handler, grpOpt);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues(nullGroup, defaultValuesSupplier));
        checkHandler(false, handler, grpOpt);

        // --- Query by an option name that does not exist (expects null / default) ---
        assertNull(commandLine.getParsedOptionValues("Nope"));
        checkHandler(false, handler, opt);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues("Nope", defaultValues));
        checkHandler(false, handler, opt);
        assertArrayEquals(defaultValues, commandLine.getParsedOptionValues("Nope", defaultValuesSupplier));
        checkHandler(false, handler, opt);
    }
}
