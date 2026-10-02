package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CommandLineTest_testNullOption {

    @Test
    void testNullOption() throws Exception {
        final Options options = new Options();
        final Option optI = Option.builder("i").hasArg().type(Number.class).get();
        final Option optF = Option.builder("f").hasArg().get();
        options.addOption(optI);
        options.addOption(optF);
        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, new String[] { "-i", "123", "-f", "foo" });

        // Passing null Option or null OptionGroup must always return null, never throw
        assertNull(cmd.getOptionValue((Option) null));
        assertNull(cmd.getParsedOptionValue((Option) null));
        assertNull(cmd.getOptionValue((OptionGroup) null));
        assertNull(cmd.getParsedOptionValue((OptionGroup) null));
    }
}
