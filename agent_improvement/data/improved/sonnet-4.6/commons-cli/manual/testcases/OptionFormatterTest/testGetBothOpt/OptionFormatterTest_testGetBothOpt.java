package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testGetBothOpt {

    @Test
    void testGetBothOpt() {
        // Both short and long opt present: "-o, --opt"
        Option bothOpts = Option.builder().option("o").longOpt("opt").hasArg().get();
        assertEquals("-o, --opt", OptionFormatter.from(bothOpts).getBothOpt());

        // Only long opt present: "--opt"
        Option longOptOnly = Option.builder().longOpt("opt").hasArg().get();
        assertEquals("--opt", OptionFormatter.from(longOptOnly).getBothOpt());

        // Only short opt present: "-o"
        Option shortOptOnly = Option.builder().option("o").hasArg().get();
        assertEquals("-o", OptionFormatter.from(shortOptOnly).getBothOpt());
    }
}
