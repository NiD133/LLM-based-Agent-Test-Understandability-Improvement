package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link OptionGroup#getNames()}.
 */
// Exercises deprecated APIs (Parser, PosixParser, OptionBuilder).
@SuppressWarnings("deprecation")
public class OptionGroupTest_testGetNames {

    /** Shared fixture mirroring the original OptionGroup test setup. */
    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        // Group 1: file / directory options.
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirectory = new OptionGroup();
        fileOrDirectory.addOption(file);
        fileOrDirectory.addOption(dir);
        options = new Options().addOptionGroup(fileOrDirectory);

        // Group 2: section / chapter options.
        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapter = new OptionGroup();
        sectionOrChapter.addOption(section);
        sectionOrChapter.addOption(chapter);
        options.addOptionGroup(sectionOrChapter);

        // Group 3: long-only import / export options.
        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExport = new OptionGroup();
        importOrExport.addOption(importOpt);
        importOrExport.addOption(exportOpt);
        options.addOptionGroup(importOrExport);

        // A standalone option that is not part of any group.
        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testGetNames() {
        final OptionGroup optionGroup = new OptionGroup();

        // A freshly created group has no selected option.
        assertFalse(optionGroup.isSelected());

        // Add two options whose names are "a" and "b".
        optionGroup.addOption(OptionBuilder.create('a'));
        optionGroup.addOption(OptionBuilder.create('b'));

        // getNames() returns the names of every option added to the group.
        assertNotNull(optionGroup.getNames(), "null names");
        assertEquals(2, optionGroup.getNames().size());
        assertTrue(optionGroup.getNames().contains("a"));
        assertTrue(optionGroup.getNames().contains("b"));
    }
}
