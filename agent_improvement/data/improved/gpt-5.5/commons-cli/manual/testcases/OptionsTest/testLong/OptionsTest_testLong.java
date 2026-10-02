package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class OptionsTest_testLong {

    @Test
    void testLong() {
        final String toggleShortName = "a";
        final String toggleLongName = "--a";
        final String valueShortName = "b";
        final String valueLongName = "--b";

        final Options options = new Options();
        options.addOption(toggleShortName, toggleLongName, false, "toggle -a");
        options.addOption(valueShortName, valueLongName, true, "set -b");

        assertTrue(options.hasOption(toggleShortName));
        assertTrue(options.hasOption(valueShortName));
    }
}
