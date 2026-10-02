package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CommandLine.Builder#addArg(String)} silently ignores {@code null}
 * arguments while real options are still retained.
 */
public class CommandLineTest_testBuilderNullArgs {

    @Test
    void testBuilderNullArgs() {
        // Add two null args (which should be discarded) plus one genuine option "-T".
        final CommandLine.Builder builder = CommandLine.builder();
        builder.addArg(null).addArg(null);
        builder.addOption(Option.builder("T").get());

        final CommandLine cmd = builder.build();

        // The null args were dropped, so no left-over arguments remain.
        assertEquals(0, cmd.getArgs().length);
        // The "-T" option survived and is the only processed option.
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }
}
