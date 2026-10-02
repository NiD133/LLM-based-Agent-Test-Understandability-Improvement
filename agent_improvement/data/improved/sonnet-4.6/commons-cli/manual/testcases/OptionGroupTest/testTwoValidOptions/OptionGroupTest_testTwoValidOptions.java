package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoValidOptions {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        // Group 1: mutually exclusive file-system target options
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirGroup = new OptionGroup();
        fileOrDirGroup.addOption(file);
        fileOrDirGroup.addOption(dir);

        // Group 2: mutually exclusive document-structure options
        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapterGroup = new OptionGroup();
        sectionOrChapterGroup.addOption(section);
        sectionOrChapterGroup.addOption(chapter);

        // Group 3: mutually exclusive import/export options (long-opt only, no short flag)
        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExportGroup = new OptionGroup();
        importOrExportGroup.addOption(importOpt);
        importOrExportGroup.addOption(exportOpt);

        options = new Options()
                .addOptionGroup(fileOrDirGroup)
                .addOptionGroup(sectionOrChapterGroup)
                .addOptionGroup(importOrExportGroup);

        // Standalone option, not part of any group
        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * Verifies that two valid options from independent option groups can be
     * supplied together: -r (standalone) and -f (from the file/dir group).
     * Confirms that sibling options in the same group are not accidentally set.
     */
    @Test
    void testTwoValidOptions() throws Exception {
        final String[] args = { "-r", "-f" };
        final CommandLine cl = parser.parse(options, args);

        // Both explicitly provided options must be present
        assertTrue(cl.hasOption("r"), "Confirm -r is set");
        assertTrue(cl.hasOption("f"), "Confirm -f is set");

        // Sibling options in the same groups must not be set
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");

        // No leftover unparsed arguments expected
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }
}
