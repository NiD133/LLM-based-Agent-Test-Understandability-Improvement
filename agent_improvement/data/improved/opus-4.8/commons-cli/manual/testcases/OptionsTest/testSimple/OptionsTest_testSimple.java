package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that options registered via {@link Options#addOption(String, boolean, String)}
 * can subsequently be looked up with {@link Options#hasOption(String)}.
 */
public class OptionsTest_testSimple {

    @Test
    void testSimple() {
        final Options options = new Options();

        // Register a flag option "-a" (takes no argument) and an option "-b" that requires an argument.
        options.addOption("a", false, "toggle -a");
        options.addOption("b", true, "toggle -b");

        // Both registered options should now be reported as present.
        assertTrue(options.hasOption("a"), "option '-a' should be registered");
        assertTrue(options.hasOption("b"), "option '-b' should be registered");
    }
}
