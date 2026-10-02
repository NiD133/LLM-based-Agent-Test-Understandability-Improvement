package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that selecting two options that belong to the same
 * {@link OptionGroup} causes the parser to fail.
 *
 * <p>This test exercises some deprecated classes (e.g. {@link Parser},
 * {@link PosixParser}).</p>
 */
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoOptionsFromGroup {

    /** Parser under test; PosixParser is a concrete deprecated Parser implementation. */
    private final Parser parser = new PosixParser();

    /** Option set built in {@link #setUp()} and shared by the test. */
    private Options options;

    @BeforeEach
    public void setUp() {
        // Group 1: "file" (-f) and "directory" (-d) are mutually exclusive.
        final OptionGroup fileOrDirectory = new OptionGroup();
        fileOrDirectory.addOption(new Option("f", "file", false, "file to process"));
        fileOrDirectory.addOption(new Option("d", "directory", false, "directory to process"));

        // Group 2: "section" (-s) and "chapter" (-c) are mutually exclusive.
        final OptionGroup sectionOrChapter = new OptionGroup();
        sectionOrChapter.addOption(new Option("s", "section", false, "section to process"));
        sectionOrChapter.addOption(new Option("c", "chapter", false, "chapter to process"));

        // Group 3: long-only "--import" and "--export" are mutually exclusive.
        final OptionGroup importOrExport = new OptionGroup();
        importOrExport.addOption(new Option(null, "import", false, "section to process"));
        importOrExport.addOption(new Option(null, "export", false, "chapter to process"));

        options = new Options()
                .addOptionGroup(fileOrDirectory)
                .addOptionGroup(sectionOrChapter)
                .addOptionGroup(importOrExport)
                .addOption("r", "revision", false, "revision number");
    }

    @Test
    void testTwoOptionsFromGroup() throws Exception {
        // -f and -d belong to the same group, so selecting both is illegal.
        final String[] args = { "-f", "-d" };

        final AlreadySelectedException exception =
                assertThrows(AlreadySelectedException.class, () -> parser.parse(options, args));

        // The exception should report the conflicting group with -f already selected...
        assertNotNull(exception.getOptionGroup(), "null option group");
        assertTrue(exception.getOptionGroup().isSelected());
        assertEquals("f", exception.getOptionGroup().getSelected(), "selected option");

        // ...and the offending option being -d.
        assertEquals("d", exception.getOption().getOpt(), "option");
    }
}
