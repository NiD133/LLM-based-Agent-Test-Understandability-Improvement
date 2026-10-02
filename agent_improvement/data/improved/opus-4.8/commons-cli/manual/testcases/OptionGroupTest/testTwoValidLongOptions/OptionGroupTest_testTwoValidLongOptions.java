package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a command line may select one long option from each of several
 * mutually exclusive {@link OptionGroup}s, plus a standalone option, without conflict.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoValidLongOptions {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        options = new Options();

        // Group 1: choose a target to process — file (-f) XOR directory (-d).
        final OptionGroup fileOrDirectory = new OptionGroup();
        fileOrDirectory.addOption(new Option("f", "file", false, "file to process"));
        fileOrDirectory.addOption(new Option("d", "directory", false, "directory to process"));
        options.addOptionGroup(fileOrDirectory);

        // Group 2: choose a scope — section (-s) XOR chapter (-c).
        final OptionGroup sectionOrChapter = new OptionGroup();
        sectionOrChapter.addOption(new Option("s", "section", false, "section to process"));
        sectionOrChapter.addOption(new Option("c", "chapter", false, "chapter to process"));
        options.addOptionGroup(sectionOrChapter);

        // Group 3: choose a direction — import XOR export (long options only).
        final OptionGroup importOrExport = new OptionGroup();
        importOrExport.addOption(new Option(null, "import", false, "section to process"));
        importOrExport.addOption(new Option(null, "export", false, "chapter to process"));
        options.addOptionGroup(importOrExport);

        // Standalone option, not part of any group.
        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testTwoValidLongOptions() throws Exception {
        // Select the standalone --revision and --file (one option from group 1).
        final String[] args = { "--revision", "--file" };

        final CommandLine cl = parser.parse(options, args);

        // The two selected options are present...
        assertTrue(cl.hasOption("r"), "Confirm -r is set");
        assertTrue(cl.hasOption("f"), "Confirm -f is set");

        // ...while every unselected option (including -f's group sibling -d) stays unset.
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");

        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }
}
