package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that long-only options (options that have no short flag, only a long name
 * such as {@code --import} or {@code --export}) are correctly recognised by the
 * parser when they belong to a mutually exclusive {@link OptionGroup}.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testValidLongOnlyOptions {

    private Options options;

    private final Parser parser = new PosixParser();

    /**
     * Populates {@code options} with three mutually exclusive groups and one
     * standalone option:
     * <ol>
     *   <li>file / directory (short options -f / -d)</li>
     *   <li>section / chapter (short options -s / -c)</li>
     *   <li>import / export  (long-only – no short flag)</li>
     * </ol>
     * A standalone {@code --revision} option is also registered.
     */
    @BeforeEach
    public void setUp() {
        // Group 1: choose between a file or a directory to process
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDir = new OptionGroup();
        fileOrDir.addOption(file);
        fileOrDir.addOption(dir);
        options = new Options().addOptionGroup(fileOrDir);

        // Group 2: choose between a section or a chapter to process
        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapter = new OptionGroup();
        sectionOrChapter.addOption(section);
        sectionOrChapter.addOption(chapter);
        options.addOptionGroup(sectionOrChapter);

        // Group 3: long-only options (null short flag) — import vs export
        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExport = new OptionGroup();
        importOrExport.addOption(importOpt);
        importOrExport.addOption(exportOpt);
        options.addOptionGroup(importOrExport);

        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * Verifies that each long-only option is recognised on the command line and
     * recorded in the resulting {@link CommandLine} when used individually.
     */
    @Test
    void testValidLongOnlyOptions() throws Exception {
        // "--export" used alone should be parsed and set in the command line
        final CommandLine cl1 = parser.parse(options, new String[] { "--export" });
        assertTrue(cl1.hasOption("export"), "Confirm --export is set");

        // "--import" used alone should be parsed and set in the command line
        final CommandLine cl2 = parser.parse(options, new String[] { "--import" });
        assertTrue(cl2.hasOption("import"), "Confirm --import is set");
    }
}
