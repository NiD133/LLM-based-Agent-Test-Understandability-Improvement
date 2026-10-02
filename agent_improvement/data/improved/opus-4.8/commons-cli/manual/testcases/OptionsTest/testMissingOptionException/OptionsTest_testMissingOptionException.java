package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that parsing a command line which omits a required option fails with a
 * {@link MissingOptionException} that names the missing option.
 *
 * <p>Uses the deprecated {@link OptionBuilder} API to build the required option.</p>
 */
@SuppressWarnings("deprecation")
public class OptionsTest_testMissingOptionException {

    @Test
    void testMissingOptionException() throws ParseException {
        // Define a single option "-f" and mark it as required.
        OptionBuilder.isRequired();
        final Options options = new Options();
        options.addOption(OptionBuilder.create("f"));

        // Parse an empty command line, so the required option "-f" is absent.
        final MissingOptionException exception = assertThrows(
                MissingOptionException.class,
                () -> new PosixParser().parse(options, new String[0]));

        // The exception message should identify the missing required option.
        assertEquals("Missing required option: f", exception.getMessage());
    }
}
