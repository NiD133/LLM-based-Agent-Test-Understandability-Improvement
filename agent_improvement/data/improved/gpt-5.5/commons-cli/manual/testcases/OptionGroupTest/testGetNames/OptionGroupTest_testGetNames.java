package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testGetNames {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option file = new Option("f", "file", false, "file to process");
        final Option directory = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileSelectionGroup = new OptionGroup();
        fileSelectionGroup.addOption(file);
        fileSelectionGroup.addOption(directory);
        options = new Options().addOptionGroup(fileSelectionGroup);

        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup documentSelectionGroup = new OptionGroup();
        documentSelectionGroup.addOption(section);
        documentSelectionGroup.addOption(chapter);
        options.addOptionGroup(documentSelectionGroup);

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup transferDirectionGroup = new OptionGroup();
        transferDirectionGroup.addOption(importOpt);
        transferDirectionGroup.addOption(exportOpt);
        options.addOptionGroup(transferDirectionGroup);

        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testGetNames() {
        final OptionGroup optionGroup = new OptionGroup();
        assertFalse(optionGroup.isSelected());

        optionGroup.addOption(OptionBuilder.create('a'));
        optionGroup.addOption(OptionBuilder.create('b'));

        assertNotNull(optionGroup.getNames(), "null names");
        assertEquals(2, optionGroup.getNames().size());
        assertTrue(optionGroup.getNames().contains("a"));
        assertTrue(optionGroup.getNames().contains("b"));
    }
}
