package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testDuplicateSimple {

    private static final String SHORT_OPTION = "a";
    private static final String ORIGINAL_DESCRIPTION = "toggle -a";
    private static final String REPLACEMENT_DESCRIPTION = "toggle -a*";

    private void assertStringRepresentationsAreAvailable(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testDuplicateSimple() {
        final Options options = new Options();

        options.addOption(SHORT_OPTION, false, ORIGINAL_DESCRIPTION);
        assertStringRepresentationsAreAvailable(options.getOption(SHORT_OPTION));

        options.addOption(SHORT_OPTION, true, REPLACEMENT_DESCRIPTION);
        assertEquals(REPLACEMENT_DESCRIPTION, options.getOption(SHORT_OPTION).getDescription(), "last one in wins");
        assertStringRepresentationsAreAvailable(options.getOption(SHORT_OPTION));
    }
}
