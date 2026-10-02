package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testSingleLongOption {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirectoryGroup = new OptionGroup();
        fileOrDirectoryGroup.addOption(file);
        fileOrDirectoryGroup.addOption(dir);
        options = new Options().addOptionGroup(fileOrDirectoryGroup);

        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapterGroup = new OptionGroup();
        sectionOrChapterGroup.addOption(section);
        sectionOrChapterGroup.addOption(chapter);
        options.addOptionGroup(sectionOrChapterGroup);

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExportGroup = new OptionGroup();
        importOrExportGroup.addOption(importOpt);
        importOrExportGroup.addOption(exportOpt);
        options.addOptionGroup(importOrExportGroup);

        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * Verifies that supplying a single long option (--file) selects only that option
     * and leaves all other options in every group unset, with no leftover arguments.
     */
    @Test
    void testSingleLongOption() throws Exception {
        final String[] args = { "--file" };
        final CommandLine cl = parser.parse(options, args);
        assertFalse(cl.hasOption("r"), "Confirm -r is NOT set");
        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }
}
