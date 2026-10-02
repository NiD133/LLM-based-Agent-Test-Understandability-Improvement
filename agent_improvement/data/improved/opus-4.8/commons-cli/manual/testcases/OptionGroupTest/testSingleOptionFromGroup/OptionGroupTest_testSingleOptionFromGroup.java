package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that when a single option is supplied on the command line, only that
 * option is reported as set and no other options (whether in a group or not) are
 * mistakenly reported as set.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testSingleOptionFromGroup {

    /** The set of options to parse against, built fresh before each test. */
    private Options options;

    /** Parser used to interpret the command-line arguments. */
    private final Parser parser = new PosixParser();

    /**
     * Builds an {@link Options} instance containing three mutually exclusive
     * option groups plus one standalone option:
     * <ul>
     *   <li>group 1: -f/--file, -d/--directory</li>
     *   <li>group 2: -s/--section, -c/--chapter</li>
     *   <li>group 3: --import, --export (long options only)</li>
     *   <li>standalone: -r/--revision</li>
     * </ul>
     */
    @BeforeEach
    public void setUp() {
        final OptionGroup fileOrDirectory = new OptionGroup()
                .addOption(new Option("f", "file", false, "file to process"))
                .addOption(new Option("d", "directory", false, "directory to process"));

        final OptionGroup sectionOrChapter = new OptionGroup()
                .addOption(new Option("s", "section", false, "section to process"))
                .addOption(new Option("c", "chapter", false, "chapter to process"));

        final OptionGroup importOrExport = new OptionGroup()
                .addOption(new Option(null, "import", false, "section to process"))
                .addOption(new Option(null, "export", false, "chapter to process"));

        options = new Options()
                .addOptionGroup(fileOrDirectory)
                .addOptionGroup(sectionOrChapter)
                .addOptionGroup(importOrExport)
                .addOption("r", "revision", false, "revision number");
    }

    @Test
    void testSingleOptionFromGroup() throws Exception {
        final CommandLine cl = parser.parse(options, new String[] { "-f" });

        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertFalse(cl.hasOption("r"), "Confirm -r is NOT set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }
}
