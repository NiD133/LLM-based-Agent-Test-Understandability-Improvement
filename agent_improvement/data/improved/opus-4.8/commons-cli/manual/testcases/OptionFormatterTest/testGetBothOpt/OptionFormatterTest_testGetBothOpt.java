package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link OptionFormatter#getBothOpt()}, which renders an option's short
 * and long names together (each with its prefix) separated by ", ".
 */
public class OptionFormatterTest_testGetBothOpt {

    @Test
    void testGetBothOpt() {
        // Both a short option ("-o") and a long option ("--opt") are present:
        // they are joined by the default separator ", ".
        final Option shortAndLong = Option.builder().option("o").longOpt("opt").hasArg().get();
        assertEquals("-o, --opt", OptionFormatter.from(shortAndLong).getBothOpt());

        // Only the long option is present: just "--opt" is rendered.
        final Option longOnly = Option.builder().longOpt("opt").hasArg().get();
        assertEquals("--opt", OptionFormatter.from(longOnly).getBothOpt());

        // Only the short option is present: just "-o" is rendered.
        final Option shortOnly = Option.builder().option("o").hasArg().get();
        assertEquals("-o", OptionFormatter.from(shortOnly).getBothOpt());
    }
}
