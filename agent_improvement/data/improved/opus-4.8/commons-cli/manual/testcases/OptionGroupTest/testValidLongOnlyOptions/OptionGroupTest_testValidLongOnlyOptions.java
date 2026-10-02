package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the parser accepts options that can only be supplied through
 * their long form (that is, options that have no short name).
 *
 * <p>The {@code --import} and {@code --export} options below are declared with a
 * {@code null} short name, so they are reachable on the command line only via
 * {@code --import} / {@code --export}.</p>
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testValidLongOnlyOptions {

    /** No short name: these options exist only in their long form. */
    private static final String NO_SHORT_NAME = null;

    private Options options;

    private final Parser parser = new PosixParser();

    /**
     * Builds an {@link Options} set containing three mutually exclusive groups
     * plus one standalone option:
     * <ul>
     *   <li>group 1: {@code -f/--file}, {@code -d/--directory}</li>
     *   <li>group 2: {@code -s/--section}, {@code -c/--chapter}</li>
     *   <li>group 3: {@code --import}, {@code --export} (long form only)</li>
     *   <li>standalone: {@code -r/--revision}</li>
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
                .addOption(new Option(NO_SHORT_NAME, "import", false, "section to process"))
                .addOption(new Option(NO_SHORT_NAME, "export", false, "chapter to process"));

        options = new Options()
                .addOptionGroup(fileOrDirectory)
                .addOptionGroup(sectionOrChapter)
                .addOptionGroup(importOrExport);
        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testValidLongOnlyOptions() throws Exception {
        final CommandLine exportCmd = parser.parse(options, new String[] { "--export" });
        assertTrue(exportCmd.hasOption("export"), "Confirm --export is set");

        final CommandLine importCmd = parser.parse(options, new String[] { "--import" });
        assertTrue(importCmd.hasOption("import"), "Confirm --import is set");
    }
}
