package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that selecting an option by its long name (e.g. {@code --file}) is
 * recognized by the parser and that no other options in the configuration are
 * mistakenly reported as set.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testSingleLongOption {

    /** Options configuration shared by the test, rebuilt before each run. */
    private Options options;

    private final Parser parser = new PosixParser();

    /**
     * Builds an {@link Options} instance containing three mutually exclusive
     * groups plus one standalone option:
     * <ul>
     *   <li>group 1: {@code -f/--file} or {@code -d/--directory}</li>
     *   <li>group 2: {@code -s/--section} or {@code -c/--chapter}</li>
     *   <li>group 3: {@code --import} or {@code --export} (long name only)</li>
     *   <li>standalone: {@code -r/--revision}</li>
     * </ul>
     */
    @BeforeEach
    public void setUp() {
        options = new Options();

        // Group 1: file vs. directory.
        options.addOptionGroup(newGroup(
            new Option("f", "file", false, "file to process"),
            new Option("d", "directory", false, "directory to process")));

        // Group 2: section vs. chapter.
        options.addOptionGroup(newGroup(
            new Option("s", "section", false, "section to process"),
            new Option("c", "chapter", false, "chapter to process")));

        // Group 3: import vs. export, both addressable only by long name.
        options.addOptionGroup(newGroup(
            new Option(null, "import", false, "section to process"),
            new Option(null, "export", false, "chapter to process")));

        // Standalone option, not part of any group.
        options.addOption("r", "revision", false, "revision number");
    }

    /** Creates an {@link OptionGroup} containing the two given options. */
    private static OptionGroup newGroup(final Option first, final Option second) {
        final OptionGroup group = new OptionGroup();
        group.addOption(first);
        group.addOption(second);
        return group;
    }

    @Test
    void testSingleLongOption() throws Exception {
        final String[] args = { "--file" };

        final CommandLine cl = parser.parse(options, args);

        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertFalse(cl.hasOption("r"), "Confirm -r is NOT set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }
}
