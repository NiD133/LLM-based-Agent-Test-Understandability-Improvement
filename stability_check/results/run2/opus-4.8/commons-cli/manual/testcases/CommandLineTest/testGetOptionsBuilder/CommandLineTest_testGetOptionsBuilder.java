package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CommandLine} instances created through {@link CommandLine#builder()}
 * expose their processed options via {@link CommandLine#getOptions()}.
 */
public class CommandLineTest_testGetOptionsBuilder {

    @Test
    void testGetOptionsBuilder() {
        // A freshly built command line has no options, but never returns null.
        final CommandLine cmd = CommandLine.builder().build();
        assertNotNull(cmd.getOptions());
        assertEquals(0, cmd.getOptions().length);

        // A null option is silently ignored and does not count towards the total.
        cmd.addOption(null);

        // Three genuine options are added and should all be reported.
        cmd.addOption(new Option("a", null));
        cmd.addOption(new Option("b", null));
        cmd.addOption(new Option("c", null));
        assertEquals(3, cmd.getOptions().length);
    }
}
