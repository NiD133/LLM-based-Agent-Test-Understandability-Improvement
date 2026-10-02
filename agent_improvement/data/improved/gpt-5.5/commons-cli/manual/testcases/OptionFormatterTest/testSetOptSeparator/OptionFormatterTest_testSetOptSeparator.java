package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetOptSeparator {

    @Test
    void testSetOptSeparator() {
        final Option optionWithShortAndLongNames = Option.builder().option("o").longOpt("opt").hasArg().get();

        OptionFormatter.Builder builder = OptionFormatter.builder().setOptSeparator(" and ");
        assertEquals("-o and --opt", builder.build(optionWithShortAndLongNames).getBothOpt());

        builder = OptionFormatter.builder().setOptSeparator("");
        assertEquals("-o--opt", builder.build(optionWithShortAndLongNames).getBothOpt(), "Empty string should return default");

        builder = OptionFormatter.builder().setOptSeparator(null);
        assertEquals("-o--opt", builder.build(optionWithShortAndLongNames).getBothOpt(), "null string should return default");
    }
}
