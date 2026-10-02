package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link OptionFormatter#toOptional(String)}, which wraps a piece of text in the
 * formatter's optional delimiters (defaulting to square brackets).
 */
public class OptionFormatterTest_testAsOptional {

    /** An option with both a short name ("-o") and a long name ("--opt") that takes an argument. */
    private final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

    @Test
    void testAsOptional() {
        // Default formatter: wraps text in the default delimiters "[" and "]".
        final OptionFormatter defaultFormatter = OptionFormatter.from(option);
        assertEquals("[what]", defaultFormatter.toOptional("what"));
        assertEquals("", defaultFormatter.toOptional(""), "empty string should return empty string");
        assertEquals("", defaultFormatter.toOptional(null), "null should return empty string");

        // Custom formatter: wraps text in the configured delimiters "-> " and " <-".
        final OptionFormatter customFormatter = OptionFormatter.builder()
                .setOptionalDelimiters("-> ", " <-")
                .build(option);
        assertEquals("-> what <-", customFormatter.toOptional("what"));
    }
}
