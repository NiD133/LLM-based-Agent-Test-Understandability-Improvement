package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetOptSeparator {

    /**
     * Verifies that {@link OptionFormatter.Builder#setOptSeparator(String)} controls the text placed
     * between the short and long option in {@link OptionFormatter#getBothOpt()}, and that empty or
     * null separators fall back to the default (empty) separator.
     */
    @Test
    void testSetOptSeparator() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // A custom separator is inserted verbatim between the short and long option.
        final OptionFormatter customSeparator = OptionFormatter.builder().setOptSeparator(" and ").build(option);
        assertEquals("-o and --opt", customSeparator.getBothOpt());

        // An empty separator is treated as the default, joining the options directly.
        final OptionFormatter emptySeparator = OptionFormatter.builder().setOptSeparator("").build(option);
        assertEquals("-o--opt", emptySeparator.getBothOpt(), "Empty string should return default");

        // A null separator is likewise treated as the default.
        final OptionFormatter nullSeparator = OptionFormatter.builder().setOptSeparator(null).build(option);
        assertEquals("-o--opt", nullSeparator.getBothOpt(), "null string should return default");
    }
}
