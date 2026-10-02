package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CommandLineTest_testNullOption {

    @Test
    void testNullOption() throws Exception {
        final Options options = createOptionsWithNumberAndStringArguments();
        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, new String[] { "-i", "123", "-f", "foo" });

        assertNull(cmd.getOptionValue((Option) null));
        assertNull(cmd.getParsedOptionValue((Option) null));
        assertNull(cmd.getOptionValue((OptionGroup) null));
        assertNull(cmd.getParsedOptionValue((OptionGroup) null));
    }

    private Options createOptionsWithNumberAndStringArguments() {
        final Options options = new Options();
        options.addOption(Option.builder("i").hasArg().type(Number.class).get());
        options.addOption(Option.builder("f").hasArg().get());
        return options;
    }
}
