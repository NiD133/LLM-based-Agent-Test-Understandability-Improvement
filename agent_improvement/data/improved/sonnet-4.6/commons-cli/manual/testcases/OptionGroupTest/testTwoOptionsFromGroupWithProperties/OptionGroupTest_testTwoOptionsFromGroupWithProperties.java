package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that when a command-line argument explicitly selects one option from a mutually-exclusive
 * OptionGroup, a conflicting property default for another option in the same group is silently
 * ignored — i.e. the explicit argument wins and the property-supplied option is not activated.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoOptionsFromGroupWithProperties {

    private Options options;

    // PosixParser is deprecated but is the parser under test for this scenario
    private final Parser parser = new PosixParser();

    /**
     * Builds an Options instance that mirrors the fixture used by the original OptionGroupTest:
     *   - Group 1: -f/--file  vs  -d/--directory  (mutually exclusive)
     *   - Group 2: -s/--section  vs  -c/--chapter  (mutually exclusive)
     *   - Group 3: --import  vs  --export          (mutually exclusive, long-only)
     *   - Standalone: -r/--revision
     */
    @BeforeEach
    public void setUp() {
        // Group 1: file vs directory
        final Option fileOption = new Option("f", "file", false, "file to process");
        final Option directoryOption = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirectoryGroup = new OptionGroup();
        fileOrDirectoryGroup.addOption(fileOption);
        fileOrDirectoryGroup.addOption(directoryOption);
        options = new Options().addOptionGroup(fileOrDirectoryGroup);

        // Group 2: section vs chapter
        final Option sectionOption = new Option("s", "section", false, "section to process");
        final Option chapterOption = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapterGroup = new OptionGroup();
        sectionOrChapterGroup.addOption(sectionOption);
        sectionOrChapterGroup.addOption(chapterOption);
        options.addOptionGroup(sectionOrChapterGroup);

        // Group 3: import vs export (long options only, no short flag)
        final Option importOption = new Option(null, "import", false, "section to process");
        final Option exportOption = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExportGroup = new OptionGroup();
        importOrExportGroup.addOption(importOption);
        importOrExportGroup.addOption(exportOption);
        options.addOptionGroup(importOrExportGroup);

        // Standalone option not part of any group
        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * When the command line explicitly passes "-f" and a Properties map also supplies "d=true"
     * (which belongs to the same mutually-exclusive group as -f), the parser must honour the
     * explicit argument and ignore the property default.
     *
     * Expected outcome:
     *   - "-f" IS present in the parsed CommandLine.
     *   - "-d" is NOT present, because the property default is suppressed by the group constraint.
     */
    @Test
    void testTwoOptionsFromGroupWithProperties() throws Exception {
        // Arrange: explicit command-line argument selects "-f"
        final String[] commandLineArgs = { "-f" };

        // Arrange: property default attempts to also activate "-d" from the same group
        final Properties propertyDefaults = new Properties();
        propertyDefaults.put("d", "true");

        // Act: parse with both the explicit args and the property defaults
        final CommandLine parsedCommandLine = parser.parse(options, commandLineArgs, propertyDefaults);

        // Assert: the explicitly provided option "-f" is active
        assertTrue(parsedCommandLine.hasOption("f"),
                "Option '-f' should be set because it was explicitly provided on the command line");

        // Assert: the property-supplied option "-d" is NOT active because "-f" already selected
        //         from the same mutually-exclusive group
        assertFalse(parsedCommandLine.hasOption("d"),
                "Option '-d' should NOT be set even though 'd=true' was in properties, "
                + "because '-f' from the same OptionGroup was already selected");
    }
}
