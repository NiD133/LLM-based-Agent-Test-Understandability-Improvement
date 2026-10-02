package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetOptArgumentSeparator {

    private static final Option OPTION_WITH_SHORT_LONG_AND_ARGUMENT = Option.builder()
            .option("o")
            .longOpt("opt")
            .hasArg()
            .get();

    @Test
    void testSetOptArgumentSeparator() {
        assertSyntaxWithSeparator(" with argument named ", "[-o with argument named <arg>]");
        assertSyntaxWithSeparator(null, "[-o<arg>]");
        assertSyntaxWithSeparator("=", "[-o=<arg>]");
    }

    private void assertSyntaxWithSeparator(final String separator, final String expectedSyntax) {
        final OptionFormatter.Builder builder = OptionFormatter.builder().setOptArgSeparator(separator);

        assertEquals(expectedSyntax, builder.build(OPTION_WITH_SHORT_LONG_AND_ARGUMENT).toSyntaxOption());
    }
}
