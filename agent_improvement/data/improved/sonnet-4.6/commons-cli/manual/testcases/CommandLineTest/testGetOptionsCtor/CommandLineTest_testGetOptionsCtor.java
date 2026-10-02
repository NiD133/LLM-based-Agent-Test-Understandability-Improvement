package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class CommandLineTest_testGetOptionsCtor {

    @Test
    void testGetOptionsCtor() {
        final CommandLine cmd = new CommandLine();
        assertNotNull(cmd.getOptions());
        assertEquals(0, cmd.getOptions().length);
        cmd.addOption(new Option("a", null));
        cmd.addOption(new Option("b", null));
        cmd.addOption(new Option("c", null));
        cmd.addOption(null);
        assertEquals(3, cmd.getOptions().length);
    }
}
