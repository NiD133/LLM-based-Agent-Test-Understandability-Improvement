package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetArgumentNameDelimiters {

    /**
     * Verifies that {@link OptionFormatter.Builder#setArgumentNameDelimiters(String, String)}
     * wraps the argument name with the configured begin/end delimiters, and that a {@code null}
     * delimiter is treated as an empty string.
     */
    @Test
    void testSetArgumentNameDelimiters() {
        // An option that takes an argument; with no explicit arg name it defaults to "arg".
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Custom begin and end delimiters surround the default argument name.
        OptionFormatter.Builder builder = OptionFormatter.builder()
                .setArgumentNameDelimiters("with argument named ", ".");
        assertEquals("with argument named arg.", builder.build(option).getArgName());

        // A null begin delimiter is treated as "", and an empty end delimiter leaves the name bare.
        builder = OptionFormatter.builder().setArgumentNameDelimiters(null, "");
        assertEquals("arg", builder.build(option).getArgName());

        // Symmetrically, an empty begin delimiter and a null end delimiter also yield the bare name.
        builder = OptionFormatter.builder().setArgumentNameDelimiters("", null);
        assertEquals("arg", builder.build(option).getArgName());
    }
}
