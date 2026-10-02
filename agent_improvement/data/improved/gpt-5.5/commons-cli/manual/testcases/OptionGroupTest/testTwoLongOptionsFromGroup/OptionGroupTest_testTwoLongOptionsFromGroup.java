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

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option fileOption = new Option("f", "file", false, "file to process");
        final Option directoryOption = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileLocationGroup = new OptionGroup();
        fileLocationGroup.addOption(fileOption);
        fileLocationGroup.addOption(directoryOption);
        options = new Options().addOptionGroup(fileLocationGroup);

        final Option sectionOption = new Option("s", "section", false, "section to process");
        final Option chapterOption = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup documentPartGroup = new OptionGroup();
        documentPartGroup.addOption(sectionOption);
        documentPartGroup.addOption(chapterOption);
        options.addOptionGroup(documentPartGroup);

        final Option importOption = new Option(null, "import", false, "section to process");
        final Option exportOption = new Option(null, "export", false, "chapter to process");
        final OptionGroup transferDirectionGroup = new OptionGroup();
        transferDirectionGroup.addOption(importOption);
        transferDirectionGroup.addOption(exportOption);
        options.addOptionGroup(transferDirectionGroup);

        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testTwoLongOptionsFromGroup() throws Exception {
        final String[] conflictingFileLocationOptions = { "--file", "--directory" };

        final AlreadySelectedException exception = assertThrows(AlreadySelectedException.class,
                () -> parser.parse(options, conflictingFileLocationOptions));

        assertNotNull(exception.getOptionGroup(), "null option group");
        assertTrue(exception.getOptionGroup().isSelected());
        assertEquals("f", exception.getOptionGroup().getSelected(), "selected option");
        assertEquals("d", exception.getOption().getOpt(), "option");
    }
}
