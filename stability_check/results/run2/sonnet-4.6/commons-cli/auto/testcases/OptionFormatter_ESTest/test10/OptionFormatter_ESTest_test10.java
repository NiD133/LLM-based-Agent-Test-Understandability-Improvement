package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;

public class OptionFormatter_ESTest_test10 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that {@link OptionFormatter.Builder#toArgName(String)} wraps the given argument
     * name with the default angle-bracket delimiters ("<" and ">").
     *
     * A single whitespace argument name should become "< >", confirming that the builder
     * applies its default delimiters without trimming or altering the supplied value.
     */
    @Test(timeout = 4000)
    public void test_toArgName_wrapsValueWithDefaultAngleBracketDelimiters() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();

        String formattedArgName = builder.toArgName(" ");

        assertEquals("< >", formattedArgName);
    }
}
