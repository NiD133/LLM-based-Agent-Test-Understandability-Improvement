package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testAsSyntaxOption {

    private void assertSyntaxOption(final Option option, final String expectedSyntax, final String assertionMessage) {
        final OptionFormatter formatter = OptionFormatter.from(option);
        assertEquals(expectedSyntax, formatter.toSyntaxOption(), assertionMessage);
    }

    @Test
    void testAsSyntaxOption() {
        assertSyntaxOption(Option.builder().option("o").longOpt("opt").hasArg().get(), "[-o <arg>]", "optional arg failed");
        assertSyntaxOption(Option.builder().option("o").longOpt("opt").hasArg().argName("other").get(), "[-o <other>]",
                "optional 'other' arg failed");
        assertSyntaxOption(Option.builder().option("o").longOpt("opt").hasArg().required().argName("other").get(), "-o <other>",
                "required 'other' arg failed");
        assertSyntaxOption(Option.builder().option("o").longOpt("opt").required().argName("other").get(), "-o", "required no arg failed");
        assertSyntaxOption(Option.builder().option("o").argName("other").get(), "[-o]", "optional no arg arg failed");
        assertSyntaxOption(Option.builder().longOpt("opt").hasArg().argName("other").get(), "[--opt <other>]",
                "optional longOpt 'other' arg failed");
        assertSyntaxOption(Option.builder().longOpt("opt").required().hasArg().argName("other").get(), "--opt <other>",
                "required longOpt 'other' arg failed");
        assertSyntaxOption(Option.builder().option("ot").longOpt("opt").hasArg().get(), "[-ot <arg>]",
                "optional multi char opt arg failed");
    }
}
