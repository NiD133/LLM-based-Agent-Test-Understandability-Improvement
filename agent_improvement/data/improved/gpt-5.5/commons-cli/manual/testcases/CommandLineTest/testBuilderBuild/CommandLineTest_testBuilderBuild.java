package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandLineTest_testBuilderBuild {

    @Test
    void testBuilderBuild() {
        final Option teeOption = Option.builder("T").get();

        final CommandLine cmd = CommandLine.builder()
            .addArg("foo")
            .addArg("bar")
            .addOption(teeOption)
            .build();

        assertEquals("foo", cmd.getArgs()[0]);
        assertEquals("bar", cmd.getArgList().get(1));
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }
}
