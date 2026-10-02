package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// Tests deprecated parser and builder APIs.
@SuppressWarnings("deprecation")
public class OptionsTest_testMissingOptionException {

    @Test
    void testMissingOptionException() throws ParseException {
        final Options options = new Options();
        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("f"));

        final MissingOptionException missingOptionException = assertThrows(MissingOptionException.class,
                () -> new PosixParser().parse(options, new String[0]));

        assertEquals("Missing required option: f", missingOptionException.getMessage());
    }
}
