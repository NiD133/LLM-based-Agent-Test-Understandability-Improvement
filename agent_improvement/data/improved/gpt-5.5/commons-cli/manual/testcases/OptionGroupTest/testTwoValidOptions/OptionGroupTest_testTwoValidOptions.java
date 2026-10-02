package org.apache.commons.cli;

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
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirectory = new OptionGroup();
        fileOrDirectory.addOption(file);
        fileOrDirectory.addOption(dir);
        options = new Options().addOptionGroup(fileOrDirectory);

        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapter = new OptionGroup();
        sectionOrChapter.addOption(section);
        sectionOrChapter.addOption(chapter);
        options.addOptionGroup(sectionOrChapter);

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExport = new OptionGroup();
        importOrExport.addOption(importOpt);
        importOrExport.addOption(exportOpt);
        options.addOptionGroup(importOrExport);

        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testTwoValidOptions() throws Exception {
        final String[] args = { "-r", "-f" };

        final CommandLine commandLine = parser.parse(options, args);

        assertTrue(commandLine.hasOption("r"), "Confirm -r is set");
        assertTrue(commandLine.hasOption("f"), "Confirm -f is set");
        assertFalse(commandLine.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(commandLine.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(commandLine.hasOption("c"), "Confirm -c is NOT set");
        assertTrue(commandLine.getArgList().isEmpty(), "Confirm no extra args");
    }
}
