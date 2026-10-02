package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link Options} handles adding two options that share the same short name.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testDuplicateSimple {

    /**
     * Asserts that the option can be rendered to a String form without failing.
     * Both {@link Option#toString()} and {@link Option#toDeprecatedString()} must
     * return a non-null value and never throw.
     */
    private void assertRenderableToString(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testDuplicateSimple() {
        final Options options = new Options();

        // Register an option "a" with no argument.
        options.addOption("a", false, "toggle -a");
        assertRenderableToString(options.getOption("a"));

        // Register another option with the same short name "a"; this should replace the first.
        options.addOption("a", true, "toggle -a*");

        // When two options share a key, the most recently added one wins.
        assertEquals("toggle -a*", options.getOption("a").getDescription(), "last one in wins");
        assertRenderableToString(options.getOption("a"));
    }
}
