package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testSingleOption {

    private Options options;

    private final Parser parser = new PosixParser();

    /** Creates an OptionGroup containing exactly two mutually exclusive options. */
    private OptionGroup buildOptionGroup(final Option first, final Option second) {
        final OptionGroup group = new OptionGroup();
        group.addOption(first);
        group.addOption(second);
        return group;
    }

    @BeforeEach
    public void setUp() {
        // Group 1: file vs directory
        final Option file    = new Option("f", "file",      false, "file to process");
        final Option dir     = new Option("d", "directory", false, "directory to process");

        // Group 2: section vs chapter
        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");

        // Group 3: import vs export (long-only options, no short flag)
        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");

        options = new Options()
                .addOptionGroup(buildOptionGroup(file, dir))
                .addOptionGroup(buildOptionGroup(section, chapter))
                .addOptionGroup(buildOptionGroup(importOpt, exportOpt));

        // Standalone option that belongs to no group
        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * Parses only the standalone "-r" flag and verifies that every option-group
     * member remains unset and that no leftover arguments are present.
     */
    @Test
    void testSingleOption() throws Exception {
        final String[] args = { "-r" };
        final CommandLine cl = parser.parse(options, args);

        assertTrue(cl.hasOption("r"),  "Confirm -r is set");
        assertFalse(cl.hasOption("f"), "Confirm -f is NOT set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }
}
