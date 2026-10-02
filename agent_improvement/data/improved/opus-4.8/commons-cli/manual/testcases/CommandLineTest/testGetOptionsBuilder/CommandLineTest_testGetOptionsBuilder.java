package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class CommandLineTest_testGetOptionsBuilder {

    @Test
    void testGetOptionsBuilder() {
        // A freshly built CommandLine reports an empty, non-null option array.
        final CommandLine cmd = CommandLine.builder().build();
        assertNotNull(cmd.getOptions());
        assertEquals(0, cmd.getOptions().length);

        // addOption ignores null but stores each real Option.
        cmd.addOption(null);
        cmd.addOption(new Option("a", null));
        cmd.addOption(new Option("b", null));
        cmd.addOption(new Option("c", null));

        // The null was skipped, so only the three named options remain.
        assertEquals(3, cmd.getOptions().length);
    }
}
