package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CommandLine#getOptions()} reflects the options added to a
 * {@link CommandLine} created through its {@link CommandLine.Builder}.
 */
public class CommandLineTest_testGetOptionsBuilder {

    @Test
    void testGetOptionsBuilder() {
        final CommandLine cmd = CommandLine.builder().build();

        // A freshly built command line exposes a non-null, empty options array.
        assertNotNull(cmd.getOptions());
        assertEquals(0, cmd.getOptions().length);

        // A null option is ignored; the three real options are stored.
        cmd.addOption(null);
        cmd.addOption(new Option("a", null));
        cmd.addOption(new Option("b", null));
        cmd.addOption(new Option("c", null));

        assertEquals(3, cmd.getOptions().length);
    }
}
