package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// Tests deprecated parser/builder APIs used by this coverage target.
@SuppressWarnings("deprecation")
public class OptionsTest_testMissingOptionsException {

    @Test
    void testMissingOptionsException() throws ParseException {
        final Options options = new Options();

        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("f"));

        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("x"));

        final MissingOptionException missingOptionsException = assertThrows(
                MissingOptionException.class,
                () -> new PosixParser().parse(options, new String[0]));

        assertEquals("Missing required options: f, x", missingOptionsException.getMessage());
    }
}
