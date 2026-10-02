package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CommandLineTest_testBuilderGet {

    @Test
    void testBuilderGet() {
        // Build a CommandLine programmatically with two positional arguments and one option
        final CommandLine cmd = CommandLine.builder()
                .addArg("foo")
                .addArg("bar")
                .addOption(Option.builder("T").get())
                .get();

        // Verify positional arguments are accessible both as array and as List
        assertEquals("foo", cmd.getArgs()[0]);
        assertEquals("bar", cmd.getArgList().get(1));

        // Verify the option "T" was recorded
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }
}
