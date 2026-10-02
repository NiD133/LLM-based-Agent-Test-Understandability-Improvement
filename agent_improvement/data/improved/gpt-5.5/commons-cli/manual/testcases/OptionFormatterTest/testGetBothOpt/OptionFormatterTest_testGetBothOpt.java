package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testGetBothOpt {

    @Test
    void testGetBothOpt() {
        Option option = Option.builder().option("o").longOpt("opt").hasArg().get();
        OptionFormatter underTest = OptionFormatter.from(option);
        assertEquals("-o, --opt", underTest.getBothOpt());

        option = Option.builder().longOpt("opt").hasArg().get();
        underTest = OptionFormatter.from(option);
        assertEquals("--opt", underTest.getBothOpt());

        option = Option.builder().option("o").hasArg().get();
        underTest = OptionFormatter.from(option);
        assertEquals("-o", underTest.getBothOpt());
    }
}
