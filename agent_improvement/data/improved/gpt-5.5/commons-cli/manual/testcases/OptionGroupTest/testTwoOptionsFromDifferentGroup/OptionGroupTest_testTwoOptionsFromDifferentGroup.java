package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests some deprecated classes.
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoOptionsFromDifferentGroup {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        options = new Options()
                .addOptionGroup(createFileLocationGroup())
                .addOptionGroup(createDocumentPartGroup())
                .addOptionGroup(createImportExportGroup())
                .addOption("r", "revision", false, "revision number");
    }

    private OptionGroup createFileLocationGroup() {
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");

        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(file);
        optionGroup.addOption(dir);
        return optionGroup;
    }

    private OptionGroup createDocumentPartGroup() {
        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");

        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(section);
        optionGroup.addOption(chapter);
        return optionGroup;
    }

    private OptionGroup createImportExportGroup() {
        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");

        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(importOpt);
        optionGroup.addOption(exportOpt);
        return optionGroup;
    }

    @Test
    void testTwoOptionsFromDifferentGroup() throws Exception {
        final String[] selectedOptionsFromDifferentGroups = { "-f", "-s" };

        final CommandLine commandLine = parser.parse(options, selectedOptionsFromDifferentGroups);

        assertFalse(commandLine.hasOption("r"), "Confirm -r is NOT set");
        assertTrue(commandLine.hasOption("f"), "Confirm -f is set");
        assertFalse(commandLine.hasOption("d"), "Confirm -d is NOT set");
        assertTrue(commandLine.hasOption("s"), "Confirm -s is set");
        assertFalse(commandLine.hasOption("c"), "Confirm -c is NOT set");
        assertTrue(commandLine.getArgList().isEmpty(), "Confirm NO extra args");
    }
}
