package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class CommandLineTest_testGetOptionsBuilder {

    @Test
    void testGetOptionsBuilder() {
        final CommandLine commandLine = CommandLine.builder().build();

        assertNotNull(commandLine.getOptions());
        assertEquals(0, commandLine.getOptions().length);

        commandLine.addOption(null);
        commandLine.addOption(new Option("a", null));
        commandLine.addOption(new Option("b", null));
        commandLine.addOption(new Option("c", null));

        assertEquals(3, commandLine.getOptions().length);
    }
}
