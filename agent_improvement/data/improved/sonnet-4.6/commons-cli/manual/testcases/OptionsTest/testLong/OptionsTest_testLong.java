package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class OptionsTest_testLong {

    @Test
    void testLong() {
        // Register two options: "a" (no argument) and "b" (requires an argument),
        // each paired with a long-form name.
        final Options options = new Options();
        options.addOption("a", "--a", false, "toggle -a");
        options.addOption("b", "--b", true, "set -b");

        // Both options must be retrievable by their short name after registration.
        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("b"));
    }
}
