package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetLongOptPrefix {

    @Test
    void testSetLongOptPrefix() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        OptionFormatter.Builder builder = OptionFormatter.builder().setLongOptPrefix("fo");
        assertEquals("foopt", builder.build(option).getLongOpt());

        builder = OptionFormatter.builder().setLongOptPrefix("");
        assertEquals("opt", builder.build(option).getLongOpt());

        builder = OptionFormatter.builder().setLongOptPrefix(null);
        assertEquals("opt", builder.build(option).getLongOpt());
    }
}
