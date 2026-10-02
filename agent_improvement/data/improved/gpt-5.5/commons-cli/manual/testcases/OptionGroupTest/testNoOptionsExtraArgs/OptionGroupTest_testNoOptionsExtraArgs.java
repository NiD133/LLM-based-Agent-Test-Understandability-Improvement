package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testNoOptionsExtraArgs {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirectory = mutuallyExclusiveGroup(file, dir);
        options = new Options().addOptionGroup(fileOrDirectory);

        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapter = mutuallyExclusiveGroup(section, chapter);
        options.addOptionGroup(sectionOrChapter);

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExport = mutuallyExclusiveGroup(importOpt, exportOpt);
        options.addOptionGroup(importOrExport);

        options.addOption("r", "revision", false, "revision number");
    }

    private OptionGroup mutuallyExclusiveGroup(final Option firstOption, final Option secondOption) {
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        return optionGroup;
    }

    @Test
    void testNoOptionsExtraArgs() throws Exception {
        final String[] args = { "arg1", "arg2" };

        final CommandLine commandLine = parser.parse(options, args);

        assertFalse(commandLine.hasOption("r"), "Confirm -r is NOT set");
        assertFalse(commandLine.hasOption("f"), "Confirm -f is NOT set");
        assertFalse(commandLine.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(commandLine.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(commandLine.hasOption("c"), "Confirm -c is NOT set");
        assertEquals(2, commandLine.getArgList().size(), "Confirm TWO extra args");
    }
}
