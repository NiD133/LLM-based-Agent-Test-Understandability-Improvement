package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandLineTest_testBuilderNullOption {

    @Test
    void testBuilderNullOption() {
        final CommandLine.Builder builder = CommandLine.builder();
        builder.addArg("foo").addArg("bar");

        builder.addOption(null);
        builder.addOption(null);
        builder.addOption(null);

        final CommandLine commandLine = builder.build();

        assertEquals("foo", commandLine.getArgs()[0]);
        assertEquals("bar", commandLine.getArgList().get(1));
        assertEquals(0, commandLine.getOptions().length);
    }
}
