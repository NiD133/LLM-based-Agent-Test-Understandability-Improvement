package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetLongOptPrefix {

    @Test
    void testSetLongOptPrefix() {
        // Option with short opt "o" and long opt "opt"
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // A non-empty prefix is prepended to the long option name
        OptionFormatter.Builder builder = OptionFormatter.builder().setLongOptPrefix("fo");
        assertEquals("foopt", builder.build(option).getLongOpt());

        // An empty-string prefix produces the bare long option name
        builder = OptionFormatter.builder().setLongOptPrefix("");
        assertEquals("opt", builder.build(option).getLongOpt());

        // A null prefix is treated the same as an empty prefix
        builder = OptionFormatter.builder().setLongOptPrefix(null);
        assertEquals("opt", builder.build(option).getLongOpt());
    }
}
