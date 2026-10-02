package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class CommandLineTest_testGetOptionsBuilder {

    @Test
    void testGetOptionsBuilder() {
        final CommandLine cmd = CommandLine.builder().build();

        // A freshly built CommandLine must return a non-null, empty options array.
        assertNotNull(cmd.getOptions());
        assertEquals(0, cmd.getOptions().length);

        // null is silently ignored; only non-null options are counted.
        cmd.addOption(null);
        cmd.addOption(new Option("a", null));
        cmd.addOption(new Option("b", null));
        cmd.addOption(new Option("c", null));

        assertEquals(3, cmd.getOptions().length);
    }
}
