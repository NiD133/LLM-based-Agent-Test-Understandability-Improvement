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

    /**
     * Provides test cases for {@link #testGetParsedOptionValue}.
     *
     * <p>Each row represents one combination of CLI args plus the option/group queried:
     * <pre>
     *   args       – raw CLI tokens passed to the parser
     *   opt        – the Option object whose single-value accessors are being checked
     *   optionGroup– the OptionGroup that contains both optT and optU
     *   optDep     – true when querying {@code opt} is expected to fire the deprecation handler
     *   optValue   – expected parsed Integer when querying by {@code opt} (null = option not selected or no arg)
     *   grpDep     – true when querying {@code optionGroup} is expected to fire the deprecation handler
     *   grpValue   – expected parsed Integer when querying by {@code optionGroup}
     *   grpOpt     – the Option that was actually selected (used to verify the handler argument)
     * </pre>
     */
    private static Stream<Arguments> createParsedOptionValueParameters() throws ParseException {
        final List<Arguments> lst = new ArrayList<>();

        // optT (-T/--tee) is deprecated; optU (-U/--you) is not
        final Option optT = Option.builder().option("T").longOpt("tee").deprecated().type(Integer.class).optionalArg(true).get();
        final Option optU = Option.builder("U").longOpt("you").type(Integer.class).optionalArg(true).get();
        final OptionGroup optionGroup = new OptionGroup().addOption(optT).addOption(optU);
        final Integer one = Integer.valueOf(1);

        // ── optT is the queried option; -T / --tee is used on the CLI ──────────────────────────
        //                args                  opt   group        optDep  optValue  grpDep  grpValue  grpOpt
        lst.add(Arguments.of(new String[]{"-T"},         optT, optionGroup, true,  null, true,  null, optT));
        lst.add(Arguments.of(new String[]{"-T", "1"},    optT, optionGroup, true,  one,  true,  one,  optT));
        lst.add(Arguments.of(new String[]{"--tee"},      optT, optionGroup, true,  null, true,  null, optT));
        lst.add(Arguments.of(new String[]{"--tee", "1"}, optT, optionGroup, true,  one,  true,  one,  optT));

        // ── optT is the queried option; -U / --you is used on the CLI (optT not selected) ──────
        lst.add(Arguments.of(new String[]{"-U"},         optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[]{"-U", "1"},    optT, optionGroup, false, null, false, one,  optU));
        lst.add(Arguments.of(new String[]{"--you"},      optT, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[]{"--you", "1"}, optT, optionGroup, false, null, false, one,  optU));

        // ── optU is the queried option; -T / --tee is used on the CLI (optU not selected) ──────
        lst.add(Arguments.of(new String[]{"-T"},         optU, optionGroup, false, null, true,  null, optT));
        lst.add(Arguments.of(new String[]{"-T", "1"},    optU, optionGroup, false, null, true,  one,  optT));
        lst.add(Arguments.of(new String[]{"--tee"},      optU, optionGroup, false, null, true,  null, optT));
        lst.add(Arguments.of(new String[]{"--tee", "1"}, optU, optionGroup, false, null, true,  one,  optT));

        // ── optU is the queried option; -U / --you is used on the CLI ──────────────────────────
        lst.add(Arguments.of(new String[]{"-U"},         optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[]{"-U", "1"},    optU, optionGroup, false, one,  false, one,  optU));
        lst.add(Arguments.of(new String[]{"--you"},      optU, optionGroup, false, null, false, null, optU));
        lst.add(Arguments.of(new String[]{"--you", "1"}, optU, optionGroup, false, one,  false, one,  optU));

        return lst.stream();
    }

    /** Returns the first character of an option's short name (e.g. 'T' for option "T"). */
    private char firstChar(final Option opt) {
        return opt.getOpt().charAt(0);
    }

    /**
     * Asserts that the deprecation handler was called exactly once (with {@code expectedOption})
     * when {@code expectTriggered} is {@code true}, or not called at all otherwise.
     * Clears the handler list after checking so each assertion block starts fresh.
     */
    private void checkHandler(final boolean expectTriggered, final List<Option> handler, final Option expectedOption) {
        if (expectTriggered) {
            assertEquals(1, handler.size());
            assertEquals(expectedOption, handler.get(0));
        } else {
            assertEquals(0, handler.size());
        }
        handler.clear();
    }

    @ParameterizedTest(name = "{0}, {1}")
    @MethodSource("createParsedOptionValueParameters")
    void testGetParsedOptionValue(
            final String[] args,
            final Option opt,
            final OptionGroup optionGroup,
            final boolean optDep,
            final Integer optValue,
            final boolean grpDep,
            final Integer grpValue,
            final Option grpOpt) throws ParseException {

        final Options options = new Options().addOptionGroup(optionGroup);
        final List<Option> handler = new ArrayList<>();
        final CommandLine commandLine = DefaultParser.builder()
                .setDeprecatedHandler(handler::add).get()
                .parse(options, args);

        // Fallback value returned when an option is absent or has no argument
        final Integer fallback = 2;
        final Supplier<Integer> fallbackSupplier = () -> 2;

        // Pre-compute "option value if present, otherwise fallback" to avoid repeated ternaries
        final Integer optExpectedOrFallback = (optValue != null) ? optValue : fallback;
        final Integer grpExpectedOrFallback = (grpValue != null) ? grpValue : fallback;

        // An unrelated group (neither option was parsed), used to verify "no match" behaviour
        final OptionGroup otherGroup = new OptionGroup()
                .addOption(Option.builder("o").longOpt("other").hasArg().get())
                .addOption(Option.builder().option("p").longOpt("part").hasArg().get());
        final OptionGroup nullGroup = null;

        // ── Query by char key (e.g. 'T') ─────────────────────────────────────────────────────
        assertEquals(optValue,             commandLine.getParsedOptionValue(firstChar(opt)));
        checkHandler(optDep, handler, opt);
        assertEquals(optExpectedOrFallback, commandLine.getParsedOptionValue(firstChar(opt), fallback));
        checkHandler(optDep, handler, opt);
        assertEquals(optExpectedOrFallback, commandLine.getParsedOptionValue(firstChar(opt), fallbackSupplier));
        checkHandler(optDep, handler, opt);

        // ── Query by short option name (e.g. "T") ────────────────────────────────────────────
        assertEquals(optValue,             commandLine.getParsedOptionValue(opt.getOpt()));
        checkHandler(optDep, handler, opt);
        assertEquals(optExpectedOrFallback, commandLine.getParsedOptionValue(opt.getOpt(), fallback));
        checkHandler(optDep, handler, opt);
        assertEquals(optExpectedOrFallback, commandLine.getParsedOptionValue(opt.getOpt(), fallbackSupplier));
        checkHandler(optDep, handler, opt);

        // ── Query by long option name (e.g. "tee") ───────────────────────────────────────────
        assertEquals(optValue,             commandLine.getParsedOptionValue(opt.getLongOpt()));
        checkHandler(optDep, handler, opt);
        assertEquals(optExpectedOrFallback, commandLine.getParsedOptionValue(opt.getLongOpt(), fallback));
        checkHandler(optDep, handler, opt);
        assertEquals(optExpectedOrFallback, commandLine.getParsedOptionValue(opt.getLongOpt(), fallbackSupplier));
        checkHandler(optDep, handler, opt);

        // ── Query by Option object ────────────────────────────────────────────────────────────
        assertEquals(optValue,             commandLine.getParsedOptionValue(opt));
        checkHandler(optDep, handler, opt);
        assertEquals(optExpectedOrFallback, commandLine.getParsedOptionValue(opt, fallback));
        checkHandler(optDep, handler, opt);
        assertEquals(optExpectedOrFallback, commandLine.getParsedOptionValue(opt, fallbackSupplier));
        checkHandler(optDep, handler, opt);

        // ── Query by the OptionGroup that contains the parsed option ──────────────────────────
        assertEquals(grpValue,             commandLine.getParsedOptionValue(optionGroup));
        checkHandler(grpDep, handler, grpOpt);
        assertEquals(grpExpectedOrFallback, commandLine.getParsedOptionValue(optionGroup, fallback));
        checkHandler(grpDep, handler, grpOpt);
        assertEquals(grpExpectedOrFallback, commandLine.getParsedOptionValue(optionGroup, fallbackSupplier));
        checkHandler(grpDep, handler, grpOpt);

        // ── Query by an OptionGroup whose options were never parsed — always null / fallback ──
        assertNull(commandLine.getParsedOptionValue(otherGroup));
        checkHandler(false, handler, grpOpt);
        assertEquals(fallback, commandLine.getParsedOptionValue(otherGroup, fallback));
        checkHandler(false, handler, grpOpt);
        assertEquals(fallback, commandLine.getParsedOptionValue(otherGroup, fallbackSupplier));
        checkHandler(false, handler, grpOpt);

        // ── Query by a null OptionGroup — always null / fallback ─────────────────────────────
        assertNull(commandLine.getParsedOptionValue(nullGroup));
        checkHandler(false, handler, grpOpt);
        assertEquals(fallback, commandLine.getParsedOptionValue(nullGroup, fallback));
        checkHandler(false, handler, grpOpt);
        assertEquals(fallback, commandLine.getParsedOptionValue(nullGroup, fallbackSupplier));
        checkHandler(false, handler, grpOpt);

        // ── Query by an option name that does not exist — always null / fallback ───────────
        assertNull(commandLine.getParsedOptionValue("Nope"));
        checkHandler(false, handler, opt);
        assertEquals(fallback, commandLine.getParsedOptionValue("Nope", fallback));
        checkHandler(false, handler, opt);
        assertEquals(fallback, commandLine.getParsedOptionValue("Nope", fallbackSupplier));
        checkHandler(false, handler, opt);
    }
}
