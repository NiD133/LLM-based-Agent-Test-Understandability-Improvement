package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// Tests some deprecated classes (OptionBuilder, PosixParser).
@SuppressWarnings("deprecation")
public class OptionsTest_testMissingOptionsException {

    /**
     * Parsing an empty argument list when two required options ("f" and "x") are
     * registered should fail with a {@link MissingOptionException} that lists every
     * missing required option.
     */
    @Test
    void testMissingOptionsException() throws ParseException {
        // Register two required options: -f and -x.
        final Options options = new Options();
        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("f"));
        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("x"));

        // Parse with no arguments, so both required options are absent.
        final MissingOptionException exception = assertThrows(MissingOptionException.class,
                () -> new PosixParser().parse(options, new String[0]));

        assertEquals("Missing required options: f, x", exception.getMessage());
    }
}
