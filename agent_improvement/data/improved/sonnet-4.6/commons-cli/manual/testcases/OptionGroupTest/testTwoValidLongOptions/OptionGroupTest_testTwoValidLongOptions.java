package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoValidLongOptions {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        // Group 1: mutually exclusive file/directory options
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirGroup = new OptionGroup();
        fileOrDirGroup.addOption(file);
        fileOrDirGroup.addOption(dir);
        options = new Options().addOptionGroup(fileOrDirGroup);

        // Group 2: mutually exclusive section/chapter options
        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapterGroup = new OptionGroup();
        sectionOrChapterGroup.addOption(section);
        sectionOrChapterGroup.addOption(chapter);
        options.addOptionGroup(sectionOrChapterGroup);

        // Group 3: mutually exclusive long-only import/export options
        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExportGroup = new OptionGroup();
        importOrExportGroup.addOption(importOpt);
        importOrExportGroup.addOption(exportOpt);
        options.addOptionGroup(importOrExportGroup);

        // Standalone option available alongside any group selection
        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * Verifies that two long-form options from different groups (--revision and --file)
     * can both be specified together. Only those two options should be active; all
     * others must remain unset and no leftover arguments should exist.
     */
    @Test
    void testTwoValidLongOptions() throws Exception {
        final String[] args = { "--revision", "--file" };
        final CommandLine cl = parser.parse(options, args);

        assertTrue(cl.hasOption("r"), "Confirm -r is set");
        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }
}
