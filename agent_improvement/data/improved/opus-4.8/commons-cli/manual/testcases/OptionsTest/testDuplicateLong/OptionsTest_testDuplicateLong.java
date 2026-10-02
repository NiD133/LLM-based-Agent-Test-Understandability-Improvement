package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link Options} handles two options that are registered under the
 * same key. This test exercises deprecated APIs on purpose.
 */
@SuppressWarnings("deprecation")
public class OptionsTest_testDuplicateLong {

    /**
     * Asserts that an option can produce its textual representations without
     * failing. Both {@link Option#toString()} and {@link Option#toDeprecatedString()}
     * must return a non-null String.
     */
    private void assertToStringsAreNonNull(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testDuplicateLong() {
        final String optionKey = "a";

        // Register two options under the same key; only the description differs.
        final Options options = new Options();
        options.addOption(optionKey, "--a", false, "toggle -a");
        options.addOption(optionKey, "--a", false, "toggle -a*");

        // When a key is added twice, the most recently added option replaces the first.
        final Option registeredOption = options.getOption(optionKey);
        assertEquals("toggle -a*", registeredOption.getDescription(), "last one in wins");
        assertToStringsAreNonNull(registeredOption);
    }
}
