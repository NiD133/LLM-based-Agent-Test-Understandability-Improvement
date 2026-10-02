package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetDefaultArgName {

    private static final Option OPTION_WITH_ARGUMENT = Option.builder().option("o").longOpt("opt").hasArg().get();

    @Test
    void testSetDefaultArgName() {
        assertDefaultArgName("foo", "<foo>");
        assertDefaultArgName("", "<arg>");
        assertDefaultArgName(null, "<arg>");
    }

    private void assertDefaultArgName(final String defaultArgName, final String expectedFormattedArgName) {
        final OptionFormatter.Builder builder = OptionFormatter.builder().setDefaultArgName(defaultArgName);

        assertEquals(expectedFormattedArgName, builder.build(OPTION_WITH_ARGUMENT).getArgName());
    }
}
