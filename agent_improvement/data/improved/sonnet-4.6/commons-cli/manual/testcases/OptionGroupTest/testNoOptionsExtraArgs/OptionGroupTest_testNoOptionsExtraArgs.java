package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that when no option flags are supplied on the command line, none of the options
 * from any option group are selected and all positional arguments are captured as extra args.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testNoOptionsExtraArgs {

    private Options options;

    private final Parser parser = new PosixParser();

    /**
     * Builds a shared {@link Options} instance containing three mutually-exclusive option groups
     * (file/directory, section/chapter, import/export) plus a standalone revision option.
     */
    @BeforeEach
    public void setUp() {
        // Group 1: choose between processing a file (-f) or a directory (-d)
        final OptionGroup fileOrDirGroup = new OptionGroup();
        fileOrDirGroup.addOption(new Option("f", "file",      false, "file to process"));
        fileOrDirGroup.addOption(new Option("d", "directory", false, "directory to process"));

        // Group 2: choose between processing a section (-s) or a chapter (-c)
        final OptionGroup sectionOrChapterGroup = new OptionGroup();
        sectionOrChapterGroup.addOption(new Option("s", "section", false, "section to process"));
        sectionOrChapterGroup.addOption(new Option("c", "chapter", false, "chapter to process"));

        // Group 3: choose between --import or --export (long-only options, no short flag)
        final OptionGroup importOrExportGroup = new OptionGroup();
        importOrExportGroup.addOption(new Option(null, "import", false, "section to process"));
        importOrExportGroup.addOption(new Option(null, "export", false, "chapter to process"));

        options = new Options()
                .addOptionGroup(fileOrDirGroup)
                .addOptionGroup(sectionOrChapterGroup)
                .addOptionGroup(importOrExportGroup);

        // Standalone option: print a revision number (-r / --revision)
        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * When the command line contains only positional arguments (no option flags), every defined
     * option must remain unset and both positional tokens must appear in the extra-args list.
     */
    @Test
    void testNoOptionsExtraArgs() throws Exception {
        final String[] args = {"arg1", "arg2"};

        final CommandLine cl = parser.parse(options, args);

        assertFalse(cl.hasOption("r"), "Confirm -r is NOT set");
        assertFalse(cl.hasOption("f"), "Confirm -f is NOT set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertEquals(2, cl.getArgList().size(), "Confirm TWO extra args");
    }
}
