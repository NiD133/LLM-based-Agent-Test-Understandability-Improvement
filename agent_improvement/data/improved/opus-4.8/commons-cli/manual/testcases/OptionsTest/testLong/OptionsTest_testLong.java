package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that options registered with both a short name and a long name can
 * be looked up afterwards via {@link Options#hasOption(String)}.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testLong {

    @Test
    void testLong() {
        final Options options = new Options();

        // Register a flag option ("-a") and a value-taking option ("-b"),
        // each with a corresponding long name and description.
        options.addOption("a", "--a", false, "toggle -a");
        options.addOption("b", "--b", true, "set -b");

        // Both options must now be discoverable by their short names.
        assertTrue(options.hasOption("a"), "option 'a' should be registered");
        assertTrue(options.hasOption("b"), "option 'b' should be registered");
    }
}
