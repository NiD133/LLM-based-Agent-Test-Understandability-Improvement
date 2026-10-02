package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link OptionFormatter.Builder#setOptArgSeparator(String)} controls the text placed
 * between an option and its argument name in the generated syntax description.
 */
public class OptionFormatterTest_testSetOptArgumentSeparator {

    /** A short option {@code -o} (long form {@code --opt}) that takes a single argument. */
    private static final Option OPTION_WITH_ARG = Option.builder().option("o").longOpt("opt").hasArg().get();

    /**
     * Builds the syntax description for {@link #OPTION_WITH_ARG} using the given separator
     * between the option and its argument name.
     */
    private static String syntaxWithSeparator(final String optArgSeparator) {
        return OptionFormatter.builder()
                .setOptArgSeparator(optArgSeparator)
                .build(OPTION_WITH_ARG)
                .toSyntaxOption();
    }

    @Test
    void testSetOptArgumentSeparator() {
        // A custom separator appears verbatim between the option and the argument name.
        assertEquals("[-o with argument named <arg>]", syntaxWithSeparator(" with argument named "));

        // A null separator falls back to an empty string, joining the option and argument directly.
        assertEquals("[-o<arg>]", syntaxWithSeparator(null));

        // An "=" separator joins the option and argument with an equals sign.
        assertEquals("[-o=<arg>]", syntaxWithSeparator("="));
    }
}
