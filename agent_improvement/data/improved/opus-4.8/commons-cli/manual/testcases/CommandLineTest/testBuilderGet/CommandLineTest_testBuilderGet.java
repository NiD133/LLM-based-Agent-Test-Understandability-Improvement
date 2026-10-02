package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CommandLine.Builder} stores the arguments and options it is
 * given and exposes them through the various accessors on the built {@link CommandLine}.
 */
public class CommandLineTest_testBuilderGet {

    @Test
    void testBuilderGet() {
        // Build a CommandLine carrying two left-over arguments ("foo", "bar")
        // and one processed option ("-T").
        final CommandLine cmd = CommandLine.builder()
                .addArg("foo")
                .addArg("bar")
                .addOption(Option.builder("T").get())
                .get();

        // The first left-over argument, read as an array, is "foo".
        assertEquals("foo", cmd.getArgs()[0]);
        // The second left-over argument, read as a list, is "bar".
        assertEquals("bar", cmd.getArgList().get(1));
        // The single stored option is the "-T" option.
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }
}
