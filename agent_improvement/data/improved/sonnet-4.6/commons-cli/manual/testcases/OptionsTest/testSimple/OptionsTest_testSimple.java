package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class OptionsTest_testSimple {

    @Test
    void testSimple() {
        final Options options = new Options();
        // "a" is a flag (no argument); "b" accepts an argument
        options.addOption("a", false, "toggle -a");
        options.addOption("b", true, "toggle -b");
        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("b"));
    }
}
