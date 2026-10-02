package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies the {@link Option}s tracked by a {@link CommandLine} built with the
 * protected no-arg constructor.
 */
public class CommandLineTest_testGetOptionsCtor {

    @Test
    void testGetOptionsCtor() {
        final CommandLine cmd = new CommandLine();

        // A freshly constructed CommandLine exposes an empty (but non-null) option array.
        assertNotNull(cmd.getOptions());
        assertEquals(0, cmd.getOptions().length);

        // Three real options are recorded; the null option is silently ignored.
        cmd.addOption(new Option("a", null));
        cmd.addOption(new Option("b", null));
        cmd.addOption(new Option("c", null));
        cmd.addOption(null);

        assertEquals(3, cmd.getOptions().length);
    }
}
