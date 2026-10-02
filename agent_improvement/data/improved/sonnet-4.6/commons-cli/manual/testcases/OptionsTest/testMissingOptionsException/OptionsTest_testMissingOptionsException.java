package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// OptionBuilder is deprecated but still exercised here to test the legacy parsing path
@SuppressWarnings("deprecation")
public class OptionsTest_testMissingOptionsException {

    /**
     * Verifies that parsing an empty command-line when two options are marked
     * required throws MissingOptionException and names both missing options in
     * its message, in the order they were registered.
     */
    @Test
    void testMissingOptionsException() throws ParseException {
        final Options options = new Options();

        // Register "-f" as required using the deprecated OptionBuilder API
        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("f"));

        // Register "-x" as required using the deprecated OptionBuilder API
        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("x"));

        // Parsing with no arguments must throw because both required options are absent
        final MissingOptionException e = assertThrows(
                MissingOptionException.class,
                () -> new PosixParser().parse(options, new String[0]));

        assertEquals("Missing required options: f, x", e.getMessage());
    }
}
