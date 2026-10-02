package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the {@link CommandLine} value getters return {@code null} when handed a
 * {@code null} {@link Option} or {@link OptionGroup}, instead of throwing.
 */
public class CommandLineTest_testNullOption {

    @Test
    void testNullOption() throws Exception {
        // Build a command line with two known options so the getters have a populated instance to query.
        final Options options = new Options();
        options.addOption(Option.builder("i").hasArg().type(Number.class).get());
        options.addOption(Option.builder("f").hasArg().get());

        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, new String[] { "-i", "123", "-f", "foo" });

        // A null Option or OptionGroup must resolve to a null value rather than raising an exception.
        assertNull(cmd.getOptionValue((Option) null));
        assertNull(cmd.getParsedOptionValue((Option) null));
        assertNull(cmd.getOptionValue((OptionGroup) null));
        assertNull(cmd.getParsedOptionValue((OptionGroup) null));
    }
}
