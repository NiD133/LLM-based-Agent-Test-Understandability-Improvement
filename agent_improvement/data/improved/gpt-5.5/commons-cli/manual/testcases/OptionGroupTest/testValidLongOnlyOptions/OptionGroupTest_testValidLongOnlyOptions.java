package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testValidLongOnlyOptions {

    private static final String LONG_EXPORT = "export";
    private static final String LONG_IMPORT = "import";

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option file = new Option("f", "file", false, "file to process");
        final Option directory = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileSelection = new OptionGroup();
        fileSelection.addOption(file);
        fileSelection.addOption(directory);
        options = new Options().addOptionGroup(fileSelection);

        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup contentSelection = new OptionGroup();
        contentSelection.addOption(section);
        contentSelection.addOption(chapter);
        options.addOptionGroup(contentSelection);

        final Option importOpt = new Option(null, LONG_IMPORT, false, "section to process");
        final Option exportOpt = new Option(null, LONG_EXPORT, false, "chapter to process");
        final OptionGroup longOnlySelection = new OptionGroup();
        longOnlySelection.addOption(importOpt);
        longOnlySelection.addOption(exportOpt);
        options.addOptionGroup(longOnlySelection);

        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testValidLongOnlyOptions() throws Exception {
        final CommandLine exportCommand = parser.parse(options, new String[] { "--export" });
        assertTrue(exportCommand.hasOption(LONG_EXPORT), "Confirm --export is set");

        final CommandLine importCommand = parser.parse(options, new String[] { "--import" });
        assertTrue(importCommand.hasOption(LONG_IMPORT), "Confirm --import is set");
    }
}
