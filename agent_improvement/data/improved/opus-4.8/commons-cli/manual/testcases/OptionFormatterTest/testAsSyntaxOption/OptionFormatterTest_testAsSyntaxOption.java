package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link OptionFormatter#toSyntaxOption()}.
 *
 * <p>Each case builds an {@link Option}, wraps it in an {@link OptionFormatter} and
 * verifies the rendered syntax string. The expectations show how the formatter:</p>
 * <ul>
 *   <li>wraps optional options in {@code [ ]} but leaves required ones bare,</li>
 *   <li>renders the argument name in {@code < >} (using {@code arg} when none is given), and</li>
 *   <li>prefixes the short option with {@code -} or the long option with {@code --}.</li>
 * </ul>
 */
public class OptionFormatterTest_testAsSyntaxOption {

    /**
     * Asserts that the given option is rendered as the expected syntax string.
     *
     * @param expected the expected syntax rendering.
     * @param option   the option to format.
     * @param message  the assertion message describing the scenario.
     */
    private void assertSyntaxOption(final String expected, final Option option, final String message) {
        final OptionFormatter formatter = OptionFormatter.from(option);
        assertEquals(expected, formatter.toSyntaxOption(), message);
    }

    @Test
    void testAsSyntaxOption() {
        // Optional short option with an argument; no argName, so the default "arg" is used.
        assertSyntaxOption("[-o <arg>]",
                Option.builder().option("o").longOpt("opt").hasArg().get(),
                "optional arg failed");

        // Optional short option with a custom argument name.
        assertSyntaxOption("[-o <other>]",
                Option.builder().option("o").longOpt("opt").hasArg().argName("other").get(),
                "optional 'other' arg failed");

        // Required short option with a custom argument name: no surrounding brackets.
        assertSyntaxOption("-o <other>",
                Option.builder().option("o").longOpt("opt").hasArg().required().argName("other").get(),
                "required 'other' arg failed");

        // Required short option without an argument: argName is ignored because hasArg() is not set.
        assertSyntaxOption("-o",
                Option.builder().option("o").longOpt("opt").required().argName("other").get(),
                "required no arg failed");

        // Optional short option without an argument.
        assertSyntaxOption("[-o]",
                Option.builder().option("o").argName("other").get(),
                "optional no arg arg failed");

        // Optional long-only option with a custom argument name.
        assertSyntaxOption("[--opt <other>]",
                Option.builder().longOpt("opt").hasArg().argName("other").get(),
                "optional longOpt 'other' arg failed");

        // Required long-only option with a custom argument name.
        assertSyntaxOption("--opt <other>",
                Option.builder().longOpt("opt").required().hasArg().argName("other").get(),
                "required longOpt 'other' arg failed");

        // Optional multi-character short option with the default argument name.
        assertSyntaxOption("[-ot <arg>]",
                Option.builder().option("ot").longOpt("opt").hasArg().get(),
                "optional multi char opt arg failed");
    }
}
