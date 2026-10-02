package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link OptionFormatter.Builder#setLongOptPrefix(String)} controls
 * the prefix that {@link OptionFormatter#getLongOpt()} prepends to an option's long name.
 */
public class OptionFormatterTest_testSetLongOptPrefix {

    /** An option whose short name is "o" and long name is "opt". */
    private static OptionFormatter formatWithLongOptPrefix(final String longOptPrefix) {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();
        return OptionFormatter.builder().setLongOptPrefix(longOptPrefix).build(option);
    }

    @Test
    void testSetLongOptPrefix() {
        // A custom prefix is prepended directly to the long option name ("opt").
        assertEquals("foopt", formatWithLongOptPrefix("fo").getLongOpt());

        // An empty prefix leaves the long option name unchanged.
        assertEquals("opt", formatWithLongOptPrefix("").getLongOpt());

        // A null prefix is treated like an empty prefix.
        assertEquals("opt", formatWithLongOptPrefix(null).getLongOpt());
    }
}
