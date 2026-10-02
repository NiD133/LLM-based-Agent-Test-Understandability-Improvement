package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CommandLineTest_testBuilderNullOption {

    @Test
    void testBuilderNullOption() {
        // null options must be silently ignored; only non-null args should be retained
        final CommandLine.Builder builder = CommandLine.builder();
        builder.addArg("foo").addArg("bar");
        builder.addOption(null);
        builder.addOption(null);
        builder.addOption(null);
        final CommandLine cmd = builder.build();

        assertEquals("foo", cmd.getArgs()[0]);
        assertEquals("bar", cmd.getArgList().get(1));
        assertEquals(0, cmd.getOptions().length);
    }
}
