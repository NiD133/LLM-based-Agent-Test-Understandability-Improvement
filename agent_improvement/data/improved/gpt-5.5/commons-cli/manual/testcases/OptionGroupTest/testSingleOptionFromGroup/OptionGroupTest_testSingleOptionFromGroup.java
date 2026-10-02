package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testSingleOptionFromGroup {

    private static final String FILE_OPTION = "f";
    private static final String DIRECTORY_OPTION = "d";
    private static final String SECTION_OPTION = "s";
    private static final String CHAPTER_OPTION = "c";
    private static final String REVISION_OPTION = "r";

    private final Parser parser = new PosixParser();

    private Options options;

    @BeforeEach
    public void setUp() {
        final Option file = new Option(FILE_OPTION, "file", false, "file to process");
        final Option dir = new Option(DIRECTORY_OPTION, "directory", false, "directory to process");
        options = new Options().addOptionGroup(optionGroup(file, dir));

        final Option section = new Option(SECTION_OPTION, "section", false, "section to process");
        final Option chapter = new Option(CHAPTER_OPTION, "chapter", false, "chapter to process");
        options.addOptionGroup(optionGroup(section, chapter));

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        options.addOptionGroup(optionGroup(importOpt, exportOpt));

        options.addOption(REVISION_OPTION, "revision", false, "revision number");
    }

    @Test
    void testSingleOptionFromGroup() throws Exception {
        final String[] args = { "-f" };

        final CommandLine cl = parser.parse(options, args);

        assertFalse(cl.hasOption(REVISION_OPTION), "Confirm -r is NOT set");
        assertTrue(cl.hasOption(FILE_OPTION), "Confirm -f is set");
        assertFalse(cl.hasOption(DIRECTORY_OPTION), "Confirm -d is NOT set");
        assertFalse(cl.hasOption(SECTION_OPTION), "Confirm -s is NOT set");
        assertFalse(cl.hasOption(CHAPTER_OPTION), "Confirm -c is NOT set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    private OptionGroup optionGroup(final Option firstOption, final Option secondOption) {
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        return optionGroup;
    }
}
