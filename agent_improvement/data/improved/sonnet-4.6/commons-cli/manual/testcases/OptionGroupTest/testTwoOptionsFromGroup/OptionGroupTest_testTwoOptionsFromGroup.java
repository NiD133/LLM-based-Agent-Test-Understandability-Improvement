package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoOptionsFromGroup {

    private Options options;

    /** Deprecated PosixParser under test — kept for backwards-compat coverage. */
    private final Parser parser = new PosixParser();

    /**
     * Builds an Options instance with three mutually exclusive groups plus one
     * standalone option, matching the fixture used by the original test suite:
     *
     *   Group 1 (file vs directory): -f / -d
     *   Group 2 (section vs chapter): -s / -c
     *   Group 3 (import vs export):  --import / --export
     *   Standalone: -r / --revision
     */
    @BeforeEach
    public void setUp() {
        // Group 1: the user must choose between processing a file or a directory.
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir  = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirGroup = new OptionGroup();
        fileOrDirGroup.addOption(file);
        fileOrDirGroup.addOption(dir);
        options = new Options().addOptionGroup(fileOrDirGroup);

        // Group 2: the user must choose between processing a section or a chapter.
        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapterGroup = new OptionGroup();
        sectionOrChapterGroup.addOption(section);
        sectionOrChapterGroup.addOption(chapter);
        options.addOptionGroup(sectionOrChapterGroup);

        // Group 3: long-only options — the user must choose between import and export.
        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExportGroup = new OptionGroup();
        importOrExportGroup.addOption(importOpt);
        importOrExportGroup.addOption(exportOpt);
        options.addOptionGroup(importOrExportGroup);

        // Standalone option that belongs to no group (always allowed alongside group selections).
        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * Supplying both options from the same mutually exclusive group on the
     * command line must throw {@link AlreadySelectedException}.
     *
     * <p>With args {@code -f -d}:
     * <ul>
     *   <li>"-f" is processed first and recorded as the selected option in Group 1.</li>
     *   <li>"-d" conflicts with the already-selected "-f", triggering the exception.</li>
     * </ul>
     * The exception must expose the group that detected the conflict and the
     * conflicting option that was rejected.
     */
    @Test
    void testTwoOptionsFromGroup() throws Exception {
        // Both "-f" and "-d" belong to the same mutually exclusive group.
        final String[] args = { "-f", "-d" };

        final AlreadySelectedException e = assertThrows(
                AlreadySelectedException.class,
                () -> parser.parse(options, args),
                "Parsing two options from the same group must throw AlreadySelectedException");

        // The exception must reference the group that was violated.
        assertNotNull(e.getOptionGroup(), "null option group");

        // The group must have already been marked as selected (by "-f").
        assertTrue(e.getOptionGroup().isSelected());

        // The first option processed ("-f") must be recorded as selected.
        assertEquals("f", e.getOptionGroup().getSelected(), "selected option");

        // The conflicting option ("-d") must be the one reported in the exception.
        assertEquals("d", e.getOption().getOpt(), "option");
    }
}
