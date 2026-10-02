package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CommandLineTest_testBuilderNullArgs {

    /**
     * Verifies that null arguments passed to Builder.addArg() are silently ignored:
     * the resulting CommandLine should have an empty args array, while options
     * added normally are still present.
     */
    @Test
    void testBuilderNullArgs() {
        final CommandLine.Builder builder = CommandLine.builder();
        builder.addArg(null).addArg(null);
        builder.addOption(Option.builder("T").get());
        final CommandLine cmd = builder.build();
        assertEquals(0, cmd.getArgs().length);
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }
}
