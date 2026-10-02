package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoLongOptionsFromGroup {

    private Options options;

    // PosixParser is the parser under test; it enforces mutually-exclusive option groups
    private final Parser parser = new PosixParser();

    /**
     * Builds an Options instance with three mutually-exclusive groups plus one standalone option:
     *   Group 1: --file (-f) | --directory (-d)
     *   Group 2: --section (-s) | --chapter (-c)
     *   Group 3: --import | --export   (long-only options, no short form)
     *   Standalone: --revision (-r)
     */
    @BeforeEach
    public void setUp() {
        // Group 1: file vs directory
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir  = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirGroup = new OptionGroup();
        fileOrDirGroup.addOption(file);
        fileOrDirGroup.addOption(dir);
        options = new Options().addOptionGroup(fileOrDirGroup);

        // Group 2: section vs chapter
        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapterGroup = new OptionGroup();
        sectionOrChapterGroup.addOption(section);
        sectionOrChapterGroup.addOption(chapter);
        options.addOptionGroup(sectionOrChapterGroup);

        // Group 3: import vs export (long-only, no short opt)
        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExportGroup = new OptionGroup();
        importOrExportGroup.addOption(importOpt);
        importOrExportGroup.addOption(exportOpt);
        options.addOptionGroup(importOrExportGroup);

        // Standalone option that does not belong to any group
        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * Providing both long-form options from the same mutually-exclusive group
     * (--file then --directory) must throw AlreadySelectedException.
     * The exception must report:
     *   - the option group in which the conflict occurred (non-null, already selected)
     *   - "f" as the first-selected option (from --file)
     *   - "d" as the conflicting option that triggered the exception (from --directory)
     */
    @Test
    void testTwoLongOptionsFromGroup() throws Exception {
        final String[] args = {"--file", "--directory"};

        final AlreadySelectedException exception =
                assertThrows(AlreadySelectedException.class, () -> parser.parse(options, args));

        final OptionGroup conflictingGroup = exception.getOptionGroup();
        assertNotNull(conflictingGroup, "The exception must carry the option group in which the conflict occurred");
        assertTrue(conflictingGroup.isSelected(), "The group must be marked as already having a selection");
        assertEquals("f", conflictingGroup.getSelected(), "The first selected option in the group should be 'f' (--file)");
        assertEquals("d", exception.getOption().getOpt(), "The option that caused the conflict should be 'd' (--directory)");
    }
}
