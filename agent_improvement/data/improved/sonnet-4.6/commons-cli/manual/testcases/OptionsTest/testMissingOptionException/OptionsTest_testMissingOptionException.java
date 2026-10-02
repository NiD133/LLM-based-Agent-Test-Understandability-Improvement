package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// Tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testMissingOptionException {

    /**
     * Verifies that parsing an empty argument list when a required option is declared
     * throws MissingOptionException with a message identifying the missing option by name.
     */
    @Test
    void testMissingOptionException() throws ParseException {
        // Arrange: declare option "f" as required using the deprecated OptionBuilder API
        Options options = new Options();
        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("f"));

        // Act & Assert: parsing empty args must throw, and the message must name the missing option
        MissingOptionException e = assertThrows(
                MissingOptionException.class,
                () -> new PosixParser().parse(options, new String[0]));
        assertEquals("Missing required option: f", e.getMessage());
    }
}
