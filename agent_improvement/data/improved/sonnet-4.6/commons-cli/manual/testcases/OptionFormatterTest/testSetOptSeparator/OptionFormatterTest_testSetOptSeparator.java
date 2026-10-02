package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetOptSeparator {

    @Test
    void testSetOptSeparator() {
        // An option with both a short form (-o) and a long form (--opt)
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // A custom non-empty separator is used as-is between short and long opt
        OptionFormatter.Builder builder = OptionFormatter.builder().setOptSeparator(" and ");
        assertEquals("-o and --opt", builder.build(option).getBothOpt());

        // An empty string separator falls back to "" (no visible separator between opts)
        builder = OptionFormatter.builder().setOptSeparator("");
        assertEquals("-o--opt", builder.build(option).getBothOpt(), "Empty string should return default");

        // A null separator also falls back to "" (same behaviour as empty string)
        builder = OptionFormatter.builder().setOptSeparator(null);
        assertEquals("-o--opt", builder.build(option).getBothOpt(), "null string should return default");
    }
}
