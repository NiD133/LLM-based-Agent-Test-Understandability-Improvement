package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testDuplicateSimple {

    /**
     * Verifies that both toString() and toDeprecatedString() return non-null values
     * for the given option, ensuring no unexpected NullPointerException is thrown.
     */
    private void assertToStringMethodsReturnNonNull(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    /**
     * Tests that adding an option with the same short key twice causes the second
     * definition to replace the first — "last one in wins" semantics.
     */
    @Test
    void testDuplicateSimple() {
        final Options options = new Options();

        // Add option "a" without an argument
        options.addOption("a", false, "toggle -a");
        assertToStringMethodsReturnNonNull(options.getOption("a"));

        // Re-add option "a" with a different description; the new definition should win
        options.addOption("a", true, "toggle -a*");
        assertEquals("toggle -a*", options.getOption("a").getDescription(), "last one in wins");
        assertToStringMethodsReturnNonNull(options.getOption("a"));
    }
}
