package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

// Tests deprecated Option string output.
@SuppressWarnings("deprecation")
public class OptionsTest_testDuplicateLong {

    private static final String OPTION_NAME = "a";
    private static final String LONG_OPTION_NAME = "--a";
    private static final String ORIGINAL_DESCRIPTION = "toggle -a";
    private static final String REPLACEMENT_DESCRIPTION = "toggle -a*";

    private void assertStringRepresentationsExist(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testDuplicateLong() {
        final Options options = new Options();

        options.addOption(OPTION_NAME, LONG_OPTION_NAME, false, ORIGINAL_DESCRIPTION);
        options.addOption(OPTION_NAME, LONG_OPTION_NAME, false, REPLACEMENT_DESCRIPTION);

        assertEquals(REPLACEMENT_DESCRIPTION, options.getOption(OPTION_NAME).getDescription(), "last one in wins");
        assertStringRepresentationsExist(options.getOption(OPTION_NAME));
    }
}
