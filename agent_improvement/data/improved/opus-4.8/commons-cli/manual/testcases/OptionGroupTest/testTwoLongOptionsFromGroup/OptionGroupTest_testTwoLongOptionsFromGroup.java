package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link OptionGroup} enforces mutual exclusion: selecting two
 * options that belong to the same group must fail with an
 * {@link AlreadySelectedException}.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoLongOptionsFromGroup {

    private Options options;

    private final Parser parser = new PosixParser();

    /**
     * Builds an {@link Options} set containing three mutually exclusive groups
     * plus one standalone option:
     * <ul>
     *   <li>Group 1: {@code --file} (-f) / {@code --directory} (-d)</li>
     *   <li>Group 2: {@code --section} (-s) / {@code --chapter} (-c)</li>
     *   <li>Group 3: {@code --import} / {@code --export} (long only)</li>
     *   <li>Standalone: {@code --revision} (-r)</li>
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
                .addOptionGroup(importOrExport);
        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testTwoLongOptionsFromGroup() throws Exception {
        // Both options belong to the same group, so the second one is rejected.
        final String[] args = {"--file", "--directory"};

        final AlreadySelectedException exception =
                assertThrows(AlreadySelectedException.class, () -> parser.parse(options, args));

        assertNotNull(exception.getOptionGroup(), "null option group");
        assertTrue(exception.getOptionGroup().isSelected());
        assertEquals("f", exception.getOptionGroup().getSelected(), "selected option");
        assertEquals("d", exception.getOption().getOpt(), "option");
    }
}
