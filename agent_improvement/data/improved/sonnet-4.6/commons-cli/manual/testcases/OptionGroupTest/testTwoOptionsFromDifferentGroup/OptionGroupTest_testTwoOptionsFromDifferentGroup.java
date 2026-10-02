package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoOptionsFromDifferentGroup {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        // Group 1: file vs. directory (mutually exclusive)
        final OptionGroup fileOrDirGroup = new OptionGroup()
                .addOption(new Option("f", "file", false, "file to process"))
                .addOption(new Option("d", "directory", false, "directory to process"));

        // Group 2: section vs. chapter (mutually exclusive)
        final OptionGroup sectionOrChapterGroup = new OptionGroup()
                .addOption(new Option("s", "section", false, "section to process"))
                .addOption(new Option("c", "chapter", false, "chapter to process"));

        // Group 3: import vs. export (mutually exclusive, long-options only)
        final OptionGroup importOrExportGroup = new OptionGroup()
                .addOption(new Option(null, "import", false, "section to process"))
                .addOption(new Option(null, "export", false, "chapter to process"));

        options = new Options()
                .addOptionGroup(fileOrDirGroup)
                .addOptionGroup(sectionOrChapterGroup)
                .addOptionGroup(importOrExportGroup)
                .addOption("r", "revision", false, "revision number");
    }

    /**
     * Verifies that selecting one option from each of two separate groups is allowed:
     * the parser must accept -f (from group 1) and -s (from group 2) together,
     * while leaving all other options unset and producing no leftover arguments.
     */
    @Test
    void testTwoOptionsFromDifferentGroup() throws Exception {
        final String[] args = {"-f", "-s"};
        final CommandLine cl = parser.parse(options, args);

        // Options that must NOT be present
        assertFalse(cl.hasOption("r"), "Confirm -r is NOT set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");

        // Options that MUST be present (one from each group)
        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertTrue(cl.hasOption("s"), "Confirm -s is set");

        assertTrue(cl.getArgList().isEmpty(), "Confirm NO extra args");
    }
}
