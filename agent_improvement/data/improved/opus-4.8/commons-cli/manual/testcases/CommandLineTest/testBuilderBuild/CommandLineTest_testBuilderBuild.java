package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CommandLine.Builder} produces a {@link CommandLine} that
 * preserves every left-over argument and option it was given, in order.
 */
public class CommandLineTest_testBuilderBuild {

    @Test
    void testBuilderBuild() {
        // Build a CommandLine with two left-over args ("foo", "bar") and one option ("-T").
        final CommandLine cmd = CommandLine.builder()
                .addArg("foo")
                .addArg("bar")
                .addOption(Option.builder("T").get())
                .build();

        // The first left-over argument is reachable as an array element.
        assertEquals("foo", cmd.getArgs()[0]);
        // The second left-over argument is reachable through the argument list (same order).
        assertEquals("bar", cmd.getArgList().get(1));
        // The single processed option keeps its short name "T".
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }
}
