package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CommandLine.Builder} silently ignores {@code null} options while still
 * keeping any arguments that were added.
 */
public class CommandLineTest_testBuilderNullOption {

    @Test
    void testBuilderNullOption() {
        // Build a CommandLine with two real arguments and three null options.
        final CommandLine.Builder builder = CommandLine.builder();
        builder.addArg("foo").addArg("bar");
        builder.addOption(null);
        builder.addOption(null);
        builder.addOption(null);
        final CommandLine cmd = builder.build();

        // The arguments are preserved, in order...
        assertEquals("foo", cmd.getArgs()[0]);
        assertEquals("bar", cmd.getArgList().get(1));
        // ...but the null options are dropped, leaving no processed options.
        assertEquals(0, cmd.getOptions().length);
    }
}
