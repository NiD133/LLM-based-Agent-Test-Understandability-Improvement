package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testSingleOptionFromGroup {

    private Options options;

    // PosixParser is deprecated but required for this test scenario
    private final Parser parser = new PosixParser();

    /**
     * Builds an Options instance with three mutually-exclusive option groups plus
     * one standalone option, so that the test can confirm only the selected option
     * is recognised and all others remain unset.
     *
     * <ul>
     *   <li>Group 1: -f/--file  vs  -d/--directory</li>
     *   <li>Group 2: -s/--section  vs  -c/--chapter</li>
     *   <li>Group 3: --import  vs  --export  (long-only options)</li>
     *   <li>Standalone: -r/--revision</li>
     * </ul>
     */
    @BeforeEach
    public void setUp() {
        final Option file      = new Option("f", "file",      false, "file to process");
        final Option dir       = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDir = new OptionGroup();
        fileOrDir.addOption(file);
        fileOrDir.addOption(dir);

        final Option section   = new Option("s", "section", false, "section to process");
        final Option chapter   = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapter = new OptionGroup();
        sectionOrChapter.addOption(section);
        sectionOrChapter.addOption(chapter);

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExport = new OptionGroup();
        importOrExport.addOption(importOpt);
        importOrExport.addOption(exportOpt);

        options = new Options()
                .addOptionGroup(fileOrDir)
                .addOptionGroup(sectionOrChapter)
                .addOptionGroup(importOrExport);
        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * Passing only "-f" should set the file option and leave every other option
     * (including sibling group members and the standalone -r flag) unset.
     */
    @Test
    void testSingleOptionFromGroup() throws Exception {
        final String[] args = { "-f" };
        final CommandLine cl = parser.parse(options, args);

        assertTrue(cl.hasOption("f"),  "Confirm -f is set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertFalse(cl.hasOption("r"), "Confirm -r is NOT set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }
}
