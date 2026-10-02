package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetArgumentNameDelimiters {

    /** An option that accepts one argument, used as the subject for all delimiter tests. */
    private static final Option OPTION_WITH_ARG = Option.builder().option("o").longOpt("opt").hasArg().get();

    @Test
    void testSetArgumentNameDelimiters_withCustomDelimiters_wrapsArgName() {
        OptionFormatter.Builder builder = OptionFormatter.builder()
                .setArgumentNameDelimiters("with argument named ", ".");
        assertEquals("with argument named arg.", builder.build(OPTION_WITH_ARG).getArgName());
    }

    @Test
    void testSetArgumentNameDelimiters_nullBeginDelimiter_treatedAsEmpty() {
        OptionFormatter.Builder builder = OptionFormatter.builder()
                .setArgumentNameDelimiters(null, "");
        assertEquals("arg", builder.build(OPTION_WITH_ARG).getArgName());
    }

    @Test
    void testSetArgumentNameDelimiters_nullEndDelimiter_treatedAsEmpty() {
        OptionFormatter.Builder builder = OptionFormatter.builder()
                .setArgumentNameDelimiters("", null);
        assertEquals("arg", builder.build(OPTION_WITH_ARG).getArgName());
    }
}
