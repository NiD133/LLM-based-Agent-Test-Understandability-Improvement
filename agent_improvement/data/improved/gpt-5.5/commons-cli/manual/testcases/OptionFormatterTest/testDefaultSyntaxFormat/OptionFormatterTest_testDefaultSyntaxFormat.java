package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testDefaultSyntaxFormat {

    @Test
    void testDefaultSyntaxFormat() {
        Option option = Option.builder().option("o").longOpt("opt").hasArg().get();
        OptionFormatter formatter = OptionFormatter.from(option);

        assertEquals("[-o <arg>]", formatter.toSyntaxOption());
        assertEquals("-o <arg>", formatter.toSyntaxOption(true));

        option = Option.builder().option("o").longOpt("opt").hasArg().required().get();
        formatter = OptionFormatter.from(option);

        assertEquals("-o <arg>", formatter.toSyntaxOption());
        assertEquals("[-o <arg>]", formatter.toSyntaxOption(false));
    }
}
