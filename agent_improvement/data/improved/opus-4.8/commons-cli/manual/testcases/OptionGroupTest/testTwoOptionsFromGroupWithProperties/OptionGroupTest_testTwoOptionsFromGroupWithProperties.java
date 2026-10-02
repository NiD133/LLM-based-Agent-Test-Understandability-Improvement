package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that when two options belonging to the <em>same</em> mutually exclusive
 * {@link OptionGroup} are supplied through different channels (one on the command
 * line, one via {@link Properties}), the command-line option wins and the property
 * option is ignored.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoOptionsFromGroupWithProperties {

    private Options options;

    private final Parser parser = new PosixParser();

    /**
     * Builds an {@link Options} instance with three mutually exclusive groups plus a
     * standalone option. The first group ({@code -f} / {@code -d}) is the focus of
     * this test; the remaining groups and the standalone option are present only to
     * mirror a realistic configuration.
     */
    @BeforeEach
    public void setUp() {
        // Group 1: file vs. directory  -- exercised by this test.
        final OptionGroup fileOrDirectory = new OptionGroup();
        fileOrDirectory.addOption(new Option("f", "file", false, "file to process"));
        fileOrDirectory.addOption(new Option("d", "directory", false, "directory to process"));
        options = new Options().addOptionGroup(fileOrDirectory);

        // Group 2: section vs. chapter.
        final OptionGroup sectionOrChapter = new OptionGroup();
        sectionOrChapter.addOption(new Option("s", "section", false, "section to process"));
        sectionOrChapter.addOption(new Option("c", "chapter", false, "chapter to process"));
        options.addOptionGroup(sectionOrChapter);

        // Group 3: import vs. export (long-option only).
        final OptionGroup importOrExport = new OptionGroup();
        importOrExport.addOption(new Option(null, "import", false, "section to process"));
        importOrExport.addOption(new Option(null, "export", false, "chapter to process"));
        options.addOptionGroup(importOrExport);

        // A standalone option, not part of any group.
        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testTwoOptionsFromGroupWithProperties() throws Exception {
        // "-f" is selected on the command line.
        final String[] commandLineArgs = { "-f" };

        // "-d" is requested via properties, but it belongs to the same group as "-f".
        final Properties properties = new Properties();
        properties.put("d", "true");

        final CommandLine commandLine = parser.parse(options, commandLineArgs, properties);

        // The command-line option takes precedence; its group-mate from the
        // properties is suppressed because a group allows only one selection.
        assertTrue(commandLine.hasOption("f"), "command-line option -f should be selected");
        assertFalse(commandLine.hasOption("d"), "-d should be ignored: it conflicts with -f in the same group");
    }
}
