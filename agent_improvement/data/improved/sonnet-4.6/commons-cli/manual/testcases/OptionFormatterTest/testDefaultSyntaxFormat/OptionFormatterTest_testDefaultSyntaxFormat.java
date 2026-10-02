package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testDefaultSyntaxFormat {

    /**
     * Verifies that an optional option (not required) is wrapped in brackets by default,
     * but can be displayed as required when explicitly requested.
     */
    @Test
    void testOptionalOptionSyntaxFormat() {
        Option optionalOption = Option.builder().option("o").longOpt("opt").hasArg().get();
        OptionFormatter formatter = OptionFormatter.from(optionalOption);

        // Default: not required, so wrapped in optional delimiters
        assertEquals("[-o <arg>]", formatter.toSyntaxOption());
        // Explicitly required: no wrapping
        assertEquals("-o <arg>", formatter.toSyntaxOption(true));
    }

    /**
     * Verifies that a required option is displayed without brackets by default,
     * but can be displayed as optional when explicitly requested.
     */
    @Test
    void testRequiredOptionSyntaxFormat() {
        Option requiredOption = Option.builder().option("o").longOpt("opt").hasArg().required().get();
        OptionFormatter formatter = OptionFormatter.from(requiredOption);

        // Default: required, so no wrapping
        assertEquals("-o <arg>", formatter.toSyntaxOption());
        // Explicitly optional: wrapped in optional delimiters
        assertEquals("[-o <arg>]", formatter.toSyntaxOption(false));
    }
}
