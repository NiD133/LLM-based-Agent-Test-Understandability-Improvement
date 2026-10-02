package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetArgumentNameDelimiters {

    private static final Option OPTION_WITH_DEFAULT_ARGUMENT_NAME = Option.builder().option("o").longOpt("opt").hasArg().get();

    @Test
    void testSetArgumentNameDelimiters() {
        assertArgumentName("with argument named ", ".", "with argument named arg.");
        assertArgumentName(null, "", "arg");
        assertArgumentName("", null, "arg");
    }

    private void assertArgumentName(final String openingDelimiter, final String closingDelimiter, final String expectedArgumentName) {
        final OptionFormatter.Builder builder = OptionFormatter.builder().setArgumentNameDelimiters(openingDelimiter, closingDelimiter);

        assertEquals(expectedArgumentName, builder.build(OPTION_WITH_DEFAULT_ARGUMENT_NAME).getArgName());
    }
}
