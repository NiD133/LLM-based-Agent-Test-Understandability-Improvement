package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoValidLongOptions {

    private static final String[] REVISION_AND_FILE_ARGS = { "--revision", "--file" };

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
        final OptionGroup contentSelectionGroup = new OptionGroup();
        contentSelectionGroup.addOption(section);
        contentSelectionGroup.addOption(chapter);
        options.addOptionGroup(contentSelectionGroup);

        final Option importOption = new Option(null, "import", false, "section to process");
        final Option exportOption = new Option(null, "export", false, "chapter to process");
        final OptionGroup transferDirectionGroup = new OptionGroup();
        transferDirectionGroup.addOption(importOption);
        transferDirectionGroup.addOption(exportOption);
        options.addOptionGroup(transferDirectionGroup);

        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testTwoValidLongOptions() throws Exception {
        final CommandLine commandLine = parser.parse(options, REVISION_AND_FILE_ARGS);

        assertTrue(commandLine.hasOption("r"), "Confirm -r is set");
        assertTrue(commandLine.hasOption("f"), "Confirm -f is set");
        assertFalse(commandLine.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(commandLine.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(commandLine.hasOption("c"), "Confirm -c is NOT set");
        assertTrue(commandLine.getArgList().isEmpty(), "Confirm no extra args");
    }
}
