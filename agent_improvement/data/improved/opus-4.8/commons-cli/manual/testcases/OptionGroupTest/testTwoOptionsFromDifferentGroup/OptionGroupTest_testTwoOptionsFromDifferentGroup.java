package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoOptionsFromDifferentGroup {

    /** All option groups under test, plus a standalone -r option. */
    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        // Group 1: mutually exclusive -f / -d
        final OptionGroup fileOrDirectoryGroup = new OptionGroup();
        fileOrDirectoryGroup.addOption(new Option("f", "file", false, "file to process"));
        fileOrDirectoryGroup.addOption(new Option("d", "directory", false, "directory to process"));

        // Group 2: mutually exclusive -s / -c
        final OptionGroup sectionOrChapterGroup = new OptionGroup();
        sectionOrChapterGroup.addOption(new Option("s", "section", false, "section to process"));
        sectionOrChapterGroup.addOption(new Option("c", "chapter", false, "chapter to process"));

        // Group 3: mutually exclusive --import / --export (long options only)
        final OptionGroup importOrExportGroup = new OptionGroup();
        importOrExportGroup.addOption(new Option(null, "import", false, "section to process"));
        importOrExportGroup.addOption(new Option(null, "export", false, "chapter to process"));

        options = new Options()
                .addOptionGroup(fileOrDirectoryGroup)
                .addOptionGroup(sectionOrChapterGroup)
                .addOptionGroup(importOrExportGroup);

        // Standalone option, not part of any group.
        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * Selecting one option from each of two different groups (-f and -s) is allowed:
     * each group may have at most one selection, but the groups are independent.
     */
    @Test
    void testTwoOptionsFromDifferentGroup() throws Exception {
        final String[] args = { "-f", "-s" };

        final CommandLine cl = parser.parse(options, args);

        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertTrue(cl.hasOption("s"), "Confirm -s is set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertFalse(cl.hasOption("r"), "Confirm -r is NOT set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm NO extra args");
    }
}
