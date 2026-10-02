package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testSingleOption {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        options = new Options();
        options.addOptionGroup(groupOf(
                new Option("f", "file", false, "file to process"),
                new Option("d", "directory", false, "directory to process")));
        options.addOptionGroup(groupOf(
                new Option("s", "section", false, "section to process"),
                new Option("c", "chapter", false, "chapter to process")));
        options.addOptionGroup(groupOf(
                new Option(null, "import", false, "section to process"),
                new Option(null, "export", false, "chapter to process")));
        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testSingleOption() throws Exception {
        final String[] args = { "-r" };

        final CommandLine cl = parser.parse(options, args);

        assertTrue(cl.hasOption("r"), "Confirm -r is set");
        assertFalse(cl.hasOption("f"), "Confirm -f is NOT set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    private OptionGroup groupOf(final Option firstOption, final Option secondOption) {
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        return optionGroup;
    }
}
