package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that, when a command line contains only positional arguments and no
 * options at all, the parser leaves every option unset and collects the
 * positional arguments as "extra" (unparsed) arguments.
 *
 * <p>Exercises some deprecated parser classes.</p>
 */
@SuppressWarnings("deprecation")
public class OptionGroupTest_testNoOptionsExtraArgs {

    private Options options;

    private final Parser parser = new PosixParser();

    /**
     * Builds an {@link Options} definition containing three mutually exclusive
     * option groups plus a single standalone option:
     * <ul>
     *   <li>group 1: {@code -f/--file} or {@code -d/--directory}</li>
     *   <li>group 2: {@code -s/--section} or {@code -c/--chapter}</li>
     *   <li>group 3: {@code --import} or {@code --export} (long options only)</li>
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
                .addOption(new Option(null, "import", false, "section to process"))
                .addOption(new Option(null, "export", false, "chapter to process"));

        options = new Options()
                .addOptionGroup(fileOrDirectory)
                .addOptionGroup(sectionOrChapter)
                .addOptionGroup(importOrExport)
                .addOption("r", "revision", false, "revision number");
    }

    @Test
    void testNoOptionsExtraArgs() throws Exception {
        final String[] argsWithoutOptions = { "arg1", "arg2" };

        final CommandLine cl = parser.parse(options, argsWithoutOptions);

        assertFalse(cl.hasOption("r"), "Confirm -r is NOT set");
        assertFalse(cl.hasOption("f"), "Confirm -f is NOT set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertEquals(2, cl.getArgList().size(), "Confirm TWO extra args");
    }
}
